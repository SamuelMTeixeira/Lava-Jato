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
public class ReFaturamentoModalidade extends PaShowData{
    public ReFaturamentoModalidade(String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"modalidades", "qtd", "faturamento"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Modalidade","Quantidade de limpezas", "Faturamento"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM REL_FATURAMENTO_MODALIDADE_VEICULOS";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "faturamento por modalidade";
    }
}
