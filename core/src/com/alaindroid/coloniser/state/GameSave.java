package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class GameSave {
    private final Grid grid;
    private final List<Unit> units;

    public Unit findWobblingUnit() {
        return units.stream().filter(Unit::wobble).findFirst().orElse(null);
    }

    public void postSelectionReset() {
        units.forEach(u -> u.resetPrevious());
    }
    public void postDecisionReset() {
        grid.unpopAll();
        units.forEach(unit -> {
            unit.wobble(false);
        });
    }
}
