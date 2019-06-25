package com.matthagy.mc_bfm_2d.state;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VectorList implements Iterable<Vector> {

    private final List<Vector> vectors;

    public VectorList(List<Vector> vectors) {
        this.vectors = vectors;
    }

    public VectorList() {
        this(new ArrayList<>());
    }

    public void append(Vector v) {
        vectors.add(v);
    }

    public void addTo(Vector v) {
        for (Vector vi : vectors) {
            vi.addTo(v);
        }
    }

    public VectorList copy() {
        return new VectorList(Lists.transform(vectors, Vector::copy));
    }

    public VectorList add(Vector v) {
        VectorList c = copy();
        c.addTo(v);
        return c;
    }

    @Override
    public Iterator<Vector> iterator() {
        return vectors.iterator();
    }
}
