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
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
/**
 *
 * @author samuel
 */
public class CaSaidaVeiculo extends javax.swing.JFrame {
        
    private     ConexaoSQL          conexao;
    private     sql_padroes         sqlPadroes;
    private     Mensagens_Prontas   msg;
    private     FormatacaoDeCampos  formatar;
    private     Validacoes          validar;
    private     DefaultListModel    dlm;
    protected   boolean             isPlaca;
    private     boolean             dataIsSelect;
    protected   String              nomeUsuario;
    protected   String              tabela;
    private     boolean             isOpened;
    
    private     int                 codControleLimpeza;
    private     float               precoLimpeza;
    private     float               precoFinal;
    private     int                 CodFormaPagamento;
        
    //CONFIGURA O NOME DO FRAME
    private void TituloAplicacao (String nomeUsuario){
        this.setTitle("PSC LAVA JATO - Usuário logado: "+nomeUsuario);
    }
    
    // FAZ O CHECK-OUT
    private void setCheckOut(){
        // REALIZA O CHECK-OUT SE EXISTIR UM CODIGO
        this.conexao.SQLExecute("UPDATE controlelimpezas SET DataHoraSaida = NOW(), CodFuncionarioCheckOut = '"+Integer.parseInt(this.sqlPadroes.getValue("funcionario", "CodFuncionario", this.nomeUsuario))+"', Preco = "+this.precoFinal+", CodFormaPagamento = "+this.CodFormaPagamento+" WHERE CodControleLimpezas = "+this.codControleLimpeza); 
    
        
    }

    // PREENCHE O COMBOBOX 
    private void setPreencherCB (){
        this.sqlPadroes.setPreencherComboBox(cbFormaPagamento, "formapagamento");
    }
    
    // PREENCHE OS VALORES DO VEICULO A SER ENTREGUE
    private void setPreencherDados(){
       String placa = this.sqlPadroes.getValueByCod("controlelimpezas", "Placa", this.codControleLimpeza);
       valorPlaca.setText(placa);
       
       int codModelo = Integer.parseInt(this.sqlPadroes.getValueByPlaca("veiculo", "CodModelo", placa));
       String modelo = this.sqlPadroes.getValueByCod("modelo", "Nome", codModelo);
       valorModelo.setText(modelo);
       
       int codCor = Integer.parseInt(this.sqlPadroes.getValueByPlaca("veiculo", "CodCor", placa));
       String cor = this.sqlPadroes.getValueByCod("cor", "Nome", codCor);
       valorCor.setText(cor);
       
    }
    
    private String getPegarJuros(JComboBox cb){
        String Nome = String.valueOf(cb.getSelectedItem());
        String juros = "";
        String cod = "";
        
        
        try {
            conexao.setResultSet("SELECT Imposto AS juros, CodFormaPagamento AS cod FROM formapagamento WHERE Nome LIKE '"+Nome+"';");
            if(this.conexao.getResultSet().first() ) {
                       juros = this.conexao.getResultSet().getString("juros");
                       cod   = this.conexao.getResultSet().getString("cod");
            }
        }
        catch (SQLException e){
            this.msg.texto("erro na forma de pagamento");
        }
        
        if(!cod.equals(""))
            this.CodFormaPagamento = Integer.parseInt(cod);
        
        return juros;
    }
    
