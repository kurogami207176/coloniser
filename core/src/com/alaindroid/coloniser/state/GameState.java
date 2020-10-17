package com.alaindroid.coloniser.state;

public interface GameState {

    void onCreate ();

    void onRender(float deltaTime);

    void onDispose ();
}
