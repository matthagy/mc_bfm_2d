package com.matthagy.mc_bfm_2d.simulation;

import com.google.common.collect.Lists;
import com.matthagy.mc_bfm_2d.state.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Propagator {

    public static final int MAX_SQUARE_BOND_LENGTH = 16;
    private final Configuration configuration;
    private final Random random;

    private final List<Monomer> monomers;

    public Propagator(Configuration configuration, Random random) {
        this.configuration = configuration;
        this.random = random;

        monomers = configuration.getPolymers().stream()
                .flatMap(p -> p.getMonomers().stream())
                .collect(Collectors.toList());
    }

    private static List<Vector> OFFSETS = Collections.unmodifiableList(
            Lists.newArrayList(
                    new Vector(0, 1),
                    new Vector(1, 0),
                    new Vector(0, -1),
                    new Vector(-1, 0)
            ));

    public boolean propagate() {
        Grid grid = configuration.getGrid();

        Monomer monomer = monomers.get(random.nextInt(monomers.size()));
        Vector offset = OFFSETS.get(random.nextInt(OFFSETS.size()));
        Vector oldLowerRightOccupancy = monomer.getLowerRightOccupancy().copy();
        Vector newLowerRightOccupancy = grid.periodize(oldLowerRightOccupancy.add(offset));

        boolean invalidBond = checkBondLengths(monomer, grid, newLowerRightOccupancy);
        if (invalidBond) {
            return false;
        }

        grid.removeOccupier(monomer);
        monomer.getLowerRightOccupancy().set(newLowerRightOccupancy);
        boolean collision = checkCollisions(monomer, grid);
        if (collision) {
            monomer.getLowerRightOccupancy().set(oldLowerRightOccupancy);
            grid.addOccupier(monomer);
            return false;
        }

        grid.addOccupier(monomer);
        return true;
    }

    private boolean checkBondLengths(Monomer monomer, Grid grid, Vector newLowerRightOccupancy) {
        for (Bond bond : monomer.getBonds()) {
            Monomer other = bond.other(monomer);
            int squareBondLength = grid.squareDistance(newLowerRightOccupancy, other.getLowerRightOccupancy());
            if (squareBondLength > MAX_SQUARE_BOND_LENGTH) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCollisions(Monomer monomer, Grid grid) {
        for (Vector occupationSite : monomer.getOccupationSites()) {
            if (grid.isSiteOccupied(occupationSite.getX(), occupationSite.getY())) {
                return true;
            }
        }
        return false;
    }
}
