package com.matthagy.mc_bfm_2d.state;

import java.util.Collections;
import java.util.List;

public class Polymer {

    private final List<Monomer> monomers;

    public Polymer(List<Monomer> monomers) {
        this.monomers = monomers;
    }

    public List<Monomer> getMonomers() {
        return Collections.unmodifiableList(monomers);
    }
}
