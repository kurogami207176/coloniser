package com.alaindroid.coloniser.service;

public class GamespeedService {
    private float gameCycle = 1f;
    private float ticker = 0f;

    public boolean tick(float delta) {
        ticker = ticker + delta;
        if (ticker < gameCycle) {
            return false;
        }
        ticker = ticker - gameCycle;
        return true;
    }
}
