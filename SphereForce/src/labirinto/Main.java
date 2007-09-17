package labirinto;

/*
 * Main.java
 *
 * Created on April 22, 2007, 10:49 AM
 *
 * Parte do projeto Sphere Force
 * por Douglas Schmidt, Renato Euclides Silva e Mateus Balconi
 */


/**
 * Imports to Main
 */
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import labirinto.core.*;


public class Main extends DoubleBufferApplet implements Runnable, KeyListener {
    
    //constantes ambiente
    public static final float ACELER = 1.5F;
    public static final float ATRITO = 0.87F;
    
    //comandos
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ENTER = 4;
    public static final int ESCAPE = 5;
    
    public static final int NUM_OF_KEYS = 6;
    
    public static final int GAME_ON = 0;
    public static final int GAME_STOP = 1;
    public static final int MENU = 2;
    public static final int LOGO = 3;
    public static final int EXIT = 4;
    public static final int WAITING_CLIENT = 5;
    public static final int GET_SET = 6;
    public static final int CHAT_NOW = 7;
    public static final int SMB_WON = 8;
    
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
    
    /* classe de logo, menu e chat */
    private Logo logoscreen;
    private Menu menuscreen;
    private Chat chatscreen;
    
    public static MediaTracker loading;
    
    private ConectionTcp conTcp;
    private ConectionTcp conTcpM;
    private ConectionUdp conUdp;
    
    public boolean servidor;
    
    private Stones cenario_stones;
    private Grass cenario_grass;
    /* getset counter */
    private int getsetcount = 0;
    
    public static boolean chatON;
    
    private Image chat_image;
    
    private AudioClip sphere_collision_song;
    private AudioClip wall_collision_song;
    private AudioClip hole_collision_song;
    private AudioClip start_song;
    private AudioClip finish_song;
    private AudioClip theme_song;
    private AudioClip stones_song;
    
    private String ip;
    
    private int blueMapPoint = 0;
    private int redMapPoint = 0;
    
    private boolean song_theme_on = false;
    private boolean song_spheregear_on = false;
    
    private int whoWin = Constantes.NOBODY_WIN;
    private int mapOn;
    private long stopTime;
    private long startTime;
    private boolean entrar;
    
