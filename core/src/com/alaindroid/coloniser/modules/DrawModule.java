package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.draw.UnitDrawer;
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

    @Provides
    @Singleton
    public UnitDrawer unitDrawer() {
        return new UnitDrawer();
    }

    @Provides
    @Singleton
    public BackgroundDrawer backgroundDrawer() {
        return new BackgroundDrawer();
    }
}
