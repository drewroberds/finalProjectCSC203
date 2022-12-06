import java.util.*;

import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public abstract class Entity {
//    public EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private double actionPeriod;
    private double animationPeriod;
    public int health;
    public int healthLimit;

    public Entity(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
//        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;
    }
    public static final Random rand = new Random();
    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.x, this.position.y, this.imageIndex);
    }
    public double getAnimationPeriod() {
                return this.animationPeriod;
    }

    public PImage getCurrentImage() {
        return this.images.get(this.imageIndex % this.images.size());
    }
//    public boolean adjacent(Point p1, Point p2) {
//        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
//    }
    public int getIntFromRange(int max, int min) {
//        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }
    public double getNumFromRange(double max, double min) {
//        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    public String getId(){ return this.id; }
    public void setId(String idKey){
        this.id = idKey;
    }
    public Point getPosition(){return this.position;}
    public void setPosition(Point pos){
        this.position = pos;
    }
    public int getHealth(){return this.health;}
    public void setHealth(int health){
        this.health = health; }
    public int getResourceLimit(){return this.resourceLimit;}
    public List<PImage> getImages() {return images;}
    public int getResourceCount() {return resourceCount;}
    public void increaseResourceCount(){this.resourceCount += 1;}
    public double getActionPeriod(){return  this.actionPeriod;}

    public int getHealthLimit() { return healthLimit; }
}
