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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran & Kappes
 */
public class P4 {
    private Connection connection;
    private PreparedStatement pStmt_Bestellung;
    private PreparedStatement pStmt_Produkt;
    private ResultSet rs_Bestellung;
    private ResultSet rs_Produkte;
    public P4() {
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection("jdbc:db2://localhost:50000/archdbs", "db2inst1", "hda");
            pStmt_Bestellung = connection.prepareStatement("SELECT b.bid, b.pid FROM Bestellung b WHERE b.bid < 10000 ORDER BY b.pid", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pStmt_Produkt = connection.prepareStatement("SELECT p.pid, p.Produktname FROM Produkt p ORDER BY p.pid", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs_Bestellung = pStmt_Bestellung.executeQuery();
            rs_Produkte = pStmt_Produkt.executeQuery();
        } catch (Exception ex) {
            Logger.getLogger(P2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   public void doNestedLoopsJoin(){
       int touchedTupel_Bestellung = 0;
       int touchedTupel_Produkt = 0;
       int numberOfResults = 0;
        try {
            Timer.start("doNestedLoopsJoin");
            while(rs_Produkte.next()) {
                ++touchedTupel_Produkt;
                while(rs_Bestellung.next()) {
                    ++touchedTupel_Bestellung;
                    if (rs_Produkte.getInt("pid") == rs_Bestellung.getInt("pid")) {
                        //System.out.println("BID: " + rs_Bestellung.getInt("bid") + " - Produktname: " + rs_Produkte.getString("produktname"));
                        ++numberOfResults;
                    }
                }
                rs_Bestellung.beforeFirst();
            }
            Timer.end();
            System.out.println("touched_Produkt: " + touchedTupel_Produkt);
            System.out.println("touched_Bestellung: " + touchedTupel_Bestellung);
            System.out.println("number of results: " + numberOfResults);
            
        } catch (SQLException ex) {
            Logger.getLogger(P4.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public void doMergeJoin() {
       
       int touchedTupel_Bestellung = 0;
       int touchedTupel_Produkt = 1;
       int numberOfResults = 0;
        try {
            Timer.start("doMergeJoin");
            rs_Produkte.first();
            rs_Bestellung.first();
            while((!rs_Produkte.isAfterLast()) && (!rs_Bestellung.isAfterLast())) {
                
                if (rs_Produkte.getInt("pid") < rs_Bestellung.getInt("pid")) {
                    rs_Produkte.next();
                    ++touchedTupel_Produkt;
                }
                    
                else if (rs_Produkte.getInt("pid") > rs_Bestellung.getInt("pid")) {
                    rs_Bestellung.next();
                    ++touchedTupel_Bestellung;
                } else {  // must be a match
                    ++numberOfResults;
                    rs_Bestellung.next();
                    ++touchedTupel_Bestellung;
                }
            }
            
            Timer.end();            
            System.out.println("touched_Produkt: " + touchedTupel_Produkt);
            System.out.println("touched_Bestellung: " + touchedTupel_Bestellung);
            System.out.println("number of results: " + numberOfResults);
        } catch (SQLException ex) {
            Logger.getLogger(P4.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   public void doClassicHashJoin() {
       int touchedTupel_Bestellung = 0;
       int touchedTupel_Produkt = 0;
       int numberOfResults = 0;
       Map<Integer, String> produktMap = new HashMap<Integer, String>();
        try {
            // kleinerer Tupel = Produkt;
            Timer.start("doClassicHashJoin");
            while(rs_Produkte.next()) {
                ++touchedTupel_Produkt;
                produktMap.put(rs_Produkte.getInt("pid"), rs_Produkte.getString("produktname"));
            }
            
            while(rs_Bestellung.next()) {
                ++touchedTupel_Bestellung;
                if(produktMap.containsKey(rs_Bestellung.getInt("pid"))) {
                    ++numberOfResults;
                }
            }
            Timer.end();
            System.out.println("touched_Produkt: " + touchedTupel_Produkt);
            System.out.println("touched_Bestellung: " + touchedTupel_Bestellung);
            System.out.println("number of results: " + numberOfResults);
            
        } catch (SQLException ex) {
            Logger.getLogger(P4.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
}
