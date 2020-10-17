package com.alaindroid.coloniser.grid;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(fluent = true)
public class Coordinate {

    private int[] numerical;

    public Coordinate(int length) {
        numerical = new int[length];
    }

    public Coordinate(int[] numerical) {
        this.numerical = numerical;
    }

    public void dim(int index, int value) {
        numerical[index % dimCount()] = value;
    }

    public int dim(int index) {
        return numerical[index % dimCount()];
    }

    public int dimCount() {
        return numerical.length;
    }

    public boolean positiveCoordsOnly() {
        for (int num: numerical()) {
            if(num < 0) {
                return false;
            }
        }
        return true;
    }

}
