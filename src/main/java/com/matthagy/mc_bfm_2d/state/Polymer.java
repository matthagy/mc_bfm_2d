package com.matthagy.mc_bfm_2d.state;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

public class Polymer {

    private final List<Monomer> monomers;

    public Polymer(List<Monomer> monomers) {
        this.monomers = monomers;
    }

    public List<Monomer> getMonomers() {
        return Collections.unmodifiableList(monomers);
    }

    public static Polymer fromJson(JSONObject obj) {
        JSONArray locations = (JSONArray) obj.get("monomer_locations");

        Map<Integer, Monomer> indexedMonomers = new HashMap<>(locations.size());
        for (Object location : locations) {
            JSONArray locationArray = (JSONArray) location;
            int index = ((Number)locationArray.get(0)).intValue();
            JSONArray coordinates = (JSONArray) locationArray.get(1);
            Vector vector = new Vector(
                    ((Number) coordinates.get(0)).intValue(),
                    ((Number) coordinates.get(1)).intValue()
            );
            indexedMonomers.put(index, new Monomer(vector));
        }

        JSONArray bondIndices = (JSONArray) obj.get("bond_indices");
        for (Object bondIndex : bondIndices) {
            JSONArray bondArray = (JSONArray) bondIndex;
            int i = ((Number) bondArray.get(0)).intValue();
            int j = ((Number) bondArray.get(1)).intValue();
            indexedMonomers.get(i).bond(indexedMonomers.get(j));
        }

        return new Polymer(new ArrayList<>(indexedMonomers.values()));
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();

        JSONArray locations = new JSONArray();
        for (int i=0; i<monomers.size(); i++) {
            JSONArray locationArray = new JSONArray();
            locationArray.add(i);

            Monomer monomer = monomers.get(i);
            JSONArray coordinates = new JSONArray();
            coordinates.add(monomer.getLowerRightOccupancy().getX());
            coordinates.add(monomer.getLowerRightOccupancy().getY());
            locationArray.add(coordinates);

            locations.add(locationArray);
        }
        obj.put("monomer_locations", locations);

        Set<Bond> bonds = monomers.stream().flatMap(m -> m.getBonds().stream()).collect(Collectors.toSet());
        JSONArray bondIndicies = new JSONArray();
        for (Bond bond : bonds) {
            JSONArray bondArray = new JSONArray();
            bondArray.add(monomers.indexOf(bond.getA()));
            bondArray.add(monomers.indexOf(bond.getB()));
            bondIndicies.add(bondArray);
        }
        obj.put("bond_indices", bondIndicies);

        return obj;
    }

    @Override
    public String toString() {
        return "Polymer{" +
                "monomers=" + monomers +
                '}';
    }
}
