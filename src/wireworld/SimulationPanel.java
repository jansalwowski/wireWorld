/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 *
 * @author james8
 */
public class SimulationPanel extends javax.swing.JPanel implements MouseListener, MouseMotionListener {

    public int[][] t;
    final int rectDim = 10;
    public final int RECTANGLE_WIDTH = 10;
    public int RECTANGLE_HEIGHT = 10;
    private static boolean check = false;
    public static String itemType = "Conductor";

    public SimulationPanel() {
        setPreferredSize(new Dimension(400, 400));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.t = new int[20][20];
    }

    public SimulationPanel(int x, int y) {
        setPreferredSize(new Dimension(y * rectDim, x * rectDim));
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.t = new int[y][x];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int j = 0; j < t.length; j++) {
            for (int i = 0; i < t[j].length; i++) {
                if (t[j][i] == 1) {
                    g2d.setColor(Color.yellow);
                    g2d.fillRect(i * rectDim, j * rectDim, rectDim, rectDim);
                } else if (t[j][i] == 2) {
                    g2d.setColor(Color.red);
                    g2d.fillRect(i * rectDim, j * rectDim, rectDim, rectDim);
                } else if (t[j][i] == 3) {
                    g2d.setColor(Color.blue);
                    g2d.fillRect(i * rectDim, j * rectDim, rectDim, rectDim);
                } else {
                    g2d.setColor(Color.black);
                    g2d.fillRect(i * rectDim, j * rectDim, rectDim, rectDim);
                }
                /* if (i == j) {
                 g2d.setColor(Color.yellow);
                 g2d.fillRect(i * RECTANGLE_WIDTH, j * RECTANGLE_HEIGHT, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                 }*/

            }
        }
        check = true;
    }

    public void loadBasic(int[][] tab) {
        this.t = new int[tab.length][tab[0].length];
        for (int i = 0; i < this.t.length; i++) {
            System.arraycopy(tab[i], 0, this.t[i], 0, tab[0].length);
        }
        setPreferredSize(new Dimension(tab.length * rectDim, tab[0].length * rectDim));
    }

    public void loadGeneration(int[][] tab) {
        t = new int[tab.length][tab[0].length];
        for (int i = 0; i < t.length; i++) {
            System.arraycopy(tab[i], 0, t[i], 0, tab[0].length);
        }
    }

    public void setPoint(int x, int y, String value) {
        if (t.length > y && t[0].length > x && x >= 0 && y >= 0) {
            switch (value) {
                case "Diode":
                    putDiode(y, x, "normal");
                    break;
                case "DiodeReversed":
                    putDiode(y, x, "Reversed");
                    break;
                case "ElectronHead":
                    t[y][x] = 2;
                    break;
                case "ElectronTail":
                    t[y][x] = 3;
                    break;
                case "OR":
                    putOR(y, x);
                    break;
                case "XOR":
                    putXOR(y, x);
                    break;
                case "EOR":
                    putXOR(y, x);
                    break;
                case "NOT":
                    putNOT(y, x);
                    break;
                case "ANDNOT":
                    putANDNOT(y, x);
                    break;
                case "Flip":
                    putFlip(y, x);
                    break;
                case "Conductor":
                    t[y][x] = 1;
                    break;
                case "Empty":
                    t[y][x] = 0;
                    break;
                default:
                    t[y][x] = 1;
                    break;
            }
            repaint();
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    public void setTest() {
        this.t = new int[20][20];
        this.t[1][1] = 1;
        this.t[1][2] = 1;
        this.t[1][3] = 1;
        this.t[1][4] = 1;
        this.t[1][5] = 1;
        this.t[1][6] = 1;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        if (isLeftMouseButton(e)) {
            setPoint(x / 10, y / 10, itemType);
        } else if (isRightMouseButton(e)) {
            setPoint(x / 10, y / 10, "Empty");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        if (isLeftMouseButton(e)) {
            setPoint(x / 10, y / 10, itemType);
        } else if (isRightMouseButton(e)) {
            setPoint(x / 10, y / 10, "Empty");
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void setCell(int y, int x, int value) {
        if (y < t.length && y >= 0 && x < t[0].length && x >= 0) {
            this.t[y][x] = value;
        }
    }

    public void putDiode(int y, int x, String type) {
        if (type.equals("Normal") || type.equals("normal")) {
            setCell(y, x, 1);
            setCell(y + 1, x, 1);
            setCell(y + 2, x, 1);
            setCell(y, x + 1, 1);
            setCell(y + 2, x + 1, 1);
        } else {
            setCell(y, x, 1);
            setCell(y + 1, x + 1, 1);
            setCell(y + 2, x + 1, 1);
            setCell(y, x + 1, 1);
            setCell(y + 2, x, 1);
        }
    }

    public void putOR(int y, int x) {
        setCell(y, x, 1);
        setCell(y + 1, x + 1, 1);
        setCell(y + 2, x, 1);
        setCell(y + 2, x + 1, 1);
        setCell(y + 2, x + 2, 1);
        setCell(y + 3, x + 1, 1);
        setCell(y + 4, x, 1);
    }

    public void putXOR(int y, int x) {
        setCell(y, x, 1);
        setCell(y + 1, x + 1, 1);
        setCell(y + 2, x, 1);
        setCell(y + 2, x + 1, 1);
        setCell(y + 2, x + 2, 1);
        setCell(y + 2, x + 3, 1);
        setCell(y + 3, x, 1);
        setCell(y + 3, x + 3, 1);
        setCell(y + 3, x + 4, 1);
        setCell(y + 4, x, 1);
        setCell(y + 4, x + 1, 1);
        setCell(y + 4, x + 2, 1);
        setCell(y + 4, x + 3, 1);
        setCell(y + 5, x + 1, 1);
        setCell(y + 6, x, 1);
    }

    public void putANDNOT(int y, int x) {
        setCell(y, x, 1);
        setCell(y + 1, x + 1, 1);
        setCell(y + 1, x + 3, 1);
        setCell(y + 2, x, 1);
        setCell(y + 2, x + 1, 1);
        setCell(y + 2, x + 2, 1);
        setCell(y + 2, x + 3, 1);
        setCell(y + 2, x + 4, 1);
        setCell(y + 3, x, 1);
        setCell(y + 3, x + 3, 1);
        setCell(y + 4, x, 1);
        setCell(y + 4, x + 1, 1);
        setCell(y + 4, x + 2, 1);
        setCell(y + 4, x + 3, 1);
        setCell(y + 4, x + 4, 1);
        setCell(y + 5, x + 1, 1);
        setCell(y + 6, x, 1);
    }

    public void putNOT(int y, int x) {
        setCell(y, x, 1);
        setCell(y + 1, x + 1, 1);
        setCell(y + 2, x, 1);
        setCell(y + 2, x + 1, 1);
        setCell(y + 2, x + 2, 1);
        setCell(y + 3, x + 1, 1);
        setCell(y + 4, x, 1);
        setCell(y + 4, x + 2, 1);
    }

    public void putFlip(int y, int x) {
        setCell(y, x, 1);
        setCell(y, x + 1, 1);
        setCell(y + 1, x + 2, 1);
        setCell(y + 2, x + 1, 1);
        setCell(y + 2, x + 2, 1);
        setCell(y + 2, x + 3, 1);
        setCell(y + 3, x + 2, 1);
        setCell(y + 4, x + 1, 1);
        setCell(y + 4, x + 3, 1);
        setCell(y + 5, x + 1, 1);
        setCell(y + 5, x + 3, 1);
        setCell(y + 5, x + 4, 1);
        setCell(y + 6, x, 1);
        setCell(y + 6, x + 1, 1);
        setCell(y + 6, x + 2, 1);
        setCell(y + 7, x + 1, 1);
        setCell(y + 8, x, 1);

    }
}
