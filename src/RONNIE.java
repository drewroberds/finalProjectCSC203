import processing.core.PFont;
import processing.core.PImage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class RONNIE extends MovableEntity {
    public static final double RONNIE_ANIMATION_PERIOD = .15;
    public static final double RONNIE_ACTION_PERIOD = .075;
    public static final int RONNIE_NUM_PROPERTIES = 2;
    public static final String RONNIE_KEY = "ronnie";

    PFont font;
    public RONNIE(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        Optional<Entity> ronnieTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (ronnieTarget.isPresent()) {
            Point tgtPos = ronnieTarget.get().getPosition();

            if (moveTo(world, ronnieTarget.get(), scheduler)) {

                Sapling sapling = new Sapling("sapling_" + this.getId(), tgtPos, imageStore.getImageList(Sapling.SAPLING_KEY), 0, 0, Sapling.getSaplingActionPeriod(), Sapling.getSaplingActionPeriod(), 0, Sapling.getSaplingHealthLimit());

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageStore);

            }
        }
 /*       else if (ronnieTarget.equals(YETI_SLEEPYTIME.class) && ronnieTarget.isPresent()){
            Point tgtPos = ronnieTarget.get().getPosition();

            if (moveTo(world, ronnieTarget.get(), scheduler)) {
                world.removeEntity(scheduler, this);
                scheduler.unscheduleAllEvents(this);
                YetiNotFull yeti = new YetiNotFull("yeti" + this.getId(), tgtPos, imageStore.getImageList(YetiNotFull.YETI_KEY), getResourceLimit(), getResourceCount(), getActionPeriod(), getAnimationPeriod(), getHealth(), getHealthLimit());

                world.addEntity(yeti);
                yeti.scheduleActions(scheduler, world, imageStore);
            }
        }*/

        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
    }


    public static void parseRONNIE(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == RONNIE_NUM_PROPERTIES) {
            RONNIE entity = RONNIE.createRONNIE(id, pt, RONNIE_ACTION_PERIOD, RONNIE_ANIMATION_PERIOD, imageStore.getImageList(RONNIE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", RONNIE_KEY, RONNIE_NUM_PROPERTIES));
        }
    }
    public static RONNIE createRONNIE(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new RONNIE(id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }
    public Point nextPositionRONNIE(WorldModel world, Point destPos) {
        AStarPathingStrategy pathing = new AStarPathingStrategy();
        Predicate<Point> predicate = s -> !world.isOccupied(s) && world.withinBounds(s);
        BiPredicate<Point, Point> bipredicate = (p1, p2) -> p1.adjacent(p2);
        List<Point> possibleNeighbors = pathing.computePath(this.getPosition(), destPos, predicate, bipredicate, PathingStrategy.CARDINAL_NEIGHBORS);

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
            scheduler.unscheduleAllEvents(target);
            return true;
        } else {
            Point nextPos = nextPositionRONNIE(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
}
