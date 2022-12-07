import processing.core.PImage;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

public abstract class Dude extends MovableEntity {
    public static final int DUDE_ACTION_PERIOD = 0;
    public static final int DUDE_ANIMATION_PERIOD = 1;
    public static final int DUDE_LIMIT = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;
    public static final String DUDE_KEY = "dude";
    public Dude(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public static void parseDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            Dude entity = DudeNotFull.createDudeNotFull(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]), imageStore.getImageList(DUDE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
    }
    public Point nextPositionDude(WorldModel world, Point destPos) {
        PathingStrategy SSP = new SingleStepPathingStrategy();
        List<Point> possibleNeighbors = SSP.computePath(this.getPosition(), destPos, (p) -> !world.isOccupied(p) || world.getOccupancyCell(p).getClass() == Stump.class, Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if(possibleNeighbors.size() == 0){
            return this.getPosition();
        }
        return possibleNeighbors.get(0);
    }
}
