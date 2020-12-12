package mxplayer.in;

import mxplayer.in.player.Player;

public class Game {
    private Board board = new Board();

    public void start() {
        board.graphic();
        Player curPlayer;
        while (true) {
            curPlayer = board.getCurPlayer();
            Coord move = curPlayer.getAction(board, curPlayer.getName());
            board.doMove(move);
            board.graphic();
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
