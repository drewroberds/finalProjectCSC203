import javax.accessibility.AccessibleBundle;

public class ActivityAction extends AnimationAction{
    private ActiveEntity activeEntity;

    public ActivityAction(ActiveEntity activeEntity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(activeEntity, world, imageStore, repeatCount);
        this.activeEntity = activeEntity;
    }


    @Override
    public void executeAction(EventScheduler scheduler) {
        activeEntity.executeActivity(scheduler, imageStore, world);
    }
}
