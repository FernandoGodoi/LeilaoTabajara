/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaracliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aluno
 */
public class Cliente extends Thread{
    InetAddress srvAddr = null;
    int port;
    DataInputStream in;
    DataOutputStream out;
    Socket sock;
    ClienteFrame frame; 
    public Cliente(int porta, String end, ClienteFrame cli){
        this.port = porta;
        this.frame = cli;
        try {
            this.srvAddr = InetAddress.getByName(end);
            System.out.print("\nConnecting to "+srvAddr.toString()+" at port "+port+"... ");
            sock = new Socket(srvAddr, port);
            System.out.print("[OK]");
            this.in = new DataInputStream(sock.getInputStream());
            this.out = new DataOutputStream(sock.getOutputStream());
        } catch (UnknownHostException ex) {
            System.out.println("Servidor nao encontrado "+ex);
        } catch (IOException ex) {
            System.out.println("Erro ao conectar no servidor "+ex);
        }
    }
     public void send(String data) throws IOException {
        out.writeUTF(data);
    }
    
    public void run() {
        try {

            while (true) {
                /* Receive message from client */
                String data = in.readUTF();
                System.out.print("\n\t[Receive - By Server"  + data);
                String[] msg = data.split(";");

                switch (Integer.parseInt(msg[0])) {
                    case 1:
                        frame.addLog(msg[1]);
                        break;
                    case 2:
                        
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                }

                /* Send the response to client */
            }

        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
    
}
