/*
 * Mapa.java
 *
 * Created on Sep 4, 2007, 11:37:08 AM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.awt.Graphics;
import java.awt.Image;
import java.util.LinkedList;
import labirinto.core.Buraco;
import labirinto.core.Esfera;
import labirinto.core.Marca;
import labirinto.core.Parede;

/**
 *
 * @author Douglas Schmidt
 */
public class Mapa {

    private LinkedList<Parede> paredes;
    private LinkedList<Buraco> buracos;

    private Image buracoImg;
    private Image blocoImg;
    private Image backgroundImg;

    private final int BackGW = 50;
    private final int BackGH = 50;

    private Marca largada;
    private Marca chegada;

    private Esfera redsphere;
    private Esfera bluesphere;

    private final int NUM_BURACOS = 3;

    public Mapa() {
        buracos = new LinkedList<Buraco>();
        paredes = new LinkedList<Parede>();
    }

    public void addObject(Object object) {
        // super(gambis-java)!;
        if (object instanceof Esfera) {
            if (bluesphere != null) {
                redsphere = (Esfera) object;
            } else {
                bluesphere = (Esfera) object;
            }
        } else if (object instanceof Marca) {
            if (largada != null) {
                chegada = (Marca) object;
            } else {
                largada = (Marca) object;
            }
        } else if (object instanceof Image) {
            if (buracoImg == null) {
                buracoImg = (Image) object;
            } else if (blocoImg == null) {
                blocoImg = (Image) object;
            } else {
                backgroundImg = (Image) object;
            }
        } else {
            System.err.println("erro: Objeto nao valido ou ordem e inicialização incorreta.");
        }
    }

    public void gerarMapa() {
        gerarBuracos();
        gerarParedes();
    }

    public void paint(Graphics g) {
        /** BEGIN background */
        int x = 0;
        int y = 0;

        while (y < Main.WINDOW_HEIGHT) {
            x = 0;
            while (x < Main.WINDOW_WIDTH) {
                g.drawImage(backgroundImg, x, y, null);
                x = x + BackGW;
            }
            y = y + BackGH;
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
        
        /* BEGIN marcas */
        largada.paint(g);
        chegada.paint(g);
        /* END marcas */
    }

    private void gerarBuracos() {
        int randX;
        int randY;
        boolean colide = false;
        int nBuracosOk = 0;
        do {
            randX = (int) (Main.WINDOW_WIDTH * Math.random());
            randY = (int) (Main.WINDOW_HEIGHT * Math.random());

            Buraco hole = new Buraco(buracoImg, randX, randY);

            if (hole.colideCom(chegada) || hole.colideCom(largada)) {
                colide = true;
            } else {
                for (Parede parede : paredes) {
                    if (hole.colideCom(parede)) {
                        colide = true;
                    }
                }
            }
            if (!colide) {
                buracos.add(new Buraco(buracoImg, randX, randY));
                nBuracosOk++;
            }
        } while (nBuracosOk < NUM_BURACOS);
    }

    private void gerarParedes() {
        // a dona aranha, subui pela parede, venho a chuva forte e um granito atravessou seu crânio
        /* BEGIN laterais */
        paredes.add(new Parede(blocoImg, 40, true, 0, 580));
        paredes.add(new Parede(blocoImg, 30, false, 0, 0));
        paredes.add(new Parede(blocoImg, 40, true, 0, 0));
        paredes.add(new Parede(blocoImg, 30, false, 780, 0));
        /* END laterais */
        
        /* BEGIN labirinto */
        paredes.add(new Parede(blocoImg, 5, true, 400, 100));
        /* END labirinto */
    }
    

}
