package com.rubengees.vocables.core.testsettings;

import java.util.List;

/**
 * Created by Ruben on 24.04.2015.
 */
public class ClassicTestSettings extends TestSettings {

    private Direction direction;

    public ClassicTestSettings() {

    }

    public ClassicTestSettings(List<Integer> unitIds, int maxRate, Direction direction) {
        super(unitIds, maxRate);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
