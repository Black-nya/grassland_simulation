package simulation;

import javafx.scene.image.Image;

/**
 * This class represents a sheep. The logic is only partially implemented.
 *
 * @author Mr. Smithe
 */
public class Sheep extends Animal{

    // The image for adult sheep
    private static final Image sheepImage = new Image("images/sheep.png");
    // The image for baby sheep
    private static final Image baby_sheepImage = new Image("images/baby_sheep (Custom).png");
    // The health a sheep initially has
    private static final int health = 100;
    /**
     * Constructor for Sheep.
     *
     * @param world this Sheep's world
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param age the age of this Sheep
     */
    public Sheep (World world, int x, int y, int age) {
        super(world, x, y, health, age, Grass.class, sheepImage, baby_sheepImage);
    }

    /**
     * Returns a String representation of this Sheep.
     *
     * @return a String representation of this Sheep
     */
    @Override
    public String toString() {
        return "Sheep{" + super.toString() + "}";
    }

}
