import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YettiFull extends Yetti{

    public static final String YETTI_DUDE_KEY = "yettiDude";
    public YettiFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(YETI_CAVE.class)));

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transformYettiFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        }
    }

    //    public static DudeFull createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
//        return new DudeFull(id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
//    }
    public void transformYettiFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        YettiDudeNotFull yetti = new YettiDudeNotFull(Yetti.YETTI_DUDE_KEY, this.getPosition(), this.getImages(), 0, 2, this.getActionPeriod(), this.getAnimationPeriod(), 0, this.getHealthLimit());

        world.removeEntity(scheduler, this);

        world.addEntity(yetti);
        yetti.scheduleActions(scheduler, world, imageStore);
    }
    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            return true;
        } else {
            Point nextPos = nextPositionYetti(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }
    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, null, null, 0), this.getAnimationPeriod());
    }
}
