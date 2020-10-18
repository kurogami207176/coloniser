package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.ColoniserGame;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { GeneratorModule.class, StateModule.class, DrawModule.class,
        ServiceModule.class})
public interface MainComponent {
    void inject(ColoniserGame mainGame);
}
