package com.zivkesten.trashdump.models;


import java.util.ArrayList;
import java.util.List;

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