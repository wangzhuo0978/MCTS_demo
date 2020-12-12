package mxplayer.in;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    public Map<Coord, TreeNode> child = new HashMap<>();
    TreeNode parent;
    int visitNum = 0;
    double pr;
    double Q = 0;

    public TreeNode(double pr) {
        this.pr = pr;
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

    public Coord select() {
        return child.entrySet().stream()
            .max(Comparator.comparingDouble(x -> x.getValue().getValue()))
            .get()
            .getKey();
    }

    public void expand(Map<Coord, Double> actionProbs) {
        actionProbs.forEach((coord, pr) -> {
            if (!child.keySet().contains(coord)) {
                child.put(coord, new TreeNode(pr));
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