    /** Starts Applet with page`s requiriment */
    @Override
    public void start() {
        state = LOGO;
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
        try {
            //conTcp.closeConection();
            //conUdp.closeConection();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /** Init called in the very first applet`s run */
    @Override
    public void init() {
        try {
            
            //set applet window size
            this.setSize(Constantes.WINDOW_WIDTH, Constantes.WINDOW_HEIGHT);
            
            keyVector = new boolean[NUM_OF_KEYS];
            
            /* Controla as entradas do teclado */
            this.addKeyListener(this);
            
            //wait for all images get ready to show everthing synchronously
            loading = new java.awt.MediaTracker(this);
            
            initSounds();
            
            initMenu();
            
            
            
            loading.waitForAll();
        } catch (InterruptedException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
    }
    
    public void startsAsClient() {
        servidor = false;
        state = GET_SET;
        startAsClientConn(ip);
    }
    
    public void startAsClientConn(String ip) {
        try {
            conTcp = new ConectionTcp(ip,4000);
            conTcpM = new ConectionTcp(ip,5000);
            conUdp = new ConectionUdp(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // conecta e executa a thread de recebimento de pacotes GameData
        
        initGame();
    }
    
    public void startsAsServer() {
        servidor = true;
        //state = GET_SET;
        try {
            conTcp = new ConectionTcp(4000);
            conTcpM = new ConectionTcp(5000);
            conUdp = new ConectionUdp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // conecta e executa a thread de recebimento de pacotes udp GameData.
        
        initGame();
    }
    
    public void iniciaCenarios(){
        cenario_stones = new Stones( getImage(getDocumentBase(), "cenario_stone/MarbleTexture.png"), getImage(getDocumentBase(), "cenario_stone/Buraco.png"), getImage(getDocumentBase(), "cenario_stone/Bloco.png"), getImage(getDocumentBase(), "cenario_stone/Pedra.png"), getImage(getDocumentBase(), "Inicio.png"), getImage(getDocumentBase(), "Fim.png"),4);
        cenario_grass = new Grass( getImage(getDocumentBase(), "cenario_grass/GrassTexture.png"), getImage(getDocumentBase(), "cenario_grass/Buraco.png"), getImage(getDocumentBase(), "cenario_grass/Bloco.png"), getImage(getDocumentBase(), "cenario_grass/Tree.png"), getImage(getDocumentBase(), "Inicio.png"), getImage(getDocumentBase(), "Fim.png"),4);
        
    }
    
    public void initCenario(Cenario cenario){
        bluesphere = new Esfera(this, getImage(getDocumentBase(), "blueSphere30p.png"), (int) cenario.inicio.getX() + 15, (int) cenario.inicio.getY() + 12);
        redsphere = new Esfera(this, getImage(getDocumentBase(), "redSphere30p.png"), (int) cenario.inicio.getX() + 55, (int) cenario.inicio.getY() + 12);
        cenario.setConTcp(conTcpM);
        cenario.setServidor(servidor);
        
    }
    public void chatNow(boolean chat) {
        chatON = chat;
    }
    
    private void initGame() {
        this.chatON = false;
        iniciaCenarios();
        initCenario(cenario_stones);
        initCenario(cenario_grass);
        //primeiro mapa a ser jogado         --->arrumar aki o mapa q inicia
        mapOn = Constantes.CENARIO_GRASS;
        gerarCenario(cenario_grass);
        
        if (servidor)
            redsphere.connect(conUdp);
        else
            bluesphere.connect(conUdp);
        
        
        chatscreen.connect(conTcp);
        state = GAME_ON;
        playStartSound();
        
    }
    
    private void initNewFase() {
        this.chatON = false;
        
        
        if (mapOn == Constantes.CENARIO_STONES){
            cenario_grass.zerarCenario();
            gerarCenario(cenario_stones);
        } else if (mapOn == Constantes.CENARIO_GRASS){
            cenario_stones.zerarCenario();
            gerarCenario(cenario_grass);
        } else
            gerarCenario(cenario_stones);  //else soh por garantia
        
        state = GAME_ON;
        playStartSound();
        playCenarioSong();
        
    }
    
    public void gerarCenario(Cenario cenario){
        LinkedList<Buraco> buracos;
        LinkedList<Pedra> pedras;
        bluesphere.setXY(cenario.inicio.getX() + 15, cenario.inicio.getY() + 12);
        redsphere.setXY(cenario.inicio.getX() + 55, cenario.inicio.getY() + 12);
        if (servidor){
            cenario.gerarCenario();
            try {
                conTcpM.Send(cenario.getQntBuracos(), cenario.getQntPedras());
                buracos = cenario.getBuracos();
                for (Buraco holes : buracos){
                    conTcpM.Send(holes);
                }
                
                pedras = cenario.getPedras();
                for (Pedra stones : pedras){
                    conTcpM.Send(stones);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            //cliente
            try {
                conTcpM.receiveQnts();
                int nburacos = conTcpM.getQntBuraco();
                int npedras = conTcpM.getQntPedra();
                
                buracos = new LinkedList<Buraco>();
                for (int i=0; i < nburacos; i++)
                    if (mapOn == Constantes.CENARIO_STONES)
                        buracos.add( conTcpM.getHole(getImage(getDocumentBase(), "cenario_stone/Buraco.png") ) ); //pega os buracos
                    else
                        buracos.add( conTcpM.getHole(getImage(getDocumentBase(), "cenario_grass/Buraco.png") ) ); //pega os buracos
                
                pedras = new LinkedList<Pedra>();
                for (int i=0; i < npedras; i++)
                    if (mapOn == Constantes.CENARIO_STONES)
                        pedras.add( conTcpM.getStone( getImage(getDocumentBase(), "cenario_stone/Pedra.png") ) );
                
                    else
                        pedras.add( conTcpM.getStone( getImage(getDocumentBase(), "cenario_grass/Tree.png") ) );
                cenario.gerarCenario(buracos,pedras);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * initMenu() inicializa as imagens para a chamada do menu interativo
     *
     */
    private void initMenu() {
        /* intantiate the logo object and add a new logo */
        logoscreen = new Logo(this);
        //        logoscreen.addLogo(getImage(getDocumentBase(), "redSphere.png"), 120);
        logoscreen.addLogo(getImage(getDocumentBase(), "logo/qua.png"));
        logoscreen.addLogo(getImage(getDocumentBase(), "logo/barigada.png"));
        
        /** BEGIN Menu */
        String[] buttons_strings = {"Play as Server", "Play as Client", "Help"};
        /* instantiate the button image, the theme song, the background and game logo */
        menuscreen = new Menu(this, buttons_strings);
        menuscreen.setImages(getImage(getDocumentBase(), "menu/MenuBackground.png"), getImage(getDocumentBase(), "menu/SphereForceLogo.png"), getImage(getDocumentBase(), "menu/ButtonUp.png"), getImage(getDocumentBase(), "menu/ButtonDown.png"), getImage(getDocumentBase(), "menu/help.png"));
        /** END Menu */
        
        /** BEGIN Chat */
        this.chat_image = getImage(getDocumentBase(), "menu/ChatScreen.png");
        chatscreen = new Chat(this, this.chat_image);
        /** END Chat */
    }
    
    
    
    public void refreshSpheres(){
        DataGame data = new DataGame();
        if (servidor) {
            // sets all the data for the bluesphere (server)
            data.setAll(keyVector, bluesphere.getX(), bluesphere.getY(), bluesphere.getVelX(), bluesphere.getVelY());
            bluesphere.refresh(keyVector, redsphere);
            try {
                conUdp.Send(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // sets all the data for the redsphere (client)
            data.setAll(keyVector, redsphere.getX(), redsphere.getY(), redsphere.getVelX(), redsphere.getVelY());
            redsphere.refresh(keyVector, bluesphere);
            try {
                conUdp.Send(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public int pintaCenario(Graphics g, Cenario cenario){
        int temp;
        cenario.setBluesphere(bluesphere);
        cenario.setRedsphere(redsphere);
        temp = cenario.paint(g);
        return temp;
    }
    
    public void paint(Graphics g) {
        
        // check the actual application's state and update it
        switch (state) {
            case GAME_ON:
                if(!song_spheregear_on) {
                    stopTitleSong();
                    playCenarioSong();
                    this.song_spheregear_on = true;
                }
                if(chatON) {
                    state = CHAT_NOW;
                } else {
                    // pinta a fase na tela, com background, buracos e paredes
                    refreshSpheres();
                    if (mapOn == Constantes.CENARIO_STONES)
                        whoWin = pintaCenario(g, cenario_stones);
                    else
                        whoWin = pintaCenario(g, cenario_grass);
                    

                    if (whoWin == Constantes.SERVER_WIN){
                        state = SMB_WON;
                        this.blueMapPoint++;
                    } else if (whoWin == Constantes.CLIENT_WIN){
                        state = SMB_WON;
                        this.redMapPoint++;
                    }
                    
                    bluesphere.paint(g);
                    redsphere.paint(g);
                    
                }
                break;
                
            case LOGO:
                if(!song_theme_on) {
                    playTitleSong();
                    song_theme_on = true;
                }
                logoscreen.paint(g);
                
                break;
                
            case MENU:
                menuscreen.paint(g);
                
                break;
                
            case GAME_STOP:
                break;
                
            case EXIT:
                break;
                
            case GET_SET:
                if(ip == null) ip = new String();
                
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, 18));
                
                g.drawImage(chat_image, 0, 0, this);
                
                if(servidor) {
                    g.drawString("Wainting for the client to connect...", Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_OUTPUT_INIT_Y + 50);
                } else {
                    g.drawString("Enter a valid IP to connect:", Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_OUTPUT_INIT_Y + 50);
                    if (ip.length() > Constantes.MAX_INPUT_CHAR) {
                        g.drawString(ip.substring(ip.length() - Constantes.MAX_INPUT_CHAR, ip.length()), Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
                    } else {
                        g.drawString(ip, Constantes.CHAT_STRING_INIT_X, Constantes.CHAT_STRING_INPUT_INIT_Y);
                    }
                }
                
                break;
                
            case SMB_WON:
                
                
                
                if (mapOn == Constantes.CENARIO_STONES) {
                    this.cenario_stones.paint(g);
                    cenario_stones.zeraPoints();
                }
                
                else {
                    this.cenario_grass.paint(g);
                    cenario_grass.zeraPoints();
                }
                
                
                this.redsphere.paint(g);
                this.bluesphere.paint(g);                                
                paintScoreMap(g);                
                if(keyWasPressed()) {
                    state = GAME_ON;
                    whoWin = Constantes.NOBODY_WIN;
                    int map;
                    if (mapOn == Constantes.CENARIO_STONES) {
                        //respaw(redsphere, bluesphere,cenario_grass);
                        map = Constantes.CENARIO_GRASS;
                        
                    } else{
                        //respaw(redsphere, bluesphere,cenario_stones);
                        map = Constantes.CENARIO_STONES;
                    }
                    
                    mapOn = map;
                    initNewFase();
                    
                }
                
                
                break;
                
            case CHAT_NOW:
                if (mapOn == Constantes.CENARIO_GRASS)
                    this.cenario_grass.paint(g);
                else if (mapOn == Constantes.CENARIO_STONES)
                    this.cenario_stones.paint(g);
                else System.out.println("PERDEU PRAYBOY!!!");
                
                this.redsphere.paint(g);
                this.bluesphere.paint(g);
                chatscreen.paint(g);
                
                break;
                
            default:
                state = MENU;
                break;
                
        }
        ;
    }
    
    public void paintScoreMap(Graphics g){
        
        g.setFont(new Font("Arial", Font.BOLD, 40));
        if(whoWin == Constantes.SERVER_WIN) {
            g.setColor(Color.BLUE);
            g.fillRect(230, 50, 390, 60);
            g.setColor(Color.RED);
            g.fillRect(234, 54, 382, 52);
            g.setColor(Color.BLUE);
            g.drawString("Blue Sphere Wins!", 250, 93);
            
        } else {
            g.setColor(Color.RED);
            g.fillRect(230, 50, 390, 60);
            g.setColor(Color.BLUE);
            g.fillRect(234, 54, 382, 52);
            g.setColor(Color.RED);
            g.drawString("Red Sphere Wins!", 250, 93);
            
        }
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.GREEN);
        g.fillRect(305,220,240,160);
        g.setColor(Color.ORANGE);
        g.fillRect(309,224,232,44);
        g.fillRect(309,272,232,104);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Number of Victories",335,250);
        g.setColor(Color.BLUE);
        g.drawString("Blue Sphere: "+blueMapPoint,355,310);
        g.setColor(Color.RED);
        g.drawString("Red Sphere: "+redMapPoint,355,350);
    }
    
    
    
    /** Thread method for the game Loop */
    
    public void run() {
        /* apresenta logos dos developers e outros e do jogo */
        /* mostra menu principal, conf de velocidade, dificuldade e tamanho tela */
        
        /* começa o jogo, mostra cenario etc */
        
        long startTime;
        startTime = System.currentTimeMillis();
        
        
        while (Thread.currentThread() == gameLoop) {
            repaint();
            try {
                startTime += Constantes.DELAY;
                Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    
    public void keyTyped(KeyEvent keyEvent) {
        if(!keyEvent.isActionKey()) {
            if(keyEvent.getKeyCode() != keyEvent.VK_BACK_SPACE) {
                if (state == CHAT_NOW) {
                    chatscreen.concatInInputMessage(keyEvent.getKeyChar());
                    
                } else if (state == GET_SET) {
                    this.ip = ip.concat(String.valueOf(keyEvent.getKeyChar()));
                    repaint();
                }
            } else {
                if (state == CHAT_NOW) {
                    chatscreen.unConcatInInputMessage();
                } else {
                    System.out.println("BACKSPACE PRESSED!");
                    ip = ip.substring(0, ip.length()-1);
                    repaint();
                }
            }
        }
    }
    
    /** KeyPressed listener, set a vector with moving events */
    public void keyPressed(KeyEvent keyEvent) {
        DataChat datac = new DataChat();
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            keyVector[UP] = true;
            if (state == MENU) {
                menuscreen.keyUpTyped();
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            keyVector[DOWN] = true;
            if (state == MENU) {
                menuscreen.keyDownTyped();
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            keyVector[LEFT] = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyVector[RIGHT] = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            keyVector[ENTER] = true;
            if (state == MENU) {
                menuscreen.keyEnterTyped();
            } else if (state == GAME_ON) {
                
                chatON = true;
                state = CHAT_NOW;
                
                try {
                    datac.setComando(Constantes.CHAT_START);
                    conTcp.Send(datac);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else if (state == CHAT_NOW) {
                chatscreen.keyEnterTyped();
            } else if (state == GET_SET) {
                if(!servidor) {
                    startAsClientConn(ip);
                    state = GAME_ON;
                }
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = true;
            if (state == MENU) {
                menuscreen.keyEscapeTyped();
            } else if (state == CHAT_NOW) {
                
                try {
                    datac.setComando(Constantes.CHAT_STOP);
                    conTcp.Send(datac);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //state = GAME_ON;
                chatON = false;
                chatscreen.keyEscapeTyped();
            } else if (state == GAME_ON) {
                this.quit();
            }
        }
    }
    
    /** KeyReleased listener */
    
    public void keyReleased(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            keyVector[UP] = false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            keyVector[DOWN] = false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            keyVector[LEFT] = false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            keyVector[RIGHT] = false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
            keyVector[ENTER] = false;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
            keyVector[ESCAPE] = false;
        }
    }
    
    /** Method keyWasPressed()
     * checks if in a game loop some key was or are pressed
     * is used in logo and menu mode
     */
    public boolean keyWasPressed() {
        for (int i = 0; i < NUM_OF_KEYS; i++) {
            if (keyVector[i]) {
                return true;
            }
        }
        return false;
    }
    
    /** Method keyVector(
     * return the actual key vector
     */
    public boolean[] keyVector() {
        return keyVector;
    }
    
    private void quit() {
        DataChat datac = new DataChat();
        datac.setComando(Constantes.CHAT_END_GAME);
        try {
            conTcp.Send(datac);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    
    public void playWallSound() {
        this.wall_collision_song.play();
    }
    
    public void playSphereSound() {
        this.sphere_collision_song.play();
    }
    
    public void playHoleSound() {
        this.hole_collision_song.play();
    }
    
    public void playStartSound() {
        this.start_song.play();
    }
    
    public void playFinishSound() {
        this.finish_song.play();
    }
    
    private void playCenarioSong() {
        this.stones_song.play();
    }
    
    private void playTitleSong() {
        this.theme_song.loop();
    }
    
    private void initSounds() {
        this.sphere_collision_song = getAudioClip(getDocumentBase(), "sound/batidabola.snd");
        this.wall_collision_song = getAudioClip(getDocumentBase(), "sound/batida.snd");
        this.hole_collision_song = getAudioClip(getDocumentBase(), "sound/buraco.snd");
        this.start_song = getAudioClip(getDocumentBase(), "sound/start.snd");
        this.finish_song = getAudioClip(getDocumentBase(), "sound/final.snd");
        this.theme_song = getAudioClip(getDocumentBase(), "sound/titlesong.au");
        this.stones_song = getAudioClip(getDocumentBase(), "sound/quietspheregearwithsolo.au");
    }
    
    private void stopTitleSong() {
        this.theme_song.stop();
    }
    
    private int respaw(Esfera redsphere, Esfera bluesphere, Cenario cenario) {
        
        redsphere.setXY((int) cenario.inicio.getX() + 20, (int) cenario.inicio.getY() + 15);
        redsphere.setVelXY(0,0);
        bluesphere.setXY((int) cenario.inicio.getX() + 40, (int) cenario.inicio.getY() + 15);
        bluesphere.setVelXY(0,0);
        
        return 0;
    }
}
