package simulation;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * This class represents a World.
 *
 * @author Mr. Smithe
 */
public class World {

    Group root = new Group();

    //a flexible array that doesn't have to have a fixed length.
    private ArrayList<Entity> entities = new ArrayList<>();

    //separate list for different types of entities
    private ArrayList<Wolf> wolves = new ArrayList<>();
    private ArrayList<Sheep> sheeps = new ArrayList<>();
    private ArrayList<Grass> grasses = new ArrayList<>();

    //dimensions of our world
    public static final int MAX_WIDTH = 800;
    public static final int MAX_HEIGHT = 800;
    public static final int CELL_SIZE = 50;
    public static final int ROW = MAX_HEIGHT / CELL_SIZE;
    public static final int COL = MAX_WIDTH / CELL_SIZE;

    //the map is made up of a grid of squares.
    private Rectangle[][] map = new Rectangle[ROW][COL];

    //the location of the fences
    private boolean[][] fences = new boolean[ROW][COL];

    //number of fences to place
    private final int numFences = 50;
    /**
     * Constructs the World.
     */
    public World(){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                Rectangle rectangle = new Rectangle(CELL_SIZE, CELL_SIZE);
                rectangle.setX(i * CELL_SIZE);
                rectangle.setY(j * CELL_SIZE);

                rectangle.setFill(Color.GREEN);
                // uncomment to show the grid
                //rectangle.setStroke(Color.BLACK);

                map[i][j] = rectangle;
                root.getChildren().add(rectangle);
            }
        }
        // randomly place some fences
        int x, y;
        for (int i = 0; i < numFences; i++) {
            do {
                x = Simulator.getRandomInt(0, World.ROW);
                y = Simulator.getRandomInt(0, World.COL);
            } while (fences[x][y]);
            fences[x][y] = true;
            new Fence(this, x, y);
        }
    }

    /**
     * Calls the act method of each Entity currently in the World.
     */
    public void act() {
        // keep track of the next location sheep and wolves can move to
        boolean[][] sheeps = new boolean[ROW][COL];
        boolean[][] wolves = new boolean[ROW][COL];
        // fix the order of display so that wolves are drawn on top of sheep and sheep are drawn on top of grass
        // for sheep and wolves, we also check whether they are on top of the food.
        // If they are, they will stay in the same place this turn to eat the food, we set the location as occupied so other sheep or wolves cannot move to this location.
        for(Grass grass : grasses){
            grass.getNode().toFront();
        }
        for(Sheep s: this.sheeps){
            if(s.isOnFood()){
                sheeps[s.getX()][s.getY()] = true;
            }
            s.getNode().toFront();
        }

        for(Wolf w: this.wolves){
            if(w.isOnFood()){
                wolves[w.getX()][w.getY()] = true;
            }
            w.getNode().toFront();
        }
        // Wolf should act first, sheep should act next, and grass should act last.
        // Iterate through the copy of current wolves, sheep and grasses to avoid ConcurrentModificationException and prevent newly added entities from acting.
        // Also, check if the entity is already died to prevent dead entities from acting.
        for(Wolf w: (ArrayList<Wolf>)this.wolves.clone()){
            if(!w.isDead())
                w.act(wolves);
        }
        for(Sheep s: (ArrayList<Sheep>)this.sheeps.clone()){
            if(!s.isDead())
                s.act(sheeps);
        }
        for(Grass g: (ArrayList<Grass>)this.grasses.clone()){
            if(!g.isDead())
                g.act();
        }
    }

    /**
     * Adds an Entity to entity lists.
     *
     * @param entity the Entity to add
     */
    public void add_to_list(Entity entity){
        if(entity instanceof Wolf){
            wolves.add((Wolf) entity);
        }else if(entity instanceof Sheep){
            sheeps.add((Sheep) entity);
        }else if(entity instanceof Grass){
            grasses.add((Grass) entity);
        }
        entities.add(entity);
    }

    /**
     * Adds the node of the entity to the root.
     *
     * @param node the node to add
     */
    public void add_to_root(Node node){
        root.getChildren().add(node);
    }

    /**
     * Removes a given Entity from the World.
     *
     * @param entity the Entity to remove
     */
    public void remove(Entity entity){
        if(entity instanceof Wolf){
            wolves.remove((Wolf) entity);
        }else if(entity instanceof Sheep){
            sheeps.remove((Sheep) entity);
        }else if(entity instanceof Grass){
            grasses.remove((Grass) entity);
        }
        entities.remove(entity);
        root.getChildren().remove(entity.getNode());
    }

    /**
     * Returns a list of all entities at a given location.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the List of Entities at the given location
     */
    public ArrayList<Entity> getEntitiesAt(int x, int y) {
        ArrayList<Entity> answer = new ArrayList<Entity>();

        for(Entity entity : entities){
            if(entity.getX() == x && entity.getY() == y){
                answer.add(entity);
            }
        }

        return answer;
    }

    /**
     * Returns the first Entity at the given location of the given type.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param c the class of the Entity we are looking for
     * @return the Entity, if one exists. null otherwise.
     */
    public Entity getOneEntityAt(int x, int y, Class c){
        for(Entity entity : entities){
            if(entity.getClass() == c && entity.getX() == x && entity.getY() == y){
                return entity;
            }
        }
        return null;
    }

    /**
     * Returns a list of all entities of the given type.
     * @param c the class of the Entity we are looking for
     * @return the List of Entities
     */
    public ArrayList<Entity> getEntities(Class c){
        ArrayList<Entity> answer = new ArrayList<Entity>();
        for(Entity entity : entities){
            if(entity.getClass() == c){
                answer.add(entity);
            }
        }
        return answer;
    }

    /**
     * Returns the container for all visuals of this World.
     *
     * @return the root node
     */
    public Group getRoot(){
        return root;
    }

    /**
     * Checks if the given coordinates are within the bounds of the World and not blocked by a fence.
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the coordinates are valid, false otherwise
     */
    public boolean isValidLocation(int x, int y){
        return x >= 0 && x < ROW && y >= 0 && y < COL && !fences[x][y];
    }
}
