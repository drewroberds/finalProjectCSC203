import processing.core.PImage;

import java.util.List;

public class Tree extends Plant {
    public static final double TREE_ANIMATION_MAX = 0.600;
    public static final double TREE_ANIMATION_MIN = 0.050;
    public static final double TREE_ACTION_MAX = 1.400;
    public static final double TREE_ACTION_MIN = 1.000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final int TREE_ANIMATION_PERIOD = 0;
    public static final int TREE_ACTION_PERIOD = 1;
    public static int TREE_HEALTH = 2;
    public static int TREE_NUM_PROPERTIES = 3;
    public static final String TREE_KEY = "tree";

    public Tree(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }

    @Override
    public void executeActivity(EventScheduler scheduler, ImageStore imageStore, WorldModel world) {
        if (!transformPlant(world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        }
    }

    public static void parseTree(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Tree entity = Tree.createTree(id, pt, Double.parseDouble(properties[TREE_ACTION_PERIOD]), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), imageStore.getImageList(Tree.TREE_KEY));
            world.tryAddEntity(entity);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Tree.TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore, 0), this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction(this, world, imageStore, 0), this.getAnimationPeriod());
    }

    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
    }

    @Override
    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Stump.createStump(Stump.STUMP_KEY + "_" + this.getId(), getPosition(), imageStore.getImageList(Stump.STUMP_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(stump);

            return true;
        }

        return false;
    }

}

