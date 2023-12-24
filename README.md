
# 🌟 TrashDump: The Effective Janitor Solution 🌟

### Solving the Effective Janitor Problem with Android

---

## Overview
TrashDump is an sample Android application designed to Solve the Effective Janitor Problem with Android, with only JAVA

## 📚 Documentation

Main componnents used for this project

- `TrashBag`:
```
public class TrashBag {
    private final double weight;

    public TrashBag(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
```

- `Trip`: 
```
public class Trip {
    private List<TrashBag> bags;

    public Trip() {
        bags = new ArrayList<>();
    }

    public List<TrashBag> getBags() {
        return bags;
    }

    public void addBag(TrashBag bag) {
        bags.add(bag);
    }
}
```

- `TripOptimizer`:
```
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
```
---

