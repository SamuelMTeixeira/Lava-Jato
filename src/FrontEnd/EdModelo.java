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
public class EdModelo extends PaEditData{
    public EdModelo (String username){
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;

        // DEFINE A VISIBILIDADE DOS FORMS
        this.typeForm = "both";
        this.tamTF = 1;
        this.tamCB = 1;
        
        // CONFIGURA OS NOMES DO LABEL -- || 1 ao 4 são TextFiel e do 5 ao 7 são combobox
        /* Numerico | CPF | Vazio | Cbvazio */
        this.nomesColuna = new String[] {"Nome", "", "", "", "Modalidade", "", ""};
        
        // SELECIONA QUAIS OS TIPOS DE VALIDACAO QUE SERÁ FEITO NO TEXTFIELD
        this.typeValidation = new String[] {"vazio","CBVAZIO"};
        
        // INDICA QUAL TEXTFIELD SERÁ VALIDADO PARA CADA VETOR ACIMA
        this.campValidation = new String[] {"1","5"};
        
        // NOME DA TABELA
        this.view = "modelo";
        
        // CONFIGURA O NOME DA JANELA
        this.tabela = "modelo";
        
        // NOME DAS TUPLAS DA TABELA. OBS: COLOCAR EM ORDEM DA SEQUENCIA DOS COMPONENTES
        this.tuplas = new String[] {"Nome", "CodModalidade"};

        // INDICA QUAL OS COMBOBOXS SERÃO PREENCHIDOS
        this.posicaoCB = new int[] {5};
        
        // NOME DA TABELA QUE O CB IRÁ RECEBER
        this.viewCB = new String[] {"modalidade"};
    }
}
