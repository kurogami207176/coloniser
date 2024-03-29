package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.bldg.Settlement;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.units.Unit;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(fluent = true)
public class GameSave {
    private final Grid grid;
    private final List<Unit> units;
    private final List<Settlement> settlements;

    private final Set<Player> players;

    private Player currentPlayer;

    public Unit findWobblingUnit() {
        return units.stream().filter(Unit::wobble).findFirst().orElse(null);
    }

    public void postDecisionReset() {
        grid.unpopAll();
        units.forEach(unit -> {
            unit.wobble(false);
        });
    }

    public void reset() {
        postDecisionReset();
    }
}
