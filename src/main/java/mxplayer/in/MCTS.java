package mxplayer.in;

import mxplayer.in.player.Player;
import org.apache.commons.lang3.SerializationUtils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class MCTS {
    private static final int num = 15;
    private static ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(num + 3);
    Random rand = new Random();
    TreeNode root = new TreeNode(null, 1.0);
    int playoutNum = 5000;

    private Map<Coord, Double> policyValueFn(Board board) {
        List<Coord> list = board.getAvailables();
        Map<Coord, Double> coordAndPr = new HashMap<>();
        double sum = 0;
        for (Coord coord : list) {
            double score = board.getPlaceScore(coord);
            coordAndPr.put(coord, score);
            sum += score;
        }
        for (Map.Entry<Coord, Double> entry : coordAndPr.entrySet()) {
            entry.setValue(entry.getValue()/sum);
        }
        return coordAndPr;
    }

    private Coord rolloutPolicyFn(Board board) {
        List<Coord> list = board.getAvailables();
        int total = 0;
        List<Integer> list1 = new ArrayList<>();
        for (Coord coord : list) {
            int score = board.getPlaceScore(coord);
            total += score;
            list1.add(score);
        }
        int v = rand.nextInt(total);
        int cur = 0;
        int ind = 0;
        for (Integer integer : list1) {
            cur += integer;
            if (cur > v) {
                break;
            }
            ind += 1;
        }
        return list.get(ind);
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
        List<Future<Double>> list = new ArrayList<>();
        for (int i = 0; i < num; ++i) {
            Board stateClone = SerializationUtils.clone(state);
            Future<Double> future = executor.submit(() -> evaluateRollout(stateClone));
            list.add(future);
        }
        double res = 0;
        for (Future<Double> future : list) {
            double v = 0;
            try {
                v = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            res += v;
        }
        res /= num;
        node.updateRecursive(-res);
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
