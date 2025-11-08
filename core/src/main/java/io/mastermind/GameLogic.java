package io.mastermind;

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
        System.out.println("Raffled sequence: " + sequence);
    }

    public Try processAttempt(AttemptLine attemptLine) {
        System.out.println("Processing attempt: " + attemptLine);
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

    /**
     * Adds an attempt built from the given AttemptLine.
     * Returns true if the attempt was added, false if the AttemptLine was invalid (contains nulls).
     */
    private boolean addAttempt(AttemptLine attemptLine) {
        System.out.println("Attempt added");
        ArrayList<String> colors = new ArrayList<String>();
        for (Position pos : attemptLine.getPositions()) {
            colors.add(pos.getColorName());
        }
        Try attempt = new Try(colors.get(0), colors.get(1), colors.get(2), colors.get(3), tries.size() + 1);
        tries.add(attempt);
        return true;
    }

    private Try checkAttempt() {
        // Two-pass matching to avoid ConcurrentModificationException and double counting
        System.out.println("Checking attempt");
        Try lastTry = tries.get(tries.size() - 1);
        int correctPositionColor = 0;
        int correctColor = 0;

        // Defensive copies that we can mutate safely
        ArrayList<String> attemptColors = new ArrayList<String>(lastTry.getColors());
        ArrayList<String> seqCopy = new ArrayList<String>(sequence);

        // First pass: exact matches by index -> mark matched slots as null
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

        // Second pass: for remaining (non-null) attempt colors, look for a match in seqCopy and consume it
        for (int i = 0; i < attemptColors.size(); i++) {
            String a = attemptColors.get(i);
            if (a == null) continue;
            for (int j = 0; j < seqCopy.size(); j++) {
                String s = seqCopy.get(j);
                if (Objects.equals(a, s)) {
                    correctColor++;
                    seqCopy.set(j, null); // consume so it isn't double counted
                    break;
                }
            }
        }

        lastTry.setChecks(correctPositionColor, correctColor);
        if (correctPositionColor == 4) {
            lastTry.setWinningTry(true);
        }

        System.out.println("Correct Positions: " + correctPositionColor + ", Correct Colors: " + correctColor);
        System.out.println(lastTry);
        return lastTry;
    }

}
