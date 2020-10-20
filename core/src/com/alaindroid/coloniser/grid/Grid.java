package com.alaindroid.coloniser.grid;

import com.alaindroid.coloniser.draw.Point2D;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Grid {
    private Map<Coordinate, HexCell> cells = new HashMap<>();
    private Map<Coordinate, Set<Coordinate>> neighborMap = new HashMap<>();

    public HexCell cell(Coordinate coordinate) {
        return cells.get(coordinate);
    }

    public void cell(Coordinate coordinate, HexCell cell) {
        cells.put(coordinate,cell);
    }

    public void neighbors(Coordinate coordinate, Collection<Coordinate> neighbors) {
        Set<Coordinate> neighborSet = neighborMap.get(coordinate);
        if (neighborSet == null) {
            neighborSet = new HashSet<>();
            neighborMap.put(coordinate, neighborSet);
        }
        neighborSet.addAll(neighbors);
    }

    public Set<Coordinate> neighbors(Coordinate coordinate) {
        return neighborMap.get(coordinate);
    }

    public void unpopAll() {
        cells.values().forEach(h -> h.popped(false));
    }

}
