package mxplayer.in;

import mxplayer.in.player.Player;
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;

public class MCTS {
    Random rand = new Random();
    TreeNode root = new TreeNode(null, 1.0);
    int playoutNum = 20000;

    private Map<Coord, Double> policyValueFn(Board board) {
        List<Coord> list = board.getAvailables();
        Map<Coord, Double> map = new HashMap<>();
        double pr = 1.0/list.size();
        list.forEach(
            x -> map.put(x, pr)
        );
        return map;
    }

    private Coord rolloutPolicyFn(Board board) {
        List<Coord> list = board.getAvailables();
        int index = rand.nextInt(list.size());
        return list.get(index);
    }

    private double evaluateRollout(Board state) {
        Player player = state.getCurPlayer();
        int v = 0;
        for (int i = 0; i < playoutNum; ++i) {
            v = state.gameEnd();
            if (v != 0) {
                break;
            }
            Coord move = rolloutPolicyFn(state);
            state.doMove(move);
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
            Map.Entry<Coord, TreeNode> entry = node.select();
            node = entry.getValue();
            state.doMove(entry.getKey());
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
            Board stateClone = SerializationUtils.clone(state);
            playout(stateClone);
        }
        return root.child.entrySet()
            .stream()
            .max(Comparator.comparing(x -> x.getValue().visitNum))
            .get()
            .getKey();
    }

    public void reset() {
        root = new TreeNode(null, 1.0);
    }
}
