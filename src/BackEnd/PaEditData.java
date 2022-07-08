/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

    Template
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
    }

 */
package BackEnd;

import Padroes.FormatacaoDeCampos;
import Padroes.Mensagens_Prontas;
import Padroes.Validacoes;
import Padroes.sql_padroes;
import java.awt.Color;
import javax.swing.DefaultListModel;
/**
 *
 * @author samuel
 */
public abstract class PaEditData extends javax.swing.JFrame {
        
    private ConexaoSQL          conexao;
    private sql_padroes         sqlPadroes;
    private Mensagens_Prontas   msg;
    private FormatacaoDeCampos  formatar;
    private Validacoes          validar;
    private DefaultListModel    dlm;
    protected boolean           isPlaca;
    private boolean             dataIsSelect;
    protected String            nomeUsuario;
    protected String            tabela;
    private boolean             isOpened;
    
    protected String            view;
    protected String[]          tuplas;
        
    protected String            typeForm;
    protected int               tamTF;
    protected int               tamCB;
    protected int[]             posicaoCB;
    protected String[]          viewCB;
    protected boolean           cbEstatico;
    
    protected String[]          nomesColuna;
    protected String[]          typeValidation;
    protected String[]          campValidation;

    //CONFIGURA O NOME DO FRAME
    private void TituloAplicacao (String nomeUsuario){
        this.setTitle("PSC LAVA JATO - Usuário logado: "+nomeUsuario);
    }
    
    //CONFIGURA O NOME DA TABELA
    private void setTituloJanela (){ 
        titulo.setText("Edição de "+this.tabela);
        
        // CONFIGURA O NOME PARA A CAIXA DE PESQUISA
        this.tabelaPesquisa.setText("PESQUISE O NOME DE "+this.tabela.toUpperCase()+" PARA EDITÁ-LO");
    }

