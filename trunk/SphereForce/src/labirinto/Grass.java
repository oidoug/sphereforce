/*
 * Grass.java
 *
 * Created on September 11, 2007, 5:39 AM
 *
 * This content can be protected with right commons, creative commons or
 * some free license. Check the main doc for more information. Thanks.
 */

package labirinto;

import java.awt.Image;
import java.util.LinkedList;
import labirinto.core.Buraco;
import labirinto.core.Marca;
import labirinto.core.Parede;
import labirinto.core.Pedra;

/**
 *
 * @author Douglas Schmidt
 */
public class Grass extends Cenario{
    
    /** Creates a new instance of Grass */
    public Grass(Image background, Image buraco, Image bloco, Image pedra, Image marca_inicio, Image marca_fim,
            int dificuldade) {
                super(background, buraco, bloco, pedra, marca_inicio, marca_fim, dificuldade);

        /* Precisa-se criar as marcas de inicio e fim do cenario */
        super.inicio = new Marca(marca_inicio, 40, 40);
        super.fim = new Marca(marca_fim, Constantes.WINDOW_WIDTH - 100 - 40, Constantes.WINDOW_HEIGHT - 50 - 40);

        labirinto();
        
 
    }


    private void labirinto() {

        paredes.add(new Parede(bloco, 40, false, 0, Constantes.TAMANHO_BLOCO * 29, 1));
        paredes.add(new Parede(bloco, 30, true, 0, 0, 2));
        paredes.add(new Parede(bloco, 40, false, 0, 0, 3));
        paredes.add(new Parede(bloco, 30, true, Constantes.TAMANHO_BLOCO * 39, 0, 4));

        paredes.add(new Parede(bloco, 4, false, Constantes.TAMANHO_BLOCO * 10, Constantes.TAMANHO_BLOCO * 25, 51));
        paredes.add(new Parede(bloco, 5, false, 0, Constantes.TAMANHO_BLOCO * 24, 52));
        paredes.add(new Parede(bloco, 6, false, Constantes.TAMANHO_BLOCO * 15, Constantes.TAMANHO_BLOCO * 21, 61));
        paredes.add(new Parede(bloco, 10, false, Constantes.TAMANHO_BLOCO * 5, Constantes.TAMANHO_BLOCO * 20, 62));
        paredes.add(new Parede(bloco, 25, false, 0, Constantes.TAMANHO_BLOCO * 15, 7));
        paredes.add(new Parede(bloco, 15 , false, Constantes.TAMANHO_BLOCO * 15, Constantes.TAMANHO_BLOCO * 10, 8));
        paredes.add(new Parede(bloco, 10, false, Constantes.TAMANHO_BLOCO * 5, Constantes.TAMANHO_BLOCO * 5, 8));
    }
    
}
