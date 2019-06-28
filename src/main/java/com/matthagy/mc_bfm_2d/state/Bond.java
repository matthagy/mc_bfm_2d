package com.matthagy.mc_bfm_2d.state;

public class Bond {

    private final Monomer a;
    private final Monomer b;

    public Bond(Monomer a, Monomer b) {
        this.a = a;
        this.b = b;
    }

    public Monomer getA() {
        return a;
    }

    public Monomer getB() {
        return b;
    }

    public Monomer other(Monomer m) {
        if (m == a) {
            return b;
        } else if (m == b) {
            return a;
        } else {
            throw new IllegalArgumentException("Monomer not in bond");
        }
    }
}
