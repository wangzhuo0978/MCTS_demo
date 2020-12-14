package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;

import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    public static Map<Integer, String> nameMap = new HashMap<Integer, String>() {{
        put(1, "robot");
        put(2, "YOU");
    }};
    public static Map<Integer, String> abbMap = new HashMap<Integer, String>() {{
        put(1, "R");
        put(2, "Y");
    }};

    public abstract int getId();
    public abstract Coord getAction(Board board);

    public String getName() {
        return nameMap.get(getId());
    }

}
