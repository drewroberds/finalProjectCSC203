import processing.core.PImage;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Yeti extends MovableEntity {
    public static final double YETI_ACTION_PERIOD = .55;
    public static final double YETI_ANIMATION_PERIOD = .05;
    public static final int YETI_LIMIT = 2;
    public static final int YETI_NUM_PROPERTIES = 3;
    public static final String YETI_KEY = "yeti";
    public static final String YETI_DUDE_KEY = "yetiDude";
    public Yeti(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
/*    public static void parseYeti(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == YETI_NUM_PROPERTIES) {
            Yeti entity = YetiNotFull.createYetiNotFull(id, pt, Double.parseDouble(properties[YETI_ACTION_PERIOD]), Double.parseDouble(properties[YETI_ANIMATION_PERIOD]), Integer.parseInt(properties[YETI_LIMIT]), imageStore.getImageList(YETI_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", YETI_KEY, YETI_NUM_PROPERTIES));
        }
    }*/

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    }
    public Point nextPositionYeti(WorldModel world, Point destPos) {
        AStarPathingStrategy pathing = new AStarPathingStrategy();
        Predicate<Point> predicate = s -> !world.isOccupied(s) && world.withinBounds(s);
        BiPredicate<Point, Point> bipredicate = (p1, p2) -> p1.adjacent(p2);
        List<Point> possibleNeighbors = pathing.computePath(this.getPosition(), destPos, predicate, bipredicate, PathingStrategy.CARDINAL_NEIGHBORS);

        if(possibleNeighbors.size() == 0){
            return this.getPosition();
        }
        return possibleNeighbors.get(0);
    }
}