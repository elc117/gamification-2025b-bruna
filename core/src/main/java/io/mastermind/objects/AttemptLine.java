package io.mastermind.objects;

import java.util.ArrayList;

public class AttemptLine {

    Position first;
    Position second;
    Position third;
    Position fourth;

    public AttemptLine() {
        first = createWindow(50f);
        second = createWindow(105f);
        third = createWindow(158f);
        fourth = createWindow(215f);
    }

    private Position createWindow(float positionX) {
        Position customWindow = new Position();
        customWindow.setSize(50f, 50f);
        customWindow.setVisible(true);
        customWindow.setPosition(positionX, 100f);

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

    public ArrayList<String> getColors() {
        ArrayList<String> colors = new ArrayList<String>();
        for (Position p : getPositions()) {
            colors.add(p.getColorName());
        }
        return colors;
    }

    public Boolean isFull() {
        return first.getColorName() != null &&
            second.getColorName() != null &&
            third.getColorName() != null &&
            fourth.getColorName() != null;
    }
}
