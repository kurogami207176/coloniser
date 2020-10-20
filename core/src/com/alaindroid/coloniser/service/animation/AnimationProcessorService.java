package com.alaindroid.coloniser.service.animation;

import com.alaindroid.coloniser.draw.Point2D;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.state.GameSave;
import com.alaindroid.coloniser.units.Unit;

import java.util.List;

public class AnimationProcessorService {
    private static final float popReductionSpeed = 100f;
    private static final float popHeight = 30f;

    private static final float wobbleSpeed = 250f;
    private static final float maxWobbleAngle = 15f;

    private static final float unitMoveSpeed = 150f;

    public void processAnimation(GameSave gameSave, float deltaTime) {
        processAnimation(gameSave.grid(), deltaTime);
        processAnimation(gameSave.units(), deltaTime);
    }

    private void processAnimation(Grid grid, float deltaTime) {
        grid.cells().values().forEach(h -> processAnimation(h, deltaTime));
    }

    private void processAnimation(HexCell hexCell, float deltaTime) {
        float nextPopHeight = hexCell.currentPopHeight();
        if (hexCell.popped() && hexCell.currentPopHeight() < popHeight) {
            nextPopHeight = Math.min(popHeight, hexCell.currentPopHeight() + popReductionSpeed * deltaTime);

        }
        else if (!hexCell.popped() && hexCell.currentPopHeight() > 0) {
            nextPopHeight = Math.max(0, hexCell.currentPopHeight() - popReductionSpeed * deltaTime);
        }
        hexCell.currentPopHeight(nextPopHeight);
    }

    private void processAnimation(List<Unit> unitList, float deltaTime) {
        unitList.forEach(unit -> processAnimation(unit, deltaTime));
    }

    private void processAnimation(Unit unit, float deltaTime) {
        processWobble(unit, deltaTime);
        processUnitMove(unit, deltaTime);
    }

    private void processUnitMove(Unit unit, float deltaTime) {
        Point2D current = unit.currentPoint();
        Point2D target = unit.targetPoint();
        boolean xPos = target.x() > current.x();
        boolean yPos = target.y() > current.y();

        float newX = xPos
                ? Math.min(target.x(), current.x() + deltaTime * unitMoveSpeed)
                : Math.max(target.x(), current.x() - deltaTime * unitMoveSpeed);
        float newY = yPos
                ? Math.min(target.y(), current.y() + deltaTime * unitMoveSpeed)
                : Math.max(target.y(), current.y() - deltaTime * unitMoveSpeed);

        unit.currentPoint(new Point2D(newX, newY));
    }

    private void processWobble(Unit unit, float deltaTime) {
        float newWobbleAngle;
        if (unit.wobble() && Math.abs(unit.currentWobbleAngle()) < maxWobbleAngle) {
            if (unit.currentWobbleDirectionLeft()) {
                newWobbleAngle = Math.max(-maxWobbleAngle, unit.currentWobbleAngle() - deltaTime*wobbleSpeed);
            }
            else {
                newWobbleAngle = Math.min(maxWobbleAngle, unit.currentWobbleAngle() + deltaTime*wobbleSpeed);
            }
        }
        else {
            newWobbleAngle = 0;
        }
        if (Math.abs(newWobbleAngle) == maxWobbleAngle) {
            unit.currentWobbleDirectionLeft(!unit.currentWobbleDirectionLeft());
        }
        unit.currentWobbleAngle(newWobbleAngle);
    }
}
