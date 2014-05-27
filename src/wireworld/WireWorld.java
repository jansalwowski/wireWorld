/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author james8
 */
public class WireWorld {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws javax.swing.UnsupportedLookAndFeelException
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, IOException {
        // TODO code application logic here
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        Engine eng = new Engine();
        SimulationPanel simPanel = new SimulationPanel();
        Gui form = new Gui(simPanel);
        EngineController engController = new EngineController(eng, simPanel, form);
        form.setLocationRelativeTo(null);
        form.pack();
        form.setVisible(true);
    }

}
