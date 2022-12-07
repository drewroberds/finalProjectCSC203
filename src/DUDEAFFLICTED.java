import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DUDEAFFLICTED extends MovableEntity {
    public static final int DUDEAFFLICTED_ANIMATION_PERIOD = 0;
    public static final int DUDEAFFLICTED_ACTION_PERIOD = 1;
    public static final int DUDEAFFLICTED_NUM_PROPERTIES = 2;
    public static final String DUDEAFFLICTED_KEY = "dudeAfflicted";
    public DUDEAFFLICTED(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        Optional<Entity> dudeAfflictedTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (dudeAfflictedTarget.isPresent()) {
            Point tgtPos = dudeAfflictedTarget.get().getPosition();

            if (moveTo(world, dudeAfflictedTarget.get(), scheduler)) {

                Sapling sapling = new Sapling("sapling_" + this.getId(), tgtPos, imageStore.getImageList(Sapling.SAPLING_KEY), getResourceLimit(), getResourceCount(), getActionPeriod(), getAnimationPeriod(), getHealth(), getHealthLimit());

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
    }


    public static void parseDUDEAFFLICTED(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDEAFFLICTED_NUM_PROPERTIES) {
            DUDEAFFLICTED entity = DUDEAFFLICTED.createDUDEAFFLICTED(id, pt, Double.parseDouble(properties[DUDEAFFLICTED_ACTION_PERIOD]), Double.parseDouble(properties[DUDEAFFLICTED_ANIMATION_PERIOD]), imageStore.getImageList(DUDEAFFLICTED_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDEAFFLICTED_KEY, DUDEAFFLICTED_NUM_PROPERTIES));
        }
    }
    public static DUDEAFFLICTED createDUDEAFFLICTED(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new DUDEAFFLICTED(id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }
    public Point nextPositionDUDEAFFLICTED(WorldModel world, Point destPos) {
        PathingStrategy SSP = new SingleStepPathingStrategy();
        List<Point> possibleNeighbors = SSP.computePath(this.getPosition(), destPos, (p) -> !world.isOccupied(p), Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);

        if(possibleNeighbors.size() == 0){
            return this.getPosition();
        }
        return possibleNeighbors.get(0);
//        int horiz = Integer.signum(destPos.x - this.getPosition().x);
//        Point newPos = new Point(this.getPosition().x + horiz, this.getPosition().y);
//
//        if (horiz == 0 || world.isOccupied(newPos)) {
//            int vert = Integer.signum(destPos.y - this.getPosition().y);
//            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
//
//            if (vert == 0 || world.isOccupied(newPos)) {
//                newPos = this.getPosition();
//            }
//        }
//
//        return newPos;
    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
    }
    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPositionDUDEAFFLICTED(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
