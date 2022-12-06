import processing.core.PImage;

import java.util.List;

abstract class MovableEntity extends ActiveEntity {
    public MovableEntity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    public abstract boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler);
}
