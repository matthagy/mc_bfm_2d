package com.matthagy.mc_bfm_2d.state;

import com.google.common.base.Preconditions;

import java.util.Optional;

public class Grid {

    private final int width;
    private final int height;

    private final GridOccupier[] sites;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;

        final int size = width * height;
        sites = new GridOccupier[size];
        for (int i = 0; i < size; i++) {
            sites[i] = null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Optional<GridOccupier> getOccupierAtSite(int x, int y) {
        return Optional.ofNullable(sites[coordinateToIndex(x, y)]);
    }

    public boolean isSiteOccupied(int x, int y) {
        return sites[coordinateToIndex(x, y)] != null;
    }

    private int coordinateToIndex(int x, int y) {
        // apply periodic boundaries
        if (x > width) {
            x -= width;
        } else if (x < 0) {
            x += width;
        }
        if (y >= height) {
            y -= height;
        } else if (y < 0) {
            y += height;
        }
        return x * height + y;
    }

    public Vector periodize(Vector v) {
        int x = v.getX();
        if (x > width) {
            x -= width;
        } else if (x < 0) {
            x += width;
        }
        int y = v.getY();
        if (y >= height) {
            y -= height;
        } else if (y < 0) {
            y += height;
        }
        return new Vector(x, y);
    }

    public void addOccupier(GridOccupier occupier) {
        for (Vector site : occupier.getOccupationSites()) {
            int index = coordinateToIndex(site.getX(), site.getY());
            if (sites[index] != null) {
                throw new IllegalArgumentException("Site already occupied: " + site);
            }
            sites[index] = occupier;
        }
    }

    public void removeOccupier(GridOccupier occupier) {
        for (Vector site : occupier.getOccupationSites()) {
            int index = coordinateToIndex(site.getX(), site.getY());
            if (sites[index] != occupier) {
                throw new IllegalArgumentException("Site not occupied: " + site);
            }
            sites[index] = null;
        }
    }

    public int squareDistance(Vector a, Vector b) {
        int x = b.getX() - a.getX();
        if (x > width / 2) {
            x = width - x;
        }
        int y = b.getY() - a.getY();
        if (y > height / 2) {
            y = height - y;
        }
        return x * x + y * y;
    }
}