    // DIMENCIONA O TAMANHO DE ALGUNS COMPONENTES
    private void setDimensionarCampos (){
        //DIMENCIONA A IMAGEM DE FUNDO DOS JTEXTFIELDS
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundo1);
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundo2);
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundo3);
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundo4);
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundoPesquisa);
        
        // DIMENCIONA A BORDA DO MENU LATERAL
        this.formatar.setDimencionarIcone(60, this.getHeight() ,"/Icons/bordaMenu.png", this.imgBorda); 
    }
   
    // CONFIGURA A VISIBILIDADE DOS COMPONENTES DO FORMULÁRIO
        /* BOTH - TEXTFIELD E COMBOBOX
           CB   - PARA COMBOBOX APENAS
           TF   - PARA TEXTFIELD APENAS */
    private void setUseForm (String type, int qtdTF, int qtdCB){    
        // CONFIGURA A VISIBILIDADE DO PAINEL
        switch(type.toUpperCase()){
            case "BOTH":
                this.sepConteudos.setVisible(true);
                this.pnlConteudo.setVisible(true);
                this.formComboBox.setVisible(true);
                this.formTextField.setVisible(true);                
                break;
            case "CB":
                this.sepConteudos.setVisible(false);
                this.pnlConteudo.setVisible(true);
                this.formComboBox.setVisible(true);
                this.formTextField.setVisible(false);
                break;
            case "TF":
                this.sepConteudos.setVisible(false);
                this.pnlConteudo.setVisible(true);
                this.formComboBox.setVisible(false);
                this.formTextField.setVisible(true);
                break; 
            default:
                this.msg.texto("Erro de configuração dos paineis");
                break;
        }
        
        // CONFIGURA A VISIBILIDADE DO ITENS DO PAINEL
        /* DOS COMBOBOX */
        switch (qtdCB) {
            case 1:
                this.txt5.setVisible(true);
                this.cb5.setVisible(true);
                this.txt6.setVisible(false);
                this.cb6.setVisible(false);
                this.txt7.setVisible(false);
                this.cb7.setVisible(false);
                break;
            case 2:
                this.txt5.setVisible(true);
                cb5.setVisible(true);
                txt6.setVisible(true);
                cb6.setVisible(true);
                txt7.setVisible(false);
                cb7.setVisible(false);
                break;
            case 3:
                txt5.setVisible(true);
                cb5.setVisible(true);
                txt6.setVisible(true);
                cb6.setVisible(true);
                txt7.setVisible(true);
                cb7.setVisible(true);
                break;
            default:
                txt5.setVisible(false);
                cb5.setVisible(false);
                txt6.setVisible(false);
                cb6.setVisible(false);
                txt7.setVisible(false);
                cb7.setVisible(false);
                break;
        }

        /* DOS TEXTFIELDS */
        switch (qtdTF) {
            case 1:
                txt1.setVisible(true);
                tf1.setVisible(true);
                fundo1.setVisible(true);
                txt2.setVisible(false);
                tf2.setVisible(false);
                fundo2.setVisible(false);
                txt3.setVisible(false);
                tf3.setVisible(false);
                fundo3.setVisible(false);
                txt4.setVisible(false);
                tf4.setVisible(false);
                fundo4.setVisible(false);
                break;
            case 2:
                txt1.setVisible(true);
                tf1.setVisible(true);
                fundo1.setVisible(true);
                txt2.setVisible(true);
                tf2.setVisible(true);
                fundo2.setVisible(true);
                txt3.setVisible(false);
                tf3.setVisible(false);
                fundo3.setVisible(false);
                txt4.setVisible(false);
                tf4.setVisible(false);
                fundo4.setVisible(false);
                break;
            case 3:
                txt1.setVisible(true);
                tf1.setVisible(true);
                fundo1.setVisible(true);
                txt2.setVisible(true);
                tf2.setVisible(true);
                fundo2.setVisible(true);
                txt3.setVisible(true);
                tf3.setVisible(true);
                fundo3.setVisible(true);
                txt4.setVisible(false);
                tf4.setVisible(false);
                fundo4.setVisible(false);
                break;
            case 4:
                txt1.setVisible(true);
                tf1.setVisible(true);
                fundo1.setVisible(true);
                txt2.setVisible(true);
                tf2.setVisible(true);
                fundo2.setVisible(true);
                txt3.setVisible(true);
                tf3.setVisible(true);
                fundo3.setVisible(true);
                txt4.setVisible(true);
                tf4.setVisible(true);
                fundo4.setVisible(true);
                break;
            default:
                txt1.setVisible(false);
                tf1.setVisible(false);
                fundo1.setVisible(false);
                txt2.setVisible(false);
                tf2.setVisible(false);
                fundo2.setVisible(false);
                txt3.setVisible(false);
                tf3.setVisible(false);
                fundo3.setVisible(false);
                txt4.setVisible(false);
                tf4.setVisible(false);
                fundo4.setVisible(false);
                break;
        }
        
    }
    
    // EFETUA A AÇÃO DE CADASTRO DO FRAME
    private void setExecuteCad(int cod){
        
        // PEGA O NOME DA TABELA E TRANSFORMA ELA NO NOME DO CODIGO. FUNCIONA APENAS PRO PADRAO "CodNome"
        String str = this.view;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());
        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        String codigoDaTabela = "Cod"+str;
        
        
        switch(this.typeForm.toUpperCase()){
            case "BOTH":
                
                if(this.cbEstatico == false){
                    if(this.tamTF == 1 && this.tamCB == 1) {
                        this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
                        this.tuplas[0]+" = '"+tf1.getText()+"',"+
                        this.tuplas[1]+" = "+this.sqlPadroes.getCodigoPorCB(this.cb5, this.viewCB[0])+" "+
                        "WHERE ("+codigoDaTabela+" = "+cod+")");
                    }
                }
                // SE FOR UTILIZAR O COMBOBOX ESTÁTICO DO CADASTRO DE FUNCIONÁRIOS
                else if (this.cbEstatico == true){
                    this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
                        this.tuplas[0]+" = '"+tf1.getText()+"',"+
                        this.tuplas[1]+" = '"+tf2.getText()+"',"+
                        this.tuplas[2]+" = '"+tf3.getText()+"',"+
                        this.tuplas[3]+" = '"+tf2.getText()+"',"+
                        this.tuplas[4]+" = "+this.cb5.getSelectedIndex()+" "+
                        "WHERE ("+codigoDaTabela+" = "+cod+")");
                }
                break;
            case "TF":
                
            switch (this.tamTF) {
                case 1:
                    this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
                        this.tuplas[0]+" = '"+tf1.getText()+"' "+
                        "WHERE ("+codigoDaTabela+" = "+cod+")");
                    break;
                case 2:
                        this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
                        this.tuplas[0]+" = '"+tf1.getText()+"',"+
                        this.tuplas[1]+" = '"+tf2.getText()+"' "+
                        "WHERE ("+codigoDaTabela+" = "+cod+")");
                    break;
                case 3:

                    break;
                case 4:
                    this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
                        this.tuplas[0]+" = '"+tf1.getText()+"',"+
                        this.tuplas[1]+" = '"+tf2.getText()+"',"+
                        this.tuplas[2]+" = '"+tf3.getText()+"',"+
                        this.tuplas[3]+" = '"+tf4.getText()+"' "+
                        "WHERE ("+codigoDaTabela+" = "+cod+")");
                    break;
                default:
                    break;
            }
                break;
            default:
                break;
        }
    }
    
    // EFETUA A AÇÃO DE CADASTRO DO FRAME
    private void setExecuteCadPlaca(String placa){
        this.conexao.SQLExecute("UPDATE "+this.view+" SET "+
            this.tuplas[0]+" = '"+tf1.getText()+"',"+
            this.tuplas[1]+" = "+this.sqlPadroes.getCodigoPorCB(this.cb5, this.viewCB[0])+", "+       
            this.tuplas[2]+" = "+this.sqlPadroes.getCodigoPorCB(this.cb6, this.viewCB[1])+", "+
            this.tuplas[3]+" = "+this.sqlPadroes.getCodigoPorCB(this.cb7, this.viewCB[2])+" "+
            "WHERE (Placa LIKE '"+placa+"')");   
    }
    
    
    // CONFIGURA OS NOMES DE CADA JLABEL DOS FORMULÁRIOS
    private void setConfigLabel (){
        this.txt1.setText(this.nomesColuna[0].toUpperCase());
        this.txt2.setText(this.nomesColuna[1].toUpperCase());
        this.txt3.setText(this.nomesColuna[2].toUpperCase());
        this.txt4.setText(this.nomesColuna[3].toUpperCase());
        this.txt5.setText(this.nomesColuna[4].toUpperCase());
        this.txt6.setText(this.nomesColuna[5].toUpperCase());
        this.txt7.setText(this.nomesColuna[6].toUpperCase());
    }
   
    // PREENCHE O COMBOBOX 
    private void setPreencherCB (){
        
        if(this.cbEstatico == false) {
            for(int i = 0; i < this.posicaoCB.length; i++){
                switch (this.posicaoCB[i]) {
                    case 5:
                        this.sqlPadroes.setPreencherComboBox(cb5, this.viewCB[i]);
                        break;
                    case 6:
                        this.sqlPadroes.setPreencherComboBox(cb6, this.viewCB[i]);
                        break;
                    case 7:
                        this.sqlPadroes.setPreencherComboBox(cb7, this.viewCB[i]);
                        break;
                    default:
                        cb5.removeAllItems();
                        cb6.removeAllItems();
                        cb7.removeAllItems();
                        break;
                }
            }
        }
    }
       
    // REALIZA VALIDAÇÕES DOS CAMPO 
    private boolean validateForm () {
        boolean isOk = false;
        for(int i=0; i < typeValidation.length; i++){
            switch(typeValidation[i].toUpperCase()){
                
                // TIPO QUE VERIFICA SE O VALOR É UM NUMERO
                case "NUMERICO":
                    // VALIDA DE ACORDO COM O TEXTFIELD
                    switch (this.campValidation[i]) {
                        case "1":
                            if(validar.isFloat(tf1, this.nomesColuna[0]) ){
                                isOk = true; 
                            }
                            else {
                                return false;
                            }
                            break;
                        case "2":
                            if(validar.isFloat(tf2, this.nomesColuna[1]) ){
                                isOk = true; 
                            }
                            else {
                                return false;
                            }
                            break;
                        case "3":
                            if(validar.isFloat(tf3, this.nomesColuna[2]) ){
                                isOk = true; 
                            }
                            else {
                                return false;
                            }
                            break;
                        case "4":
                            if(validar.isFloat(tf4, this.nomesColuna[3]) ){
                                isOk = true; 
                            }
                            else {
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                break;
                
                // TIPO QUE VERIFICA SE O VALOR É UM CPF
                case "CPF":
                    // VALIDA DE ACORDO COM O TEXTFIELD
                    switch (this.campValidation[i]) {
                        case "1":
                            if(validar.isCPF(tf1.getText())){
                                isOk = true;
                            }
                            else {
                                msg.erro("CPF", "Este cpf é inválido", tf1);
                                return false;
                            }
                            break;
                        case "2":
                            if(validar.isCPF(tf2.getText()) ){
                                isOk = true;
                            }
                            else {
                                msg.erro("CPF", "Este cpf é inválido", tf2);
                                return false;
                            }
                            break;
                        case "3":
                            if(validar.isCPF(tf3.getText()) ){
                                isOk = true;
                            }
                            else {
                                msg.erro("CPF", "Este cpf é inválido", tf3);
                                tf3.requestFocus();
                                return false;
                            }
                            break;
                        case "4":
                            if(validar.isCPF(tf4.getText()) ){
                                isOk = true;
                            }
                            else {
                                msg.erro("CPF", "Este cpf é inválido", tf4);
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                break;
                
                case "VAZIO":
                    // VALIDA DE ACORDO COM O TEXTFIELD
                    switch (this.campValidation[i]) {
                        case "1":
                            if(!tf1.getText().equals("") ){
                                isOk = true;
                            }
                            else {
                                msg.erro(this.nomesColuna[0], "Este campo não pode ficar vazio", tf1);
                                return false;
                            }
                            break;
                        case "2":
                            if(!tf2.getText().equals("") ){
                                isOk = true;
                            }
                            else {
                                msg.erro(this.nomesColuna[0], "Este campo não pode ficar vazio", tf2);
                                return false;
                            }
                            break;
                        case "3":
                            if(!tf3.getText().equals("")){
                                isOk = true;
                            }
                            else {
                                msg.erro(this.nomesColuna[0], "Este campo não pode ficar vazio", tf3);
                                return false;
                            }
                            break;
                        case "4":
                            if(!tf4.getText().equals("") ){
                                isOk = true;
                            }
                            else {
                                msg.erro(this.nomesColuna[0], "Este campo não pode ficar vazio", tf4);
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                   break;
                
                case "CBVAZIO":
                    // VALIDA DE ACORDO COM O TEXTFIELD
                    switch (this.campValidation[i]) {
                        case "5":
                            if(validar.isCBempty(this.cb5) == false){
                                isOk = true;
                            }
                            else {
                                msg.erroParaCombobox(this.nomesColuna[4], "Este campo não pode ficar vazio", cb5);
                                return false;
                            }
                            break;
                        case "6":
                            if (validar.isCBempty(this.cb6) == false){
                                isOk = true;
                            }
                            else {
                                msg.erroParaCombobox(this.nomesColuna[4], "Este campo não pode ficar vazio", cb6);
                                return false;
                            }
                            break;
                        case "7":
                            if(validar.isCBempty(this.cb7) == false){
                                isOk = true;
                            }
                            else {
                                msg.erroParaCombobox(this.nomesColuna[4], "Este campo não pode ficar vazio", cb7);
                                return false;
                            }
                            break;
                        default:
                            break;
                    }
                    
                    break;                 
                default:
                    msg.texto("Erro ao validar formulário, não existe este tipo de validação");
                    break;
            }
            
            if(isOk == false ){
                return false;
            }
            
        }
        return isOk;
    }

    private void setPreencherCampos (String codigo){
        int cod;
    // PREENCHE OS DADOS 
        try {
            switch(this.typeForm.toUpperCase()){
            case "BOTH":
                
                if(this.cbEstatico == false){
                    if(this.tamTF == 1 && this.tamCB == 1) {
                        cod = Integer.parseInt(codigo);
                        tf1.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[0] , cod));       
                              
                        cb5.setSelectedItem(this.sqlPadroes.getValueByCod(this.viewCB[0], this.tuplas[0], Integer.parseInt(this.sqlPadroes.getValueByCod(this.view, this.tuplas[1] , cod))));
                    }
                    
                    else if (this.tamTF == 1 && this.tamCB == 3) {
                        tf1.setText(this.sqlPadroes.getValueByPlaca(this.view, this.tuplas[0] , codigo));       
                        
                        cb5.setSelectedItem(sqlPadroes.getValueByCod(this.viewCB[0], "Nome", Integer.parseInt(this.sqlPadroes.getValueByPlaca(this.view, this.tuplas[1] , codigo))));
                        
                        cb6.setSelectedItem(sqlPadroes.getValueByCod(this.viewCB[1], "Nome", Integer.parseInt(this.sqlPadroes.getValueByPlaca(this.view, this.tuplas[2] , codigo))));
                        
                        cb7.setSelectedItem(sqlPadroes.getValueByCod(this.viewCB[2], "Nome", Integer.parseInt(this.sqlPadroes.getValueByPlaca(this.view, this.tuplas[3] , codigo))));
                        
                    }
                }
                // SE FOR UTILIZAR O COMBOBOX ESTÁTICO DO CADASTRO DE FUNCIONÁRIOS
                else if (this.cbEstatico == true){
                    cod = Integer.parseInt(codigo);
                    tf1.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[0] , cod));
                    tf2.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[1] , cod));
                    tf3.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[2] , cod));
                    
                    cb5.setSelectedIndex(Integer.parseInt(this.sqlPadroes.getValueByCod(this.view, this.tuplas[4] , cod)));
                }
                break;
            case "TF":
                
            switch (this.tamTF) {
                case 1:
                    cod = Integer.parseInt(codigo);
                    tf1.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[0] , cod));
                    break;
                case 2:
                    cod = Integer.parseInt(codigo);
                    tf1.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[0] , cod));
                    tf2.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[1] , cod));
                    break;
                case 3:
                    
                    break;
                case 4:
                    cod = Integer.parseInt(codigo);
                    tf1.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[0] , cod));
                    tf2.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[1] , cod));
                    tf3.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[2] , cod));
                    tf4.setText(this.sqlPadroes.getValueByCod(this.view, this.tuplas[3] , cod));
                    break;
                default:
                    break;
            }
                break;
            default:
                break;
            }
        }
        catch (Exception e){
            msg.texto("erro ao preencher dados");
        }
    }
    
    public PaEditData() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        
        // INSTANCIA ALGUMAS CLASSES QUE SERÃO UTAdministradorAdminiAILIZADAS
        this.msg        = new Mensagens_Prontas();
        this.formatar   = new FormatacaoDeCampos();
        this.sqlPadroes = new sql_padroes();
        this.conexao    = new ConexaoSQL();
        this.validar    = new Validacoes();
        this.dlm        = new DefaultListModel();
        
        // PERSONALIZA O BOTÃO MENU
        btnMenu.setOpaque(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);

        
        // BOOLEAN QUE MARCA SE A BORDA DO MENU ESTÁ ABERTA OU NÃO
        this.isOpened = true;
        
        // DIMENCIONA O TAMANHO DE ALGUNS COMPONENTES
        this.setDimensionarCampos();
        
        // CONFIGURA ALGUNS PADRÕES DA LISTA DE OPÇÕES
        this.listPesquisa.setModel(this.dlm);
        this.listPesquisa.setSelectionBackground(new java.awt.Color(54,100,139));
        this.listPesquisa.setSelectionForeground(Color.white);
        
        // DEIXA OS COMPONETES DESABILITADOS POR PADRÃO, ATÉ ESCOLHER QUAIS IRÃO SER UTILIZADOS
        this.pnlConteudo.setVisible(false);
        
        // VARIAVEL QUE DEFINE SE O USUARIO SELECIOU OU NÃO UMA OPÇÃO NA LISTA DE PESQUISAS
        this.dataIsSelect = false;
        
        // DEFINE O TIPO DE CÓDIGO COMO PLACA AO INVÉS DE COD
        this.isPlaca = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrincipal = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        iconTitulo = new javax.swing.JLabel();
        sepTitulo = new javax.swing.JSeparator();
        pnLateralMenu = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        imgBorda = new javax.swing.JLabel();
        pnlLateral = new javax.swing.JPanel();
        txtLogo = new javax.swing.JLabel();
        btnConcluir = new javax.swing.JButton();
        sepLogo = new javax.swing.JSeparator();
        txtTituloEmpresa = new javax.swing.JLabel();
        pnlPesquisa = new javax.swing.JPanel();
        iconLupa = new javax.swing.JLabel();
        tfPesquisa = new javax.swing.JTextField();
        fundoPesquisa = new javax.swing.JLabel();
        listPesquisa = new javax.swing.JList<>();
        tabelaPesquisa = new javax.swing.JLabel();
        pnlConteudo = new javax.swing.JPanel();
        formTextField = new javax.swing.JPanel();
        txt1 = new javax.swing.JLabel();
        tf1 = new javax.swing.JTextField();
        fundo1 = new javax.swing.JLabel();
        txt2 = new javax.swing.JLabel();
        tf2 = new javax.swing.JTextField();
        fundo2 = new javax.swing.JLabel();
        txt3 = new javax.swing.JLabel();
        tf3 = new javax.swing.JTextField();
        fundo3 = new javax.swing.JLabel();
        txt4 = new javax.swing.JLabel();
        tf4 = new javax.swing.JTextField();
        fundo4 = new javax.swing.JLabel();
        formComboBox = new javax.swing.JPanel();
        txt5 = new javax.swing.JLabel();
        cb5 = new javax.swing.JComboBox<>();
        txt6 = new javax.swing.JLabel();
        cb6 = new javax.swing.JComboBox<>();
        txt7 = new javax.swing.JLabel();
        cb7 = new javax.swing.JComboBox<>();
        sepConteudos = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlPrincipal.setBackground(new java.awt.Color(79, 148, 205));

        titulo.setFont(new java.awt.Font("MV Boli", 1, 30)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Edição de dados");

        iconTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconEdit.png"))); // NOI18N

        pnLateralMenu.setBackground(new java.awt.Color(79, 148, 205));
        pnLateralMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconMenu.png"))); // NOI18N
        btnMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMenuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMenuMouseExited(evt);
            }
        });
        pnLateralMenu.add(btnMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 50, -1));

        imgBorda.setBackground(new java.awt.Color(79, 148, 205));
        imgBorda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/bordaMenu.png"))); // NOI18N
        imgBorda.setToolTipText("");
        pnLateralMenu.add(imgBorda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlLateral.setBackground(new java.awt.Color(17, 32, 63));
        pnlLateral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/LOGO.png"))); // NOI18N
        pnlLateral.add(txtLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 0, 217, -1));

        btnConcluir.setBackground(new java.awt.Color(255, 255, 255));
        btnConcluir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnConcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconVerificado.png"))); // NOI18N
        btnConcluir.setText("CADASTRAR");
        btnConcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConcluirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConcluirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConcluirMouseExited(evt);
            }
        });
        pnlLateral.add(btnConcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 256, 240, -1));

        sepLogo.setBackground(new java.awt.Color(255, 255, 255));
        sepLogo.setForeground(new java.awt.Color(255, 255, 255));
        pnlLateral.add(sepLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 228, 240, 10));

        txtTituloEmpresa.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        txtTituloEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        txtTituloEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloEmpresa.setText("PSC LAVA-JATO");
        pnlLateral.add(txtTituloEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 186, 217, -1));

        pnlPesquisa.setBackground(new java.awt.Color(79, 148, 205));
        pnlPesquisa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconLupa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconLupa.png"))); // NOI18N
        iconLupa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconLupaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(iconLupa, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 0, -1, 40));

        tfPesquisa.setBackground(new java.awt.Color(255, 255, 255));
        tfPesquisa.setForeground(new java.awt.Color(0, 0, 0));
        tfPesquisa.setBorder(null);
        tfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPesquisaKeyReleased(evt);
            }
        });
        pnlPesquisa.add(tfPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, 390, 20));

        fundoPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundoPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundoPesquisaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(fundoPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, -1, 40));

        listPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listPesquisaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(listPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, 420, -1));

        tabelaPesquisa.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        tabelaPesquisa.setForeground(new java.awt.Color(255, 255, 255));
        tabelaPesquisa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tabelaPesquisa.setText("PESQUISE O NOME DO XXXXXXXXX");
        pnlPesquisa.add(tabelaPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 510, 20));

        pnlConteudo.setBackground(new java.awt.Color(79, 148, 205));

        formTextField.setBackground(new java.awt.Color(79, 148, 205));
        formTextField.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt1.setForeground(new java.awt.Color(255, 255, 255));
        txt1.setText("LABEL 1");
        formTextField.add(txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 200, -1));

        tf1.setBackground(new java.awt.Color(255, 255, 255));
        tf1.setForeground(new java.awt.Color(0, 0, 0));
        tf1.setBorder(null);
        formTextField.add(tf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 420, 20));

        fundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo1MouseClicked(evt);
            }
        });
        formTextField.add(fundo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, 40));

        txt2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt2.setForeground(new java.awt.Color(255, 255, 255));
        txt2.setText("LABEL 2");
        formTextField.add(txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 200, -1));

        tf2.setBackground(new java.awt.Color(255, 255, 255));
        tf2.setForeground(new java.awt.Color(0, 0, 0));
        tf2.setBorder(null);
        formTextField.add(tf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 420, 20));

        fundo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo2MouseClicked(evt);
            }
        });
        formTextField.add(fundo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, 40));

        txt3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt3.setForeground(new java.awt.Color(255, 255, 255));
        txt3.setText("LABEL 3");
        formTextField.add(txt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 200, -1));

        tf3.setBackground(new java.awt.Color(255, 255, 255));
        tf3.setForeground(new java.awt.Color(0, 0, 0));
        tf3.setBorder(null);
        formTextField.add(tf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 420, 20));

        fundo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo3MouseClicked(evt);
            }
        });
        formTextField.add(fundo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, 40));

        txt4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt4.setForeground(new java.awt.Color(255, 255, 255));
        txt4.setText("LABEL 4");
        formTextField.add(txt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 200, -1));

        tf4.setBackground(new java.awt.Color(255, 255, 255));
        tf4.setForeground(new java.awt.Color(0, 0, 0));
        tf4.setBorder(null);
        formTextField.add(tf4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 420, 20));

        fundo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo4MouseClicked(evt);
            }
        });
        formTextField.add(fundo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, -1, 40));

        formComboBox.setBackground(new java.awt.Color(79, 148, 205));

        txt5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt5.setForeground(new java.awt.Color(255, 255, 255));
        txt5.setText("LABEL 5");

        cb5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Operador", "Administrador" }));
        cb5.setSelectedIndex(-1);

        txt6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt6.setForeground(new java.awt.Color(255, 255, 255));
        txt6.setText("LABEL 6");

        txt7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txt7.setForeground(new java.awt.Color(255, 255, 255));
        txt7.setText("LABEL 7");

        javax.swing.GroupLayout formComboBoxLayout = new javax.swing.GroupLayout(formComboBox);
        formComboBox.setLayout(formComboBoxLayout);
        formComboBoxLayout.setHorizontalGroup(
            formComboBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formComboBoxLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(formComboBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cb5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(formComboBoxLayout.createSequentialGroup()
                        .addGroup(formComboBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt7, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 90, Short.MAX_VALUE))
                    .addComponent(cb6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cb7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        formComboBoxLayout.setVerticalGroup(
            formComboBoxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formComboBoxLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(txt5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(txt6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(txt7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cb7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        sepConteudos.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout pnlConteudoLayout = new javax.swing.GroupLayout(pnlConteudo);
        pnlConteudo.setLayout(pnlConteudoLayout);
        pnlConteudoLayout.setHorizontalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addContainerGap(96, Short.MAX_VALUE)
                .addComponent(formTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(sepConteudos, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(formComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlConteudoLayout.setVerticalGroup(
            pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlConteudoLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pnlConteudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sepConteudos, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(iconTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(titulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sepTitulo)
                                    .addComponent(pnlPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE))))
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(pnLateralMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnLateralMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(iconTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(sepTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(pnlConteudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        // FUNÇÃO QUE FECHA E ABRE O MENU LATERAL...
        if(this.isOpened == true) {
            pnlLateral.setVisible(false);
            this.isOpened = false;}
        
        else {
            pnlLateral.setVisible(true);
            this.isOpened = true;}
    }//GEN-LAST:event_btnMenuMouseClicked

    private void btnConcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseExited
        btnConcluir.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btnConcluirMouseExited

    private void btnConcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseEntered
        btnConcluir.setBackground(new java.awt.Color(152 ,251, 152));
    }//GEN-LAST:event_btnConcluirMouseEntered

    private void btnMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseEntered
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconMenuSelect.png")));
    }//GEN-LAST:event_btnMenuMouseEntered

    private void btnMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseExited
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconMenu.png")));
    }//GEN-LAST:event_btnMenuMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // DEFINE O NOME DO FRAME
        this.TituloAplicacao(this.nomeUsuario);
       
        // CONFIGURA O NOME DA JANELA
        this.setTituloJanela();
        
    }//GEN-LAST:event_formWindowOpened

    private void btnConcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseClicked
        boolean isOK = true;
        
        // VALIDA SE OS DOS INFORMADOS SÃO VÁLIDOS
        if(this.validateForm() == false){
            isOK = false;
        }
        // CASO NÃO TENHA NENHUM VALOR ERRADO, É EFETUADO O CADASTRO
        else {
            
            if(this.dataIsSelect == true) {
                
                if(this.isPlaca == false)
                    // EXECUTA O COMANDO SQL DO CADASTRO
                    this.setExecuteCad(this.sqlPadroes.getCodigoByList(this.listPesquisa, this.view));
                else
                    //EXECUTA CADASTRO DO VEÍCULO, UNICO DIFERENTE
                    this.setExecuteCadPlaca(this.listPesquisa.getSelectedValue());

                
                
                // EXIBE A MENSAGEM DE INSERÇÃO CONCUÍDA
                msg.successfullyRegistered("Os dados foram cadastrados");
                this.dispose();
            }
            else 
                msg.erro("dados selecionados", "Por favor, escolha uma opção para editar na barra de pesquisa", tfPesquisa);
        }
    }//GEN-LAST:event_btnConcluirMouseClicked

    private void fundo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo2MouseClicked
        tf2.requestFocus();
    }//GEN-LAST:event_fundo2MouseClicked

    private void fundo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo1MouseClicked
        tf1.requestFocus();
    }//GEN-LAST:event_fundo1MouseClicked

    private void fundo3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo3MouseClicked
        tf3.requestFocus();
    }//GEN-LAST:event_fundo3MouseClicked

    private void fundo4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo4MouseClicked
        tf4.requestFocus();
    }//GEN-LAST:event_fundo4MouseClicked

    private void fundoPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundoPesquisaMouseClicked
        tfPesquisa.requestFocus();
    }//GEN-LAST:event_fundoPesquisaMouseClicked

    private void iconLupaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLupaMouseClicked
        tfPesquisa.requestFocus();
    }//GEN-LAST:event_iconLupaMouseClicked

    private void tfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaKeyReleased
        listPesquisa.setVisible(true);
        this.sqlPadroes.setPreencherListaOpcoes(listPesquisa, tfPesquisa, this.view, this.tuplas[0]);
    }//GEN-LAST:event_tfPesquisaKeyReleased

    private void listPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPesquisaMouseClicked
        String codSelect;
        
        // PEGA O COD DO DADO SELECIONADO
        if(this.isPlaca == false)
            codSelect = String.valueOf(this.sqlPadroes.getCodigoByList(this.listPesquisa, this.view));
        else
            codSelect = this.listPesquisa.getSelectedValue();
        
        // PREENCHE O NOME DO DADO NO TEXTFIELD DE PESQUISA E TIRA A LISTA DE OPÇÕES
        tfPesquisa.setText(this.listPesquisa.getSelectedValue());
        listPesquisa.setVisible(false);
        
        // DEFINE A VISIBILIDADE DOS FORMS
        this.setUseForm(this.typeForm, this.tamTF, this.tamCB);
        
        // CONFIGURA A VISIBILIADE DOS LABELS
        this.setConfigLabel();
        
        // PREENCHE OS COMBOBOX SE TIVER
        if(this.tamCB > 0)
            this.setPreencherCB();
        
        // PREENCHE OS CAMPOS COM OS DADOS DA TABELA SELECIONADO
        this.setPreencherCampos(codSelect);
        this.dataIsSelect = true;
    }//GEN-LAST:event_listPesquisaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcluir;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cb5;
    private javax.swing.JComboBox<String> cb6;
    private javax.swing.JComboBox<String> cb7;
    private javax.swing.JPanel formComboBox;
    private javax.swing.JPanel formTextField;
    private javax.swing.JLabel fundo1;
    private javax.swing.JLabel fundo2;
    private javax.swing.JLabel fundo3;
    private javax.swing.JLabel fundo4;
    private javax.swing.JLabel fundoPesquisa;
    private javax.swing.JLabel iconLupa;
    private javax.swing.JLabel iconTitulo;
    private javax.swing.JLabel imgBorda;
    private javax.swing.JList<String> listPesquisa;
    private javax.swing.JPanel pnLateralMenu;
    private javax.swing.JPanel pnlConteudo;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSeparator sepConteudos;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JSeparator sepTitulo;
    private javax.swing.JLabel tabelaPesquisa;
    private javax.swing.JTextField tf1;
    private javax.swing.JTextField tf2;
    private javax.swing.JTextField tf3;
    private javax.swing.JTextField tf4;
    private javax.swing.JTextField tfPesquisa;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txt1;
    private javax.swing.JLabel txt2;
    private javax.swing.JLabel txt3;
    private javax.swing.JLabel txt4;
    private javax.swing.JLabel txt5;
    private javax.swing.JLabel txt6;
    private javax.swing.JLabel txt7;
    private javax.swing.JLabel txtLogo;
    private javax.swing.JLabel txtTituloEmpresa;
    // End of variables declaration//GEN-END:variables
}
