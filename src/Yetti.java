import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public abstract class Yetti extends MovableEntity {
    public static final int YETTI_ACTION_PERIOD = 0;
    public static final int YETTI_ANIMATION_PERIOD = 1;
    public static final int YETTI_LIMIT = 1;
    public static final int YETTI_NUM_PROPERTIES = 3;
    public static final String YETTI_KEY = "yetti";
    public static final String YETTI_DUDE_KEY = "yettiDude";
    public Yetti(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public static void parseYetti(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == YETTI_NUM_PROPERTIES) {
            Yetti entity = YettiNotFull.createYettiNotFull(id, pt, Double.parseDouble(properties[YETTI_ACTION_PERIOD]), Double.parseDouble(properties[YETTI_ANIMATION_PERIOD]), Integer.parseInt(properties[YETTI_LIMIT]), imageStore.getImageList(YETTI_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", YETTI_KEY, YETTI_NUM_PROPERTIES));
        }
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    }
    public Point nextPositionYetti(WorldModel world, Point destPos) {
        PathingStrategy SSP = new SingleStepPathingStrategy();
        List<Point> possibleNeighbors = SSP.computePath(this.getPosition(), destPos, (p) -> !world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class, Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if(possibleNeighbors.size() == 0){
            return this.getPosition();
        }
        return possibleNeighbors.get(0);
    }
}