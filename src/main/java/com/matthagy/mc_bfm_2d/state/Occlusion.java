package com.matthagy.mc_bfm_2d.state;

public class Occlusion implements GridOccupier {

    private final VectorList sites;

    public Occlusion(VectorList sites) {
        this.sites = sites;
    }

    @Override
    public VectorList getOccupationSites() {
        return sites;
    }
}
