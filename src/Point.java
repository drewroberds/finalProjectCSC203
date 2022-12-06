import processing.core.PApplet;
import processing.core.PImage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A simple class representing a location in 2D space.
 */
public final class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
//    private final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
//    private final int SAPLING_HEALTH_LIMIT = 5;
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public boolean equals(Object other) {
        return other instanceof Point && ((Point) other).x == this.x && ((Point) other).y == this.y;
    }

    public int hashCode() {
        int result = 17;
        result = result * 31 + x;
        result = result * 31 + y;
        return result;
    }
    public int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
    public int manhattanDistance(Point end){
        int result = Math.abs(end.y - this.y) + Math.abs(end.x - this.x);
        return result;
    }

    public Optional<Entity> nearestEntity(List<Entity> entities) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), this);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), this);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
    public boolean adjacent(Point p2) {
        return (this.x == p2.x && Math.abs(this.y - p2.y) == 1) || (this.y == p2.y && Math.abs(this.x - p2.x) == 1);
    }
    public void setValues(int x, int y){
        this.x = x;
        this.y = y;
    }
}
