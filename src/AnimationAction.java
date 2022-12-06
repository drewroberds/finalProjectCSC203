public class AnimationAction extends Action{
    private AnimatedEntity animatedEntity;

    public AnimationAction(AnimatedEntity animatedEntity, WorldModel world, ImageStore imageStore, int repeatCount) {
        super(animatedEntity, world, imageStore, repeatCount);
        this.animatedEntity = animatedEntity;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        animatedEntity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.animatedEntity, new AnimationAction(this.animatedEntity, null, null, repeatCount), this.animatedEntity.getAnimationPeriod());
        }
    }
}
