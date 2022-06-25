package simulation;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * This class represents an Animal.
 *
 * @author Mr. Smithe
 */
public class Animal extends Entity{

    // The class of the prey this animal eats.
    private final Class<?> food;
    // The food at current position, if any.
    private Entity currentFood;
    // The animal's age.
    private int age;
    // The image of the adult animal.
    private final Image adult;

    //this handles smooth movement from one tile to the next.
    private final TranslateTransition mover;

    /**
     * Constructor for the Animal class.
     *
     * @param world The world in which the animal lives.
     * @param x The x coordinate of the animal.
     * @param y The y coordinate of the animal.
     * @param health  The health of the animal.
     * @param age   The age of the animal.
     * @param food  The food the animal eats.
     * @param adult The image of the adult animal.
     * @param baby  The image of the baby animal.
     */
    public Animal(World world, int x, int y, int health, int age, Class<?> food, Image adult, Image baby) {
        // Use different images based on the age of the animal.
        super(world, x, y, health, age >= 10 ? adult : baby);
        this.age = age;
        this.food = food;
        this.adult = adult;

        mover = new TranslateTransition(Duration.millis(500), getImageView());
        mover.setCycleCount(1);
    }

    /**
     * Randomly wandering around the world.
     *
     * @param grid available locations
     */
    public void wander(boolean[][] grid) {
        //TODO implement wander behaviour
        int x = getX();
        int y = getY();
        int newx = x; // new x coordinate
        int newy = y; // new y coordinate
        int t = 0; // number of tries
        do {
            switch (Simulator.getRandomInt(0, 5)) {
                case 0:
                    // move down
                    newx = x + 1;
                    newy = y;
                    break;
                case 1:
                    // move right
                    newx = x;
                    newy = y + 1;
                    break;
                case 2:
                    // move up
                    newx = x - 1;
                    newy = y;
                    break;
                case 3:
                    // move left
                    newx = x;
                    newy = y - 1;
                    break;
                case 4:
                    // stay put
                    newx = x;
                    newy = y;
                    break;
            }
            t++;
            if(t>10){
                // if we have tried more than 10 times and still haven't found a valid location just kill the animal
                destroy();
                return;
            }
        }while(!getWorld().isValidLocation(newx, newy) || grid[newx][newy]);
        // if we have found a valid location, move there
        moveTo(newx, newy);
        // each time the animal wanders, it loses 10 health
        decreaseHealth(10);
    }

    /**
     * Eat the food
     *
     * @param e the food
     */
    public void eat(Entity e) {
        //TODO implement eat behaviour
        increaseHealth(e.getHealth());
        e.destroy();
    }

    /**
     * Check if the animal is dead because of starvation
     *
     */
    public boolean isAlive() {

        return getHealth() > 0;
    }

    public boolean isHealthy() {
        return getHealth() > 50;
    }

    /**
     * Seek for food
     *
     * @param grid unoccupied locations
     */
    public void seekFood(boolean[][] grid) {
        //TODO implement seekFood behaviour
        int x = getX();
        int y = getY();
        ArrayList<Entity> foods = getWorld().getEntities(food);
        //find the closest food
        Entity closest = null;
        int dist = Integer.MAX_VALUE;
        for(Entity f : foods){
            int d = Math.abs(f.getX()-x) + Math.abs(f.getY()-y);
            if(!grid[f.getX()][f.getY()] && d <= dist){
                dist = d;
                closest = f;
            }
        }
        //if we find a food, move towards it, otherwise keep wandering
        if(closest != null){
            int dx = 0, dy = 0;
            // dx: unit vector in vertical direction
            // dy: unit vector in horizontal direction
            if(closest.getX()-x != 0)
                dx = (closest.getX()-x)/Math.abs(closest.getX()-x);
            if(closest.getY()-y != 0)
                dy = (closest.getY()-y)/Math.abs(closest.getY()-y);
            // tries to move towards the closest food, if it's blocked by fence or another animal, it will wander
            if(dx!=0 && !grid[x+dx][y] && getWorld().isValidLocation(x+dx, y))
                moveTo(x+dx, y);
            else if(dy!=0 && !grid[x][y+dy] && getWorld().isValidLocation(x, y+dy))
                moveTo(x, y+dy);
            else {
                wander(grid);
                return;
            }
            // each time the animal seeks for food, it loses 5 health
            decreaseHealth(5);
        }else{
            wander(grid);
        }
    }

    /**
     * Animal's actions in each turn
     *
     * @param grid unoccupied locations
     */
    public void act(boolean[][] grid) {
        if(isOnFood()){
            //System.out.println(this + " just ate " + currentFood);
            //if it's on food, it eats the food
            eat(currentFood);
            //reproduce only if it's at least 10 years old
            if(age >= 10)
                breed();
            //TODO add health
        }else{
            // if the animal still has a lot of health, it just wanders around, otherwise it seeks for food
            if(isHealthy()) {
                wander(grid);
            }
            else{
                seekFood(grid);
            }
            //TODO maybe seek out grass?
            //TODO prevent sheep from wandering off the world.
        }

        // if the health of the animal drops below 0, it dies of starvation
        if(!isAlive()){
            destroy();
            System.out.println(this+ " died of starvation");
            return;
        }
        // grow older every time they act, and if they grow older than 20, they die of old age
        age++;
        if(age > 20){
            destroy();
            System.out.println(this+ " died of old age");
            return;
        }
        grid[getX()][getY()] = true;
        // if the baby reaches age 10, it becomes an adult
        if(age == 10){
            setImage(adult);
        }
    }

    /**
     * check if the animal is on food
     */
    public boolean isOnFood(){
        currentFood = getWorld().getOneEntityAt(getX(), getY(), food);
        return currentFood != null;
    }
    /**
     * Breed a baby animal
     */
    public void breed(){
        //TODO implement reproduce behaviour
        try {
            this.getClass().getConstructor(World.class, int.class, int.class, int.class).newInstance(getWorld(), getX(), getY(), 1);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error reproducing");
        }
    }

    /**
     * Animates the Entity moving to the given location in half a second.
     *
     * @param x the new x-coordinate
     * @param y the new y-coordinate
     */
    public void moveTo(int x, int y){
        //set the location to move to
        mover.setByX((x - getX()) * World.CELL_SIZE);
        mover.setByY((y - getY()) * World.CELL_SIZE);

        //animate the movement
        mover.play();

        //update coordinates
        setX(x);
        setY(y);
    }
}
