package com.alaindroid.coloniser.grid;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class HexCell {
    public int neighborCount() {
        return 6;
    }
}