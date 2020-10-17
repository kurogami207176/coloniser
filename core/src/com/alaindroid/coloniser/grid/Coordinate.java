package com.alaindroid.coloniser.grid;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Coordinate {
    private int r, g, b;

}
