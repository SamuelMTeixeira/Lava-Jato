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
public class ShowVeiculo extends PaShowData{
    public ShowVeiculo (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"placa", "modelo", "cor", "proprietario"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Placa", "Modelo", "Cor", "Proprietário"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT v.Placa AS placa, m.nome AS modelo, c.Nome AS cor, p.Nome AS proprietario FROM veiculo v  INNER JOIN modelo m ON m.CodModelo = v.CodModelo INNER JOIN cor c ON c.CodCor = v.CodCor INNER JOIN proprietario p ON p.CodProprietario = v.CodProprietario";
    
        // CONFIGURA O NOME DA JANELA
        this.tabela = "veículo";
    
    }
}
