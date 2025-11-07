package io.mastermind;

import io.mastermind.objects.AttemptLine;
import io.mastermind.objects.Position;
import io.mastermind.objects.Try;

import java.util.ArrayList;

public class GameLogic {

    ArrayList<String> sequence = new ArrayList<String>();
    public ArrayList<Try> tries = new ArrayList<Try>();

    public GameLogic() {
        String[] colors = {"amarelo", "azul", "laranja", "rosa", "verde", "vermelho"};
        ArrayList<String> colorList = new ArrayList<String>();
        for (String color : colors) {
            colorList.add(color);
        }
        java.util.Collections.shuffle(colorList);
        for (int i = 0; i < 4; i++) {
            sequence.add(colorList.get(i));
        }
        System.out.println("Raffled sequence: " + sequence);
    }

    public Try processAttempt(AttemptLine attemptLine) {
        if (tries.size() > 8) {
            addAttempt(attemptLine);
            return checkAttempt();
        } else {
            System.out.println("Attempt line is not full");
        }
        return null;
    }

    private void addAttempt(AttemptLine attemptLine) {
        System.out.println("Attempt added");
        ArrayList<String> colors = new ArrayList<String>();
        for (Position pos : attemptLine.getPositions()) {
            colors.add(pos.getColorName());
        }
        Try attempt = new Try(colors.get(0), colors.get(1), colors.get(2), colors.get(3), tries.size() + 1);
        tries.add(attempt);
    }

    private Try checkAttempt() {
        Try lastTry = tries.get(tries.size() - 1);
        Integer correctPositionColor = 0;
        Integer correctColor = 0;

        ArrayList<String> tempAttempt = new ArrayList<String>(lastTry.getColors());

        Integer i = 0;
        for (String color : tempAttempt) {
            if (color.equals(sequence.get(i))) {
                correctPositionColor++;
                tempAttempt.remove(color);
            }
            i++;
        }

        for (String color : tempAttempt) {
            if (color != null && sequence.contains(color)) {
                correctColor++;
            }
        }

        lastTry.setChecks(correctPositionColor, correctColor);
        System.out.println("Correct Positions: " + correctPositionColor + ", Correct Colors: " + correctColor);
        if (correctPositionColor == 4) {
            lastTry.setWinningTry(true);
        }

        return lastTry;
    }

}
