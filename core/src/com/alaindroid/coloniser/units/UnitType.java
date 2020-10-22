package com.alaindroid.coloniser.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum UnitType {
    LAND_ATTACK(0, 5,2),
    LAND_SPEED( 0, 1, 4),
    LAND_LARGE(0, 3, 2),
    SHIP_ATTACK(1, 5, 3),
    SHIP_SPEED(1, 1, 6),
    SHIP_LARGE(3, 1, 3)
    ;

    private int capacity;
    private int strength;
    private int range;
}
