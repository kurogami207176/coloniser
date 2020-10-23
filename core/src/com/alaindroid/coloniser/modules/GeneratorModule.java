package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.generator.BuildingGeneratorService;
import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import com.alaindroid.coloniser.service.grid.LandTileWeightService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class GeneratorModule {

    @Provides
    @Singleton
    public GridGeneratorService gridGeneratorService(CellGeneratorService cellGeneratorService) {
        return new GridGeneratorService(cellGeneratorService);
    }

    @Provides
    @Singleton
    public UnitGenerator unitGenerator(NavigationService navigationService) {
        return new UnitGenerator(navigationService);
    }

    @Provides
    @Singleton
    public BuildingGeneratorService buildingGeneratorService(NavigationService navigationService) {
        return new BuildingGeneratorService(navigationService);
    }

    @Provides
    @Singleton
    public CellGeneratorService cellGeneratorService(LandTileWeightService landTileWeightService) {
        return new CellGeneratorService(landTileWeightService);
    }
    @Provides
    @Singleton
    public LandTileWeightService tileWeightService() {
        return new LandTileWeightService();
    }
}
