package mxplayer.in;

import mxplayer.in.player.Player;

public class Game {
    private Board board = new Board();

    public void start() {
        System.out.println("author: zhuo.wang");
        Player curPlayer;
        board.graphic();
        while (true) {
            curPlayer = board.getCurPlayer();
            Coord move = curPlayer.getAction(board);
            board.doMove(move);
            board.graphic();
            if (board.getLastPlayer().getId() == 1) {
                System.out.println("robot completed: " + board.lastLoc.toString());
            }
            int res = board.gameEnd();
            if (res == 1) {
                System.out.println("game end, tie");
                break;
            } else if (res == 2) {
                System.out.printf("game end, winner is %s", curPlayer.getName());
                System.out.println();
                break;
            }
        }
    }
}
