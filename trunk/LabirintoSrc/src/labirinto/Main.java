/*
 * Main.java
 *
 * Created on April 22, 2007, 10:49 AM
 *
 * Parte do projeto Sphere Force
 * por Douglas Schmidt, Renato Euclides Silva e Mateus Balconi
 */

package labirinto;

/**
 * Imports to Main
 */
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;


public class Main extends DoubleBufferApplet implements Runnable, KeyListener{
    
    //constantes ambiente
    static final public int DELAY = 33;
    
    static final public float ACELER = 1.5f;
    static final public float ATRITO = 0.87f;
    
    //comandos
    static final public int UP = 0;
    static final public int DOWN = 1;
    static final public int LEFT = 2;
    static final public int RIGHT = 3;
    static final public int ESCAPE = 4;
    
    private boolean gameON;
    private Thread gameLoop;
    
    /** Vector [5] with:
     *0 = UP
     *1 = DOWN
     *2 = LEFT
     *3 = RIGHT
     *4 = ESCAPE
     */
    private boolean[] keyVector;
    
    private Esfera sphere;
    private Image sphereImage;
    
    private Image offScreen;
    
    /** Useless Main method */
    public Main () {
        
        sphere = new Esfera();
        
        keyVector = new boolean[5];
        
    }
    
    /** Starts Applet with page`s requiriment */
    public void start() {
        gameON = true;
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    /** Stop Applet when user leave the page */
    public void stop() {
        gameON = false;
    }
    
    /** Destroy Applet before leave the page */
    public void destroy() {
        
    }
    
    /** Init called in the very first applet`s run */
    public void init() {
                
        this.addKeyListener(this);
        
        //wait for all images get ready to show everthing synchronously
        MediaTracker loading = new MediaTracker(this);
        
        
        sphereImage = getImage(getDocumentBase(), "Esfera.png");
        loading.addImage(sphereImage, 0);
        
    }
    
    /** Paint all the images in the set, Applet`s default method */
    public void paint(Graphics g) {
        g.drawImage(sphereImage, (int) sphere.getX(), (int) sphere.getY(), this);
    }

    /** Thread method for the game Loop */
    public void run() {
        long startTime;
        startTime = System.currentTimeMillis();
        
        while(Thread.currentThread() == gameLoop) {
            sphere.refresh(keyVector);
            repaint();
            try {
                startTime += DELAY;
                Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
            }
            catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public void keyTyped(KeyEvent keyEvent) {
    }

    /** KeyPressed listener, set a vector with moving events */
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == keyEvent.VK_UP) {
            keyVector[UP] = true;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_DOWN) {
            keyVector[DOWN] = true;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            keyVector[LEFT] = true;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            keyVector[RIGHT] = true;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = true;        
        }
    }
    
    /** KeyReleased listener */
    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == keyEvent.VK_UP) {
            keyVector[UP] = false;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_DOWN) {
            keyVector[DOWN] = false;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            keyVector[LEFT] = false;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            keyVector[RIGHT] = false;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = false;
        }  
    }
}
