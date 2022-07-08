/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Padroes;


import BackEnd.ConexaoSQL;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author samuel
 */
public class sql_padroes {
    
    Mensagens_Prontas msg = new Mensagens_Prontas();
    
    private ConexaoSQL  conexao = new ConexaoSQL();
    
    public boolean isExists ( String table, String column, String name){
    String val = "false";
        try {
            /* <<< LINHA DE COMANDO SQL >>> */
            String SQL = "SELECT 'true' AS result FROM "+table+" WHERE ("+column+" = '"+name+"' )";
            
            /* <<< EXECUTA LINHA DE COMANDO SQL >>> */
            this.conexao.setResultSet(SQL);
            
            /* <<< PEGA OS VALORES DA TELA, DO PRIMEIRO PARA O ULTIMO >>> */
            if (this.conexao.getResultSet().first() ){
                val = this.conexao.getResultSet().getString("result");
            } 
        }
        catch(SQLException e)  {
            JOptionPane.showMessageDialog(null, e);
        }
        
        
        if(val.equals("true")) {
            return true;
        }
        else return false;
        
    }
    
    public String getCodigoPorCB (JComboBox cb, String table){
        
        // PEGA O NOME DA TABELA E TRANSFORMA ELA NO NOME DO CODIGO. FUNCIONA APENAS PRO PADRAO "CodNome"
        String str = table;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());
        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        String codigoDaTabela = "Cod"+str;

        
        String codigo = (String) cb.getSelectedItem();
        try {
           this.conexao.setResultSet("SELECT "+codigoDaTabela+" AS cod FROM "+table+" WHERE nome LIKE '"+codigo+"'");
           if(this.conexao.getResultSet().first() )
                return this.conexao.getResultSet().getString("cod");
            else return "erro";
        }
        catch (SQLException e){
            return "erro";
        }
    }  
    
    public int getCodigoByList (JList list, String table){       
        // PEGA O NOME DA TABELA E TRANSFORMA ELA NO NOME DO CODIGO. FUNCIONA APENAS PRO PADRAO "CodNome"
        String str = table;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());
        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        String codigoDaTabela = "Cod"+str;

        try {
           this.conexao.setResultSet("SELECT "+codigoDaTabela+" AS cod FROM "+table+" WHERE nome LIKE '"+String.valueOf(list.getSelectedValue())+"'");
           if(this.conexao.getResultSet().first() )
                return Integer.parseInt(this.conexao.getResultSet().getString("cod"));
            else return 0;
        }
        catch (SQLException e){
            return 0;
        }
    }
  
    public void setPreencherComboBox (JComboBox cb, String table){
        try {
            String SQL = "SELECT DISTINCT Nome FROM "+table+" order by Nome";
            this.conexao.setResultSet(SQL);
            cb.removeAllItems();
            if(this.conexao.getResultSet().first() ) {
                do {
                    cb.addItem(this.conexao.getResultSet().getString("Nome"));
                }while(this.conexao.getResultSet().next());
            }
           cb.setSelectedIndex(-1);
        }
        catch (SQLException e){
            msg.problemMessage("erro", "");
        }
    }
    

    // VALIDA SE O LOGIN ESTÁ CORRETO
    public boolean loginIsCorrect (String table, String usuario, JPasswordField campoSenha){  
        // DESCRIPTOGRAFA A SENHA RECEBIDA E TRANFORMA EM STRING
        String senha  = new String (campoSenha.getPassword()) ;
        String count = "0";
        
        try {
                this.conexao.setResultSet("SELECT count(*) AS total, Nome FROM "+table+" WHERE Usuario LIKE '"+usuario+"' AND Senha LIKE BINARY '"+senha+"' ");
                if(this.conexao.getResultSet().first() ) {
                    do {
                        count = this.conexao.getResultSet().getString("total");
                    }while(this.conexao.getResultSet().next());
                }

            }
            catch (SQLException e){
                msg.problemMessage("erro", "");
            }
        
        return count.equals("1");
    }
    
       
    public String getValue (String table, String column, String user){
        String result = "nulo";
            try {
                this.conexao.setResultSet("SELECT "+column+" AS yourResult FROM "+table+" WHERE Usuario LIKE '"+user+"'");
                if(this.conexao.getResultSet().first() ) {
                        result = this.conexao.getResultSet().getString("yourResult");
                }

            }
            catch (SQLException e){
                msg.problemMessage("erro", "");
            }      
        return result;
    }
    
    public String getCodByName (String table, String column, String name){
        String result = "nulo";
            try {
                this.conexao.setResultSet("SELECT "+column+" AS yourResult FROM "+table+" WHERE Nome LIKE '"+name+"'");
                if(this.conexao.getResultSet().first() ) {
                        result = this.conexao.getResultSet().getString("yourResult");
                }

            }
            catch (SQLException e){
                msg.problemMessage("erro", "");
            }      
        return result;
    }
    
    public String getValueByCod (String table, String column, int cod){
        // PEGA O NOME DA TABELA E TRANSFORMA ELA NO NOME DO CODIGO. FUNCIONA APENAS PRO PADRAO "CodNome"
        String str = table;
        String firstLtr = str.substring(0, 1);
        String restLtrs = str.substring(1, str.length());
        firstLtr = firstLtr.toUpperCase();
        str = firstLtr + restLtrs;
        String codigoDaTabela = "Cod"+str;
        
        String result = "nulo";
            try {
                this.conexao.setResultSet("SELECT "+column+" AS yourResult FROM "+table+" WHERE "+codigoDaTabela+" = "+cod);
                    if(this.conexao.getResultSet().first() ) {
                        result = this.conexao.getResultSet().getString("yourResult");
                }
            }
            catch (SQLException e){
                msg.problemMessage("erro", "");
            }      
        return result;
    }
    
        public String getValueByPlaca (String table, String column, String cod){
 
        String result = "nulo";
            try {
                this.conexao.setResultSet("SELECT "+column+" AS yourResult FROM "+table+" WHERE Placa LIKE '"+cod+"'");
                    if(this.conexao.getResultSet().first() ) {
                        result = this.conexao.getResultSet().getString("yourResult");
                }
            }
            catch (SQLException e){
                msg.problemMessage("erro", "");
            }      
        return result;
    }
    
    public boolean existsValue (String table, String campo, String valor){
        int result = 0;
            try {
                    this.conexao.setResultSet("SELECT COUNT(*) AS yourResult FROM "+table+" WHERE "+campo+" LIKE '"+valor+"'");
                    if(this.conexao.getResultSet().first() ) {
                            result = Integer.parseInt(this.conexao.getResultSet().getString("yourResult"));}
                }
                catch (SQLException e){
                    msg.problemMessage("erro", "");
                }    
            
        if(result == 0)
            return false;
        else
            return true;
    }

    public void setPreencherListaOpcoes (JList listaOpcoes, JTextField tf, String table, String coluna){
        // DEFINE O MODELO DA LISTA DE OPÇÕES DO TEXTFIELD
        DefaultListModel dlm = new DefaultListModel();
        listaOpcoes.setModel(dlm);
        
        try {
            String SQL = "SELECT DISTINCT "+coluna+" AS Resultado FROM "+table+" where "+coluna+" like '"+tf.getText()+"%' LIMIT 4";
            this.conexao.setResultSet(SQL);
            dlm.removeAllElements();
            
            if(this.conexao.getResultSet().first() ){
                do {
                    dlm.addElement(this.conexao.getResultSet().getString("Resultado"));     
                }while(this.conexao.getResultSet().next() );

            }
        }
        catch (SQLException e){
            msg.problemMessage("erro", "");
        }
    }
}
