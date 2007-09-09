/*
 * Conection.java
 *
 * Created on April 22, 2007, 11:46 PM
 */

package labirinto.core;

import java.io.*;
import java.net.*;
import labirinto.Constantes;
import labirinto.Main;

public class Conection {
    
    private DatagramSocket udp_socket;
    private DatagramPacket sendPacket;
    private DatagramPacket receivePacket;
    
    private Socket tcp_socket;
    private String message;
    
        
    private byte[] sendData;
    private byte[] receiveData;
    private InetAddress ipAddr;
    private boolean servidor;
    
    private boolean chatON;
    
    private int port = 3000;
    
    
    /** Creates a new instance of Conection */
    
    //cria instancia para o servidor
    public Conection() throws Exception {
        servidor = true;
        udp_socket = new DatagramSocket(3000);
    }
    
    //cria instancia para o cliente
    public Conection(String ip) throws Exception {
        servidor = false;
        ipAddr = InetAddress.getByName(ip);
        udp_socket = new DatagramSocket();
    }
    
    /** Method Conection(int)
     * Glue to the chat problem!
     */
    public Conection(int connection_type) {
        if (connection_type == Constantes.TCP_CON) {
            if(servidor) {
                
            } else {
                
            }
        }
    }
    
    public void setChatON(boolean chatON) {
        this.chatON = chatON;
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
    
    // sender para mensagens String
    public void Send(DataChat message) {
        
    }
    
    // receiver para mensagens String
    public void Receive(DataChat message) {
        
    }
    
    /** Method getData(String message)
     * wait for the message input
     */
    public void getData(String message) {
        DataChat data = new DataChat();
        try {
        Receive(data);
        } catch (Exception e){
            e.printStackTrace();
        }
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
