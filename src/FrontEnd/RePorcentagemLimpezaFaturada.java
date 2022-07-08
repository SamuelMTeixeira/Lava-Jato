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
public class RePorcentagemLimpezaFaturada extends PaShowData{
    public RePorcentagemLimpezaFaturada(String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"tipo", "porcentagem"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Tipo de limpeza", "Porcentagem de limpezas efetuadas"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM REL_PORCENTAGEM_TIPO_LIMPEZA_FATURADA";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "porcentagem dos tipo de limpeza efetuada";
    }
}
