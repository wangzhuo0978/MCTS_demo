package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;
import mxplayer.in.MCTS;

public class Robot extends Player {
    private MCTS mcts = new MCTS();
    private int id;
    private Robot() {}

    public static Player create(int id) {
        Robot r = new Robot();
        r.id = id;
        return r;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Coord getAction(Board board, String name) {
        return null;
    }
}
