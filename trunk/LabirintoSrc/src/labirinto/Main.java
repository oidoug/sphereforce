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
import labirinto.core.Conection;


public class Main extends DoubleBufferApplet implements Runnable, KeyListener{
    
    //constantes ambiente
    static final public int WINDOW_WIDTH = 800;
    static final public int WINDOW_HEIGHT = 600;
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
    
    static final public int GAME_ON = 0;
    static final public int GAME_STOP = 1;
    static final public int MENU = 2;
    static final public int LOGO = 3;
    static final public int EXIT = 4;
    
    public int state;
    
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
    
    /* instancias das imagens dos logos a serem exibidos */
    private Logo logoscreen;
    
    static public MediaTracker loading;
    
    private Conection conn;
    
    /** Starts Applet with page`s requiriment */
    @Override
public void start() {
        state = GAME_ON;
        gameLoop = new Thread(this);
        gameLoop.start();
    }
    
    /** Stop Applet when user leave the page */
    @Override
public void stop() {
        state = EXIT;
    }
    
    /** Destroy Applet before leave the page */
    public void destroy() {
        
    }
    
    /** Init called in the very first applet`s run */
    @Override
public void init() {
        
        //set applet window size
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        keyVector = new boolean[NUM_OF_KEYS];
                
        /* Controla as entradas do teclado */
        this.addKeyListener(this);
        
        //wait for all images get ready to show everthing synchronously
        loading = new MediaTracker(this);
        
        /* intantiate the logo object and add a new logo */
        logoscreen = new Logo(this);
        logoscreen.addLogo(getImage(getDocumentBase(), "qua.png"));
        
        /* inicializa uma esfera que guardara a ref da sua imagem */
        bluesphere = new Esfera(getImage(getDocumentBase(), "EsferaAzul.png"));
        
        /* inicializa uma esfera que guardara a ref da sua imagem */
        redsphere = new Esfera(getImage(getDocumentBase(), "EsferaVermelha.png"));
        
        /* start a conection */
        conn = new Conection();
    }
    
    /** Paint all the images in the set, Applet`s default method */
    public void paint(Graphics g) {

        // check the actual application's state and update it
        switch (state) {
        case GAME_ON:
            bluesphere.refresh(keyVector);
            redsphere.refresh(conn);
            
            bluesphere.paint(g);
            redsphere.paint(g);
            
            break;
            
        case LOGO:
            logoscreen.paint(g);
            
            break;
            
        case MENU:
            break;
            
        case GAME_STOP:
            break;
            
        case EXIT:
            break;
            
        default:
            state = MENU;
            break;
        };    
    }

    /** Thread method for the game Loop */
    @Override
public void run() {
        /* apresenta logos dos developers e outros e do jogo */       
        /* mostra menu principal, conf de velocidade, dificuldade e tamanho tela */
        
        /* começa o jogo, mostra cenario etc */
        
        long startTime;
        startTime = System.currentTimeMillis();
        
        while(Thread.currentThread() == gameLoop) {
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
    
    @Override
public void keyTyped(KeyEvent keyEvent) {
    }

    /** KeyPressed listener, set a vector with moving events */
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            keyVector[UP] = true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            keyVector[DOWN] = true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            keyVector[LEFT] = true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyVector[RIGHT] = true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            keyVector[ENTER] = true;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = true;
        }
    }
    
    /** KeyReleased listener */
    @Override
public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            keyVector[UP] = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            keyVector[DOWN] = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            keyVector[LEFT] = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyVector[RIGHT] = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            keyVector[ENTER] = false;
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = false;
        }  
    }

}
