package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.generator.GridGeneratorService;
import com.alaindroid.coloniser.service.generator.UnitGenerator;
import com.alaindroid.coloniser.service.grid.CellGeneratorService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class GeneratorModule {

    @Provides
    @Singleton
    public GridGeneratorService gridGeneratorService() {
        return new GridGeneratorService();
    }    @Provides

    @Singleton
    public UnitGenerator unitGenerator() {
        return new UnitGenerator();
    }

    @Provides
    @Singleton
    public CellGeneratorService cellGeneratorService() {
        return new CellGeneratorService();
    }
}
