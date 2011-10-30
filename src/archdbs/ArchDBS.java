package archdbs;

import com.ibm.db2.jcc.am.InternalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;
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

        P1 p1 = new P1();
        P2 p2 = new P2();
	

        System.out.println("==== Menue ====");
        System.out.println("0 - Programm beenden");
        System.out.println("==== P1 ====");
        System.out.println("1 - createTestdata()");
        System.out.println("==== P2 ====");
        System.out.println("2 - Szenario 1 - Stmt");
        System.out.println("3 - Szenario 1 - pStmt");
        System.out.println("4 - Szenario 2 - zufälliger Einstiegspunkt");
        System.out.println("5 - Szenario 3 - selben Sätze");
        System.out.println("6 - Szenario 4 - 10er Pack");
        System.out.println("==== END ====");
        System.out.print("Auswahl: ");
        
        Scanner scanner = new Scanner(System.in);
	if (scanner.hasNextInt()) {
		switch(scanner.nextInt()) {
                    case 1:
                        p1.createTestdata();
                        break;
                    case 2:
                        p2.scenarioOneStmt(); // true = Stmt
                        break;
                    case 3:
                        p2.scenarioOnePStmt();
                        break;
                    case 4:
                        p2.scenarioTwo_Test();
                        break;
                    case 5:
                        p2.scenarioThree();
                        break;
                    case 6:
                        p2.scenarioFour();
                        break;
                    default:
                        break;
                }
	} else {
		System.out.println("Leider ungültige Eingabe.");
	}


    }
    
    
}
