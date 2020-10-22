package com.alaindroid.coloniser.modules;

import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.service.GamespeedService;
import com.alaindroid.coloniser.service.NavigationService;
import com.alaindroid.coloniser.service.PathFinderService;
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
    public GamespeedService gamespeedService() {
        return new GamespeedService();
    }

}
