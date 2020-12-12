package mxplayer.in;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    public Map<Coord, TreeNode> child = new HashMap<>();
    TreeNode parent;
    int visitNum = 0;
    double pr;
    double Q = 0;

    public TreeNode(TreeNode parent, double pr) {
        this.pr = pr;
        this.parent = parent;
    }

    private double getValue() {
        double u = 5 * this.pr *
            Math.sqrt(1.0 * parent.visitNum / (1+visitNum));
        return this.Q + u;
    }

    private void update(double leafValue) {
        visitNum += 1;
        this.Q += 1.0*(leafValue-Q)/visitNum;
    }

    public boolean isleaf() {
        return child.size() == 0;
    }

    public Map.Entry<Coord, TreeNode> select() {
        return child.entrySet().stream()
            .max(Comparator.comparingDouble(x -> x.getValue().getValue()))
            .get();
    }

    public void expand(Map<Coord, Double> actionProbs) {
        actionProbs.forEach((coord, pr) -> {
            if (!child.keySet().contains(coord)) {
                child.put(coord, new TreeNode(this, pr));
            }
        });
    }

    public void updateRecursive(Double leafValue) {
        if (parent != null) {
            parent.updateRecursive(-leafValue);
        }
        update(leafValue);
    }
}