    public CaSaidaVeiculo(String user, int codCL, float precoLimpeza) {
        initComponents();
        
        this.nomeUsuario        = user;
        this.codControleLimpeza = codCL;
        this.precoLimpeza       = precoLimpeza;
        
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
        
        // VARIAVEL QUE DEFINE SE O USUARIO SELECIOU OU NÃO UMA OPÇÃO NA LISTA DE PESQUISAS
        this.dataIsSelect = false;
        
        // DEFINE O TIPO DE CÓDIGO COMO PLACA AO INVÉS DE COD
        this.isPlaca = false;
        
        // PREENCHE O COMBOBOX DO FRAME
        this.setPreencherCB();
        
        // PREENCHE OS VALORES DO VEICULO A SER ENTREGUE
        this.setPreencherDados();
       
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
        txtPlaca = new javax.swing.JLabel();
        txtModelo = new javax.swing.JLabel();
        txtCor = new javax.swing.JLabel();
        txtPrecoFinal = new javax.swing.JLabel();
        valorPlaca = new javax.swing.JLabel();
        valorModelo = new javax.swing.JLabel();
        valorCor = new javax.swing.JLabel();
        valorPrecoFinal = new javax.swing.JLabel();
        txtCb2 = new javax.swing.JLabel();
        cbFormaPagamento = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();

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
        titulo.setText("Retirada do veículo");

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

        txtPlaca.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        txtPlaca.setForeground(new java.awt.Color(255, 255, 255));
        txtPlaca.setText("PLACA:");

        txtModelo.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        txtModelo.setForeground(new java.awt.Color(255, 255, 255));
        txtModelo.setText("MODELO:");

        txtCor.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        txtCor.setForeground(new java.awt.Color(255, 255, 255));
        txtCor.setText("COR:");

        txtPrecoFinal.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        txtPrecoFinal.setForeground(new java.awt.Color(0, 102, 51));
        txtPrecoFinal.setText("PREÇO FINAL:      ");

        valorPlaca.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        valorPlaca.setForeground(new java.awt.Color(204, 204, 204));
        valorPlaca.setText("xxx");

        valorModelo.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        valorModelo.setForeground(new java.awt.Color(204, 204, 204));
        valorModelo.setText("xxx");

        valorCor.setBackground(new java.awt.Color(204, 204, 204));
        valorCor.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        valorCor.setForeground(new java.awt.Color(204, 204, 204));
        valorCor.setText("xxx");

        valorPrecoFinal.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        valorPrecoFinal.setForeground(new java.awt.Color(0, 102, 51));
        valorPrecoFinal.setText("escolha um método de pagamento primeiro");

        javax.swing.GroupLayout pnlPesquisaLayout = new javax.swing.GroupLayout(pnlPesquisa);
        pnlPesquisa.setLayout(pnlPesquisaLayout);
        pnlPesquisaLayout.setHorizontalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrecoFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(valorModelo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(valorCor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(valorPrecoFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                        .addComponent(valorPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPesquisaLayout.setVerticalGroup(
            pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPesquisaLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlaca)
                    .addComponent(valorPlaca))
                .addGap(24, 24, 24)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModelo)
                    .addComponent(valorModelo))
                .addGap(24, 24, 24)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCor)
                    .addComponent(valorCor))
                .addGap(48, 48, 48)
                .addGroup(pnlPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecoFinal)
                    .addComponent(valorPrecoFinal))
                .addGap(12, 12, 12))
        );

        txtCb2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtCb2.setForeground(new java.awt.Color(255, 255, 255));
        txtCb2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtCb2.setText("FORMA DE PAGAMENTO:");

        cbFormaPagamento.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbFormaPagamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFormaPagamentoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlPrincipalLayout = new javax.swing.GroupLayout(pnlPrincipal);
        pnlPrincipal.setLayout(pnlPrincipalLayout);
        pnlPrincipalLayout.setHorizontalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                                .addGap(0, 104, Short.MAX_VALUE)
                                .addComponent(txtCb2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnlPesquisa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sepTitulo))
                        .addGap(80, 80, 80)))
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
                .addGap(49, 49, 49)
                .addComponent(pnlPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCb2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    }//GEN-LAST:event_formWindowOpened

    private void btnConcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseClicked
        boolean isOK = true;
        
        if(cbFormaPagamento.getSelectedIndex() == -1){
            isOK = false;
        }

        if(isOK == true) {
            // ENTREGA O VEICULO SE O USUARIO CONFIRMAR
            if(this.msg.confirmarAcao("confirmar a entrega do veículo")){
                
                // EXECUTA O CHECK-OUT
                this.setCheckOut();
                
                // EXIBE A MENSAGEM DE INSERÇÃO CONCUÍDA
                msg.successfullyRegistered("Os dados foram cadastrados");
                this.dispose();
            }

        }
        else 
            msg.problemMessage("Entregar veículo", "Não foi selecionado nenhum meio de pagamento");
        
    }//GEN-LAST:event_btnConcluirMouseClicked

    private void cbFormaPagamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFormaPagamentoItemStateChanged

        // ALTERA O PREÇO FINAL DA LIMPEZA DE ACORDO COM A FORMA DE PAGAMENTO
        if(cbFormaPagamento.getSelectedIndex() == -1){
            valorPrecoFinal.setText("escolha uma forma de pagamento primeiro");
        } else {
            cbFormaPagamento.addItemListener((java.awt.event.ItemEvent evt1) -> {

                if (evt1.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                    float valor = precoLimpeza;
                    float juros = Float.parseFloat(this.getPegarJuros(cbFormaPagamento));

                    float valorFinal = (valor - (valor * juros));

                    precoFinal = valorFinal;
                    txtPrecoFinal.setText("PREÇO FINAL: R$");
                    valorPrecoFinal.setText(String.format("%.2f", valorFinal));

                }
            });
        }
    }//GEN-LAST:event_cbFormaPagamentoItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcluir;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cbFormaPagamento;
    private javax.swing.JLabel iconTitulo;
    private javax.swing.JLabel imgBorda;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel pnLateralMenu;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlPesquisa;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JSeparator sepTitulo;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txtCb2;
    private javax.swing.JLabel txtCor;
    private javax.swing.JLabel txtLogo;
    private javax.swing.JLabel txtModelo;
    private javax.swing.JLabel txtPlaca;
    private javax.swing.JLabel txtPrecoFinal;
    private javax.swing.JLabel txtTituloEmpresa;
    private javax.swing.JLabel valorCor;
    private javax.swing.JLabel valorModelo;
    private javax.swing.JLabel valorPlaca;
    private javax.swing.JLabel valorPrecoFinal;
    // End of variables declaration//GEN-END:variables
}
