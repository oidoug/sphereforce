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
import labirinto.core.DoubleBufferApplet;
import labirinto.core.Esfera;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;


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
    static final public int ENTER = 4;
    static final public int ESCAPE = 5;
    
    static final public int NUM_OF_KEYS = 6;
    
    
    private boolean gameOn;
    private Thread gameLoop;
    
    /** Vector [5] with:
     *0 = UP
     *1 = DOWN
     *2 = LEFT
     *3 = RIGHT
     *4 = ESCAPE
     */
    private boolean[] keyVector;
   
    /* instancia para os objetos utilizados durante o jogo */
    private Esfera bluesphere;
    private Esfera redsphere;
    
    static public MediaTracker loading;
    
    /** Starts Applet with page`s requiriment */
    public void start() {
        gameOn = true;
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    /** Stop Applet when user leave the page */
    public void stop() {
        gameOn = false;
    }
    
    /** Destroy Applet before leave the page */
    public void destroy() {
        
    }
    
    /** Init called in the very first applet`s run */
    @Override
public void init() {
        
        keyVector = new boolean[NUM_OF_KEYS];
                
        /* Controla as entradas do teclado */
        this.addKeyListener(this);
        
        //wait for all images get ready to show everthing synchronously
        loading = new MediaTracker(this);
        
        /* inicializa uma esfera que guardara a ref da sua imagem */
        bluesphere = new Esfera(getImage(getDocumentBase(), "EsferaAzul.png"));
        
        /* inicializa uma esfera que guardara a ref da sua imagem */
        redsphere = new Esfera(getImage(getDocumentBase(), "EsferaVermelha.png"));
    }
    
    /** Paint all the images in the set, Applet`s default method */
    public void paint(Graphics g) {
        if (gameOn) {
            bluesphere.paint(g);
            redsphere.paint(g);
        }
    }

    /** Thread method for the game Loop */
    public void run() {
        /* apresenta logos dos developers e outros e do jogo */
        logos();
        
        /* mostra menu principal, conf de velocidade, dificuldade e tamanho tela */
        menu();
        
        long startTime;
        startTime = System.currentTimeMillis();
        
        while(Thread.currentThread() == gameLoop) {
            bluesphere.refresh(keyVector);
            //sphereOther.refresh();
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
        if(keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
            keyVector[ENTER] = true;
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
        if(keyEvent.getKeyCode() == keyEvent.VK_ENTER) {
            keyVector[ENTER] = false;
        }
        if(keyEvent.getKeyCode() == keyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = false;
        }  
    }
}
