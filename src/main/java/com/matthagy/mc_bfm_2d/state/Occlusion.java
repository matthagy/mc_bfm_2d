package com.matthagy.mc_bfm_2d.state;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Occlusion implements GridOccupier {

    private final VectorList sites;

    public Occlusion(VectorList sites) {
        this.sites = sites;
    }

    @Override
    public VectorList getOccupationSites() {
        return sites;
    }

    public static Occlusion fromJson(JSONObject obj) {
        VectorList sites = new VectorList();
        for (Object site : (JSONArray) obj.get("sites")) {
            JSONArray coordinates = (JSONArray) site;
            Vector vector = new Vector(
                    ((Number) coordinates.get(0)).intValue(),
                    ((Number) coordinates.get(1)).intValue()
            );
            sites.append(vector);
        }
        return new Occlusion(sites);
    }

    public JSONObject toJson() {
        JSONArray jsonSites = new JSONArray();
        for (Vector site : sites) {
            JSONArray jsonSite = new JSONArray();
            jsonSite.add(site.getX());
            jsonSite.add(site.getY());
            jsonSites.add(jsonSite);
        }
        JSONObject object = new JSONObject();
        object.put("sites", jsonSites);
        return object;
    }
}
