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
        this.SQLcommand = "SELECT * FROM xxxxxxx";


 */
package BackEnd;

import BackEnd.ConexaoSQL;
import Padroes.FormatacaoDeCampos;
import Padroes.Mensagens_Prontas;
import Padroes.sql_padroes;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author samuel
 */
public abstract class PaShowData extends javax.swing.JFrame {
        
    private ConexaoSQL          conexao;
    protected DefaultTableModel dtm;
    private sql_padroes         sqlPadroes;
    private Mensagens_Prontas   msg;
    private FormatacaoDeCampos  formatar;
    private int                 previlegios;
    protected String            nomeUsuario;
    protected String            tabela;
    private boolean             isOpened;
    
    protected String[]          nomesColuna;
    protected String[]          nomeDadosColuna;
    protected String            view;
    protected String            SQLcommand;
    protected boolean           isPrev;
    
    private String Titulo;
    private Document document;
    
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
        
        this.grdDados.getColumnModel().getColumn(0).setMinWidth(140); 
        this.grdDados.getColumnModel().getColumn(0).setMaxWidth(140); 
    }
    
    private void getRelatorio(JTable Grid) {             
        int tab, widthcol = 0;
        try {
            //Nome do arquivo que será criado na pasta do .jar
            PdfWriter.getInstance(document, new FileOutputStream("Relatorio.pdf"));

            Paragraph p;
            document.open();  

            //Inserir título 
            p = new Paragraph();                 
            p.add(new Chunk(Titulo + "\n\n", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD)));                
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);                

            //Inserir cabeçalho  
            tab = 0;
            p = new Paragraph(); 
            for(int j=0; j<Grid.getColumnCount(); j++) {
                if (Grid.getColumnModel().getColumn(j).getPreferredWidth() > 0) {
                    if (tab > 0) {
                        float tamanho = (float) widthcol;                                                                                                
                        p.setTabSettings(new TabSettings(tamanho));                                               
                        p.add(Chunk.createTabspace(tamanho));            
                    }   
                    tab++;
                    widthcol = Grid.getColumnModel().getColumn(j).getWidth();
                    p.add(new Chunk(Grid.getColumnName(j), new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
                }
            }
            document.add(p);  

            //Inserir Conteúdo
            for(int k=0; k<Grid.getRowCount(); k++) {
                p = new Paragraph();
                //colunas
                tab = 0;
                for(int j=0; j<Grid.getColumnCount(); j++) {
                    if (Grid.getColumnModel().getColumn(j).getPreferredWidth() > 0) {
                        if (tab > 0) {
                            float tamanho = (float) widthcol;
                            p.setTabSettings(new TabSettings(tamanho));
                            p.add(Chunk.createTabspace(tamanho));
                        }
                        tab++;
                        widthcol = Grid.getColumnModel().getColumn(j).getWidth();
                        p.add(new Chunk(String.valueOf(Grid.getValueAt(k, j)))); 
                    }
                }
                document.add(p); 
            }                

        } catch (DocumentException ex) {
            System.out.println("Error:"+ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error:"+ex);
        }finally{
            document.close();
            this.dispose();
        }

        //Abrir o arquivo PDF criado
        try {
            Desktop.getDesktop().open(new File("Relatorio.pdf"));
        } catch (IOException ex) {
            System.out.println("Error:"+ex);
        }             
    }
    
    public PaShowData() {
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
        
        // ARQUIVOS DA CONVERSÃO DE PDF
        this.document = new Document();   
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
        sepLogo = new javax.swing.JSeparator();
        txtTituloEmpresa = new javax.swing.JLabel();
        btnConcluir = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
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

        sepLogo.setBackground(new java.awt.Color(255, 255, 255));
        sepLogo.setForeground(new java.awt.Color(255, 255, 255));
        pnlLateral.add(sepLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, 228, 250, 10));

        txtTituloEmpresa.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        txtTituloEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        txtTituloEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloEmpresa.setText("PSC LAVA-JATO");
        pnlLateral.add(txtTituloEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 186, 217, -1));

        btnConcluir.setBackground(new java.awt.Color(255, 255, 255));
        btnConcluir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnConcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconVerificado.png"))); // NOI18N
        btnConcluir.setText("CONCLUIR CONSULTA");
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
        pnlLateral.add(btnConcluir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 240, -1));

        btnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        btnImprimir.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconExportar.png"))); // NOI18N
        btnImprimir.setText(" IMPRIMIR                   ");
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnImprimirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnImprimirMouseExited(evt);
            }
        });
        pnlLateral.add(btnImprimir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 240, 50));

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
                .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
        
        // CONFIGURA O MOME DO TÍTULO DO RELATÓRIO
        this.Titulo = titulo.getText();
    }//GEN-LAST:event_formWindowOpened

    private void btnConcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseClicked
        this.dispose(); 
    }//GEN-LAST:event_btnConcluirMouseClicked

    private void btnConcluirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseEntered
        btnConcluir.setBackground(new java.awt.Color(152 ,251, 152));
    }//GEN-LAST:event_btnConcluirMouseEntered

    private void btnConcluirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConcluirMouseExited
        btnConcluir.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btnConcluirMouseExited

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
        this.msg.successfullyRegistered("O relatório foi exportado");
        
        this.document.setPageSize(PageSize.A4.rotate());
        this.getRelatorio(this.grdDados);
    }//GEN-LAST:event_btnImprimirMouseClicked

    private void btnImprimirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseEntered
        btnImprimir.setBackground(new java.awt.Color(154, 192, 205));
    }//GEN-LAST:event_btnImprimirMouseEntered

    private void btnImprimirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseExited
        btnImprimir.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btnImprimirMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConcluir;
    private javax.swing.JButton btnImprimir;
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
