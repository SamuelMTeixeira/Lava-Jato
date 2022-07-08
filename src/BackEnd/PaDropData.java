/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

    Template
        // DEFINE O NOME DO USUÁRIO 
        this.nomeUsuario = username;
        
        // NOMES DAS COLUNAS NO BANCO DE DADOS 
        this.nomeDadosColuna = new String [] {"CodFuncionario", "Nome", "CPF", "Usuario", "IsAdministrador"};
        
        // NOME DAS COLUNAS NO GRID 
        this.nomesColuna = new String [] {"Código", "Nome", "CPF", "Usuário", "Previlégio"};
       
        // COMANDO SQL PARA PREENCHER GRID
        this.SQLcommand = "SELECT * FROM xxxxxx";
        
        // NOME DA TABELA
        this.view = "xxxxxx";

        // CONFIGURA O NOME DA JANELA
        this.tabela = "funcionário";
        
        // POSIÇÃO DO GRID ONDE SE ENCONTRA O NOME QUE REFERENCIA À TABELA
        this.posColuna = 1;
        
        // NOME DO DADO QUE IRÁ SER REFERÊNCIA PARA SER APAGADO (CÓDIGO)
        this.tipoDeDado = "xxxxxxxxx";

 */
package BackEnd;

import Padroes.FormatacaoDeCampos;
import Padroes.Mensagens_Prontas;
import Padroes.sql_padroes;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author samuel
 */
public abstract class PaDropData extends javax.swing.JFrame {
        
    private ConexaoSQL          conexao;
    private sql_padroes         sqlPadroes;
    protected DefaultTableModel dtm;
    private Mensagens_Prontas   msg;
    private FormatacaoDeCampos  formatar;
    private int                 previlegios;
    protected String            nomeUsuario;
    protected String            tabela;
    private boolean             isOpened;
    
    protected String[]          nomesColuna;
    protected String[]          nomeDadosColuna;
    protected String            view;
    protected int               posColuna;
    protected String            SQLcommand;
    protected String            tipoDeDado;
    protected boolean           isPrev;
    
    //CONFIGURA O NOME DO FRAME
    private void TituloAplicacao (String nomeUsuario){
        this.setTitle("PSC LAVA JATO - Usuário logado: "+nomeUsuario);
    }

    //CONFIGURA O NOME DA TABELA
    private void setTituloJanela (){ 
        titulo.setText("Cadastro de "+this.tabela);
    }
    
