//import processing.core.PImage;
//
//import java.util.List;
//
//public class EscapeCounter extends ActiveEntity{
//    int escapeTimer;
//    public EscapeCounter(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit, int escapeTimer) {
//        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
//    }
//
//    @Override
//    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
//        if(escapeTimer == 0){
//            DUDEAFFLICTED dude = new DUDEAFFLICTED(DUDEAFFLICTED.DUDEAFFLICTED_KEY, this.getPosition(), this.getImages(), 1, 0, 1, 1, 1, 1);
//            world.removeEntity(scheduler, this);
//            scheduler.unscheduleAllEvents(this);
//        }
//        escapeTimer--;
//    }
//
//    @Override
//    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
//        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
//    }
//}
