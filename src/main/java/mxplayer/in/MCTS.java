package mxplayer.in;

import mxplayer.in.player.Player;

import java.util.*;

public class MCTS {
    Random rand = new Random();
    TreeNode root = new TreeNode(1.0);
    int playoutNum = 10000;

    private Map<Coord, Double> policyValueFn(Board board) {
        List<Coord> list = board.getAvailables();
        Map<Coord, Double> map = new HashMap<>();
        double pr = 1.0/list.size();
        list.forEach(
            x -> map.put(x, pr)
        );
        return map;
    }

    private Map<Coord, Double> rolloutPolicyFn(Board board) {
        // to do;
        return null;
    }

    private double evaluateRollout(Board state) {
        Player player = state.getCurPlayer();
        int v = 0;
        for (int i = 0; i < 1000; ++i) {
            v = state.gameEnd();
            if (v != 0) {
                break;
            }
            Map<Coord, Double> actionProbs = policyValueFn(state);
        }
        if (v == 1) {
            return 0;
        } else {
            return state.getLastPlayer().getId() == player.getId() ? 1 : -1;
        }
    }

    private void playout(Board state) {
        TreeNode node = this.root;
        while(true) {
            if (node.isleaf()) {
                break;
            }
            Coord coord = node.select();
            state.doMove(coord);
        }
        Map<Coord, Double> actionProbs = policyValueFn(state);
        int status = state.gameEnd();
        if (status == 0) {
            node.expand(actionProbs);
        }
        double leafValue = evaluateRollout(state);
        node.updateRecursive(-leafValue);
    }

    public Coord getMove(Board state) {
        for (int i = 0; i < playoutNum; ++i) {
            playout(state);
        }
        return root.child.entrySet()
            .stream()
            .max(Comparator.comparing(x -> x.getValue().visitNum))
            .get()
            .getKey();
    }
}
