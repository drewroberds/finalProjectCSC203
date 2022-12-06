import processing.core.PImage;

import java.util.List;

public abstract class AnimatedEntity extends Entity{
    public AnimatedEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);

    }
    public abstract void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
}
