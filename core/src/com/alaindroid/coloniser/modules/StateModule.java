package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.draw.UnitDrawer;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.state.MainGameState;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StateModule {

    @Provides
    @Singleton
    public MainGameState mainGameState(HexGridDrawer hexDrawer,
                                       UnitDrawer unitDrawer,
                                       BackgroundDrawer backgroundDrawer,
                                       GridGeneratorService gridGeneratorService,
                                       UnitGenerator unitGenerator,
                                       CellGeneratorService cellGeneratorService,
                                       NavigationService navigationService,
                                       GamespeedService gamespeedService) {
        return new MainGameState(hexDrawer, unitDrawer, backgroundDrawer, gridGeneratorService, unitGenerator,
                cellGeneratorService, navigationService, gamespeedService);
    }
}
