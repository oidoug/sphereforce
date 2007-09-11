/*
 * Stones.java
 *
 * Created on Sep 6, 2007, 4:24:58 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labirinto;

import java.applet.AudioClip;
import java.awt.Image;
import labirinto.core.Parede;
import labirinto.core.Marca;
import java.util.LinkedList;
import labirinto.core.Pedra;
import labirinto.core.Buraco;

/**
 *
 * @author Douglas Schmidt
 */

public class Stones extends Cenario {

    public Stones(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim,
            int dificuldade) {
        
        super(background, buraco, bloco, pedra, marca_inicio, marca_fim, dificuldade);

        /* Precisa-se criar as marcas de inicio e fim do cenario */
        super.inicio = new Marca(marca_inicio, 40, 40);
        super.fim = new Marca(marca_fim, Constantes.WINDOW_WIDTH - 100 - 40, Constantes.WINDOW_HEIGHT - 50 - 40);

        labirinto();
        geraburacos(buraco);
        geraobstaculos(pedra);
    }
    

    private void labirinto() {

        paredes.add(new Parede(bloco, 40, false, 0, Constantes.TAMANHO_BLOCO * 29, 1));
        paredes.add(new Parede(bloco, 30, true, 0, 0, 2));
        paredes.add(new Parede(bloco, 40, false, 0, 0, 3));
        paredes.add(new Parede(bloco, 30, true, Constantes.TAMANHO_BLOCO * 39, 0, 4));

        paredes.add(new Parede(bloco, 21, true, Constantes.TAMANHO_BLOCO * 8, 0, 5));
        paredes.add(new Parede(bloco, 21, true, Constantes.TAMANHO_BLOCO * 16, Constantes.TAMANHO_BLOCO * 10, 6));
        paredes.add(new Parede(bloco, 21, true, Constantes.TAMANHO_BLOCO * 24, 0, 7));
        paredes.add(new Parede(bloco, 21, true, Constantes.TAMANHO_BLOCO * 31, Constantes.TAMANHO_BLOCO * 10, 8));
    }
    
    private void geraburacos(Image buraco) {
        LinkedList<Buraco> buracos = new LinkedList<Buraco>();
        
        buracos.add(new Buraco(buraco, 27, 60));
        buracos.add(new Buraco(buraco, 269, 90));
        buracos.add(new Buraco(buraco, 310, 340));
        buracos.add(new Buraco(buraco, 150, 510));
        buracos.add(new Buraco(buraco, 740, 268));
        
        super.setBuracos(buracos);
    }
    
    private void geraobstaculos(Image arvore) {
        LinkedList<Pedra> arvores = new LinkedList<Pedra>();
        
        arvores.add(new Pedra(arvore, 100, 100));
        arvores.add(new Pedra(arvore, 200, 600));
        arvores.add(new Pedra(arvore, 500, 100));
        arvores.add(new Pedra(arvore, 200, 400));
        arvores.add(new Pedra(arvore, 70, 50));
    
        super.setPedras(arvores);
    }
}
