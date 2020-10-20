package com.alaindroid.coloniser.inputs;

import com.alaindroid.coloniser.service.DecisionService;
import com.alaindroid.coloniser.state.GameSave;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GameStateInputProcessor implements InputProcessor {
    private OrthographicCamera camera;
    private GameSave gameSave;
    private DecisionService decisionService;

    @Override
    public boolean keyDown (int keycode) {
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }
}
