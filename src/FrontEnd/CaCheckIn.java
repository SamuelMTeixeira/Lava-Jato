/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.ConexaoSQL;
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
public class CaCheckIn extends javax.swing.JFrame {
        
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
        
    //CONFIGURA O NOME DO FRAME
    private void TituloAplicacao (String nomeUsuario){
        this.setTitle("PSC LAVA JATO - Usuário logado: "+nomeUsuario);
    }
    
    // DIMENCIONA O TAMANHO DE ALGUNS COMPONENTES
    private void setDimensionarCampos (){
        this.formatar.setDimencionarIcone(440, 40 ,"/Icons/fundoTextField.png", this.fundoPesquisa);
        
        // DIMENCIONA A BORDA DO MENU LATERAL
        this.formatar.setDimencionarIcone(60, this.getHeight() ,"/Icons/bordaMenu.png", this.imgBorda); 
    }
    
    // EFETUA A AÇÃO DE CADASTRO DO FRAME
    private boolean setExecuteCad(String placa){
        // PEGA O CÓDIGO DO FUNCIONARIO
        int codFuncionario = Integer.parseInt(this.sqlPadroes.getValue("funcionario", "CodFuncionario", this.nomeUsuario));
        
        // PEGA O CODIGO DO TIPO DE LIMPEZA
        int codTipoLimpeza = Integer.parseInt(this.sqlPadroes.getCodByName("tipolimpeza", "CodTipoLimpeza", String.valueOf(this.cbTipoLimpeza.getSelectedItem())));

        // PEGA OS VALORES, ORGANIZA E EXECUTA O INSERT
        String SQLcommand = "INSERT INTO controlelimpezas (DataHoraEntrada, Placa, CodFuncionarioCheckIn, CodTipoLimpeza, Preco) VALUES ( NOW(), '"+placa.toUpperCase()+"',"+codFuncionario+","+codTipoLimpeza+", 0)";
        
        if(this.conexao.SQLExecute(SQLcommand))
            return true;
        else
            return false;

   
     
    }

    // PREENCHE O COMBOBOX 
    private void setPreencherCB (){
        this.sqlPadroes.setPreencherComboBox(cbTipoLimpeza, "tipolimpeza");
    }
       
    public CaCheckIn(String user) {
        initComponents();
        
        this.nomeUsuario = user;
        
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

        // CONFIGURA ALGUNS PADRÕES DA LISTA DE OPÇÕES
        this.listPesquisa.setModel(this.dlm);
        this.listPesquisa.setSelectionBackground(new java.awt.Color(54,100,139));
        this.listPesquisa.setSelectionForeground(Color.white);
        
        // BOOLEAN QUE MARCA SE A BORDA DO MENU ESTÁ ABERTA OU NÃO
        this.isOpened = true;
        
        // DIMENCIONA O TAMANHO DE ALGUNS COMPONENTES
        this.setDimensionarCampos();
        
        // VARIAVEL QUE DEFINE SE O USUARIO SELECIOU OU NÃO UMA OPÇÃO NA LISTA DE PESQUISAS
        this.dataIsSelect = false;
        
        // DEFINE O TIPO DE CÓDIGO COMO PLACA AO INVÉS DE COD
        this.isPlaca = false;
        
        this.setPreencherCB();
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
        listPesquisa = new javax.swing.JList<>();
        iconLupa = new javax.swing.JLabel();
        tfPesquisa = new javax.swing.JTextField();
        fundoPesquisa = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JLabel();
        cbTipoLimpeza = new javax.swing.JComboBox<>();
        txtCb2 = new javax.swing.JLabel();

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
        titulo.setText("CHECK-IN");

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
        btnConcluir.setText("CONCLUIR");
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

        listPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listPesquisaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(listPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 420, -1));

