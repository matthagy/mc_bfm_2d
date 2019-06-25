package com.matthagy.mc_bfm_2d.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Configuration {

    private final Grid grid;
    private final List<Polymer> polymers;
    private final List<Occlusion> occlusions;

    public Configuration(int width, int height) {
        this.grid = new Grid(width, height);
        this.polymers = new ArrayList<>();
        this.occlusions = new ArrayList<>();
    }

    public Grid getGrid() {
        return grid;
    }

    public List<Polymer> getPolymers() {
        return Collections.unmodifiableList(polymers);
    }

    public List<Occlusion> getOcclusions() {
        return Collections.unmodifiableList(occlusions);
    }

    public void addPolymer(Polymer polymer) {
        for (Monomer monomer : polymer.getMonomers()) {
            grid.addOccupier(monomer);
        }
        polymers.add(polymer);
    }

    public void addOcclusion(Occlusion occlusion) {
        grid.addOccupier(occlusion);
    }
}
