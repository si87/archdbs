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
	

        System.out.println("==== Menue ====");
        System.out.println("0 - Programm beenden");
        System.out.println("==== P1 ====");
        System.out.println("1 - createTestdata()");
        System.out.println("==== P2 ====");
        System.out.println("2 - Szenario 1");
        System.out.println("3 - Szenario 2");
        System.out.println("4 - Szenario 3");
        System.out.println("5 - Szenario 4");
        System.out.println("==== END ====");
        System.out.print("Auswahl: ");
        
        Scanner scanner = new Scanner(System.in);
	if (scanner.hasNextInt()) {
		switch(scanner.nextInt()) {
                    case 1:
                        P1.createTestdata();
                        break;
                    case 2:
                        P2.scenarioOne();
                        break;
                    case 3:
                        P2.scenarioTwo();
                        break;
                    case 4:
                        P2.scenarioThree();
                        break;
                    case 5:
                        P2.scenarioFour();
                        break;
                    default:
                        break;
                }
	} else {
		System.out.println("Leider ungültige Eingabe.");
	}

    }
    
    
}