    // FORMATA OS DADOS DA TABELA
    public void setGrid () {        
        grdDados.setModel(new javax.swing.table.DefaultTableModel(
                /* DEIXA A TABELA SEM LINHAS INICIALMENTE*/
                new Object [][] { },
                /* NOME DAS COLUNAS */
                this.nomesColuna      
        ) 
        // DEIXA A COLUNA NÃO EDITÁVEL
        {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
    }
    
    // SUBSTITUI DADOS DA TABELA POR OUTROS
    private void setTrocarDados(){
        // SUBSTITUI O 0 E 1 DO ISADMIN POR ADM E OPERADOR
        for(int i = 0; i < this.grdDados.getRowCount(); i++){
            if(String.valueOf(this.grdDados.getValueAt(i, 4)).toLowerCase().equals("0"))
                this.grdDados.setValueAt("operador", i, 4);
            else if(String.valueOf(this.grdDados.getValueAt(i, 4)).toLowerCase().equals("1"))
                this.grdDados.setValueAt("administrador", i, 4);
        }  
    }
    
    
    // PREENCHE OS DADOS DA TABELA
    public void setPreencherGrid () {
        dtm = (DefaultTableModel) grdDados.getModel();
        
        // LIMPA OS DADOS ANTIGOS DA TABELA 
        while(dtm.getRowCount() > 0 )
            dtm.removeRow(0);
        
        // PREENCHE A TABELA
        try{
            conexao.setResultSet(SQLcommand);
            
            if(conexao.getResultSet().first()) {
                do {
                    Object[] campos = new Object[this.nomeDadosColuna.length];
                    
                    for(int i = 0; i < this.nomeDadosColuna.length; i++){
                        campos[i] = this.conexao.getResultSet().getString(this.nomeDadosColuna[i]);
                    }
                    dtm.addRow(campos);
                    
                }while(conexao.getResultSet().next());
            }
            
        }
        catch (SQLException e){
            this.msg.texto("Erro ao preencher grid");
        }
    }
    
    
    public PaDropData() {
        initComponents();
        this.isPrev = false;
        this.setExtendedState(MAXIMIZED_BOTH);
        
        // INSTANCIA ALGUMAS CLASSES QUE SERÃO UTILIZADAS
        this.msg        = new Mensagens_Prontas();
        this.formatar   = new FormatacaoDeCampos();
        this.sqlPadroes = new sql_padroes();
        this.conexao    = new ConexaoSQL();
        
        // PERSONALIZA AS CORES DA TABELA PRINCIPAL
        scrollListaVeiculos.getViewport().setBackground(new java.awt.Color(79,148,205));
        grdDados.setSelectionBackground(new java.awt.Color(54,100,139));
        
        // PERSONALIZA O BOTÃO MENU
        btnMenu.setOpaque(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);
        
        // DIMENCIONA A BORDA DO MENU LATERAL
        this.formatar.setDimencionarIcone(60, this.getHeight() ,"/Icons/bordaMenu.png", this.imgBorda);  
        
        // BOOLEAN QUE MARCA SE A BORDA DO MENU ESTÁ ABERTA OU NÃO
        this.isOpened = true;
        
        //PREENCHE OS VALORES DA TABELA
        this.setGrid();
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
        scrollListaVeiculos = new javax.swing.JScrollPane();
        grdDados = new javax.swing.JTable();
        txtFiltro = new javax.swing.JLabel();
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
        cbFiltro = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlPrincipal.setBackground(new java.awt.Color(79, 148, 205));

        grdDados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "dados"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdDados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollListaVeiculos.setViewportView(grdDados);

        txtFiltro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtFiltro.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconLupa.png"))); // NOI18N

        titulo.setFont(new java.awt.Font("MV Boli", 1, 30)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("Lista de dados");

        iconTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconAgenda.png"))); // NOI18N

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
        btnConcluir.setText("EXCLUIR DADO");
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

        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ordem de cadastro" }));

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
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sepTitulo, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scrollListaVeiculos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlPrincipalLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtFiltro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(80, 80, 80)))
                .addComponent(pnLateralMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlLateral, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlPrincipalLayout.setVerticalGroup(
            pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPrincipalLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlPrincipalLayout.createSequentialGroup()
                        .addComponent(sepTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(txtFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollListaVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(pnLateralMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addComponent(pnlPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        // FORMATA OS DADOS DA TABELA
        this.setGrid();
        
        // PREENCHE A TABELA
        this.setPreencherGrid();
        
        // DEFINE O NOME DO FRAME
        this.TituloAplicacao(this.nomeUsuario);
        
        // PEGA O TIPO DE PREVILÉGIO DA PESSOA (USUÁRIO X OPERADOR)
        this.previlegios = Integer.parseInt(sqlPadroes.getValue("funcionario", "IsAdministrador", this.nomeUsuario));
        
        // CONFIGURA O NOME DA JANELA
        this.setTituloJanela();
        
        // TROCA OS VALORES DA TABELO, CASO SEJA AUTORIZADO
        if(isPrev == true) 
            this.setTrocarDados();
        
    }//GEN-LAST:event_formWindowOpened

    private void btnConcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseClicked
            if(grdDados.getSelectedRow() <0 ) {               
                JOptionPane.showMessageDialog(this, "Nenhum dado foi selecionado");
            }
            else if (this.msg.confirmarAcao("Excluir os dados de "+String.valueOf(grdDados.getValueAt(grdDados.getSelectedRow(), this.posColuna)) )) {
                
                // APAGA UMA TUPLA DA TABELA
                this.conexao.SQLExecute("DELETE FROM "+view+" WHERE "+this.tipoDeDado+" = '"+String.valueOf(grdDados.getValueAt(grdDados.getSelectedRow(), 0))+"'");

                this.setPreencherGrid();
                msg.successfullyRegistered("Dados excluídos");
                this.dispose();
            }   
    }//GEN-LAST:event_btnConcluirMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcluir;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JTable grdDados;
    private javax.swing.JLabel iconTitulo;
    private javax.swing.JLabel imgBorda;
    private javax.swing.JPanel pnLateralMenu;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JScrollPane scrollListaVeiculos;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JSeparator sepTitulo;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txtFiltro;
    private javax.swing.JLabel txtLogo;
    private javax.swing.JLabel txtTituloEmpresa;
    // End of variables declaration//GEN-END:variables
}
