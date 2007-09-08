/*
 * Conection.java
 *
 * Created on April 22, 2007, 11:46 PM
 */

package labirinto.core;

import java.io.*;
import java.net.*;
import labirinto.Main;

public class Conection {
    
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    private byte[] sendData;
    private byte[] receiveData;
    private InetAddress ipAddr;
    private boolean servidor;
    
    private int port = 3000;
    
    /** Creates a new instance of Conection */
    
    //cria instancia para o servidor
    public Conection() throws Exception {
        servidor = true;
        socket = new DatagramSocket(3000);
    }
    
    //cria instancia para o cliente
    public Conection(String ip) throws Exception {
        servidor = false;
        ipAddr = InetAddress.getByName(ip);
        socket = new DatagramSocket();
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
            int port = receivePacket.getPort();
            ipAddr = receivePacket.getAddress();
            sendPacket = 
                    new DatagramPacket
                    (sendData,sendData.length,ipAddr,port);
        }
        else {
            sendPacket = new DatagramPacket
                    (sendData, sendData.length, ipAddr, port);
        }
        socket.send(sendPacket);
    }
    // sender para mensagens String
    public void Send(DataChat message) {
        
    }
    
    // receiver para mensagens String
    public void Receive(DataChat message) {
        
    }
    
    // receiver para dados do jogo
    public DataGame Receive() throws Exception {
        DataGame data;
        receiveData = new byte[300];
        receivePacket = new DatagramPacket(receiveData,receiveData.length);
        socket.receive(receivePacket);
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
