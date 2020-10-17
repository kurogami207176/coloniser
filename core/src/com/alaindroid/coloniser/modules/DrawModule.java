package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.HexGridDrawer;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DrawModule {

    @Provides
    @Singleton
    public HexGridDrawer hexDrawer() {
        return new HexGridDrawer();
    }
}
