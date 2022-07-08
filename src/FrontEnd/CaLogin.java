/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FrontEnd;

import BackEnd.ConexaoSQL;
import Padroes.Mensagens_Prontas;
import Padroes.sql_padroes;
import java.sql.SQLException;

/**
 *
 * @author samuel
 */
public class CaLogin extends javax.swing.JDialog {

    private ConexaoSQL          db;
    private sql_padroes         sqlPadroes;
    private Mensagens_Prontas   msg;
    private int                 qntdTentativasLogin;
    public  String              nomeUsuario; 
    
    public CaLogin() {
        initComponents();
        this.setModal(true);
        
        this.db         = new ConexaoSQL();
        this.sqlPadroes = new sql_padroes();
        this.msg        = new Mensagens_Prontas();
        
        this.qntdTentativasLogin = 3;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAbaLogin = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        txtNome = new javax.swing.JLabel();
        txtDesc = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlLogin = new javax.swing.JPanel();
        IconSenha = new javax.swing.JLabel();
        pfSenha = new javax.swing.JPasswordField();
        fundo2 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JLabel();
        tfLogin = new javax.swing.JTextField();
        IconLogin = new javax.swing.JLabel();
        fundo1 = new javax.swing.JLabel();
        txtNomeLogin = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PSC LAVA JATO");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnlAbaLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlAbaLogin.setForeground(new java.awt.Color(255, 255, 255));

        pnlLogo.setBackground(new java.awt.Color(17, 32, 63));

        txtNome.setFont(new java.awt.Font("Agency FB", 1, 58)); // NOI18N
        txtNome.setForeground(new java.awt.Color(255, 255, 255));
        txtNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtNome.setText("PSC ");

        txtDesc.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        txtDesc.setForeground(new java.awt.Color(255, 255, 255));
        txtDesc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDesc.setText("LAVA-JATO");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/LOGO.png"))); // NOI18N

        javax.swing.GroupLayout pnlLogoLayout = new javax.swing.GroupLayout(pnlLogo);
        pnlLogo.setLayout(pnlLogoLayout);
        pnlLogoLayout.setHorizontalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLogoLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlLogoLayout.setVerticalGroup(
            pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLogoLayout.createSequentialGroup()
                .addGroup(pnlLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(pnlLogoLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(txtNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDesc)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        IconSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/ImgSenha.png"))); // NOI18N
        pnlLogin.add(IconSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, -1, 40));

        pfSenha.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        pfSenha.setBorder(null);
        pnlLogin.add(pfSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, 380, 40));

        fundo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo2.setToolTipText("");
        fundo2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo2MouseClicked(evt);
            }
        });
        pnlLogin.add(fundo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, -1));

        txtSenha.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtSenha.setForeground(new java.awt.Color(156, 156, 156));
        txtSenha.setText("SENHA:");
        pnlLogin.add(txtSenha, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        tfLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tfLogin.setForeground(new java.awt.Color(51, 51, 51));
        tfLogin.setBorder(null);
        pnlLogin.add(tfLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 380, 40));

        IconLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/ImgIdCard.png"))); // NOI18N
        pnlLogin.add(IconLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, 40));

        fundo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fundoTextField.png"))); // NOI18N
        fundo1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fundo1MouseClicked(evt);
            }
        });
        pnlLogin.add(fundo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, -1, -1));

        txtNomeLogin.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        txtNomeLogin.setForeground(new java.awt.Color(156, 156, 156));
        txtNomeLogin.setText("NOME DE USUÁRIO:");
        pnlLogin.add(txtNomeLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        btnLogin.setBackground(new java.awt.Color(72, 118, 255));
        btnLogin.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("ENTRAR");
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });

        javax.swing.GroupLayout pnlAbaLoginLayout = new javax.swing.GroupLayout(pnlAbaLogin);
        pnlAbaLogin.setLayout(pnlAbaLoginLayout);
        pnlAbaLoginLayout.setHorizontalGroup(
            pnlAbaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlAbaLoginLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(pnlAbaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        pnlAbaLoginLayout.setVerticalGroup(
            pnlAbaLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAbaLoginLayout.createSequentialGroup()
                .addComponent(pnlLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlAbaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlAbaLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        btnLogin.setBackground(new java.awt.Color(72, 118 ,255));
    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        btnLogin.setBackground(new java.awt.Color(54,100,139));
    }//GEN-LAST:event_btnLoginMouseExited

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        if(sqlPadroes.loginIsCorrect("funcionario", tfLogin.getText(), pfSenha)  ) {
            // PEGA O NOME DO USUÁRIO
            this.nomeUsuario = tfLogin.getText();
            
            // REGISTRA O HORARIO DE USUÁRIO
            this.db.SQLExecute("INSERT INTO registrologin(CodFuncionario, DataHora) VALUES ("+this.sqlPadroes.getValue("funcionario", "CodFuncionario", this.nomeUsuario)+", NOW())");
            
            this.dispose();
        }
        else {
            msg.texto("Usuário ou senha estão incorretos");
            this.qntdTentativasLogin--;
        }
         
        // CASO O USUÁRIO EXCEDA AS TENTATIVAS DE LOGIN
        if(this.qntdTentativasLogin == 0){
            msg.texto("A aplicação será finalizada...");
            System.exit(0);
        }
        
    }//GEN-LAST:event_btnLoginMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void fundo2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo2MouseClicked
        pfSenha.requestFocus();
    }//GEN-LAST:event_fundo2MouseClicked

    private void fundo1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fundo1MouseClicked
        tfLogin.requestFocus();
    }//GEN-LAST:event_fundo1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IconLogin;
    private javax.swing.JLabel IconSenha;
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel fundo1;
    private javax.swing.JLabel fundo2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPasswordField pfSenha;
    private javax.swing.JPanel pnlAbaLogin;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JTextField tfLogin;
    private javax.swing.JLabel txtDesc;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtNomeLogin;
    private javax.swing.JLabel txtSenha;
    // End of variables declaration//GEN-END:variables
}