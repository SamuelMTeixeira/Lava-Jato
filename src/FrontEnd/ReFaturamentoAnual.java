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
public class ReFaturamentoAnual extends PaShowData{
    public ReFaturamentoAnual(String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"ano", "faturamento"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Ano", "Faturamento"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM REL_FATURAMENTO_GERAL_ANUAL";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "faturamento anual";
    }
}
