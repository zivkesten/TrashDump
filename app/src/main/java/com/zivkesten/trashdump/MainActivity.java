package com.zivkesten.trashdump;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.zivkesten.trashdump.models.TrashBag;
import com.zivkesten.trashdump.models.Trip;
import com.zivkesten.trashdump.optimizer.TripOptimizer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout bagsContainer;
    private EditText editTextWeight;
    private TextView results;

    private final List<TrashBag> bags = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView mainView = findViewById(R.id.main_view);
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainView.addView(mainLayout);

        bagsContainer = new LinearLayout(this);
        bagsContainer.setOrientation(LinearLayout.VERTICAL);

        editTextWeight = new EditText(this);
        editTextWeight.setHint("Enter bag weight (1.01-3.0 kg)");
        editTextWeight.setImeOptions(EditorInfo.IME_ACTION_DONE);

        editTextWeight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Button btnAddBag = new Button(this);
        btnAddBag.setText("Add Bag");

        LinearLayout inputView = new LinearLayout(this);
        inputView.setOrientation(LinearLayout.VERTICAL);
        inputView.addView(editTextWeight);
        inputView.addView(btnAddBag);
        inputView.addView(bagsContainer);
        mainLayout.addView(inputView);

        Button btnCalculate = new Button(this);
        btnCalculate.setText("Calculate Trips");

        results = new TextView(this);

        mainLayout.addView(btnCalculate);
        mainLayout.addView(results);

        btnAddBag.setOnClickListener(view -> addBag());

        btnCalculate.setOnClickListener(view -> calculateTrips());
    }

    private void addBag() {
        if (results.getText().length() > 0) { results.setText(""); }
        String weightStr = editTextWeight.getText().toString().trim();
        if (!weightStr.isEmpty()) {
            double weight = Double.parseDouble(weightStr);
            if (weight >= 1.01 && weight <= 3.0) {
                bags.add(new TrashBag(weight));
                TextView newBagView = new TextView(this);
                newBagView.setText(String.valueOf(weight));
                bagsContainer.addView(newBagView);
                editTextWeight.setText("");
            } else {
                editTextWeight.setError("Invalid weight (1.01-3.0 kg)");
            }
        }
    }

    private void calculateTrips() {
        bagsContainer.removeAllViews();
        List<Trip> trips = TripOptimizer.calculateMinimalTrips(bags);
        bags.clear();
        results.setText(buildResultOutput(trips));
    }

    private String buildResultOutput(List<Trip> trips) {
        StringBuilder bagOrder = new StringBuilder();
        for (int i = 1; i < trips.size()-1; i++) {
            Trip trip = trips.get(i);
            bagOrder
                    .append("Trip number ")
                    .append(i)
                    .append(" with ")
                    .append(trip.getBags().size())
                    .append(" bags")
                    .append("\n");

            for (int j = 0; j < trip.getBags().size() ; j++) {
                TrashBag bag = trip.getBags().get(j);
                bagOrder
                        .append("Bag number ")
                        .append(j+1)
                        .append(" has a weight of ")
                        .append(bag.getWeight())
                        .append("Kg")
                        .append("\n");
            }
        }
        return bagOrder.toString();
    }
}
