package Test1;

import org.newdawn.slick.state.*;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.logging.Level;

import java.util.logging.Logger;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

import org.newdawn.slick.AppGameContainer;

import org.newdawn.slick.BasicGame;

import org.newdawn.slick.Font;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Graphics;

import org.newdawn.slick.Image;

import org.newdawn.slick.Input;

import org.newdawn.slick.SlickException;

import org.newdawn.slick.SpriteSheet;

import org.newdawn.slick.TrueTypeFont;

import org.newdawn.slick.geom.Rectangle;

import org.newdawn.slick.geom.Shape;

import org.newdawn.slick.state.BasicGameState;

import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import org.newdawn.slick.tiled.TiledMap;
import org.w3c.dom.css.Rect;

class blocked {

    public static boolean[][] blocked;

    public static boolean[][] getblocked() {

        return blocked;

    }
};

public class Main_wavering extends BasicGameState {

    public Player player;
    public Item healthpotion, healthpotion1;
//    public Item1 speedpotion, speedpotion1;
    public Itemwin antidote;
    public Ninja stormy, daniel;
    public Enemy flava, flav;
    public Orb magic8ball, orb1;

    public ArrayList<Item> stuff = new ArrayList();

//    public ArrayList<Item1> stuff1 = new ArrayList();
    public ArrayList<Itemwin> stuffwin = new ArrayList();

    public ArrayList<Ninja> dojo = new ArrayList();

    public ArrayList<Enemy> bonez = new ArrayList();

    private boolean[][] hostiles;

    private static TiledMap grassMap;

    private static AppGameContainer app;

    private static Camera camera;

    public static int counter = 0;

	// player stuff
    // private Animation sprite, up, down, left, right, wait;
    /**
     *
     * The collision map indicating which tiles block movement - generated based
     *
     * on tile properties
     */
    // changed to match size of sprites & map
    private static final int SIZE = 32;

    // screen width and height won't change
    private static final int SCREEN_WIDTH = 1000;

    private static final int SCREEN_HEIGHT = 750;

    public Main_wavering(int xSize, int ySize) {

    }

    public void init(GameContainer gc, StateBasedGame sbg)
            throws SlickException {

        gc.setTargetFrameRate(60);

        gc.setShowFPS(false);

		// *******************
        // Scenerey Stuff
        // ****************
        grassMap = new TiledMap("res/Fontana_7_first_map.tmx");

		// Ongoing checks are useful
        //System.out.println("Tile map is this wide: " + grassMap.getWidth());
        camera = new Camera(gc, grassMap);

		// *********************************************************************************
        // player stuff --- these things should probably be chunked into methods
        // and classes
        // *********************************************************************************
		// *****************************************************************
        // Obstacles etc.
        // build a collision map based on tile properties in the TileD map
        blocked.blocked = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        player = new Player();

		// System.out.println("Map height:" + grassMap.getHeight());
        // System.out.println("Map width:" + grassMap.getWidth());
        // There can be more than 1 layer. You'll check whatever layer has the
        // obstacles.
        // You could also use this for planning traps, etc.
        // System.out.println("Number of tile layers: "
        // +grassMap.getLayerCount());
        //System.out.println("The grassmap is " + grassMap.getWidth() + "by "
        //      + grassMap.getHeight());
        for (int layer = 0; layer < 12; layer++) {

            for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {

                for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {

				// int tileID = grassMap.getTileId(xAxis, yAxis, 0);
                    // Why was this changed?
                    // It's a Different Layer.
                    // You should read the TMX file. It's xml, i.e.,human-readable
                    // for a reason
                    int tileID = grassMap.getTileId(xAxis, yAxis, layer);

                    String value = grassMap.getTileProperty(tileID,
                            "blocked", "false");

                    if ("true".equals(value)) {

//                        System.out.println("The tile at x " + xAxis + " andy axis "
//                                + yAxis + " is blocked.");
                        blocked.blocked[xAxis][yAxis] = true;

                    }

                }

            }
        }

        //System.out.println("Array length" + blocked.blocked[0].length);
        // A remarkably similar process for finding hostiles
        hostiles = new boolean[grassMap.getWidth()][grassMap.getHeight()];

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!blocked.blocked[xBlock][yBlock]) {
                    if (yBlock % 7 == 0 && xBlock % 15 == 0) {
                        Item i = new Item(xAxis * SIZE, yAxis * SIZE);
                        stuff.add(i);
                        //stuff1.add(h);
                        hostiles[xAxis][yAxis] = true;
                    }
                }
            }
        }

        for (int xAxis = 0; xAxis < grassMap.getWidth(); xAxis++) {
            for (int yAxis = 0; yAxis < grassMap.getHeight(); yAxis++) {
                int xBlock = (int) xAxis;
                int yBlock = (int) yAxis;
                if (!blocked.blocked[xBlock][yBlock]) {
////                    if (xBlock % 9 == 0 && yBlock % 25 == 0) {
//                        Item1 h = new Item1(xAxis * SIZE, yAxis * SIZE);
//                        //	stuff.add(i);
//                        stuff1.add(h);
//                        hostiles[xAxis][yAxis] = true;
//                    }
                }
            }
        }

        healthpotion = new Item(100, 100);
        healthpotion1 = new Item(450, 400);
        stormy = new Ninja(0, 0);
        daniel = new Ninja(124, 254);
        dojo.add(stormy);
        dojo.add(daniel);
        flava = new Enemy(300, 300);
        flav = new Enemy(256, 256);
        bonez.add(flava);
        bonez.add(flav);
        stuff.add(healthpotion);
        stuff.add(healthpotion1);
        magic8ball = new Orb((int) player.x + 5, (int) player.y - 10);
        orb1 = new Orb((int) player.x + 5, (int) player.y - 10);

