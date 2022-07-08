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
public class DropFuncionario extends PaDropData{
    public DropFuncionario (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"CodFuncionario", "Nome", "CPF", "Usuario", "IsAdministrador"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "CPF", "Usuário", "Previlégio"};
       
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM funcionario";
        
        // NOME DA TABELA
        this.view = "funcionario";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "funcionário";
        
        // POSIÇÃO DO GRID ONDE SE ENCONTRA O NOME QUE REFERENCIA À TABELA
        this.posColuna = 1;
        
        // NOME DO DADO QUE IRÁ SER REFERÊNCIA PARA SER APAGADO (CÓDIGO)
        this.tipoDeDado = "CodFuncionario";
        
        // ATIVA A OPÇÃO DE TROCAR DADOS DA TABELA, CASO SEJA O ISADMIN DO FUNCIONARIO
        this.isPrev = true;
    }
}
