package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.TileType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Unit {
    private final UnitType unitType;
    private final Map<TileType, Float> traversalSpeed = new HashMap<>();
    private Coordinate coordinate;

    public Optional<Float> traversalSpeed(TileType tileType) {
        return Optional.ofNullable(traversalSpeed.get(tileType));
    }
}
