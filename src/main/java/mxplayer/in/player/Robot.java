package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;
import mxplayer.in.MCTS;

import java.io.Serializable;

public class Robot extends Player implements Serializable {
    private transient MCTS mcts = new MCTS();
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
    public Coord getAction(Board board) {
        Coord move = mcts.getMove(board);
        mcts.reset();
        return move;
    }
}
