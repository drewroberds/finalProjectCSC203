import processing.core.PImage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class YetiNotFull extends Yeti {
    public static final String YETI_KEY = "yeti";
    public YetiNotFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        Optional<Entity> target = world.findNearest(this.getPosition(), Arrays.asList(DudeFull.class, DudeNotFull.class));

        if (target.isEmpty() || !moveTo(world, target.get(), scheduler) || !transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        }
    }

/*    public static YetiNotFull createYetiNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new YetiNotFull(id, position, images, resourceLimit-2, 0, actionPeriod, animationPeriod, 0, 0);
    }*/
    public boolean transformNotFull( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.getResourceCount() >= this.getResourceLimit()) {
            YetiFull yeti = new YetiFull(this.getId(), this.getPosition(), this.getImages(), this.getResourceLimit(), this.getResourceCount(), this.getActionPeriod(), this.getAnimationPeriod(), this.getHealth(), this.getHealthLimit());

            world.removeEntity(scheduler, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(yeti);
            yeti.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }
    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
            this.increaseResourceCount();
            target.health--;
            return true;
        } else {
            Point nextPos = nextPositionYeti(world, target.getPosition());

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