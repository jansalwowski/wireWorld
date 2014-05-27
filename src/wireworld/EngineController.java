/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author james8
 */
public class EngineController {

    Engine eng = new Engine();
    SimulationPanel simPanel = new SimulationPanel();
    FileOperator fileOp = new FileOperator();
    Gui form = new Gui(simPanel);
    Options options = new Options();
    ImageProducer image;
    public static boolean simulate = false;
    public static boolean started = false;

    public EngineController(Engine eng, SimulationPanel simPanel, Gui form) throws IOException {
        this.eng = eng;
        this.simPanel = simPanel;
        this.form = form;
        this.image = new ImageProducer();
        /*fileOp.t = new int[10][10];
         fileOp.putANDNOT(1, 1);
         simPanel.loadGeneration(fileOp.t);*/
        simPanel.loadGeneration(eng.t);
        this.form.addStartListener(new StartListener());
        this.form.addPauseListener(new PauseListener());
        this.form.addFinishListener(new FinishListener());
        this.form.addFasterListener(new FasterListener());
        this.form.addSlowerListener(new SlowerListener());
        this.form.ComboListener(new ComboListener());
        this.form.LoadListener(new LoadListener());
        this.form.SaveListener(new SaveListener());
        this.form.CreateNewListener(new CreateNewListener());
        this.form.OptionsApplyListener(new OptionsApplyListener());
        this.form.OptionsCheckProdImgListener(new OptionsCheckProdImgListener());
        this.form.QuitListener(new QuitListener());
        this.form.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save your data before quiting?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    saveGeneration();
                } else if (dialogResult == JOptionPane.NO_OPTION) {
                    form.dispose();
                }
            }
        });
    }

    EngineController() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadFile() {
        simPanel.setPreferredSize(new Dimension(eng.t[0].length * 10, eng.t.length * 10));
        loadGenration();
    }

    private void loadGenration() {
        simPanel.loadGeneration(eng.t);
        simPanel.repaint();
    }

    private void nextGenereation() {

        if (options.produceScreenshots) {
            image.createImage(simPanel.t, 1);
        }
        eng.t = simPanel.t;
        eng.nextGeneration();
        loadGenration();
    }

    private void saveGeneration() {
        boolean save = true;
        form.fileopen.setDialogTitle("Specify a file to save");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        form.fileopen.addChoosableFileFilter(filter);
        form.fileopen.setFileFilter(filter);
        form.fileopen.setSelectedFile(new File("save.txt"));
        int ret = form.fileopen.showSaveDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = form.fileopen.getSelectedFile();
            if (file.exists()) {
                int dialogResult = JOptionPane.showConfirmDialog(form, "File already exists. Do you want to overwrite it?", "Confirm save", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    save = true;
                }
            } else {
                save = true;
            }
            if (save) {
                try {
                    fileOp.t = simPanel.t;
                    fileOp.saveFile(file.getAbsolutePath());
                } catch (IOException ex) {
                    Logger.getLogger(EngineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    class StartListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!started) {
                started = true;
                Runnable runnable = new MyRunnable();
                Thread newThread = new Thread(runnable);
                newThread.start();
            }
            simulate = true;
        }

    }

    class PauseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            simulate = false;
        }
    }

    class MyRunnable implements Runnable {

        @Override
        public void run() {
            while (started) {
                if (simulate == true) {
                    nextGenereation();
                }
                try {
                    Thread.sleep(options.AnimationInterval * 100);
                } catch (InterruptedException e) {
                }
            }

        }
    }

    class CreateNewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int x = form.getRows();
            int y = form.getColls();
            image = new ImageProducer();
            simPanel.setPreferredSize(new Dimension(x * 10, y * 10));
            eng = new Engine(x, y);
            loadGenration();
        }

    }

    class FasterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (options.AnimationInterval > 1) {
                options.AnimationInterval--;
                form.setOptionsAnimationInterval(Integer.toString(options.AnimationInterval));
            }
        }
    }

    class SlowerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (options.AnimationInterval < 20) {
                options.AnimationInterval++;
                form.setOptionsAnimationInterval(Integer.toString(options.AnimationInterval));
            }
        }
    }

    class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int ret = form.fileopen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = form.fileopen.getSelectedFile();
                if (file.exists()) {
                    try {
                        fileOp.readFile(file.getAbsolutePath());
                        eng.loadGeneration(fileOp.t);
                        image = new ImageProducer();
                        loadFile();
                    } catch (IOException ex) {
                        Logger.getLogger(EngineController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(form, "File not found.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            saveGeneration();
        }
    }

    class FinishListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            simulate = false;
            saveGeneration();
            boolean savegif = false;
            File fileGif = null;
            int dialogResult = JOptionPane.showConfirmDialog(form, "Do you want to create GIF from simulation?", "Create GIF?", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                form.fileopen.setDialogTitle("Specify a file to save");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("GIF Images", "gif");
                form.fileopen.addChoosableFileFilter(filter);
                form.fileopen.setFileFilter(filter);
                form.fileopen.setSelectedFile(new File("output.gif"));
                int saveGif = form.fileopen.showSaveDialog(null);
                if (saveGif == JFileChooser.APPROVE_OPTION) {
                    fileGif = form.fileopen.getSelectedFile();
                    if (fileGif.exists()) {
                        int confirmExist = JOptionPane.showConfirmDialog(form, "File already exists. Do you want to overwrite it?", "Confirm save", JOptionPane.YES_NO_OPTION);
                        if (confirmExist == JOptionPane.YES_OPTION) {
                            savegif = true;
                        }
                    } else {
                        savegif = true;
                    }
                }
            }
            if (savegif) {
                try {
                    image.saveGif(fileGif);
                } catch (IOException ex) {
                    Logger.getLogger(EngineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    class ComboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            simPanel.itemType = form.getCombo();
        }
    }

    class OptionsApplyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            options.produceScreenshots = form.checkProduceScreenshots();
            options.imageInterval = form.getOptionsImageInterval();
            options.AnimationInterval = form.getOptionsAnimationInterval();
        }
    }

    class OptionsCheckProdImgListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            options.produceScreenshots = form.checkProduceScreenshots();
        }
    }

    class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save your data before quiting?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                saveGeneration();
            } else if (dialogResult == JOptionPane.NO_OPTION) {
                form.dispose();
            }
        }
    }
}
