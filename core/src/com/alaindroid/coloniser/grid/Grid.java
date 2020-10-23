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
    private final Coordinate minRGB;
    private final Coordinate maxRGB;


    public HexCell cell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public void cell(Coordinate coordinate, HexCell cell) {
        cells.put(coordinate,cell);
    }

    public void unpopAll() {
        cells.values().forEach(h -> h.popped(false));
    }

    public boolean within(Coordinate coordinate) {
        return minRGB.r() <= coordinate.r() && coordinate.r() <= maxRGB.r() &&
                minRGB.g() <= coordinate.g() && coordinate.g() <= maxRGB.g() &&
                minRGB.b() <= coordinate.b() && coordinate.b() <= maxRGB.b();
    }

}
