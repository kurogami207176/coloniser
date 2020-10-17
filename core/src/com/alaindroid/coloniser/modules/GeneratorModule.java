package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.CellGeneratorService;
import com.alaindroid.coloniser.service.GridGeneratorService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class GeneratorModule {

    @Provides
    @Singleton
    public GridGeneratorService gridGeneratorService() {
        return new GridGeneratorService();
    }

    @Provides
    @Singleton
    public CellGeneratorService cellGeneratorService() {
        return new CellGeneratorService();
    }
}
