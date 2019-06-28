package com.matthagy.mc_bfm_2d.state;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Monomer implements GridOccupier {

    private final Vector lowerRightOccupancy;
    private final List<Bond> bonds;

    public Monomer(Vector lowerRightOccupancy) {
        this.lowerRightOccupancy = lowerRightOccupancy;
        bonds = new ArrayList<>();
    }

    public Vector getLowerRightOccupancy() {
        return lowerRightOccupancy;
    }

    public List<Bond> getBonds() {
        return Collections.unmodifiableList(bonds);
    }

    public Bond bond(Monomer other) {
        Bond bond = new Bond(this, other);
        bonds.add(bond);
        other.bonds.add(bond);
        return bond;
    }

    private static final VectorList OFFSETS = new VectorList(
            Lists.newArrayList(
                    new Vector(0, 0),
                    new Vector(0, 1),
                    new Vector(1, 0),
                    new Vector(1, 1)
            )
    );

    /**
     * Note this method doesn't apply periodic boundary conditions
     */
    @Override
    public VectorList getOccupationSites() {
        return OFFSETS.add(lowerRightOccupancy);
    }

    @Override
    public String toString() {
        return "Monomer{" +
                "lowerRightOccupancy=" + lowerRightOccupancy +
                ", bonds=" + bonds.size() +
                '}';
    }
}
