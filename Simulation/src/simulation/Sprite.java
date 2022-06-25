package simulation;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Sprite class.
 *
 * @author Mr. Smithe
 */

public class Sprite {
    //instance variables
    private final World world;

    private int x;
    private int y;

    private Image image; //the image just points to the file
    private final ImageView imageView; //this is the visual part

    /**
     * Creates a new Sprite.
     *
     * @param world the World that contains this Sprite
     * @param x the x-coordinate of this Sprite
     * @param y the y-coordinate of this Sprite
     * @param image the Image of this Sprite
     */
    public Sprite(World world, int x, int y, Image image) {
        this.world = world;
        this.image = image;
        this.x = x;
        this.y = y;
        imageView = new ImageView(image);
        imageView.setX(x * World.CELL_SIZE);
        imageView.setY(y * World.CELL_SIZE);
        world.add_to_root(imageView);
    }

    /**
     * Returns the visual component of this Sprite.
     *
     * @return the visual component of this Sprite
     */
    public Node getNode() {
        return imageView;
    }

    /**
     * Returns the current x-coordinate of the Sprite.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the current y-coordinate of the Sprite.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of this Sprite.
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this Sprite.
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the Image of this Sprite.
     * @param image the new Image
     */
    public void setImage(Image image){
        this.image = image;
        imageView.setImage(image);
    }

    /**
     * Returns the Imageview of this Sprite.
     *
     * @return the Imageview of this Sprite
     */
    public ImageView getImageView(){
        return imageView;
    }

    /**
     * Returns the World that contains this Sprite.
     *
     * @return this Sprite's World
     */
    public World getWorld(){
        return world;
    }
}
