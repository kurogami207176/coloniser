package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.state.MainGameState;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class StateModule {

    @Provides
    @Singleton
    public MainGameState mainGameState() {
        return new MainGameState();
    }
}
