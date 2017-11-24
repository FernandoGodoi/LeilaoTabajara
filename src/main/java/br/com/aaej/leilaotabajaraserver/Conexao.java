/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author TI
 */
public class Conexao extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    String nome;
    IniciarLeilaoFrame leilao;

    public Conexao(Socket aClientSocket, IniciarLeilaoFrame leilao) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            nome = "";
            this.leilao = leilao;

        } catch (IOException e) {
            System.out.println("Connection: " + e.getMessage());
        }
    }

    public void send(String data) throws IOException {
        out.writeUTF(data);
        System.out.println("Enviando "+data);
    }

    @Override
    public String toString() {
        return this.nome; //To change body of generated methods, choose Tools | Templates.
    }

    public void run() {
        try {

            while (true) {
                /* Receive message from client */
                String data = in.readUTF();
                System.out.print("\n\t[Receive - " + clientSocket.getInetAddress().toString()
                        + ":" + clientSocket.getPort() + "]: " + this.nome + " >  " + data);
                String[] msg = data.split(";");

                switch (Integer.parseInt(msg[0])) {
                    case 1:
                        this.nome = msg[1];
                        leilao.atualizarConexao();
                        send("1;Registrado!");
                        break;
                    case 2:
                        Produto p = leilao.produtos.get(Integer.parseInt(msg[1]));
                        if (p.isFinalizado()) {
                            send("1;Este produto foi Finalizado!");
                        } else {
                            if (p.getPrecoVencedor()>= Double.parseDouble(msg[2])){
                                send("1;Valor invalido");
                            }else{
                                send("1;Lance Aceito");
                                p.setNomeVencedor(nome);
                                p.setPrecoVencedor(Double.parseDouble(msg[2]));
                                leilao.atualizarProdutos();
                                leilao.atualizarClientes("2;"+msg[1]+";"+msg[2]+";"+nome);
                            }
                        }
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
