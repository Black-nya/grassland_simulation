package simulation;

import javafx.scene.image.Image;

/**
 * Fence class.
 *
 * @author Layton Zhou
 */
public class Fence extends Sprite{
    private final static Image image = new Image("images/fence.png");

    /**
     * Constructor for Fence
     * @param world the world the fence is in
     * @param x x-coordinate of the fence
     * @param y y-coordinate of the fence
     */
    public Fence(World world, int x, int y){
        super(world, x, y, image);
    }
}
