package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.service.CellGeneratorService;
import com.alaindroid.coloniser.service.GridGeneratorService;
import com.alaindroid.coloniser.state.MainGameState;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StateModule {

    @Provides
    @Singleton
    public MainGameState mainGameState(HexGridDrawer hexDrawer, GridGeneratorService gridGeneratorService,
                                       CellGeneratorService cellGeneratorService) {
        return new MainGameState(hexDrawer, gridGeneratorService, cellGeneratorService);
    }
}
