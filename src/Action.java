/**
 * An action that can be taken by an entity
 */
public abstract class Action {
    public Entity entity;
    public WorldModel world;
    public ImageStore imageStore;
    public int repeatCount;
    public Action(Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }
    public abstract void executeAction(EventScheduler scheduler);
}
