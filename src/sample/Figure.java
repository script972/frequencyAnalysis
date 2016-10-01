package sample;

/**
 * Created by script972 on 01.10.2016.
 */
public class Figure {
    private Character figure;
    private Integer number;
    private Integer basket;

    public Figure(Character figure, Integer number, Integer basket) {
        this.figure = figure;
        this.number = number;
        this.basket = basket;
    }

    public Character getFigure() {
        return figure;
    }

    public void setFigure(Character figure) {
        this.figure = figure;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getBasket() {
        return basket;
    }

    public void setBasket(Integer basket) {
        this.basket = basket;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "figure=" + figure +
                ", number=" + number +
                ", basket=" + basket +
                '}'+'\n';
    }
}
