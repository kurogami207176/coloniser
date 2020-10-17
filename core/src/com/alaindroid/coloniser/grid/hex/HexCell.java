package com.alaindroid.coloniser.grid.hex;

import com.alaindroid.coloniser.grid.Cell;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@RequiredArgsConstructor
public class HexCell implements Cell {

    @Override
    public int neighborCount() {
        return 6;
    }
}
