package mxplayer.in;

import java.io.Serializable;

public class Coord implements Serializable{
    private static final long serialVersionUID = 4031952613388369811L;
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
