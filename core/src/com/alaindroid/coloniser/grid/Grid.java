package com.alaindroid.coloniser.grid;

import java.util.Map;

public interface Grid {
    Map<Coordinate, Cell> cells();
    Cell cell(Coordinate coordinate);
    void cell(Coordinate coordinate, Cell cell);
    boolean positiveCoordsOnly();
}
