package simulation;

import javafx.scene.image.Image;

/**
 * This class represents grass. Grass can spread to neighbouring tiles.
 *
 * @author Mr. Smithe
 */
public class Grass extends Entity {

    //all grass share one image
    private static final Image grassImage = new Image("images/grass.png");
    //the health sheep can get by eating grass
    private static final int health = 5;
    /**
     * Construct a new Grass object in the given world, at the given location.
     *
     * @param world the World where this grass will live.
     * @param x the x-coordinate of the grass
     * @param y the y-coordinate of the grass
     * @param age unused, just to keep consistent with other entities constructors
     */
    public Grass(World world, int x, int y, int age) {
        super(world, x, y, health, grassImage);
    }

    /**
     * Grass might grow.
     */
    @Override
    public void act(){
        //grass has a 10% chance to try to spread to a neighbouring tile.
        if(Simulator.getRandomInt(1, 11) == 1){
            int x = getX();
            int y = getY();
            switch (Simulator.getRandomInt(0, 4)){
                case 0: x++; break;
                case 1: y++; break;
                case 2: x--; break;
                case 3: y--; break;
            }
            //spawn only if the tile is empty and not out of bounds
            if(getWorld().isValidLocation(x, y) && getWorld().getOneEntityAt(x, y, Grass.class) == null){
                new Grass(getWorld(), x, y, 10);
            }
        }
    }

    /**
     * Returns a String representation of this Grass Object.
     *
     * @return the String representation of this Grass
     */
    @Override
    public String toString() {
        return "Grass{" + super.toString() + "}";
    }


}
