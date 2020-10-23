package com.alaindroid.coloniser.bldg;

import com.alaindroid.coloniser.grid.Coordinate;
import com.alaindroid.coloniser.state.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Settlement {
    private int age;
    private Coordinate coordinate;
    private Player player;

    public SettlementType type() {
        return SettlementType.getType(age);
    }
}
