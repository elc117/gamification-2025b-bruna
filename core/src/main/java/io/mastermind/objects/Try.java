package io.mastermind.objects;

import java.util.ArrayList;

public class Try {

    public String firstColor;
    public String secondColor;
    public String thirdColor;
    public String fourthColor;
    public Integer correctPositionsAndColors;
    public Integer correctColors;
    public Boolean isWinningTry;
    public Integer attemptNumber;

    public Try(String firstColor, String secondColor, String thirdColor, String fourthColor, Integer attemptNumber) {
        this.attemptNumber = attemptNumber;
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.thirdColor = thirdColor;
        this.fourthColor = fourthColor;
        this.isWinningTry = false;
    }

    public ArrayList<String> getColors() {
        ArrayList<String> colors = new ArrayList<String>();
        colors.add(firstColor);
        colors.add(secondColor);
        colors.add(thirdColor);
        colors.add(fourthColor);
        return colors;
    }

    public void setChecks(Integer correctPositionsAndColors, Integer correctColors) {
        this.correctPositionsAndColors = correctPositionsAndColors;
        this.correctColors = correctColors;
    }

    public Integer getCorrectPositions() {
        return correctPositionsAndColors;
    }

    public Integer getCorrectColors() {
        return correctColors;
    }

    public void setWinningTry(Boolean isWinningTry) {
        this.isWinningTry = isWinningTry;
    }

    public Boolean getIsWinningTry() {
        return isWinningTry;
    }
}
