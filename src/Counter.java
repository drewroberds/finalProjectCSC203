import processing.core.PImage;

import java.util.List;

public class Counter extends ActiveEntity{

    int escapeCounter = 10;

    public Counter(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        escapeCounter--;
        if(escapeCounter == 0){
            DUDEAFFLICTED dude = new DUDEAFFLICTED("dudeAfflicted", new Point(10, 3), imageStore.getImageList("dudeAfflicted"), 1,0,.35, 1, 1,1);
            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);
        }
        this.scheduleActions(scheduler, world, imageStore);
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
    }
}
