/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archdbs;

/**
 *
 * @author db2inst1
 */
public class Timer {
    private static String title;
    private static long startTime;
    private static long duration;
    
    public Timer() {
        
    }
    
    public static void start(String inputTitle) {
        title = inputTitle;
        startTime = System.currentTimeMillis();
    }
    
    public static void end() {
        duration = System.currentTimeMillis() - startTime;
        System.out.println(title + " - Dauer: " + duration);
    }
    
}
