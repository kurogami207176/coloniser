package com.alaindroid.coloniser.service;

import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;

import java.util.ArrayList;
import java.util.List;

public class PlayerViewFilterService {

    public GameSave filterGameSave(GameSave fullGameSave) {
        return new GameSave(
                filterGrid(fullGameSave.grid()),
                filterUnits(fullGameSave.units()),
                fullGameSave.players()
        );
    }

    public Grid filterGrid(Grid fullGrid) {
        return new Grid();
    }

    public List<Unit> filterUnits(List<Unit> allUnits) {
        return new ArrayList<>();
    }
}
