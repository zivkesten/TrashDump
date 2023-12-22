package com.zivkesten.trashdump.optimizer;

import com.zivkesten.trashdump.models.TrashBag;
import com.zivkesten.trashdump.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripOptimizer {
    public static List<Trip> calculateMinimalTrips(List<TrashBag> trashBags) {
        List<Trip> trips = new ArrayList<>();
        List<TrashBag> remainingBags = new ArrayList<>(trashBags);

        remainingBags.sort((bag1, bag2) -> Double.compare(bag2.getWeight(), bag1.getWeight()));

        while (!remainingBags.isEmpty()) {
            Trip trip = new Trip(); // must declare?
            double currentWeight = 0;

            for (int i = 0; i < remainingBags.size(); i++) {
                TrashBag bag = remainingBags.get(i);

                if (currentWeight + bag.getWeight() <= 3.0) {
                    trip.addBag(bag);
                    currentWeight += bag.getWeight();
                    remainingBags.remove(i); // this is expensive maybe?
                    i--;
                } else {
                    break;
                }
            }

            trips.add(trip);
        }

        return trips;
    }
}
