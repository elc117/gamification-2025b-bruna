package io.mastermind.objects;

import java.util.ArrayList;

public class AttemptLine {

    Position first;
    Position second;
    Position third;
    Position fourth;

    public AttemptLine() {
        first = createWindow(0.9f);
        second = createWindow(2f);
        third = createWindow(3.1f);
        fourth = createWindow(4.2f);
    }

    private Position createWindow(float positionX) {
        Position customWindow = new Position();
        customWindow.setSize(1, 1);
        customWindow.setVisible(true);
        customWindow.setPosition(positionX, 1.9f);

        return customWindow;
    }

    public ArrayList<Position> getPositions() {
        ArrayList<Position> positions = new ArrayList<Position>();
        positions.add(first);
        positions.add(second);
        positions.add(third);
        positions.add(fourth);
        return positions;
    }
}
