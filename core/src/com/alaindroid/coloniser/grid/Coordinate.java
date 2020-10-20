package com.alaindroid.coloniser.grid;

import com.alaindroid.coloniser.draw.Point2D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
@AllArgsConstructor
public class Coordinate {
    private int r, g, b;

    @EqualsAndHashCode.Exclude
    private List<Point2D> point;
}
