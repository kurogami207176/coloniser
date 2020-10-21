package com.alaindroid.coloniser.units;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum UnitType {
    LAND(0, 0),
    TANKS_DESERT(5, 2),
    TANKS_GREEN(5, 2),
    TANKS_GREY(5, 2),
    TANKS_NAVY(5, 2),
    SHIP(0, 0),
    SHIP_CROSS(4,4),
    SHIP_GOLD(4, 4),
    SHIP_NEUTRAL(4, 4),
    SHIP_PIRATE(4, 4),
    SHIP_SPEED(4, 4),
    SHIP_SWORD(4, 4);

    private int levels;
    private int range;
}
