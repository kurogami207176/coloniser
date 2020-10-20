package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.SpriteDrawer;
import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.animation.AnimationProcessorService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.state.MainGameState;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StateModule {

    @Provides
    @Singleton
    public MainGameState mainGameState(SpriteDrawer spriteDrawer,
                                       BackgroundDrawer backgroundDrawer,
                                       GridGeneratorService gridGeneratorService,
                                       UnitGenerator unitGenerator,
                                       CellGeneratorService cellGeneratorService,
                                       NavigationService navigationService,
                                       DecisionService decisionService,
                                       GamespeedService gamespeedService,
                                       AnimationProcessorService animationProcessorService) {
        return new MainGameState(spriteDrawer, backgroundDrawer, gridGeneratorService, unitGenerator,
                cellGeneratorService, navigationService, decisionService, gamespeedService, animationProcessorService);
    }
}
