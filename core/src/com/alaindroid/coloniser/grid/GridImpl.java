package com.alaindroid.coloniser.grid;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class GridImpl implements Grid{
    private final boolean positiveCoordsOnly;
    private Map<Coordinate, Cell> cells = new HashMap<>();

    public Cell cell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public void cell(Coordinate coordinate, Cell cell) {
        cells.put(coordinate,cell);
    }
}
