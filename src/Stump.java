import processing.core.PImage;

import java.util.List;

public class Stump extends Entity{
    private static final int STUMP_NUM_PROPERTIES = 0;
    public static final String STUMP_KEY = "stump";
    public Stump(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Stump entity = Stump.createStump(id, pt, imageStore.getImageList(Stump.STUMP_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Stump.STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }
    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images, 0, 0, 0, 0, 0, 0);
    }
}
