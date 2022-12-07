import processing.core.PImage;

import java.util.List;

public class YETI_CAVE extends Entity{
    private static final int CAVE_NUM_PROPERTIES = 0;
    public static final String CAVE_KEY = "cave";

    public YETI_CAVE(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public static void parseYETI_CAVE(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == CAVE_NUM_PROPERTIES) {
            YETI_CAVE entity = YETI_CAVE.createYETI_CAVE(id, pt, imageStore.getImageList(CAVE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", CAVE_KEY, CAVE_NUM_PROPERTIES));
        }
    }
    public static YETI_CAVE createYETI_CAVE(String id, Point position, List<PImage> images) {
        return new YETI_CAVE(id, position, images, 0, 0, 0, 0, 0, 0);
    }
}
