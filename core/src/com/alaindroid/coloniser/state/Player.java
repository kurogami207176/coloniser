package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.grid.Coordinate;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
    @EqualsAndHashCode.Include
    private final String id;

    private int turnLeft;
    private int maxTurns;

    private Set<Coordinate> seenCoordinates;
}
