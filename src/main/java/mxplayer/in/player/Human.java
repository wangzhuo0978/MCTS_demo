package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;
import mxplayer.in.InputReader;

import java.io.Serializable;

public class Human extends Player implements Serializable {
    private static final long serialVersionUID = 7173089393546677174L;

    private int id;
    private transient InputReader in = new InputReader(System.in);

    private Human() {}

    public static Player create(int id) {
        Human h = new Human();
        h.id = id;
        return h;
    }

    public int getId() {
        return id;
    }

    public Coord getAction(Board board) {
        System.out.print(String.format("%s move: ", getName()));
        int x, y;
        x = in.nextInt();
        y = in.nextInt();
        int res = board.getLoc(x, y);
        if (res != 0) {
            System.out.println("invalid move");
            return getAction(board);
        }
        return new Coord(x, y);
    }
}
