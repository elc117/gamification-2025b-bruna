package io.mastermind.objects;

public class AttemptLine {

//    TODO: ajustar o tipo das posicoes
    Position first;
    Position second;
    Position third;
    Position fourth;

    AttemptLine() {
        first = createWindow(1, 1, 0.9f, 1.9f);
        second = createWindow(1, 1, 2f, 1.9f);
        third = createWindow(1, 1, 3.1f, 1.9f);
        fourth = createWindow(1, 1, 4.2f, 1.9f);
    }

    private Position createWindow(float width, float height, float positionX, float positionY) {

        Position customWindow = new Position();
        customWindow.setSize(width, height);
        customWindow.setVisible(true);
        customWindow.setMovable(true);
        customWindow.setPosition(positionX, positionY);

        return customWindow;

    }
}
