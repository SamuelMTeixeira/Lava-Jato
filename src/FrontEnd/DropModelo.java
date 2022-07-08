/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.PaDropData;

/**
 *
 * @author samuel
 */
public class DropModelo extends PaDropData{
    public DropModelo (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"cod", "nome", "modal" };
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "Modalidade"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT m.CodModelo AS cod, m.Nome AS nome, md.Nome AS modal FROM modelo m INNER JOIN modalidade md ON md.CodModalidade = m.CodModalidade ";
    
        // NOME DA TABELA
        this.view = "modelo";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "modelo";
        
        // POSIÇÃO DO GRID ONDE SE ENCONTRA O NOME QUE REFERENCIA À TABELA
        this.posColuna = 1;
        
        // NOME DO DADO QUE IRÁ SER REFERÊNCIA PARA SER APAGADO (CÓDIGO)
        this.tipoDeDado = "CodModelo";
    }
}
