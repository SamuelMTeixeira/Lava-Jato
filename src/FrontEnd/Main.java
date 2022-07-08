/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.ConexaoSQL;
import Padroes.FormatacaoDeCampos;
import Padroes.Mensagens_Prontas;
import Padroes.sql_padroes;
import java.awt.Desktop;
import java.net.URI;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author samuel
 */
public class Main extends javax.swing.JFrame {
        
    private ConexaoSQL          conexao;
    private DefaultTableModel   dtm;
    private sql_padroes         sqlPadroes;
    private Mensagens_Prontas   msg;
    private FormatacaoDeCampos  formatar;
    private int                 previlegios;
    private String              nomeUsuario;
    private boolean             isOpened;
    private String              opcFiltroAtivo;
    
    // OCULTA/MOSTRA ALGUNS ITENS DE MENU DE ACORDO COM O PREVILEGIO DO USUÁRIO
    private void ocultarRecursosPorPrevilegio (int tipoPrevilegio){
        if(tipoPrevilegio == 1){
            mnRelatorios.setVisible(true);
            
            // VISIBILIDADE DOS CADASTROS
            mnFuncionario.setVisible(true);
            mnTipoLimpeza.setVisible(true);}
        
        else if(tipoPrevilegio == 0) {
            mnRelatorios.setVisible(false);
            
            // VISIBILIDADE DOS CADASTROS
            mnFuncionario.setVisible(false);
            mnTipoLimpeza.setVisible(false);}         
    }
    
    //CONFIGURA O NOME DO FRAME
    private void TituloAplicacao (String nomeUsuario){
        this.setTitle("PSC LAVA JATO - Usuário logado: "+nomeUsuario);
    }

    // PREENCHE A TABELA DE CHECKIN/CHECKOUT
    private void setGridChecks (String filtro){
        /* 
        FILTROS:
          - statusLimpeza
          - HorarioEntrada
        */
        this.dtm = (DefaultTableModel) grdlListaVeiculos.getModel();
 
        // LIMPA OS DADOS ANTIGOS DA TABELA 
        while(this.dtm.getRowCount() > 0 )
            this.dtm.removeRow(0);
        
        // PREENCHE A TABELA
        try{
            
            // LINHA DE COMANDO SQL 
            String SQL = "SELECT cl.Placa AS placa, m.Nome AS modelo, c.Nome AS cor, DATE_FORMAT(cl.DataHoraEntrada, '%d-%m-%y às %h:%i:%s') AS HorarioEntrada, "+
                "tp.Nome AS tipoLimpeza, IF(cl.DataHoraFinalizado is null, 'ABERTO',IF(cl.DataHoraSaida is null, 'CONCLUÍDO', 'FINALIZADO')) AS statusLimpeza "+
                "FROM controlelimpezas cl INNER JOIN veiculo v ON v.Placa = cl.Placa INNER JOIN modelo m ON m.CodModelo = v.CodModelo "+
                "INNER JOIN cor c ON c.CodCor = v.CodCor INNER JOIN tipolimpeza tp ON tp.CodTipoLimpeza = cl.CodTipoLimpeza "+
                "WHERE cl.DataHoraSaida IS NULL ORDER BY "+filtro;
            
            // EXECUTA LINHA DE COMANDO SQL
            this.conexao.setResultSet(SQL);
            
            // PEGA OS VALORES DA TELA, DO PRIMEIRO PARA O ULTIMO
            if (this.conexao.getResultSet().first() ){
                do {
                    this.dtm.addRow  (
                            new Object[] {
                                this.conexao.getResultSet().getString("placa"),
                                this.conexao.getResultSet().getString("modelo"),
                                this.conexao.getResultSet().getString("cor"),
                                this.conexao.getResultSet().getString("HorarioEntrada"),
                                this.conexao.getResultSet().getString("tipoLimpeza"),
                                this.conexao.getResultSet().getString("statusLimpeza")
                                    
                            }
                    );
              
                } while (this.conexao.getResultSet().next());
            } 
            
        }
        catch (SQLException e){
            this.msg.texto("Erro ao preencher grid");
        }
    }

