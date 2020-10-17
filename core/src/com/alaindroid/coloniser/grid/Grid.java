package com.alaindroid.coloniser.grid;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Grid {
    private Map<Coordinate, HexCell> cells = new HashMap<>();

    public HexCell cell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public void cell(Coordinate coordinate, HexCell cell) {
        cells.put(coordinate,cell);
    }

}
