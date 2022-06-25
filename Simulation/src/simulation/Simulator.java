package simulation;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

/**
 * This class runs our Simulation. It also has some basic helper methods.
 *
 * @author Mr. Smithe
 */
public class Simulator extends Application {

    private static Random random = new Random();

    private World world;
    private long lastRun = 0;

    /**
     * Launches the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sets up the visuals.
     *
     * @param primaryStage the Program's Stage
     */
    @Override
    public void start(Stage primaryStage) {
        //set the scene
        world = new World();
        Scene scene = new Scene(world.getRoot(), World.MAX_WIDTH, World.MAX_HEIGHT);

        //add some entities
        addRandomEntities(10, Wolf.class);
        addRandomEntities(20, Sheep.class);
        addRandomEntities(100, Grass.class);

        //this timer calls the act method of the world once per second.
        AnimationTimer at = new AnimationTimer() {
            @Override
            public void handle(long now) {

                //now is in nanoseconds.
                if (now - lastRun > 1000000000) {
                    lastRun = now;
                    world.act();
                }
            }
        };
        at.start();

        primaryStage.setTitle("Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Adds a number of entities of the specified class to the world at random locations.
     *
     * @param n the number of entities to add
     * @param c the class of the entities to add
     */
    public void addRandomEntities(int n, Class c){
        boolean[][] occupied = new boolean[World.ROW][World.COL];       //array of occupied locations
        int x, y;
        for (int i = 0; i < n; i++){
            do {
                x = getRandomInt(0, World.ROW);    //get random x location
                y = getRandomInt(0, World.COL);    //get random y location
            }while(!world.isValidLocation(x, y) || occupied[x][y]);   //make sure location is valid and not occupied
            occupied[x][y] = true;
            try {
                c.getConstructor(World.class, int.class, int.class, int.class).newInstance(world, x, y, 10);   //create new entity
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Generates a random number between the given range [a,b).
     *
     * @param min the minimum number possible to generate
     * @param max the maximum number possible to generate, not included
     * @return a random number within the given range
     */
    public static int getRandomInt(int min, int max){
        if(min > max){
            //ensure parameters are in correct order.
            return getRandomInt(max, min);
        }
        return random.nextInt(max - min) + min;
    }
}
