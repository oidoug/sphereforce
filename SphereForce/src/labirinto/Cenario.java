/*
 * Cenario_Stones.java
 *
 * Created on Sep 6, 2007, 4:20:23 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;
import labirinto.core.*;

/**
 *
 * @author Douglas Schmidt
 */
public class Cenario {
    
    protected LinkedList<Parede> paredes;
    protected LinkedList<Buraco> buracos;
    protected LinkedList<Pedra> pedras;
    
    protected Marca inicio;
    protected Marca fim;
    
    protected Image background;
    protected Image buraco;
    protected Image bloco;
    protected Image pedra;
    protected Image marca_inicio;
    protected Image marca_fim;
    
    protected boolean servidor;
    
    protected int dificuldade = 1; // 1 - 5
    
    public Cenario(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim, int dificuldade) {
        
        paredes = new LinkedList<Parede>();
        buracos = new LinkedList<Buraco>();
        pedras = new LinkedList<Pedra>();
        
        this.background = background;
        this.buraco = buraco;
        this.bloco = bloco;
        this.pedra = pedra;
        this.marca_inicio = marca_inicio;
        this.marca_fim = marca_fim;
        
        this.dificuldade = dificuldade;
        this.servidor = servidor;
    }
    
    public Image getBlocoImg() {
        return bloco;
    }
    
    public Image getBackgroundImg() {
        return background;
    }
    
    public Image getBuracoImg() {
        return buraco;
    }
    
    public Image getPedraImg() {
        return pedra;
    }
    
    public LinkedList<Parede> getParedes() {
        return paredes;
    }
    
    public LinkedList<Buraco> getBuracos() {
        return buracos;
    }
    
    public LinkedList<Pedra> getPedras() {
        return pedras;
    }
    
    public void setBuracos(LinkedList<Buraco> holes){
        buracos = holes;
    }
    
    public void setPedras(LinkedList<Pedra> stones){
        pedras = stones;
    }
    
    /** pinta todos os elementos do cenario */
    public void paint(Graphics g) {
        /** BEGIN background */
        int x = 0;
        int y = 0;
        while (y < Constantes.WINDOW_HEIGHT) {
            
            x = 0;
            
            while (x < Constantes.WINDOW_WIDTH) {
                
                g.drawImage(background, x, y, null);
                x = x + 50;
            }
            y = y + 50;
        }
        /* END background */
        
        /* BEGIN buracos */
        for (Buraco hole : buracos) {
            hole.paint(g);
        }
        /* END buracos */
        
        /* BEGIN paredes */
        for (Parede wall : paredes) {
            wall.paint(g);
        }
        /* END paredes */
        
        /* BEGIN pedras */
        for (Pedra stone : pedras) {
            stone.paint(g);
        }
        /* END pedras */
        
        /* BEGIN marcas */
        inicio.paint(g);
        fim.paint(g);
        /* END marcas */
    }
    
    /**
     * Method: buracos()
     * Gera uma lista de buracos divididos em regioes definidas por
     * Constantes.DIVISOES_CANVAS;
     * Para gerar um buraco o mesmo nao deve colidir com nenhum outro elemento.
     */
    protected void buracos() {
        int randX;
        int randY;
        int nBuracosOk = 0;
        Random generator = new Random();
        Buraco hole;
        
        do {
            randX = generator.nextInt(Constantes.WINDOW_WIDTH);
            randY = generator.nextInt(Constantes.WINDOW_HEIGHT);
            
            hole = new Buraco(buraco, randX, randY);
            
            if (!hole.colideCom(inicio) && !hole.colideCom(fim) && !hole.colideCom(paredes)) {
                nBuracosOk++;
                buracos.add(hole);
            }
        } while (nBuracosOk <= dificuldade);
    }
    
    protected void pedras() {
        
        int randx;
        int randy;
        
        Pedra stone;
        Random generator = new Random();
        
        int nPedrasOk = 0;
        
        do {
            
            randx = generator.nextInt(Constantes.WINDOW_WIDTH);
            randy = generator.nextInt(Constantes.WINDOW_HEIGHT);
            
            stone = new Pedra(pedra, randx, randy);
            
            if (!stone.colideCom(inicio) && !stone.colideCom(fim) && !stone.colideComParedes(paredes) && !stone.colideComBuracos(buracos)) {
                nPedrasOk++;
                pedras.add(stone);
            }
        } while (nPedrasOk <= dificuldade);
    }
    
    protected int getQntBuracos(){
        return buracos.size();
    }
    
    protected int getQntPedras(){
        return pedras.size();
    }

    protected Marca getFim(){
        return fim;
    }
}
