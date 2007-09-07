/*
 * Cenario_Stones.java
 *
 * Created on Sep 6, 2007, 4:20:23 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
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


    /** pinta todos os elementos do cenario */
    public void paint(Graphics g) {
        /** BEGIN background */
        int x = 0;
        int y = 0;
        while (y < Constantes.WINDOW_HEIGHT) {

            x = 0;

            while (x < Constantes.WINDOW_WIDTH) {

                g.drawImage(background, x, y, null);
                x = x + Constantes.BACKGROUND_W;
            }
            y = y + Constantes.BACKGROUND_H;
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
        boolean colide = false;
        int nBuracosOk = 0;
        for (int i = 0; i <= Constantes.DIVISOES_CANVAS * Constantes.DIVISOES_CANVAS; i++) {
            do {
                randX = (int) ((Constantes.WINDOW_WIDTH/Constantes.DIVISOES_CANVAS * i) * Math.random());
                randY = (int) ((Constantes.WINDOW_HEIGHT/Constantes.DIVISOES_CANVAS * i) * Math.random());

                Buraco hole = new Buraco(buraco, randX, randY);

                if (hole.colideCom(inicio) || hole.colideCom(fim)) {
                    colide = true;
                } else {
                    for (Parede parede : paredes) {
                        if (hole.colideCom(parede)) {
                            colide = true;
                        }
                    }
                }
                if (!colide) {
                    buracos.add(new Buraco(buraco, randX, randY));
                    nBuracosOk++;
                }
            } while (nBuracosOk <= dificuldade);
        }
    }
    
    protected void pedras(){
        //pedras.add(new Pedra(pedra, 75, 250));
        //pedras.add(new Pedra(pedra, 200, 300));
        
        int randx;
        int randy;
        boolean colide = false;
        int nPedrasOk = 0;
        for (int i=0; i < Constantes.DIVISOES_CANVAS; i++){
            for (int j=0; j < Constantes.DIVISOES_CANVAS; j++){
                do {
                    randx = (int) ( (i * Constantes.WINDOW_WIDTH/Constantes.DIVISOES_CANVAS) +
                            (Constantes.WINDOW_WIDTH/Constantes.DIVISOES_CANVAS * Math.random()) );
                    randy = (int) ( (j * Constantes.WINDOW_HEIGHT/Constantes.DIVISOES_CANVAS) +
                            (Constantes.WINDOW_HEIGHT/Constantes.DIVISOES_CANVAS * Math.random()) );
                    
                    Pedra stone = new Pedra(pedra, randx, randy);
                    
                    if (stone.colideCom(inicio) || stone.colideCom(fim)){
                        colide = true;
                    }
                    else {
                        for (Parede wall : paredes){
                            if (stone.colideCom(wall)){
                                colide = true;
                                break;
                            }
                        }
                    }
                    if (!colide){
                        for (Buraco hole : buracos){
                            if (stone.colideCom(hole)) {
                                colide = true;
                                break;
                            }
                        }
                    }
                    if (!colide) {
                        pedras.add(new Pedra(pedra, randx, randy));
                        nPedrasOk++;
                    }
                    
                }while (nPedrasOk <= dificuldade);
            }
        }
    }
}
