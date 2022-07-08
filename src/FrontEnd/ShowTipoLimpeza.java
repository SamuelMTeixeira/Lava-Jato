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
public class ShowTipoLimpeza extends PaShowData{
    public ShowTipoLimpeza (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"CodTipoLimpeza", "Nome", "Preco"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "Preço"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM tipolimpeza";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "tipo de limpeza";
    }
}
