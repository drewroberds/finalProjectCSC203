import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



class AStarPathingStrategy implements PathingStrategy {
    public class WorldNode {
        private int estimatedDistToEnd;   // h value
        private int distFromStart;         // g value
        private Point position;
        private WorldNode previous;
        private int totalDist;             // f value

        WorldNode(int estimatedDistToEnd, int distFromStart, Point position, WorldNode previous) {
            this.estimatedDistToEnd = estimatedDistToEnd;
            this.distFromStart = distFromStart;
            this.position = position;
            this.previous = previous;
            this.totalDist = distFromStart + estimatedDistToEnd;
        }

        public int getEstimatedDistToEnd() {
            return estimatedDistToEnd;
        }

        public int getDistFromStart() {
            return distFromStart;
        }

        public int getTotalDist() {
            return totalDist;
        }

        public Point getPosition() {
            return position;
        }

        public void setPosition(int y, int x) {
            position.y = y;
            position.x = x;
        }

        public WorldNode getPrevious() {
            return previous;
        }
    }

    @Override
    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        /*
        initialize a PriorityQueue
         */

        PriorityQueue<WorldNode> openList = new PriorityQueue<>(new WorldNodeComparator());
        HashSet<Point> closedList = new HashSet<>();
        List<Point> finalPath = new LinkedList<>();
        WorldNode startNode = new WorldNode(start.manhattanDistance(end), 0, start, null);
        openList.add(startNode);
        WorldNode currentNode = startNode;
        while (!openList.isEmpty()) {                                // while openList has entries
            currentNode = openList.poll();
            if (withinReach.test(currentNode.getPosition(), end)) {
                List<Point> path = new LinkedList<>();
                while(currentNode.getPrevious() != null){
                    path.add(0,currentNode.getPosition());
                    currentNode = currentNode.getPrevious();
                    finalPath = path;
                }
                break;
            }
            closedList.add(currentNode.getPosition());              // add the current node's Point to the closed list, so we mark it as checked
            //WorldNode temp = currentNode;
            List<Point> neighbors = potentialNeighbors.apply(currentNode.getPosition())    // gets potentialNeighbors of currentNode
                    .filter(p -> !(start.equals(p)&&end.equals(p)))
                    .filter(canPassThrough)                        // filters potential neighbors that "canPassThrough"
                    .filter((p) -> !closedList.contains(p))        // filters the points that are NOT on the closed list
                    .collect(Collectors.toList());

            for (Point wn : neighbors) {
                openList.add(new WorldNode(wn.manhattanDistance(end), currentNode.distFromStart + 1, wn, currentNode));
                closedList.add(wn);
            }
        }
        return finalPath;
    }
}
