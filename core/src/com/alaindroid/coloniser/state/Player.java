package com.alaindroid.coloniser.state;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
    @EqualsAndHashCode.Include
    private String id;

    private int turnLeft;
    private int maxTurns;
}
