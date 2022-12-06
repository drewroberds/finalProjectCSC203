import java.util.Comparator;

public class WorldNodeComparator implements Comparator<AStarPathingStrategy.WorldNode> {
    @Override
    public int compare(AStarPathingStrategy.WorldNode n1, AStarPathingStrategy.WorldNode n2) {
        if(Math.abs(n1.getTotalDist()) > Math.abs(n2.getTotalDist())){
            return 1;
        }
        else if(Math.abs(n1.getTotalDist()) < Math.abs(n2.getTotalDist())){
            return -1;
        }
        else return 0;
    }
}
