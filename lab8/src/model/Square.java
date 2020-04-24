package model;

public class Square
{
    public static final int BOARD_SIZE = 10;

    public Square(int x, int y, boolean ship1, boolean ship2, boolean hit1, boolean hit2)
    {
        this.x = x;
        this.y = y;
        this.ship1 = ship1;
        this.ship2 = ship2;
        this.hit1 = hit1;
        this.hit2 = hit2;
    }

    int x;
    int y;
    boolean ship1;
    boolean ship2;
    boolean hit1;
    boolean hit2;

    @Override
    public String toString()
    {
        return "{" +
                "\"x\":" + x +
                ", \"y\":" + y +
                ", \"ship1\":" + ship1 +
                ", \"ship2\":" + ship2 +
                ", \"hit1\":" + hit1 +
                ", \"hit2\":" + hit2 +
                '}';
    }
}
