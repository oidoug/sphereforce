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
    
    private int bluePoint = 0;
    private int redPoint = 0;
    
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
            
            initGame();
            
            loading.waitForAll();
        } catch (InterruptedException ex) {
            Logger.getLogger("global").log(Level.SEVERE, null, ex);
        }
    }
    
    public void startsAsClient() {
        playStartSound();
        servidor = false;
        
        state = GET_SET;
        
        //ip = "192.168.200.102";
//        ip = "169.254.202.181";
//        ip = "localhost";
//        state = GAME_ON;
    }
    
    public void startAsClientConn(String ip) {
        try {
            conTcp = new ConectionTcp(ip);
            conUdp = new ConectionUdp(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        cenario_stones = new Stones( getImage(getDocumentBase(), "cenario_stone/MarbleTexture.png"), getImage(getDocumentBase(), "cenario_stone/Buraco.png"), getImage(getDocumentBase(), "cenario_stone/Bloco.png"), getImage(getDocumentBase(), "cenario_stone/Pedra.png"), getImage(getDocumentBase(), "Inicio.png"), getImage(getDocumentBase(), "Fim.png"),4);
        cenario_grass = new Grass( getImage(getDocumentBase(), "cenario_grass/GrassTexture.png"), getImage(getDocumentBase(), "cenario_grass/Buraco.png"), getImage(getDocumentBase(), "cenario_grass/Bloco.png"), getImage(getDocumentBase(), "cenario_grass/Tree.png"), getImage(getDocumentBase(), "Inicio.png"), getImage(getDocumentBase(), "Fim.png"),6 );
        
        initCenario(cenario_stones);
        
    }
    
    public void startsAsServer() {
        servidor = true;
        //state = GET_SET;
        
        try {
            conTcp = new ConectionTcp();
            conUdp = new ConectionUdp();
        } catch (Exception e) {
            e.printStackTrace();
        }
        cenario_stones = new Stones( getImage(getDocumentBase(), "cenario_stone/MarbleTexture.png"), getImage(getDocumentBase(), "cenario_stone/Buraco.png"), getImage(getDocumentBase(), "cenario_stone/Bloco.png"), getImage(getDocumentBase(), "cenario_stone/Pedra.png"), getImage(getDocumentBase(), "Inicio.png"), getImage(getDocumentBase(), "Fim.png"),4);
        initCenario(cenario_stones);
    }
    
    public void initCenario(Cenario cenario){
        mapOn = Constantes.CENARIO_STONES;
        LinkedList<Buraco> buracos;
        LinkedList<Pedra> pedras;
        bluesphere = new Esfera(this, getImage(getDocumentBase(), "blueSphere30p.png"), (int) cenario.inicio.getX() + 15, (int) cenario.inicio.getY() + 12);
        
        /* inicializa uma esfera que guardara a ref da sua imagem */
        redsphere = new Esfera(this, getImage(getDocumentBase(), "redSphere30p.png"), (int) cenario.inicio.getX() + 55, (int) cenario.inicio.getY() + 12);
        
        
        if (servidor){
            cenario.gerarCenario();
            try {
                conTcp.Send(cenario.getQntBuracos(), cenario.getQntPedras());
                buracos = cenario.getBuracos();
                for (Buraco holes : buracos){
                    conTcp.Send(holes);
                }
                
                pedras = cenario.getPedras();
                for (Pedra stones : pedras){
                    conTcp.Send(stones);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            
            //executa thread do chat
            System.out.println("antes de rodar a trhead dentro da classe main");
            chatscreen.connect(conTcp);
            
            // conecta e executa a thread de recebimento de pacotes udp GameData.
            redsphere.connect(conUdp);
        } else {
            
            
            //cliente
            try {
                conTcp.receiveQnts();
                int nburacos = conTcp.getQntBuraco();
                int npedras = conTcp.getQntPedra();
                
                buracos = new LinkedList<Buraco>();
                for (int i=0; i < nburacos; i++)
                    buracos.add( conTcp.getHole(getImage(getDocumentBase(), "cenario_stone/Buraco.png") ) ); //pega os buracos
                
                pedras = new LinkedList<Pedra>();
                for (int i=0; i < npedras; i++)
                    pedras.add( conTcp.getStone( getImage(getDocumentBase(), "cenario_stone/Pedra.png") ) );
                
                cenario.gerarCenario(buracos,pedras);
            } catch(Exception e) {
                e.printStackTrace();
            }
            // apos fazer a tranferencia dos pacotes de montagem dos cenarios, conecta o chat
            //executa thread do chat
            chatscreen.connect(conTcp);
            
            // conecta e executa a thread de recebimento de pacotes GameData
            bluesphere.connect(conUdp);
        }
        
        state = GAME_ON;
    }
    public void chatNow(boolean chat) {
        chatON = chat;
    }
    
    private void initGame() {
        this.chatON = false;
        
        /* in,icializa uma esfera que guardara a ref da sua imagem */
        
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
    
    /** Paint all the images in the set, Applet`s default method */
    @Override
    public void paint(Graphics g) {
        
        // check the actual application's state and update it
        switch (state) {
            case GAME_ON:
                if(!song_spheregear_on) {
                    stopTitleSong();
                    playCenarioSong();
                    this.song_spheregear_on = true;
                }
                /* sobre a conexao:
                 *
                 * o servidor sempre ficara esperando as coordenadas
                 * do cliente e somente depois envia as dele.
                 *
                 * ja o cliente envia primeiro e depois recebe as
                 * coordenadas do servidor
                 *
                 */
                DataGame data = new DataGame();
                
                
                if (servidor) {
                    // sets all the data for the bluesphere (server)
                    data.setAll(keyVector, bluesphere.getX(), bluesphere.getY(), bluesphere.getVelX(), bluesphere.getVelY());
                    
                    if(!chatON) {
                        
//                        redsphere.refresh(this.conUdp);
                        bluesphere.refresh(keyVector, redsphere);
                        try {
                            conUdp.Send(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if(!chatON) {
                        // sets all the data for the redsphere (client)
                        data.setAll(keyVector, redsphere.getX(), redsphere.getY(), redsphere.getVelX(), redsphere.getVelY());
                        redsphere.refresh(keyVector, bluesphere);
                        try {
                            conUdp.Send(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        bluesphere.refresh(conUdp);
                    }
                }
                
                if(chatON) {
                    state = CHAT_NOW;
                } else {
                    // pinta a fase na tela, com background, buracos e paredes
                    if(mapOn == Constantes.CENARIO_STONES) {
                        cenario_stones.paint(g);
                    } else if (mapOn == Constantes.CENARIO_GRASS) {
                        cenario_grass.paint(g);
                    }
                    
                    // pinta o score
                    if (servidor){
                        g.setFont(new Font("Arial", Font.BOLD, 36));
                        g.setColor(Color.BLUE);
                        g.drawString(String.valueOf(bluePoint),Constantes.WINDOW_WIDTH/2-50,Constantes.TAMANHO_BLOCO*3);
                        g.setColor(Color.RED);
                        g.drawString(String.valueOf(redPoint),Constantes.WINDOW_WIDTH/2+10,Constantes.TAMANHO_BLOCO*3);
                    } else {
                        g.setFont(new Font("Arial", Font.BOLD, 36));
                        g.setColor(Color.RED);
                        g.drawString(String.valueOf(redPoint),Constantes.WINDOW_WIDTH/2-50,Constantes.TAMANHO_BLOCO*3);
                        g.setColor(Color.BLUE);
                        g.drawString(String.valueOf(bluePoint),Constantes.WINDOW_WIDTH/2+10,Constantes.TAMANHO_BLOCO*3);
                        
                    }
                    if (mapOn == Constantes.CENARIO_STONES){
                        trataColisoes(cenario_stones);
                    } else if(mapOn == Constantes.CENARIO_GRASS) {
                        trataColisoes(cenario_grass);
                    }
                    if (bluePoint == Constantes.POINTS_TO_WIN){
                        whoWin = Constantes.SERVER_WIN;
                        state = SMB_WON;
                        entrar = true;
                    }
                    if (redPoint == Constantes.POINTS_TO_WIN){
                        whoWin = Constantes.CLIENT_WIN;
                        state = SMB_WON;
                        entrar = true;
                    }
                    //pinta ambas as eferas
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
                
//                if (!this.keyWasPressed()){
//
//                    this.cenario_stones.paint(g);
//                    this.redsphere.paint(g);
//                    this.bluesphere.paint(g);
//                    if (whoWin == Constantes.SERVER_WIN){
//                        g.setFont(new Font("Arial", Font.BOLD, 50));
//                        g.setColor(Color.BLUE);
//                        g.drawString("BLUE SPHERE WIN's",100, Constantes.WINDOW_HEIGHT/2 - 25);
//                    } else {
//                        g.setFont(new Font("Arial", Font.BOLD, 50));
//                        g.setColor(Color.RED);
//                        g.drawString("RED SPHERE WIN's",100, Constantes.WINDOW_HEIGHT/2 - 25);
//                    }
//                }
                
//                else {
                System.out.println("to no else");
                bluePoint  = 0;
                redPoint = 0;
                whoWin = Constantes.NOBODY_WIN;
                mapOn = Constantes.CENARIO_GRASS;
//                }
                
                
                
                break;
                
            case CHAT_NOW:
                if(mapOn == Constantes.CENARIO_STONES) {
                    cenario_stones.paint(g);
                } else if (mapOn == Constantes.CENARIO_GRASS) {
                    cenario_grass.paint(g);
                }
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
    
    public void trataColisoes(Cenario cenario) {
        bluesphere.trataBuracos(cenario.getBuracos());
        redsphere.trataBuracos(cenario.getBuracos());
        
        bluesphere.trataParedes(cenario.getParedes());
        redsphere.trataParedes(cenario.getParedes());
        
        bluesphere.trataPedras(cenario.getPedras());
        redsphere.trataPedras(cenario.getPedras());
        
        if (bluesphere.trataMarca(cenario.getFim())){
            this.bluePoint++;
            this.playFinishSound();
            
            respaw(bluesphere, cenario);
        } else if (redsphere.trataMarca(cenario.getFim())){
            this.redPoint++;
            this.playFinishSound();
            
            respaw(redsphere, cenario);
        }
    }
    
    public void respaw(Esfera sphere, Cenario cenario){
        
        sphere.setXY((int) cenario.inicio.getX() + 20, (int) cenario.inicio.getY() + 15);
        sphere.setVelXY(0,0);
        
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
        this.theme_song.play();
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
}
