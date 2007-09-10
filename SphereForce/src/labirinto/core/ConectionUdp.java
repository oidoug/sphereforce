/*
 * ConectionUdp.java
 *
 * Created on September 9, 2007, 8:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package labirinto.core;

/**
 *
 * @author r
 */

import java.io.*;
import java.net.*;
import labirinto.Constantes;
import labirinto.Main;

public class ConectionUdp {
    
    private DatagramSocket udp_socket;
    
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;

    private byte[] sendData;
    private byte[] receiveData;

    private InetAddress ipAddr;
    private int porta;
    private boolean servidor;
    
    private int port = 3000;
    
    
    /** Creates a new instance of Conection */
    
    //cria instancia para o servidor
    public ConectionUdp() throws Exception {
        servidor = true;
        udp_socket = new DatagramSocket(3000);
    }
    
    //cria instancia para o cliente
    public ConectionUdp(String ip) throws Exception {
        servidor = false;
        ipAddr = InetAddress.getByName(ip);
        udp_socket = new DatagramSocket();
    }
   

    // sender para dados do jogo
    public void Send(DataGame data) throws Exception {
        try {
            sendData = Serialize(data);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (servidor){
            sendPacket = 
                    new DatagramPacket
                    (sendData,sendData.length,ipAddr,porta);
        }
        else {
            sendPacket = new DatagramPacket
                    (sendData, sendData.length, ipAddr, port);
        }

        udp_socket.send(sendPacket);
    }
    
    // receiver para dados do jogo
    public DataGame Receive() throws Exception {
        DataGame data;
        receiveData = new byte[300];
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        udp_socket.receive(receivePacket);
        data = UnSerialize(receivePacket.getData());
        return data;
    }
        
    /** Method getData()
     * returns the Object DataGame data, captured by the connection instance
     */
    public DataGame getData() {
        DataGame data = new DataGame();
        try {
            data = Receive();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
  
    public byte[] Serialize (DataGame data) throws Exception {
        byte[] byteVector;
        ByteArrayOutputStream bao = new ByteArrayOutputStream ();
        ObjectOutputStream oo = new ObjectOutputStream(bao);
        oo.writeObject(data);
        oo.close();
        byteVector = new byte[bao.toByteArray().length];
        byteVector = bao.toByteArray();
        return byteVector;
    }
    

    public DataGame UnSerialize (byte[] byteVector) throws Exception{
        DataGame data;
        ByteArrayInputStream bai = new ByteArrayInputStream(byteVector);
        ObjectInputStream oi = new ObjectInputStream (bai);
        data = (DataGame) oi.readObject();
        return data;
    }
  
}
