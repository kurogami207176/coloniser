package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.draw.BackgroundDrawer;
import com.alaindroid.coloniser.draw.HexGridDrawer;
import com.alaindroid.coloniser.draw.SpriteDrawer;
import com.alaindroid.coloniser.draw.UnitDrawer;
import com.alaindroid.coloniser.service.animation.AnimationProcessorService;
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
    public SpriteDrawer spriteDrawer(HexGridDrawer hexDrawer, UnitDrawer unitDrawer) {
        return new SpriteDrawer(hexDrawer, unitDrawer);
    }

    @Provides
    @Singleton
    public BackgroundDrawer backgroundDrawer() {
        return new BackgroundDrawer();
    }

    @Provides
    @Singleton
    public AnimationProcessorService animationProcessorService() {
        return new AnimationProcessorService();
    }
}
