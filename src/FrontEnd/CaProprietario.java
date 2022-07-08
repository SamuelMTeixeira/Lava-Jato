/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.PaCadData;

/**
 *
 * @author samuel
 */
public class CaProprietario extends PaCadData{
    public CaProprietario (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;

        // DEFINE A VISIBILIDADE DOS FORMS
        this.typeForm = "tf";
        this.tamTF = 4;
        this.tamCB = 0;
        
        // CONFIGURA OS NOMES DO LABEL -- || 1 ao 4 são TextFiel e do 5 ao 7 são combobox
        this.nomesColuna = new String[] {"Nome", "CPF", "Endereço", "Contatos", "", "", ""};
        
        // SELECIONA QUAIS OS TIPOS DE VALIDACAO QUE SERÁ FEITO NO TEXTFIELD
        this.typeValidation = new String[] {"vazio","cpf","vazio","vazio"};
        
        // INDICA QUAL TEXTFIELD SERÁ VALIDADO PARA CADA VETOR ACIMA
        this.campValidation = new String[] {"1","2","3","4"};
        
        // NOME DA TABELA
        this.view = "proprietario";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "proprietário";
        
        // NOME DAS TUPLAS DA TABELA, MODELO ("nome 1, nome2"). OBS: COLOCAR EM ORDEM DA SEQUENCIA DOS COMPONENTES
        this.tuplas = "(Nome, CPF, Endereco, Contatos)";

        // INDICA QUAL OS COMBOBOXS SERÃO PREENCHIDOS
        this.posicaoCB = new int[] {0};
        
        // NOME DA TABELA QUE O CB IRÁ RECEBER
        this.viewCB = new String[] {""};
    }
}
