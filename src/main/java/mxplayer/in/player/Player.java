package mxplayer.in.player;

import mxplayer.in.Board;
import mxplayer.in.Coord;

import java.util.HashMap;
import java.util.Map;

public abstract class Player {
    public static Map<Integer, String> nameMap = new HashMap<Integer, String>() {{
        put(1, "white");
        put(2, "black");
    }};
    public static Map<Integer, String> abbMap = new HashMap<Integer, String>() {{
        put(1, "W");
        put(2, "B");
    }};

    public abstract int getId();
    public abstract Coord getAction(Board board, String name);

    public String getName() {
        return nameMap.get(getId());
    }

}
