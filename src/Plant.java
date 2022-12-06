import processing.core.PImage;

import java.util.List;

public abstract class Plant extends ActiveEntity{

    public Plant(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    public abstract boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore);
}
