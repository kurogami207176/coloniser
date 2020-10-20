package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.TileType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ToString
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class Unit {
    private final UnitType unitType;
    private final Map<TileType, Float> traversalSpeed = new HashMap<>();
    private Coordinate coordinate;
    private float maxHealth = 100;
    private float currHealth = 100;

    private Coordinate previousCoordinate;

    @Setter
    private float currentWobbleAngle = 0;
    @Setter
    private boolean currentWobbleDirectionLeft = false;

    @Setter
    private boolean wobble = false;

    public void resetPrevious() {
        this.previousCoordinate = null;
    }

    public void setNextDestination(Coordinate nextCoordinate) {
        this.previousCoordinate = this.coordinate;
        this.coordinate = nextCoordinate;
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
