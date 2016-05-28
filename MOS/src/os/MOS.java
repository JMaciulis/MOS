package os;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import os.gui.MainWindow;
import os.machine.Machine;

public class MOS {

    static void initLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            System.err.println("Error: Look and Feel");
        } 
    }

    public static void main(String[] args) {
        System.out.println("Pauliaus OS.");
        initLookAndFeel();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                OS os = new OS(new Machine());
                MainWindow mw = new MainWindow(os);
                mw.setVisible(true);
            }
        });
    }

}
