package com.alaindroid.coloniser.modules;

public class DaggerInjectorModule {
    public static MainComponent get() {
        return DaggerMainComponent.builder().build();
    }
}
