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
public class ShowFuncionarios extends PaShowData {
    public ShowFuncionarios (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"CodFuncionario", "Nome", "CPF", "Usuario", "IsAdministrador"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "CPF", "Usuário", "Previlégio"};
       
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM funcionario";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "funcionários";
        
        // ATIVA A OPÇÃO DE TROCAR DADOS DA TABELA, CASO SEJA O ISADMIN DO FUNCIONARIO
        this.isPrev = true;
    }
}
