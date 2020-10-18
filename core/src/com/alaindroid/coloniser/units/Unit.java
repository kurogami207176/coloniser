package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.TileType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Unit {
    private final UnitType unitType;
    private final Map<TileType, Float> traversalSpeed = new HashMap<>();
    private Coordinate coordinate;
    private Point2D point;
    private float maxHealth = 100;
    private float currHealth = 100;

    private Coordinate previousCoordinate;
    private Point2D previousPoint;

    public void setNextDestination(Coordinate nextCoordinate, Point2D nextPoint) {
        this.previousCoordinate = this.coordinate;
        this.previousPoint = this.point;

        this.coordinate = nextCoordinate;
        this.point = nextPoint;
    }

    public Optional<Float> traversalSpeed(TileType tileType) {
        return Optional.ofNullable(traversalSpeed.get(tileType));
    }

    public int healthLevel() {
        float percent = (float) unitType.levels() * currHealth / maxHealth;
        float level = Math.max(unitType.levels()-1, Math.min(0, percent));
        return (int) level;
    }
}
