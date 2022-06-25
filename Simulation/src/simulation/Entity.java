package simulation;

import javafx.scene.image.Image;

/**
 * This class handles a lot of the logic for any type of Object that might live in our world.
 *
 * @author Mr. Smithe
 */
public class Entity extends Sprite{

    private boolean died;
    private int health;
    /**
     * Create a new Entity. This will also add this Entity to the world and set the image.
     *
     * @param world the world this Entity lives in
     * @param x the x-coordinate of this Entity
     * @param y the y-coordinate of this Entity
     * @param image the image that represents this Entity
     */
    public Entity(World world, int x, int y, int health, Image image){
        super(world, x, y, image);
        world.add_to_list(this);
        this.died = false;
        this.health = health;
    }

    /**
     * This method is called by the World class once per second.
     *
     * This method should be overridden by any child classes.
     */
    public void act() {

    }

    /**
     * Returns a String representation of this Entity.
     * @return a String representation of this Entity.
     */
    @Override
    public String toString() {
        return "Entity{x=" + getX() + ", y=" + getY() + '}';
    }

    /**
     * Delete this Entity from the world.
     */
    public void destroy(){
        getWorld().remove(this);
        died = true;
    }

    /**
     * Returns the health of this Entity.
     *
     * @return the health of this Entity.
     */
    public int getHealth(){
        return health;
    }

    /**
     * decreases the health of this Entity by the amount specified.
     *
     * @param amount the amount to decrease the health by.
     */
    public void decreaseHealth(int amount){
        health -= amount;
    }

    /**
     * increases the health of this Entity by the amount specified.
     *
     * @param amount the amount to increase the health by.
     */
    public void increaseHealth(int amount){
        health += amount;
    }

    /**
     * Returns whether this Entity has died.
     *
     * @return whether this Entity has died.
     */
    public boolean isDead(){
        return died;
    }


}
