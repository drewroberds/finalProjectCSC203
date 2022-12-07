import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YetiDudeFull extends Yeti {


    public YetiDudeFull(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), new ArrayList<>(List.of(House.class)));

        if (fullTarget.isPresent() && this.moveTo(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        }
    }
    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        YetiDudeNotFull yeti = new YetiDudeNotFull(Yeti.YETI_KEY, this.getPosition(), this.getImages(), 3, this.getResourceCount(), this.getActionPeriod(), this.getAnimationPeriod(), this.getHealth(), this.getHealthLimit());

        world.removeEntity(scheduler, this);

        world.addEntity(yeti);
        yeti.scheduleActions(scheduler, world, imageStore);
    }
    @Override
    public boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler) {
        if (this.getPosition().adjacent(target.getPosition())) {
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