    // PEGA DO REGISTRO DE DESPESAS
    private int getCodRegLimpeza (){
        int codRegistro = -1;
        
        // VALORES DAS ENTRADAS
        String placa = String.valueOf(this.grdlListaVeiculos.getValueAt(this.grdlListaVeiculos.getSelectedRow(), 0));
        String dataEntrada = String.valueOf(this.grdlListaVeiculos.getValueAt(this.grdlListaVeiculos.getSelectedRow(), 3));
        
        // PEGA O CODIGO DO REGISTRO DE LIMPEZA QUE SERÁ CONCLUÍDO
        try {
            this.conexao.setResultSet("SELECT cl.CodControleLimpezas AS cod FROM controlelimpezas cl " +
                "WHERE (cl.Placa LIKE '"+placa+"') AND " +
                "(DATE_FORMAT(cl.DataHoraEntrada, '%d-%m-%y às %h:%i:%s') = '"+dataEntrada+"')");
            
            if(this.conexao.getResultSet().first() ) {
                do {
                        codRegistro = Integer.parseInt(this.conexao.getResultSet().getString("cod"));
                }while(this.conexao.getResultSet().next());
            }
           
        }
        catch (SQLException e){
            msg.problemMessage("erro", "");
        }  
        
        
        return codRegistro;
    }
    
    // FAZ O CHECK-OUT
    private void setCheckOut(){
        
        // REALIZA O CHECK-OUT SE EXISTIR UM CODIGO
        if(this.getCodRegLimpeza() > -1)
            this.conexao.SQLExecute("UPDATE controlelimpezas SET DataHoraFinalizado = NOW() WHERE CodControleLimpezas = "+this.getCodRegLimpeza()); 
        
        this.setGridChecks(opcFiltroAtivo);
    }
    
    public Main() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        
        // INSTANCIA ALGUMAS CLASSES QUE SERÃO UTILIZADAS
        this.dtm        = new DefaultTableModel();
        this.msg        = new Mensagens_Prontas();
        this.formatar   = new FormatacaoDeCampos();
        this.conexao    = new ConexaoSQL();
        this.sqlPadroes = new sql_padroes();
        
        // INSTANCIA A PÁGINA DE LOGIN
        CaLogin login = new CaLogin();
        login.setVisible(true);

        // NOMEIA O TÍTULO DA PÁGINA
        this.nomeUsuario = login.nomeUsuario;
        this.TituloAplicacao(this.nomeUsuario);
        
        // PEGA O TIPO DE PREVILÉGIO DA PESSOA (USUÁRIO X OPERADOR)
        this.previlegios = Integer.parseInt(sqlPadroes.getValue("funcionario", "IsAdministrador", this.nomeUsuario));
        
        // OCULTA OS VALORES REFERETES AO TIPO DE PREVILÉGIO
        this.ocultarRecursosPorPrevilegio(this.previlegios);
        
        // PERSONALIZA AS CORES DA TABELA PRINCIPAL
        scrollListaVeiculos.getViewport().setBackground(new java.awt.Color(79,148,205));
        grdlListaVeiculos.setSelectionBackground(new java.awt.Color(54,100,139));
        
