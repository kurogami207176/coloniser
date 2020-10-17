package com.alaindroid.coloniser.grid.twod;

import com.alaindroid.coloniser.grid.Cell;

public class Cell2D implements Cell {
    @Override
    public int neighborCount() {
        return 4;
    }
}
