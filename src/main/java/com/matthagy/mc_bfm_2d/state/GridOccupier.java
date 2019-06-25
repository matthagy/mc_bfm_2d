package com.matthagy.mc_bfm_2d.state;

public interface GridOccupier {

    /**
     * Note this method doesn't apply periodic boundary conditions
     */
    VectorList getOccupationSites();
}
