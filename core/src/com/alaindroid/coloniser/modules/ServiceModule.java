package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ServiceModule {
    @Provides
    @Singleton
    public NavigationService navigationService() {
        return new NavigationService();
    }

    @Provides
    @Singleton
    public GamespeedService gamespeedService() {
        return new GamespeedService();
    }

}
