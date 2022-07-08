/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.PaCadData;
import BackEnd.PaShowData;

/**
 *
 * @author samuel
 */
public class ReLogin extends PaShowData {
    public ReLogin (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"horario", "nome"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Horário de login", "Usuário"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM REL_HISTORICO_LOGIN";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "registros de login";
    }
}
