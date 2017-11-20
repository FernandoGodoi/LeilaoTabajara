/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

/**
 *
 * @author aluno
 */
public class Produto {
    private String nome;
    private String caracteristica;
    private double precoInicial;
    private String nomeVencedor;
    private double precoVencedor;
    public Produto(String nome, String carac, double precoInicial){
        this.nome = nome;
        this.caracteristica = carac;
        this.precoInicial = precoInicial;
        this.precoVencedor = precoInicial;
        this.nomeVencedor = "";
    
    }

    public String getNomeVencedor() {
        return nomeVencedor;
    }

    public void setNomeVencedor(String nomeVencedor) {
        this.nomeVencedor = nomeVencedor;
    }

    public double getPrecoVencedor() {
        return precoVencedor;
    }

    public void setPrecoVencedor(double precoVencedor) {
        this.precoVencedor = precoVencedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public double getPrecoInicial() {
        return precoInicial;
    }

    public void setPrecoInicial(double precoInicial) {
        this.precoInicial = precoInicial;
    }
    @Override
    public String toString(){
        return this.nome+";"+this.precoInicial+";"+this.caracteristica;
    }
    
}