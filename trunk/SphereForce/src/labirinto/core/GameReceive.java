/*
 * GameReceive.java
 *
 * Created on September 11, 2007, 1:32 AM
 *
 * This content can be protected with right commons, creative commons or
 * some free license. Check the main doc for more information. Thanks.
 */

package labirinto.core;

/**
 *
 * @author Douglas Schmidt
 */
public class GameReceive implements Runnable{
    
    private ConectionUdp conn;
    private Esfera esfera;
    private DataGame datag;
    
    /** Creates a new instance of GameReceive */
    public GameReceive( ConectionUdp conn, Esfera esfera ) {
        this.conn = conn;
        this.esfera = esfera;
    }
    
    public void run() {
        while(true) {
            try {
                datag = conn.getData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            esfera.refresh(datag);
        }
    }
    
}
