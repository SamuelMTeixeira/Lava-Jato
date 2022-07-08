/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.PaEditData;

/**
 *
 * @author samuel
 */
public class EdVeiculo extends PaEditData{
    public EdVeiculo(String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;

        // DEFINE A VISIBILIDADE DOS FORMS
        this.typeForm = "both";
        this.tamTF = 1;
        this.tamCB = 3;
        
        // CONFIGURA OS NOMES DO LABEL -- || 1 ao 4 são TextFiel e do 5 ao 7 são combobox
        this.nomesColuna = new String[] {"Placa do veículo", "", "", "", "Modelo", "Cor", "Proprietário"};
        
        // SELECIONA QUAIS OS TIPOS DE VALIDACAO QUE SERÁ FEITO NO TEXTFIELD
        this.typeValidation = new String[] {"Vazio","CBvazio","CBvazio","CBvazio"};
        
        // INDICA QUAL TEXTFIELD SERÁ VALIDADO PARA CADA VETOR ACIMA
        this.campValidation = new String[] {"1", "5","6","7"};
        
        // NOME DA TABELA
        this.view = "veiculo";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "veículo";
        
        // NOME DAS TUPLAS DA TABELA, MODELO ("nome 1, nome2"). OBS: COLOCAR EM ORDEM DA SEQUENCIA DOS COMPONENTES
        this.tuplas = new String[] {"Placa", "CodModelo", "CodCor", "CodProprietario"};

        // INDICA QUAL OS COMBOBOXS SERÃO PREENCHIDOS
        this.posicaoCB = new int[] {5, 6, 7};
        
        // NOME DA TABELA QUE O CB IRÁ RECEBER
        this.viewCB = new String[] {"modelo", "cor", "proprietario"};
        
        // DEFINE O TIPO DE CÓDIGO COMO PLACA AO INVÉS DE COD
        this.isPlaca = true;
    }
}
