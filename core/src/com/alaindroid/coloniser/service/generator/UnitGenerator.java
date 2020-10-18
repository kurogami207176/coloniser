package com.alaindroid.coloniser.service.generator;

import com.alaindroid.coloniser.CoordinateUtil;
import com.alaindroid.coloniser.grid.Grid;
import com.alaindroid.coloniser.grid.HexCell;
import com.alaindroid.coloniser.units.ShipUnit;
import com.alaindroid.coloniser.units.Unit;
import com.alaindroid.coloniser.units.WagonUnit;

import java.util.Arrays;
import java.util.List;

public class UnitGenerator {
    public List<Unit> generate(Grid grid) {
        Unit ship = new ShipUnit();
        Unit wagon = new WagonUnit();
        setCoordinate(ship, grid);
        setCoordinate(wagon, grid);
        List<Unit> gens = Arrays.asList(ship, wagon);
        System.out.println("Generated units: ");
        gens.forEach(System.out::println);
        return gens;
    }

    private void setCoordinate(Unit unit, Grid grid) {
        grid.cells().keySet().stream()
                .filter(CoordinateUtil.navigable(unit, grid))
                .peek(c -> {
                    System.out.println("valid: unit: "+ unit.unitType() + "\r\n\t coord: " + c + "\n\n\t hex: " +  grid.cell(c));
                })
                .findAny()
                .ifPresent(unit::coordinate);
    }


}
