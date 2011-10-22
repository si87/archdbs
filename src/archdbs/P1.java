package archdbs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Tran & Kappes
 */
public class P1 {
    public static void createTestdata() {
        try {
            // TODO code application logic here
            Class.forName("com.ibm.db2.jcc.DB2Driver");
            Connection connection = DriverManager.getConnection("jdbc:db2://localhost:50000/archdbs", "db2inst1", "hda");
            
            // Turn off autocommit
            connection.setAutoCommit(false);
            
            System.out.println("::: Daten werden eingetragen, bitte warten :::");
            
            long startTime = System.currentTimeMillis();
            
            PreparedStatement pStmt_Kunde = connection.prepareStatement("INSERT INTO Kunde (KNR, KNAME, KVORNAME) VALUES (?, ?, ?)");
            PreparedStatement pStmt_Produkt = connection.prepareStatement("INSERT INTO Produkt (PID, PRODUKTNAME, PREIS) VALUES (?, ?, ?)");
            PreparedStatement pStmt_Bestellung = connection.prepareStatement("INSERT INTO Bestellung (BID, KNR, PID, DATUM, STATUS) VALUES (?, ?, ?, ?, ?)");
            
            // Create all 'Kunden'
            for(int i=1; i<=100;i++) {
                pStmt_Kunde.setInt(1, i);
                pStmt_Kunde.setString(2, "Name "+i);
                pStmt_Kunde.setString(3, "Vorname "+i);
                pStmt_Kunde.execute();
            }
            
            // Create all 'Produkte'
            for(int i=1;i<=1000;i++) {
                pStmt_Produkt.setInt(1, i);
                pStmt_Produkt.setString(2, "Produkt "+i);
                pStmt_Produkt.setDouble(3, i);
                pStmt_Produkt.execute();
            }
            
            // Create all 'Bestellung'
            for(int i=1;i<=750000;i++) {
                pStmt_Bestellung.setInt(1, i);
                pStmt_Bestellung.setInt(2, 1);//(i%101)+1);
                pStmt_Bestellung.setInt(3, 1);//(i%1001)+1);
                pStmt_Bestellung.setDate(4, new Date(2011,10,17));
                pStmt_Bestellung.setInt(5, 1);//i%2);
                pStmt_Bestellung.execute();
            }
            
            connection.commit();
            connection.close();
            
            System.out.println("Zeitdauer eintragen: " + (System.currentTimeMillis() - startTime));
            // Test 3
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
