/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archdbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran & Kappes
 */
public class P2 {
    private Connection connection;
    public P2() {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection("jdbc:db2://localhost:50000/archdbs", "db2inst1", "hda");
        } catch (Exception ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void scenarioOneStmt() {
        try {
            // ToDo Code here
            Timer.start("Szenario 1 - Stmt");
            Statement stmt = connection.createStatement();
            for (int i=0; i < 4;i++) {
                for(int j=0;j<750000;j+=300) {
                    //stmt.execute("SELECT * FROM Bestellung WHERE bid > "+(j)+" AND bid <= "+(j+300)+"");
                    // SQL kleiner gleich in WHERE ??
                    ResultSet result = stmt.executeQuery("SELECT * FROM Bestellung WHERE bid > "+(j)+" AND bid <= "+(j+300)+"");
                    if(!checkResult(result,j+1, j+300)){
                        System.out.println("Fehler P2.java : scenarioOneStmt -> checkResult");
                    }
                }
            }
            Timer.end();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void scenarioOnePStmt() {
        try {
            // ToDo Code here
            Timer.start("Szenario 1 - pStmt");
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM Bestellung WHERE bid > ? AND bid <= ?");
            for (int i=0; i < 4;i++) {
                for(int j=0;j<750000;j+=300) {
                    pStmt.setInt(1, j);
                    pStmt.setInt(2, j+300);
                    //pStmt.execute();
                    // SQL kleiner gleich in WHERE ??
                    ResultSet result = pStmt.executeQuery();
                    if(!checkResult(result,j+1, j+300)){
                        System.out.println("Fehler P2.java : scenarioOnePStmt -> checkResult");
                    }
                }
            }
            Timer.end();
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void scenarioTwo() {
        try {
            // ToDo Code here
            Random random = new Random();
            int randomStart;
            Timer.start("Szenario 2 - zufällige Startpunkt");
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM Bestellung WHERE bid > ? AND bid <= ?");
            for (int i=0; i < 4;i++) {
                for(int j=0;j<750000;j+=300) {
                    randomStart = random.nextInt(749700);
                    pStmt.setInt(1, randomStart);
                    pStmt.setInt(2, randomStart+300);
                    //pStmt.execute();
                    // SQL kleiner gleich in WHERE ??
                    ResultSet result = pStmt.executeQuery();
                    if(!checkResult(result,randomStart+1, randomStart+300)){
                        System.out.println("Fehler P2.java : scenarioTwo -> checkResult");
                    }
                }
            }
            Timer.end();
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void scenarioThree() {
        try {
            // ToDo Code here
            Timer.start("Szenario 3 - selbe Sätze");
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM Bestellung WHERE bid > ? AND bid <= ?");
            for (int i=0; i < 4;i++) {
                for(int j=0;j<750000;j+=300) {
                    pStmt.setInt(1, 0);
                    pStmt.setInt(2, 300);
                    //pStmt.execute();
                    // SQL kleiner gleich in WHERE ??
                    ResultSet result = pStmt.executeQuery();
                    if(!checkResult(result,1, 300)){
                        System.out.println("Fehler P2.java : scenarioThree -> checkResult");
                    }
                    
                }
            }
            Timer.end();
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void scenarioFour() {
        // ToDo Code here
        try {
            // ToDo Code here
            Timer.start("Szenario 4 - 10er-Pack");
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM Bestellung WHERE bid > ? AND bid <= ?");
            for (int i=0; i < 4;i++) {
                for(int j=0;j<750000;j+=10) {
                    pStmt.setInt(1, j);
                    pStmt.setInt(2, j+10);
                    pStmt.execute();
                    // SQL kleiner gleich in WHERE ??
                    ResultSet result = pStmt.executeQuery();
                    if(!checkResult(result,j+1, j+10)){
                        System.out.println("Fehler P2.java : scenarioFour -> checkResult");
                    }
                }
            }
            Timer.end();
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkResult(ResultSet result, int start, int end){
        boolean ret = true;
        int bid;
        try {
            while(result.next()){
               bid = result.getInt(1);
               if((bid != start)||(bid > end)){
                   ret = false;
                   break;
               }
               start++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
