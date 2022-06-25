package simulation;

import javafx.scene.image.Image;

public class Wolf extends Animal {
    // Image for adult wolf
    private static final Image wolfImage = new Image("images/wolf (Custom).png");
    // Image for baby wolf
    private static final Image baby_wolfImage = new Image("images/baby_wolf (Custom).png");
    // The health a wolf initially has
    private static final int health = 100;

    /**
     * Constructor for the wolf
     *
     * @param world The world the wolf is in
     * @param x - the x coordinate of the wolf
     * @param y - the y coordinate of the wolf
     * @param age - the age of the wolf
     */
    public Wolf (World world, int x, int y, int age) {
        super(world, x, y, health, age, Sheep.class, wolfImage, baby_wolfImage);
    }
    @Override
    public String toString() {
        return "Wolf{" + super.toString() + "}";
    }
}
