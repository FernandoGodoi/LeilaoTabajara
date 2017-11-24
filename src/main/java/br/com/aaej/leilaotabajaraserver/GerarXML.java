/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.aaej.leilaotabajaraserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author TI
 */
public class GerarXML {    
    public GerarXML(){
    }
    public void gerar(ArrayList<Produto> produtos) throws IOException{
        Element root = new Element("List");
        produtos.forEach((p) -> {
            Element produto = new Element("Produto");
            root.addContent(produto);
            Element nome = new Element("nome").setText(p.getNome());
            produto.addContent(nome);
            Element caracteristica = new Element("caracteristica").setText(p.getCaracteristica());
            produto.addContent(caracteristica);
            Element precoInicial = new Element("precoInicial").setText(""+p.getPrecoInicial());
            produto.addContent(precoInicial);
                    });
        Document doc = new Document(root);
        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setEncoding("ISO-8859-1"));
        outputter.output(doc, new PrintWriter(System.out));
        outputter.output(doc, new PrintWriter("ListaProdutos.xml"));
        
    }
}
