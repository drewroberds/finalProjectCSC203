import processing.core.PImage;

import java.util.List;

public class YETI_SLEEPYTIME extends Entity{
    private static final int YETI_SLEEPYTIME_NUM_PROPERTIES = 0;
    public static final String YETI_SLEEPYTIME_KEY = "yetisleep";
    public YETI_SLEEPYTIME(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
/*    public static void parseYETI_SLEEPYTIME(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == YETI_SLEEPYTIME_NUM_PROPERTIES) {
            YETI_SLEEPYTIME entity = YETI_SLEEPYTIME.createYETI_SLEEPYTIME(id, pt, imageStore.getImageList(YETI_SLEEPYTIME.YETI_SLEEPYTIME_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", YETI_SLEEPYTIME.YETI_SLEEPYTIME_KEY, YETI_SLEEPYTIME_NUM_PROPERTIES));
        }
    }*/
/*    public static YETI_SLEEPYTIME createYETI_SLEEPYTIME(String id, Point position, List<PImage> images) {
        return new YETI_SLEEPYTIME(id, position, images, 0, 0, 0, 0, 0, 0);
    }*/
}
