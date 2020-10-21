package com.alaindroid.coloniser.units;

import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.grid.TileType;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.*;

@ToString
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Unit {
    @EqualsAndHashCode.Include
    private final String id = UUID.randomUUID().toString();
    private final UnitType unitType;
    private final Set<TileType> traversable = new HashSet<>();
    private Coordinate coordinate;
    private float maxHealth = 100;
    private float currHealth = 100;

    @Setter
    private float currentWobbleAngle = 0;
    @Setter
    private boolean currentWobbleDirectionLeft = false;
    @Setter
    private Point2D currentPoint;
    @Setter
    private Point2D targetPoint;

    @Setter
    private boolean wobble = false;

    public void setNextDestination(Coordinate nextCoordinate) {
        if (coordinate != null) {
            this.currentPoint = this.coordinate.point().get(0);
        }
        else {
            this.coordinate = nextCoordinate;
        }
        this.targetPoint = nextCoordinate.point().get(0);
        this.coordinate = nextCoordinate;
    }

    public Point2D currentPoint() {
        return currentPoint == null
                ? coordinate.point().get(0)
                : currentPoint;
    }

    public boolean traversable(TileType tileType) {
        return traversable.contains(tileType);
    }

    public int healthLevel() {
        float percent = (float) unitType.levels() * currHealth / maxHealth;
        float level = Math.max(unitType.levels()-1, Math.min(0, percent));
        return (int) level;
    }
}
