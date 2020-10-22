package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.*;
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
    public DecisionService decisionService(NavigationService navigationService, PathFinderService pathFinderService) {
        return new DecisionService(navigationService, pathFinderService);
    }

    @Provides
    @Singleton
    public PathFinderService pathFinderService(NavigationService navigationService) {
        return new PathFinderService(navigationService);
    }

    @Provides
    @Singleton
    public PlayerViewFilterService playerViewFilterService(NavigationService navigationService) {
        return new PlayerViewFilterService(navigationService);
    }

    @Provides
    @Singleton
    public GamespeedService gamespeedService() {
        return new GamespeedService();
    }

}
