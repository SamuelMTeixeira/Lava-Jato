/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.PaShowData;



/**
 *
 * @author samuel
 */
public class ShowModelo extends PaShowData{
    public ShowModelo (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"cod", "nome", "modal" };
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "Modalidade"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT m.CodModelo AS cod, m.Nome AS nome, md.Nome AS modal FROM modelo m INNER JOIN modalidade md ON md.CodModalidade = m.CodModalidade ";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "modelo";
    }
}
