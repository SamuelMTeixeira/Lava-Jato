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
public class DropCor extends PaDropData{
        public DropCor(String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"CodCor", "Nome"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM cor";
        
        // NOME DA TABELA
        this.view = "cor";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "cor";
        
        // POSIÇÃO DO GRID ONDE SE ENCONTRA O NOME QUE REFERENCIA À TABELA
        this.posColuna = 1;
        
        // NOME DO DADO QUE IRÁ SER REFERÊNCIA PARA SER APAGADO (CÓDIGO)
        this.tipoDeDado = "CodCor";
    }
}
