package BackEnd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author samuel
 */

public class ConexaoSQL {
    private Statement stmt; //executar consultas DML
    private ResultSet rs; //gerenciar consultas DQL
    private Connection c;         

    public ConexaoSQL() {
        String Database = "xx";
        String user = "xx";
        String password = "xx";
        
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://psc-samuelmo.mysql.uhserver.com:3306/" + Database;
        
        //Abrir conexão
        try {            
            Class.forName(driver).newInstance();                               
            c = DriverManager.getConnection(url, user, password);            
            stmt = c.createStatement();              
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na conexão MySQL");            
            JOptionPane.showMessageDialog(null, e);
            JOptionPane.showMessageDialog(null, "A aplicação será finalizada...");
            System.exit(0); //Finalizar a aplicação
        }              
    }    
    
    public boolean SQLExecute(String SQL) {
        try {
            this.stmt.execute(SQL);
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }        
    }

    public boolean setResultSet(String Query) {
        try {
            this.rs = this.stmt.executeQuery(Query);
            return true;
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(null, e);
            return false;
        }                
    }             
    
    public ResultSet getResultSet() {
        return rs;
    }       
          
}
