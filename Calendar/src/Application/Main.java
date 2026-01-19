package Application;
import Controller.Controller;
import Model.*;
import View.View;

/**
 * The Main class serves as the primary entry point for the Erasmus Calendar application.
 * It contains the main method required to launch the program.
 */
public class Main {
    /**
     * The main method called by the JVM upon application startup.
     * It delegates the initialization process to the {@link ErasmusCalendar} utility class.
     * * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        ErasmusCalendar.startCalendar();
    }
}