//        speedpotion = new Item1(100, 150);
//        speedpotion1 = new Item1(450, 100);
//        stuff1.add(speedpotion);
//        stuff1.add(speedpotion1);
        antidote = new Itemwin(3004, 92);
        stuffwin.add(antidote);
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
            throws SlickException {

        camera.centerOn((int) player.x, (int) player.y);

        camera.drawMap();

        camera.translateGraphics();

		// it helps to add status reports to see what's going on
        // but it gets old quickly
        //System.out.println("Current X: " +player.x + " \n Current Y: "+ y);
        player.sprite.draw((int) player.x, (int) player.y);

		//g.drawString("x: " + (int)player.x + "y: " +(int)player.y , player.x, player.y - 10);
//        g.drawString("Health: " + player.health / 100000, camera.cameraX + 10,
//                camera.cameraY + 10);
        g.drawString("speed: " + (int) (player.speed * 10), camera.cameraX + 10,
                camera.cameraY + 25);

        //g.draw(player.rect);
        g.drawString("time passed: " + counter / 1000, camera.cameraX + 600, camera.cameraY);
        // moveenemies();

        for (Item i : stuff) {
            if (i.isvisible) {
                i.currentImage.draw(i.x, i.y);
                // draw the hitbox
                //g.draw(i.hitbox);

            }
        }

        //stormy.currentImage.draw(stormy.x,stormy.y);
        //daniel.currentImage.draw(daniel.x,daniel.y);
        for (Enemy e : bonez) {
            if (e.isAlive) {
                e.currentanime.draw(e.Bx, e.By);
                //draw the hitbox
                //g.draw(e.hitbox);

            }
        }

//        for (Item1 h : stuff1) {
//            if (h.isvisible) {
//                h.currentImage.draw(h.x, h.y);
//				// draw the hitbox
//                //g.draw(h.hitbox);
//
//            }
//        }
        for (Itemwin w : stuffwin) {
            if (w.isvisible) {
                w.currentImage.draw(w.x, w.y);
                // draw the hitbox
                //g.draw(w.hitbox);

            }
            if (magic8ball.isIsVisible()) {
                magic8ball.orbpic.draw(orb1.getX(), orb1.getY());
                orb1.orbpic.draw(orb1.getX(), orb1.getY());
            }
        }

    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta)
            throws SlickException {

        counter += delta;

        Input input = gc.getInput();

        float fdelta = delta * player.speed;

        player.setpdelta(fdelta);

        double rightlimit = (grassMap.getWidth() * SIZE) - (SIZE * 0.75);

        // System.out.println("Right limit: " + rightlimit);
        float projectedright = player.x + fdelta + SIZE;

        boolean cangoright = projectedright < rightlimit;

        // there are two types of fixes. A kludge and a hack. This is a kludge.
        if (input.isKeyDown(Input.KEY_UP)) {
            player.sprite = player.up;
            float fdsc = (float) (fdelta - (SIZE * .15));
            if (!(isBlocked(player.x, player.y - fdelta) || isBlocked((float) (player.x + SIZE + 1.5), player.y - fdelta))) {
                player.sprite.update(delta);
                // The lower the delta the slower the sprite will animate.
                player.y -= fdelta;
            }

        } else if (input.isKeyDown(Input.KEY_DOWN)) {

            player.sprite = player.down;

            if (!isBlocked(player.x, player.y + SIZE + fdelta)
                    || !isBlocked(player.x + SIZE - 1, player.y + SIZE + fdelta)) {

                player.sprite.update(delta);

                player.y += fdelta;

            }

        } else if (input.isKeyDown(Input.KEY_LEFT)) {

            player.sprite = player.left;

            if (!(isBlocked(player.x - fdelta, player.y) || isBlocked(player.x
                    - fdelta, player.y + SIZE - 1))) {

                player.sprite.update(delta);

                player.x -= fdelta;

            }

        } else if (input.isKeyDown(Input.KEY_RIGHT)) {

            player.sprite = player.right;

            // the boolean-kludge-implementation
            if (cangoright
                    && (!(isBlocked(player.x + SIZE + fdelta,
                            player.y) || isBlocked(player.x + SIZE + fdelta, player.y
                            + SIZE - 1)))) {

                player.sprite.update(delta);
                player.x += fdelta;
            } // else { System.out.println("Right limit reached: " +
            // rightlimit);}
        } else if (input.isKeyDown(Input.KEY_SPACE)) {
            //otb1.setLocation(player.x, player.y);
            orb1.setX((int) player.x);
            orb1.setY((int) player.y);
            orb1.hitbox.setCenterX(orb1.getX());
            orb1.hitbox.setCenterY(orb1.getY());
            orb1.setIsVisible(!orb1.isIsVisible());
               magic8ball.setIsVisible(true);
        }
        if (orb1.isIsVisible()) {
            if (orb1.gettimeExists() > 0) {
                if (orb1.getDirection() == 0) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() - 5);
                } else if (orb1.getDirection() == 2) {
                    orb1.setX((int) player.x);
                    orb1.setY(orb1.getY() + 5);
                } else if (orb1.getDirection() == 3) {
                    orb1.setX(orb1.getX() - 5);
                    orb1.setY(orb1.getY());
                } else if (orb1.getDirection() == 1) {
                    orb1.setX(orb1.getX() + 5);
                    orb1.setY(orb1.getY());
                }
                orb1.hitbox.setX(orb1.getX());
                orb1.hitbox.setY(orb1.getY());
                orb1.countdown();
            } else {
                orb1.setIsVisible(false);
            }
        }
        
        player.rect.setLocation(player.getPlayershitboxX(),
                player.getPlayershitboxY());

        for (Item i : stuff) {

            if (player.rect.intersects(i.hitbox)) {
                //System.out.println("yay");
                if (i.isvisible) {

                    player.health += 10000;
                    i.isvisible = false;
                }

            }
        }
        for (Ninja n : dojo) {

            if (player.rect.intersects(n.hitbox)) {
                //System.out.println("yay");
                if (n.isvisible) {

                    player.health += 10000;
                    n.isvisible = false;
                }

            }
        }

//        for (Item1 h : stuff1) {
//
//            if (player.rect.intersects(h.hitbox)) {
//                //System.out.println("yay");
//                if (h.isvisible) {
//
//                    player.speed += .1f;
//                    h.isvisible = false;
//                }
//
//            }
//        }
        for (Itemwin w : stuffwin) {

            if (player.rect.intersects(w.hitbox)) {
                //System.out.println("yay");
                if (w.isvisible) {
                    w.isvisible = false;
                    makevisible();
                    sbg.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));

                }

            }
        }

        player.health -= counter / 100000;
//        if (player.health <= 0) {
//            makevisible();
//            sbg.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
//        }

    }

    public int getID() {

        return 1;

    }

    public void makevisible() {
//        for (Item1 h : stuff1) {
//
//            h.isvisible = true;
//        }

        for (Item i : stuff) {

            i.isvisible = true;
        }
    }

    private boolean isBlocked(float tx, float ty) {

        int xBlock = (int) tx / SIZE;

        int yBlock = (int) ty / SIZE;

        return blocked.blocked[xBlock][yBlock];

        // this could make a better kludge
    }

}
