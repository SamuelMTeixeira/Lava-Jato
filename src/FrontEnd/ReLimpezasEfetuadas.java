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
public class ReLimpezasEfetuadas extends PaShowData {
    public ReLimpezasEfetuadas (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"placa", "modalidade", "modelo", "Proprietario", "funcionarioCheckIn", "funcionarioCheckOut", "horaEntrada", "horaFinalizado", "horaSaida", "precoBruto", "formaPagamento", "imposto", "precoLiquido"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Placa", "Modalidade", "Modelo", "Proprietario", "Funcionario CheckIn", "Funcionario CheckOut", "Entrada", "Finalização", "Saída", "Preço bruto", "Tipo de pagamento", "Imposto", "Preço líquido"};
        
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM REL_LIMPEZAS_EFETUADAS";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "limpezas efetuadas";
    }
}
