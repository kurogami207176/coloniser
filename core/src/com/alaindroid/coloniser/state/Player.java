package com.alaindroid.coloniser.state;

import com.alaindroid.coloniser.grid.Coordinate;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
    @EqualsAndHashCode.Include
    private final String id = UUID.randomUUID().toString();
    private final Color color;

    private int turnLeft;
    private int maxTurns;

    private Set<Coordinate> seenCoordinates;

    enum Color {
        GREEN, RED
    }
}
