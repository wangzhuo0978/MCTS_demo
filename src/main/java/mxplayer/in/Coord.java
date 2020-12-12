package mxplayer.in;

public class Coord {
    private int x, y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Coord) {
            Coord anotherString = (Coord)obj;
            return anotherString.getX() == x && anotherString.getY() == y;
        }
        return false;
    }
}
