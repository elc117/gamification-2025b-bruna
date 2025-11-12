package io.mastermind.utils;

import io.mastermind.objects.AttemptLine;
import io.mastermind.objects.Position;
import io.mastermind.objects.Try;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class GameLogic {

    ArrayList<String> sequence = new ArrayList<String>();
    public ArrayList<Try> tries = new ArrayList<Try>();

    public GameLogic() {
        String[] colors = {"amarelo", "azul", "laranja", "rosa", "verde", "vermelho"};
        ArrayList<String> colorList = new ArrayList<String>(Arrays.asList(colors));
        java.util.Collections.shuffle(colorList);
        for (int i = 0; i < 4; i++) {
            sequence.add(colorList.get(i));
        }
        System.out.println("Shuffle sequence: " + sequence);
    }

    public Try processAttempt(AttemptLine attemptLine) {
        if (tries.size() < 8) {
            boolean added = addAttempt(attemptLine);
            if (!added) {
                System.err.println("Attempt was not added because it contains null/invalid colors.");
                return null;
            }
            return checkAttempt();
        } else {
            System.out.println("Game over: maximum attempts reached");
        }
        return null;
    }

    private boolean addAttempt(AttemptLine attemptLine) {
        ArrayList<String> colors = new ArrayList<String>();
        for (Position pos : attemptLine.getPositions()) {
            colors.add(pos.getColorName());
        }
        Try attempt = new Try(colors, tries.size() + 1);
        tries.add(attempt);
        return true;
    }

    private Try checkAttempt() {
        Try lastTry = tries.get(tries.size() - 1);
        int correctPositionColor = 0;
        int correctColor = 0;

        ArrayList<String> attemptColors = new ArrayList<String>(lastTry.getColors());
        ArrayList<String> seqCopy = new ArrayList<String>(sequence);

        int min = Math.min(attemptColors.size(), seqCopy.size());
        for (int i = 0; i < min; i++) {
            String a = attemptColors.get(i);
            String s = seqCopy.get(i);
            if (Objects.equals(a, s)) {
                correctPositionColor++;
                attemptColors.set(i, null);
                seqCopy.set(i, null);
            }
        }

        for (int i = 0; i < attemptColors.size(); i++) {
            String a = attemptColors.get(i);
            if (a == null) continue;
            for (int j = 0; j < seqCopy.size(); j++) {
                String s = seqCopy.get(j);
                if (Objects.equals(a, s)) {
                    correctColor++;
                    seqCopy.set(j, null);
                    break;
                }
            }
        }

        lastTry.setChecks(correctPositionColor, correctColor);
        if (correctPositionColor == 4) {
            lastTry.setWinningTry(true);
        }

        return lastTry;
    }

    public ArrayList<String> getSequence(){
        return sequence;
    }
}
