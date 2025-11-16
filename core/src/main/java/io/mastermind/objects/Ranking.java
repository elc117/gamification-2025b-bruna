package io.mastermind.objects;

public class Ranking {
    String name;
    Integer quantity;
    String date;

    public Ranking(String name, Integer quantity, String date) {
        this.name = name;
        this.quantity = quantity;
        this.date = date;
    }

    public Ranking(String name, String quantity, String date) {
        this.name = name;
        this.quantity = Integer.valueOf(quantity);
        this.date = date;
    }

    public String getName() {
        return name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public String getDate() {
        return date;
    }
}
