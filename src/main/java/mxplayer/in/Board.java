package mxplayer.in;

import mxplayer.in.player.Human;
import mxplayer.in.player.Player;
import mxplayer.in.player.Robot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable{
    private static final long serialVersionUID = -6137892776929979116L;
    private int INTERVAL_WIDTH = 2;
    public int WIDTH = 8;
    public int LENGTH = 8;
    private int bias = 0;
    private int step = 0;
    private int[][] arr = new int[LENGTH][];
    private Coord lastLoc;
    private Player lastPlayer;
    private Player[] players = new Player[] {
        Robot.create(2),
        Human.create(1)
    };

    private List<Coord> availables;

    public Board() {
        for (int i = 0; i < LENGTH; ++i) {
            arr[i] = new int[WIDTH];
        }
        availables = new ArrayList<>(WIDTH*LENGTH + 10);
        for (int i = 0; i < LENGTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                availables.add(new Coord(i, j));
            }
        }
    }

    public Player getCurPlayer() {
        return players[(step+bias)%players.length];
    }

    public Player getLastPlayer() {
        return lastPlayer;
    }

    public List<Coord> getAvailables() {
        return availables;
    }

    public int getLoc(int x, int y) {
        if (x < 0 || x >= LENGTH || y < 0 || y >= WIDTH)
            return -1;
        return arr[x][y];
    }

    public void graphic() {
        System.out.println("-----------------------------");
        System.out.println();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INTERVAL_WIDTH; ++i) {
            sb.append(" ");
        }
        String suffix = sb.toString();
        System.out.print(suffix + " ");
        for (int i = 0; i < 8; ++i) {
            System.out.printf("%s%s", i, suffix);
        }
        System.out.println();
        for (int i = 0; i < 8; ++i) {
            System.out.printf("%s%s", i, suffix);
            for (int j = 0; j < 8; ++j) {
                int x = arr[i][j];
                if (x == 1) {
                    System.out.print(Player.abbMap.get(1) + suffix);
                } else if (x == 2) {
                    System.out.print(Player.abbMap.get(2) + suffix);
                } else {
                    System.out.print("_" + suffix);
                }
            }
            for (int j = 0; j < INTERVAL_WIDTH; ++j) {
                System.out.println();
            }
        }
    }

    public void doMove(Coord coord) {
        int x = coord.getX();
        int y = coord.getY();
        Player curPlayer = getCurPlayer();
        arr[x][y] = curPlayer.getId();
        lastLoc = new Coord(x, y);
        availables.remove(lastLoc);
        lastPlayer = curPlayer;
        step++;
    }

    @SuppressWarnings("Duplicates")
    public int gameEnd() { // 0:没有结束 1:平局 2.胜利
        if (lastLoc == null)
            return 0;
        int x = lastLoc.getX();
        int y = lastLoc.getY();
        int y1 = y-4;
        int y2 = y+4;
        int v = 0;
        for (int ty = y1; ty <= y2; ++ty) {
            if (getLoc(x, ty) == lastPlayer.getId()) {
                v += 1;
            } else {
                v = 0;
            }
            if (v == 5) {
                return 2;
            }
        }

        int x1 = x-4;
        int x2 = x+4;
        for (int tx = x1; tx <= x2; ++tx) {
            if (getLoc(tx, y) == lastPlayer.getId()) {
                v+=1;
            } else {
                v = 0;
            }
            if (v == 5) {
                return 2;
            }
        }

        x1 = x-4;
        y1 = y-4;
        x2 = x+4;
        y2 = y+4;
        int tx = x1, ty = y1;
        for (; tx<=x2 && ty<=y2; ) {
            if (getLoc(tx, ty) == lastPlayer.getId()) {
                v+=1;
            } else {
                v = 0;
            }
            if (v == 5) {
                return 2;
            }
            ++tx;
            ++ty;
        }

        x1 = x+4;
        y1 = y-4;
        x2 = x-4;
        y2 = y+4;
        tx = x1;
        ty = y1;
        for (; tx>=x2 && ty <= y2; ) {
            if (getLoc(tx, ty) == lastPlayer.getId()) {
                v+=1;
            } else {
                v = 0;
            }
            if (v == 5) {
                return 2;
            }
            --tx;
            ++ty;
        }

        for (int i = 0; i < LENGTH; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                if (getLoc(i, j) == 0)
                    return 0;
            }
        }
        return 1;
    }
}
