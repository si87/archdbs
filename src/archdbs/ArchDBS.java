/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archdbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author db2inst1
 */
public class ArchDBS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            Connection connection = DriverManager.getConnection("jdbc:db2://localhost:50000/archdbs", "db2inst1", "hda");
            connection.close();
            // Test
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
