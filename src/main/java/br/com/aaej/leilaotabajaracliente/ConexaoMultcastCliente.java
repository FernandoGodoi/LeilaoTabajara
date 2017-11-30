/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaracliente;

import br.com.aaej.leilaotabajaraserver.Produto;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class ConexaoMultcastCliente extends Thread {

    int porta;
    String nome;
    String grupo;
    byte[] buffer;
    DatagramPacket pacote;
    Boolean stop = true;
    MulticastSocket socket;
    DatagramPacket msgOut;
    ClienteFrame frame;

    public ConexaoMultcastCliente(int porta, String nome, String grupo, ClienteFrame cli) {
        this.porta = porta;
        this.nome = nome;
        this.grupo = grupo;
        this.frame = cli;
    }

    public void parar() {
        try {
            socket.leaveGroup(InetAddress.getByName(grupo));
            socket.close();
            stop = false;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConexaoMultcastCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoMultcastCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarMensagem(String msg) {
        byte[] data = msg.getBytes();
        try {
            msgOut = new DatagramPacket(data, data.length, InetAddress.getByName(grupo), porta);
            socket.send(msgOut);
        } catch (IOException ex) {
            System.out.println("ConectarThread - deu pal para enviar mensagem");
        }
    }

    public void run() {
        try {
            InetAddress group = InetAddress.getByName(grupo);
            socket = new MulticastSocket(porta);
            socket.joinGroup(group);
            System.out.println("Multcast Join");
            buffer = new byte[1000];
            pacote = new DatagramPacket(buffer, buffer.length);

            while (stop) {
                buffer = new byte[1024];
                pacote = new DatagramPacket(buffer, buffer.length);
                socket.receive(pacote);               
                String data = new String(buffer);
                System.out.print("\n\t[Receive - By Server" + data);
                String[] msg = data.split(";");
                Produto p;
                switch (Integer.parseInt(msg[0])) {
                    case 1:
                        frame.addLog(msg[1]);
                        break;
                    case 2:
                        p = frame.produtos.get(Integer.parseInt(msg[1]));
                        p.setNomeVencedor(msg[3]);
                        p.setPrecoVencedor(Double.parseDouble(msg[2]));
                        frame.atualizarProdutos();
                        frame.addLog("Produto atualizado: " + p.getNome()+ " no valor: "+ p.getPrecoVencedor()+ "Para: "+ p.getNomeVencedor());
                        break;
                    case 3:
                        p = frame.produtos.get(Integer.parseInt(msg[1]));
                        p.setFinalizado(true);
                        frame.atualizarProdutos();
                        frame.addLog("Item " + p.getNome() + " Finalizado");
                        break;
                    case 4:
                        break;
                    default:

                        pacote = new DatagramPacket(buffer, buffer.length);

                }
            }

        } catch (Exception e) {
            System.out.println("Deu pal no ConectarThread RUN() Cliente:" + e);
        }
    }
}