        // PERSONALIZA O BOTÃO MENU
        btnMenu.setOpaque(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setBorderPainted(false);
        
        // DIMENCIONA A BORDA DO MENU LATERAL
        this.formatar.setDimencionarIcone(60, this.getHeight() ,"/Icons/bordaMenu.png", imgBorda); 
        
        // BOOLEAN QUE MARCA SE A BORDA DO MENU ESTÁ ABERTA OU NÃO
        this.isOpened = true;
        
        // DEFINE O DAFAULT DO FILTRO
        this.opcFiltroAtivo = "statusLimpeza";

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
        grdlListaVeiculos = new javax.swing.JTable();
        cbFiltro = new javax.swing.JComboBox<>();
        txtFiltro = new javax.swing.JLabel();
        titulo = new javax.swing.JLabel();
        iconTitulo = new javax.swing.JLabel();
        sepTitulo = new javax.swing.JSeparator();
        pnLateralMenu = new javax.swing.JPanel();
        btnMenu = new javax.swing.JButton();
        imgBorda = new javax.swing.JLabel();
        pnlLateral = new javax.swing.JPanel();
        txtLogo = new javax.swing.JLabel();
        btnCheckIn = new javax.swing.JButton();
        btnCheckOut = new javax.swing.JButton();
        sepLogo = new javax.swing.JSeparator();
        txtTituloEmpresa = new javax.swing.JLabel();
        mbPrincipal = new javax.swing.JMenuBar();
        mnCadastros = new javax.swing.JMenu();
        mnFuncionario = new javax.swing.JMenu();
        miCaFuncionario = new javax.swing.JMenuItem();
        miEditFuncionario = new javax.swing.JMenuItem();
        miExcuirFuncionario = new javax.swing.JMenuItem();
        miDadosFuncionario = new javax.swing.JMenuItem();
        mnTipoLimpeza = new javax.swing.JMenu();
        miCaTipoLImpeza = new javax.swing.JMenuItem();
        miEditTipoLImpeza = new javax.swing.JMenuItem();
        miExcuirTipoLImpeza = new javax.swing.JMenuItem();
        miDadosTipoLimpeza = new javax.swing.JMenuItem();
        mnModalidade = new javax.swing.JMenu();
        miCaModalidade = new javax.swing.JMenuItem();
        miEditModalidade = new javax.swing.JMenuItem();
        miExcuirModalidade = new javax.swing.JMenuItem();
        miDadosModalidade = new javax.swing.JMenuItem();
        mnModelo = new javax.swing.JMenu();
        miCaModelo = new javax.swing.JMenuItem();
        miEditModelo = new javax.swing.JMenuItem();
        miExcuirModelo = new javax.swing.JMenuItem();
        miDadosModelo = new javax.swing.JMenuItem();
        mnVeiculo = new javax.swing.JMenu();
        miCaVeiculo = new javax.swing.JMenuItem();
        miEditVeiculo = new javax.swing.JMenuItem();
        miExcuirVeiculo = new javax.swing.JMenuItem();
        miDadosVeiculo = new javax.swing.JMenuItem();
        mnProprietario = new javax.swing.JMenu();
        miCaProprietario = new javax.swing.JMenuItem();
        miEditProprietario = new javax.swing.JMenuItem();
        miExcuirProprietario = new javax.swing.JMenuItem();
        miDadosProprietario = new javax.swing.JMenuItem();
        mnCor = new javax.swing.JMenu();
        miCaCor = new javax.swing.JMenuItem();
        miEditCor = new javax.swing.JMenuItem();
        miExcuirCor = new javax.swing.JMenuItem();
        miDadosCor = new javax.swing.JMenuItem();
        mnRelatorios = new javax.swing.JMenu();
        miRelLimpezasEfetuadas = new javax.swing.JMenuItem();
        miFaturamentoGeral = new javax.swing.JMenuItem();
        miFaturamentoTipoLimpeza = new javax.swing.JMenuItem();
        miFaturamentoModalidadeVeiculo = new javax.swing.JMenuItem();
        miPorcentTipoLimpeza = new javax.swing.JMenuItem();
        miRelLogin = new javax.swing.JMenuItem();
        mnConfig = new javax.swing.JMenu();
        miAlterarSenha = new javax.swing.JMenuItem();
        sepCOnfiguracoes = new javax.swing.JPopupMenu.Separator();
        miSair = new javax.swing.JMenuItem();
        mnAjuda = new javax.swing.JMenu();
        miFlaticon = new javax.swing.JMenuItem();
        sep1 = new javax.swing.JPopupMenu.Separator();
        miSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        pnlPrincipal.setBackground(new java.awt.Color(79, 148, 205));

        grdlListaVeiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Placa", "Modelo do veículo", "Cor", "Horário de Entrada", "Tipo de limpeza", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        grdlListaVeiculos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scrollListaVeiculos.setViewportView(grdlListaVeiculos);

        cbFiltro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Status", "Horario de entrada" }));
        cbFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbFiltroItemStateChanged(evt);
            }
        });

        txtFiltro.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtFiltro.setForeground(new java.awt.Color(255, 255, 255));
        txtFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconLupa.png"))); // NOI18N

        titulo.setFont(new java.awt.Font("MV Boli", 1, 30)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("MENU PRINCIPAL");

        iconTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/home.png"))); // NOI18N

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

        btnCheckIn.setBackground(new java.awt.Color(255, 255, 255));
        btnCheckIn.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCheckIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconCheckIn.png"))); // NOI18N
        btnCheckIn.setText("CHECK-IN   ");
        btnCheckIn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCheckInMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCheckInMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCheckInMouseExited(evt);
            }
        });
        pnlLateral.add(btnCheckIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 256, 240, -1));

        btnCheckOut.setBackground(new java.awt.Color(255, 255, 255));
        btnCheckOut.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnCheckOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconCheckOut.png"))); // NOI18N
        btnCheckOut.setText("CHECK-OUT");
        btnCheckOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCheckOutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCheckOutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCheckOutMouseExited(evt);
            }
        });
        pnlLateral.add(btnCheckOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 322, 240, -1));

        sepLogo.setBackground(new java.awt.Color(255, 255, 255));
        sepLogo.setForeground(new java.awt.Color(255, 255, 255));
        pnlLateral.add(sepLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 228, 240, 10));

        txtTituloEmpresa.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        txtTituloEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        txtTituloEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTituloEmpresa.setText("PSC LAVA-JATO");
        pnlLateral.add(txtTituloEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 186, 217, -1));

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
                            .addComponent(iconTitulo)
                            .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addComponent(sepTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addGroup(pnlPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(cbFiltro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollListaVeiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnLateralMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mnCadastros.setText("Cadastros");

        mnFuncionario.setText("Funcionário");

        miCaFuncionario.setText("Adicionar");
        miCaFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaFuncionarioActionPerformed(evt);
            }
        });
        mnFuncionario.add(miCaFuncionario);

        miEditFuncionario.setText("Editar");
        miEditFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditFuncionarioActionPerformed(evt);
            }
        });
        mnFuncionario.add(miEditFuncionario);

        miExcuirFuncionario.setText("Excluir");
        miExcuirFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirFuncionarioActionPerformed(evt);
            }
        });
        mnFuncionario.add(miExcuirFuncionario);

        miDadosFuncionario.setText("Ver cadastros");
        miDadosFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosFuncionarioActionPerformed(evt);
            }
        });
        mnFuncionario.add(miDadosFuncionario);

        mnCadastros.add(mnFuncionario);

        mnTipoLimpeza.setText("Tipo de limpeza");

        miCaTipoLImpeza.setText("Adicionar");
        miCaTipoLImpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaTipoLImpezaActionPerformed(evt);
            }
        });
        mnTipoLimpeza.add(miCaTipoLImpeza);

        miEditTipoLImpeza.setText("Editar");
        miEditTipoLImpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditTipoLImpezaActionPerformed(evt);
            }
        });
        mnTipoLimpeza.add(miEditTipoLImpeza);

        miExcuirTipoLImpeza.setText("Excluir");
        miExcuirTipoLImpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirTipoLImpezaActionPerformed(evt);
            }
        });
        mnTipoLimpeza.add(miExcuirTipoLImpeza);

        miDadosTipoLimpeza.setText("Ver cadastros");
        miDadosTipoLimpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosTipoLimpezaActionPerformed(evt);
            }
        });
        mnTipoLimpeza.add(miDadosTipoLimpeza);

        mnCadastros.add(mnTipoLimpeza);

        mnModalidade.setText("Tipo de modalidade");

        miCaModalidade.setText("Adicionar");
        miCaModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaModalidadeActionPerformed(evt);
            }
        });
        mnModalidade.add(miCaModalidade);

        miEditModalidade.setText("Editar");
        miEditModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditModalidadeActionPerformed(evt);
            }
        });
        mnModalidade.add(miEditModalidade);

        miExcuirModalidade.setText("Excluir");
        miExcuirModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirModalidadeActionPerformed(evt);
            }
        });
        mnModalidade.add(miExcuirModalidade);

        miDadosModalidade.setText("Ver cadastros");
        miDadosModalidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosModalidadeActionPerformed(evt);
            }
        });
        mnModalidade.add(miDadosModalidade);

        mnCadastros.add(mnModalidade);

        mnModelo.setText("Modelo de veículo");

        miCaModelo.setText("Adicionar");
        miCaModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaModeloActionPerformed(evt);
            }
        });
        mnModelo.add(miCaModelo);

        miEditModelo.setText("Editar");
        miEditModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditModeloActionPerformed(evt);
            }
        });
        mnModelo.add(miEditModelo);

        miExcuirModelo.setText("Excluir");
        miExcuirModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirModeloActionPerformed(evt);
            }
        });
        mnModelo.add(miExcuirModelo);

        miDadosModelo.setText("Ver cadastros");
        miDadosModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosModeloActionPerformed(evt);
            }
        });
        mnModelo.add(miDadosModelo);

        mnCadastros.add(mnModelo);

        mnVeiculo.setText("Veículo");

        miCaVeiculo.setText("Adicionar");
        miCaVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaVeiculoActionPerformed(evt);
            }
        });
        mnVeiculo.add(miCaVeiculo);

        miEditVeiculo.setText("Editar");
        miEditVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditVeiculoActionPerformed(evt);
            }
        });
        mnVeiculo.add(miEditVeiculo);

        miExcuirVeiculo.setText("Excluir");
        miExcuirVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirVeiculoActionPerformed(evt);
            }
        });
        mnVeiculo.add(miExcuirVeiculo);

        miDadosVeiculo.setText("Ver cadastros");
        miDadosVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosVeiculoActionPerformed(evt);
            }
        });
        mnVeiculo.add(miDadosVeiculo);

        mnCadastros.add(mnVeiculo);

        mnProprietario.setText("Proprietário");

        miCaProprietario.setText("Adicionar");
        miCaProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaProprietarioActionPerformed(evt);
            }
        });
        mnProprietario.add(miCaProprietario);

        miEditProprietario.setText("Editar");
        miEditProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditProprietarioActionPerformed(evt);
            }
        });
        mnProprietario.add(miEditProprietario);

        miExcuirProprietario.setText("Excluir");
        miExcuirProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirProprietarioActionPerformed(evt);
            }
        });
        mnProprietario.add(miExcuirProprietario);

        miDadosProprietario.setText("Ver cadastros");
        miDadosProprietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosProprietarioActionPerformed(evt);
            }
        });
        mnProprietario.add(miDadosProprietario);

        mnCadastros.add(mnProprietario);

        mnCor.setText("Cor do veículo");

        miCaCor.setText("Adicionar");
        miCaCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCaCorActionPerformed(evt);
            }
        });
        mnCor.add(miCaCor);

        miEditCor.setText("Editar");
        miEditCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEditCorActionPerformed(evt);
            }
        });
        mnCor.add(miEditCor);

        miExcuirCor.setText("Excluir");
        miExcuirCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExcuirCorActionPerformed(evt);
            }
        });
        mnCor.add(miExcuirCor);

        miDadosCor.setText("Ver cadastros");
        miDadosCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miDadosCorActionPerformed(evt);
            }
        });
        mnCor.add(miDadosCor);

        mnCadastros.add(mnCor);

        mbPrincipal.add(mnCadastros);

        mnRelatorios.setText("Relatórios");

        miRelLimpezasEfetuadas.setText("Relatório de limpezas efetuadas");
        miRelLimpezasEfetuadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRelLimpezasEfetuadasActionPerformed(evt);
            }
        });
        mnRelatorios.add(miRelLimpezasEfetuadas);

        miFaturamentoGeral.setText("Faturamento geral anual");
        miFaturamentoGeral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFaturamentoGeralActionPerformed(evt);
            }
        });
        mnRelatorios.add(miFaturamentoGeral);

        miFaturamentoTipoLimpeza.setText("Faturamento por tipo de limpeza");
        miFaturamentoTipoLimpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFaturamentoTipoLimpezaActionPerformed(evt);
            }
        });
        mnRelatorios.add(miFaturamentoTipoLimpeza);

        miFaturamentoModalidadeVeiculo.setText("Faturamento por modalidade de veículos");
        miFaturamentoModalidadeVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFaturamentoModalidadeVeiculoActionPerformed(evt);
            }
        });
        mnRelatorios.add(miFaturamentoModalidadeVeiculo);

        miPorcentTipoLimpeza.setText("Porcentagem por tipo de limpeza efetuada");
        miPorcentTipoLimpeza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPorcentTipoLimpezaActionPerformed(evt);
            }
        });
        mnRelatorios.add(miPorcentTipoLimpeza);

        miRelLogin.setText("Relatórios de logins");
        miRelLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRelLoginActionPerformed(evt);
            }
        });
        mnRelatorios.add(miRelLogin);

        mbPrincipal.add(mnRelatorios);

        mnConfig.setText("Configurações");

        miAlterarSenha.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miAlterarSenha.setText("Alterar senha");
        miAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAlterarSenhaActionPerformed(evt);
            }
        });
        mnConfig.add(miAlterarSenha);
        mnConfig.add(sepCOnfiguracoes);

        miSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        miSair.setText("Sair");
        miSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSairActionPerformed(evt);
            }
        });
        mnConfig.add(miSair);

        mbPrincipal.add(mnConfig);

        mnAjuda.setText("Ajuda");

        miFlaticon.setText("Ícones da página");
        miFlaticon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFlaticonActionPerformed(evt);
            }
        });
        mnAjuda.add(miFlaticon);
        mnAjuda.add(sep1);

        miSobre.setText("Sobre");
        miSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSobreActionPerformed(evt);
            }
        });
        mnAjuda.add(miSobre);

        mbPrincipal.add(mnAjuda);

        setJMenuBar(mbPrincipal);

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

    private void miSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSairActionPerformed
        if(msg.confirmarAcao("fechar o programa"))
            System.exit(0);
    }//GEN-LAST:event_miSairActionPerformed

    private void miAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAlterarSenhaActionPerformed
        new AltSenha(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miAlterarSenhaActionPerformed

    private void btnMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseClicked
        // FUNÇÃO QUE FECHA E ABRE O MENU LATERAL...
        if(this.isOpened == true) {
            pnlLateral.setVisible(false);
            this.isOpened = false;}
        
        else {
            pnlLateral.setVisible(true);
            this.isOpened = true;}
    }//GEN-LAST:event_btnMenuMouseClicked

    private void btnCheckOutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckOutMouseExited
        btnCheckOut.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btnCheckOutMouseExited

    private void btnCheckOutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckOutMouseEntered
        btnCheckOut.setBackground(new java.awt.Color(238, 221, 130));
    }//GEN-LAST:event_btnCheckOutMouseEntered

    private void btnCheckInMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckInMouseExited
        btnCheckIn.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btnCheckInMouseExited

    private void btnCheckInMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckInMouseEntered
        btnCheckIn.setBackground(new java.awt.Color(152 ,251, 152));
    }//GEN-LAST:event_btnCheckInMouseEntered

    private void btnMenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseEntered
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconMenuSelect.png")));

    }//GEN-LAST:event_btnMenuMouseEntered

    private void btnMenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMenuMouseExited
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/iconMenu.png")));

    }//GEN-LAST:event_btnMenuMouseExited

    private void miCaFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaFuncionarioActionPerformed
        new CaFuncionario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaFuncionarioActionPerformed

    private void miDadosFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosFuncionarioActionPerformed
        new ShowFuncionarios(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosFuncionarioActionPerformed

    private void miDadosTipoLimpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosTipoLimpezaActionPerformed
        new ShowTipoLimpeza(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosTipoLimpezaActionPerformed

    private void miDadosModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosModalidadeActionPerformed
        new ShowTipoModalidade(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosModalidadeActionPerformed

    private void miDadosModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosModeloActionPerformed
        new ShowModelo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosModeloActionPerformed

    private void miDadosVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosVeiculoActionPerformed
        new ShowVeiculo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosVeiculoActionPerformed

    private void miDadosProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosProprietarioActionPerformed
        new ShowProprietario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosProprietarioActionPerformed

    private void miDadosCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miDadosCorActionPerformed
        new ShowCor(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miDadosCorActionPerformed

    private void miExcuirFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirFuncionarioActionPerformed
        new DropFuncionario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirFuncionarioActionPerformed

    private void miExcuirTipoLImpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirTipoLImpezaActionPerformed
        new DropTipoLimpeza(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirTipoLImpezaActionPerformed

    private void miExcuirModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirModalidadeActionPerformed
        new DropTipoModalidade(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirModalidadeActionPerformed

    private void miExcuirModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirModeloActionPerformed
        new DropModelo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirModeloActionPerformed

    private void miExcuirVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirVeiculoActionPerformed
        new DropVeiculo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirVeiculoActionPerformed

    private void miExcuirProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirProprietarioActionPerformed
        new DropProprietario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirProprietarioActionPerformed

    private void miExcuirCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExcuirCorActionPerformed
        new DropCor(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miExcuirCorActionPerformed

    private void miCaTipoLImpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaTipoLImpezaActionPerformed
        new CaTipoLimpeza(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaTipoLImpezaActionPerformed

    private void miCaModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaModalidadeActionPerformed
        new CaModalidade(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaModalidadeActionPerformed

    private void miCaModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaModeloActionPerformed
        new CaModelo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaModeloActionPerformed

    private void miCaVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaVeiculoActionPerformed
        new CaVeiculo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaVeiculoActionPerformed

    private void miCaProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaProprietarioActionPerformed
        new CaProprietario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaProprietarioActionPerformed

    private void miCaCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCaCorActionPerformed
        new CaCor(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miCaCorActionPerformed

    private void miEditFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditFuncionarioActionPerformed
        new EdFuncionario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditFuncionarioActionPerformed

    private void miEditTipoLImpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditTipoLImpezaActionPerformed
        new EdTipoLimpeza(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditTipoLImpezaActionPerformed

    private void miEditModalidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditModalidadeActionPerformed
        new EdModalidade(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditModalidadeActionPerformed

    private void miEditModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditModeloActionPerformed
        new EdModelo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditModeloActionPerformed

    private void miEditVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditVeiculoActionPerformed
        new EdVeiculo(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditVeiculoActionPerformed

    private void miEditCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditCorActionPerformed
        new EdCor(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditCorActionPerformed

    private void miEditProprietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEditProprietarioActionPerformed
        new EdProprietario(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miEditProprietarioActionPerformed

    private void miSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSobreActionPerformed
        new ShowSobre().setVisible(true);
    }//GEN-LAST:event_miSobreActionPerformed

    private void miFlaticonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFlaticonActionPerformed
        try{
            Desktop.getDesktop().browse(new URI("https://www.flaticon.com/br/"));
        }catch(Exception erro){
            this.msg.texto("erro");
        }
    }//GEN-LAST:event_miFlaticonActionPerformed

    private void btnCheckInMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckInMouseClicked
        new CaCheckIn(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_btnCheckInMouseClicked

    private void cbFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbFiltroItemStateChanged

        // FILTRA A TEBELA DE ACORDO COM A OPÇÃO DESEJADA
        if(this.cbFiltro.getSelectedIndex() == 0) {
            this.opcFiltroAtivo = "statusLimpeza";
            this.setGridChecks(this.opcFiltroAtivo);
        }
            
        else if(this.cbFiltro.getSelectedIndex() == 1) {
            this.opcFiltroAtivo = "HorarioEntrada";
            this.setGridChecks(this.opcFiltroAtivo);
        }     
        
    }//GEN-LAST:event_cbFiltroItemStateChanged

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.setGridChecks(this.opcFiltroAtivo);
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnCheckOutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCheckOutMouseClicked
        // SE UM VEÍCULO DA LISTA FOR SELECIONADO...
        if(this.grdlListaVeiculos.getSelectedRowCount()> 0) {
            
            // RECEBE O STATUS DO VEÍCULO
            switch(String.valueOf(this.grdlListaVeiculos.getValueAt(this.grdlListaVeiculos.getSelectedRow(), 5)).toUpperCase()){
                // SE A LAVAGEM ESTIVER EM ABERTO, É DEFINIDO QUE O VEÍCULO PODE SER BUSCADO
                case "ABERTO":
                    
                    // SE ELE DESEJA CONCLUIR A AÇÃO, É REGISTRADO A LIMPEZA DO VEÍCULO
                    if(this.msg.confirmarAcao("definir Limpeza do veículo, PLACA "+String.valueOf(this.grdlListaVeiculos.getValueAt(this.grdlListaVeiculos.getSelectedRow(), 0))+" como concluída")){
                        // REALIZA O CHECK-OUT
                        this.setCheckOut();
                        
                        this.msg.successfullyRegistered("Check-out efetuado");
                    }
                    break;
                case "CONCLUÍDO":
                    // PEGA O VALOR FINAL DA LAVAGEM. Juros da forma de pagamento * preço limpeza
                    String cod = this.sqlPadroes.getValueByCod("controlelimpezas", "CodTipoLimpeza", this.getCodRegLimpeza());
                            
                    float valorLimpeza = Float.parseFloat(this.sqlPadroes.getValueByCod("tipolimpeza", "Preco", Integer.parseInt(cod)));

                    new CaSaidaVeiculo(this.nomeUsuario, this.getCodRegLimpeza(), valorLimpeza).setVisible(true);
                    break;
                    
                default: 
                    break;
            }
        }
        else
            this.msg.problemMessage("check-out", "Nenhum veículo foi selecionado");
    }//GEN-LAST:event_btnCheckOutMouseClicked

    private void miRelLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRelLoginActionPerformed
        new ReLogin(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miRelLoginActionPerformed

    private void miRelLimpezasEfetuadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRelLimpezasEfetuadasActionPerformed
        new ReLimpezasEfetuadas(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miRelLimpezasEfetuadasActionPerformed

    private void miFaturamentoGeralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFaturamentoGeralActionPerformed
        new ReFaturamentoAnual(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miFaturamentoGeralActionPerformed

    private void miFaturamentoTipoLimpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFaturamentoTipoLimpezaActionPerformed
        new ReFaturamentoTipoLimpeza(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miFaturamentoTipoLimpezaActionPerformed

    private void miFaturamentoModalidadeVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFaturamentoModalidadeVeiculoActionPerformed
        new ReFaturamentoModalidade(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miFaturamentoModalidadeVeiculoActionPerformed

    private void miPorcentTipoLimpezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPorcentTipoLimpezaActionPerformed
        new RePorcentagemLimpezaFaturada(this.nomeUsuario).setVisible(true);
    }//GEN-LAST:event_miPorcentTipoLimpezaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheckIn;
    private javax.swing.JButton btnCheckOut;
    private javax.swing.JButton btnMenu;
    private javax.swing.JComboBox<String> cbFiltro;
    private javax.swing.JTable grdlListaVeiculos;
    private javax.swing.JLabel iconTitulo;
    private javax.swing.JLabel imgBorda;
    private javax.swing.JMenuBar mbPrincipal;
    private javax.swing.JMenuItem miAlterarSenha;
    private javax.swing.JMenuItem miCaCor;
    private javax.swing.JMenuItem miCaFuncionario;
    private javax.swing.JMenuItem miCaModalidade;
    private javax.swing.JMenuItem miCaModelo;
    private javax.swing.JMenuItem miCaProprietario;
    private javax.swing.JMenuItem miCaTipoLImpeza;
    private javax.swing.JMenuItem miCaVeiculo;
    private javax.swing.JMenuItem miDadosCor;
    private javax.swing.JMenuItem miDadosFuncionario;
    private javax.swing.JMenuItem miDadosModalidade;
    private javax.swing.JMenuItem miDadosModelo;
    private javax.swing.JMenuItem miDadosProprietario;
    private javax.swing.JMenuItem miDadosTipoLimpeza;
    private javax.swing.JMenuItem miDadosVeiculo;
    private javax.swing.JMenuItem miEditCor;
    private javax.swing.JMenuItem miEditFuncionario;
    private javax.swing.JMenuItem miEditModalidade;
    private javax.swing.JMenuItem miEditModelo;
    private javax.swing.JMenuItem miEditProprietario;
    private javax.swing.JMenuItem miEditTipoLImpeza;
    private javax.swing.JMenuItem miEditVeiculo;
    private javax.swing.JMenuItem miExcuirCor;
    private javax.swing.JMenuItem miExcuirFuncionario;
    private javax.swing.JMenuItem miExcuirModalidade;
    private javax.swing.JMenuItem miExcuirModelo;
    private javax.swing.JMenuItem miExcuirProprietario;
    private javax.swing.JMenuItem miExcuirTipoLImpeza;
    private javax.swing.JMenuItem miExcuirVeiculo;
    private javax.swing.JMenuItem miFaturamentoGeral;
    private javax.swing.JMenuItem miFaturamentoModalidadeVeiculo;
    private javax.swing.JMenuItem miFaturamentoTipoLimpeza;
    private javax.swing.JMenuItem miFlaticon;
    private javax.swing.JMenuItem miPorcentTipoLimpeza;
    private javax.swing.JMenuItem miRelLimpezasEfetuadas;
    private javax.swing.JMenuItem miRelLogin;
    private javax.swing.JMenuItem miSair;
    private javax.swing.JMenuItem miSobre;
    private javax.swing.JMenu mnAjuda;
    private javax.swing.JMenu mnCadastros;
    private javax.swing.JMenu mnConfig;
    private javax.swing.JMenu mnCor;
    private javax.swing.JMenu mnFuncionario;
    private javax.swing.JMenu mnModalidade;
    private javax.swing.JMenu mnModelo;
    private javax.swing.JMenu mnProprietario;
    private javax.swing.JMenu mnRelatorios;
    private javax.swing.JMenu mnTipoLimpeza;
    private javax.swing.JMenu mnVeiculo;
    private javax.swing.JPanel pnLateralMenu;
    private javax.swing.JPanel pnlLateral;
    private javax.swing.JPanel pnlPrincipal;
    private javax.swing.JScrollPane scrollListaVeiculos;
    private javax.swing.JPopupMenu.Separator sep1;
    private javax.swing.JPopupMenu.Separator sepCOnfiguracoes;
    private javax.swing.JSeparator sepLogo;
    private javax.swing.JSeparator sepTitulo;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txtFiltro;
    private javax.swing.JLabel txtLogo;
    private javax.swing.JLabel txtTituloEmpresa;
    // End of variables declaration//GEN-END:variables
}