        iconLupa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconLupa.png"))); // NOI18N
        iconLupa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconLupaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(iconLupa, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, 40));

        tfPesquisa.setBackground(new java.awt.Color(255, 255, 255));
        tfPesquisa.setForeground(new java.awt.Color(0, 0, 0));
        tfPesquisa.setBorder(null);
        tfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfPesquisaKeyReleased(evt);
            }
        });
        pnlPesquisa.add(tfPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 390, 20));

        fundoPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundoPesquisa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundoPesquisaMouseClicked(evt);
            }
        });
        pnlPesquisa.add(fundoPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, 40));

        txtPlaca.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtPlaca.setForeground(new java.awt.Color(255, 255, 255));
        txtPlaca.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtPlaca.setText("PLACA DO VEÍCULO:");
        pnlPesquisa.add(txtPlaca, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 170, 40));

        cbTipoLimpeza.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbTipoLimpeza.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        pnlPesquisa.add(cbTipoLimpeza, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 440, 30));

        txtCb2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtCb2.setForeground(new java.awt.Color(255, 255, 255));
        txtCb2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtCb2.setText("TIPO DE LIMPEZA:");
        pnlPesquisa.add(txtCb2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 170, 30));

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(sepTitulo)
                                .addGap(80, 80, 80))
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addComponent(iconTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(titulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPrincipalLayout.createSequentialGroup()
                        .addContainerGap(117, Short.MAX_VALUE)
                        .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)))
                .addComponent(pnLateralMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnLateralMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(sepTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(150, 150, 150)
                .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(pnlLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
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
    }//GEN-LAST:event_formWindowOpened

    private void btnConcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseClicked
        boolean isOK = true;
        
        if(sqlPadroes.existsValue("veiculo", "Placa", tfPesquisa.getText()) == false){
            isOK = false;
        }
        else if(cbTipoLimpeza.getSelectedIndex() == -1){
            isOK = false;
        }
            
        if(isOK == true) {
            // CADASTRA O CHECK-IN
            this.setExecuteCad(tfPesquisa.getText());
                
            // EXIBE A MENSAGEM DE INSERÇÃO CONCUÍDA
            msg.successfullyRegistered("Os dados foram cadastrados");
            this.dispose();
        }
        else 
            msg.erro("dados selecionados", "Por favor, escolha uma opção para editar na barra de pesquisa", tfPesquisa);
    }//GEN-LAST:event_btnConcluirMouseClicked

    private void fundoPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundoPesquisaMouseClicked
        tfPesquisa.requestFocus();
    }//GEN-LAST:event_fundoPesquisaMouseClicked

    private void listPesquisaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listPesquisaMouseClicked
        // PREENCHE O NOME DO DADO NO TEXTFIELD DE PESQUISA E TIRA A LISTA DE OPÇÕES
        tfPesquisa.setText(this.listPesquisa.getSelectedValue());
        listPesquisa.setVisible(false);
    }//GEN-LAST:event_listPesquisaMouseClicked

    private void tfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPesquisaKeyReleased
        listPesquisa.setVisible(true);
        this.sqlPadroes.setPreencherListaOpcoes(listPesquisa, tfPesquisa, "veiculo", "Placa");

        if(tfPesquisa.getText().equals("")) {
            DefaultListModel dlm = new DefaultListModel();
            listPesquisa.setModel(dlm);
            dlm.removeAllElements();
        }
    }//GEN-LAST:event_tfPesquisaKeyReleased

    private void iconLupaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconLupaMouseClicked
        tfPesquisa.requestFocus();
    }//GEN-LAST:event_iconLupaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcluir;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cbTipoLimpeza;
    private javax.swing.JLabel fundoPesquisa;
    private javax.swing.JLabel iconLupa;
    private javax.swing.JLabel iconTitulo;
    private javax.swing.JLabel imgBorda;
    private javax.swing.JList<String> listPesquisa;
    private javax.swing.JPanel pnLateralMenu;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JSeparator sepTitulo;
    private javax.swing.JTextField tfPesquisa;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txtCb2;
    private javax.swing.JLabel txtLogo;
    private javax.swing.JLabel txtPlaca;
    private javax.swing.JLabel txtTituloEmpresa;
    // End of variables declaration//GEN-END:variables
}
