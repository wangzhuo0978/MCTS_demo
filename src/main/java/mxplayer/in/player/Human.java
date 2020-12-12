package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;
import mxplayer.in.InputReader;

public class Human extends Player {
    private int id;
    private InputReader in = new InputReader(System.in);

    private Human() {}

    public static Player create(int id) {
        Human h = new Human();
        h.id = id;
        return h;
    }

    public int getId() {
        return id;
    }

    public Coord getAction(Board board, String name) {
        System.out.print(String.format("%s move: ", name));
        int x, y;
        x = in.nextInt();
        y = in.nextInt();
        int res = board.getLoc(x, y);
        if (res != 0) {
            System.out.println("invalid move");
            return getAction(board, name);
        }
        return new Coord(x, y);
    }
}
