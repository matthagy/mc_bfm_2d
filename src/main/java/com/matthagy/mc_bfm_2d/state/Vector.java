package com.matthagy.mc_bfm_2d.state;

import java.util.Objects;

public class Vector {

    private int x;
    private int y;

    public Vector(int x, int y) {
        set(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector v) {
        set(v.getX(), v.getY());
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    public void addTo(Vector v) {
        x += v.x;
        y += v.y;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return x == vector.x &&
                y == vector.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
