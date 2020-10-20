package com.alaindroid.coloniser.service.animation;

import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;

public class AnimationProcessorService {
    private static final float popReductionSpeed = 100f;
    private static final float popHeight = 30f;

    public void processAnimation(Grid grid, float deltaTime) {
        grid.cells().values().forEach(h -> processAnimation(h, deltaTime));
    }

    public void processAnimation(HexCell hexCell, float deltaTime) {
        float nextPopHeight = hexCell.currentPopHeight();
        if (hexCell.popped() && hexCell.currentPopHeight() < popHeight) {
            nextPopHeight = Math.min(popHeight, hexCell.currentPopHeight() + popReductionSpeed * deltaTime);

        }
        else if (!hexCell.popped() && hexCell.currentPopHeight() > 0) {
            nextPopHeight = Math.max(0, hexCell.currentPopHeight() - popReductionSpeed * deltaTime);
        }
        hexCell.currentPopHeight(nextPopHeight);
    }
}
