package com.matthagy.mc_bfm_2d.state;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        occlusions.add(occlusion);
    }

    public static Configuration fromJson(JSONObject obj) {
        int width = ((Number) obj.get("width")).intValue();
        int height = ((Number) obj.get("height")).intValue();
        Configuration configuration = new Configuration(width, height);

        JSONArray polymersJson = (JSONArray) obj.get("polymers");
        for (Object polymerJson : polymersJson) {
            configuration.addPolymer(Polymer.fromJson((JSONObject) polymerJson));
        }

        JSONArray occlusionsJson = (JSONArray) obj.get("occlusions");
        for (Object occlusionJson : occlusionsJson) {
            configuration.addOcclusion(Occlusion.fromJson((JSONObject) occlusionJson));
        }

        return configuration;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("width", grid.getWidth());
        obj.put("height", grid.getHeight());

        JSONArray polymersJson = new JSONArray();
        for (Polymer polymer : polymers) {
            polymersJson.add(polymer.toJson());
        }
        obj.put("polymers", polymersJson);

        JSONArray occlusionsJson = new JSONArray();
        for (Occlusion occlusion : occlusions) {
            occlusionsJson.add(occlusion.toJson());
        }
        obj.put("occlusions", occlusionsJson);

        return obj;
    }
}
