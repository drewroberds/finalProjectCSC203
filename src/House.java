import processing.core.PImage;

import java.util.List;

public class House extends Entity{
    private static final int HOUSE_NUM_PROPERTIES = 0;
    public static final String HOUSE_KEY = "house";

    public House(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        super(id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod, health, healthLimit);
    }
    public static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            House entity = House.createHouse(id, pt, imageStore.getImageList(HOUSE_KEY));
            world.tryAddEntity(entity);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House(id, position, images, 0, 0, 0, 0, 0, 0);
    }
}
