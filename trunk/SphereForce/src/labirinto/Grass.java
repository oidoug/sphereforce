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
        
        geraburacos(buraco);
        geraobstaculos(pedra);
    }

    public void gerarCenario(){
        super.buracos();
        super.pedras();
    }
    
    public void gerarCenario( LinkedList<Buraco> buracos, LinkedList<Pedra> pedras){
        super.setBuracos(buracos);
        super.setPedras(pedras);
    }

    private void labirinto() {

        paredes.add(new Parede(bloco, 40, false, 0, Constantes.TAMANHO_BLOCO * 29, 1));
        paredes.add(new Parede(bloco, 30, true, 0, 0, 2));
        paredes.add(new Parede(bloco, 40, false, 0, 0, 3));
        paredes.add(new Parede(bloco, 30, true, Constantes.TAMANHO_BLOCO * 39, 0, 4));

        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 8, Constantes.TAMANHO_BLOCO, 5));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 16, Constantes.TAMANHO_BLOCO * 10, 6));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 24, Constantes.TAMANHO_BLOCO, 7));
        paredes.add(new Parede(bloco, 20, true, Constantes.TAMANHO_BLOCO * 31, Constantes.TAMANHO_BLOCO * 10, 8));
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
