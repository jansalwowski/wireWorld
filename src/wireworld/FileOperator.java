/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wireworld;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author james8
 */
public class FileOperator {

    int t[][];
    String file;

    public FileOperator() throws FileNotFoundException, IOException {

    }

    void readFile(String filename) throws FileNotFoundException, IOException {
        int i = 0, j;
        boolean check = false;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] result = line.split(" ");
                j = 0;
                if (check == false) {
                    this.t = new int[Integer.parseInt(result[0])][Integer.parseInt(result[1])];
                    check = true;
                } else if (!isNumeric(result[0])) {
                    putItem(result);
                } else {
                    t[Integer.parseInt(result[0])][Integer.parseInt(result[1])] = Integer.parseInt(result[2]);
                }
            }
        }
    }

    void saveFile(String filename) throws IOException {
        try (PrintWriter f0 = new PrintWriter(new FileWriter(filename))) {
            f0.println(t.length + " " + t[0].length);
            for (int i = 0; i < t.length; i++) {
                for (int j = 0; j < t[i].length; j++) {
                    if (t[i][j] == 1 || t[i][j] == 2 || t[i][j] == 3) {
                        f0.println(i + " " + j + " " + t[i][j]);
                    }
                }
            }
        }
    }

    static String checkFile(String filename) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();
        return line;
    }

    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /*Diode: 0, 3, Normal
     Diode: 0, 9, Reversed
     ElectronHead: 1, 3
     ElectronHead: 1, 9 
     ElectronTail: 0, 3
     ElectronTail: 0, 9 */
    public void putItem(String[] result)
    {
        
        switch(result[0])
        {
            case "Diode":
                putDiode(Integer.parseInt(result[0]), Integer.parseInt(result[1]), result[2]);
                break;
            case "ElectronHead":
                putElectronHead(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "ElectronTail":
                putElectronTail(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "OR":
                putOR(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "XOR":
                putXOR(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "EOR":
                putXOR(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "NOT":
                putNOT(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "ANDNOT":
                putANDNOT(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "Flip":
                putFlip(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            case "Conductor":
                putConductor(Integer.parseInt(result[0]), Integer.parseInt(result[1]));
                break;
            default:
                
                break;
        }
    }
    public int getCell(int y, int x) {
        if (y < t.length && y >= 0 && x < t[0].length && x >= 0) {
            return t[y][x];
        } else {
            return 0;
        }
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
        }
        else
        {
            setCell(y, x, 1);
            setCell(y + 1, x+1, 1);
            setCell(y + 2, x+1, 1);
            setCell(y, x + 1, 1);
            setCell(y + 2, x, 1);
        }
    }
    
    public void putConductor(int y, int x)
    {
        setCell(y, x, 1);
    }
    
    public void putElectronHead(int y, int x)
    {
        setCell(y, x, 2);
    }
    
    public void putElectronTail(int y, int x)
    {
        setCell(y, x, 3);
    }
    
    public void putOR(int y, int x)
    {
        setCell(y, x, 1);
        setCell(y+1, x+1, 1);
        setCell(y+2, x, 1);
        setCell(y+2, x+1, 1);
        setCell(y+2, x+2, 1);
        setCell(y+3, x+1, 1);
        setCell(y+4, x, 1);
    }
    
    public void putXOR(int y, int x)
    {
        setCell(y, x, 1);
        setCell(y+1, x+1, 1);
        setCell(y+2, x, 1);
        setCell(y+2, x+1, 1);
        setCell(y+2, x+2, 1);
        setCell(y+2, x+3, 1);
        setCell(y+3, x, 1);
        setCell(y+3, x+3, 1);
        setCell(y+3, x+4, 1);
        setCell(y+4, x, 1);
        setCell(y+4, x+1, 1);
        setCell(y+4, x+2, 1);
        setCell(y+4, x+3, 1);
        setCell(y+5, x+1, 1);
        setCell(y+6, x, 1);
    }
    
    public void putANDNOT(int y, int x)
    {
        setCell(y, x, 1);
        setCell(y+1, x+1, 1);
        setCell(y+1, x+3, 1);
        setCell(y+2, x, 1);
        setCell(y+2, x+1, 1);
        setCell(y+2, x+2, 1);
        setCell(y+2, x+3, 1);
        setCell(y+2, x+4, 1);
        setCell(y+3, x, 1);
        setCell(y+3, x+3, 1);
        setCell(y+4, x, 1);
        setCell(y+4, x+1, 1);
        setCell(y+4, x+2, 1);
        setCell(y+4, x+3, 1);
        setCell(y+4, x+4, 1);
        setCell(y+5, x+1, 1);
        setCell(y+6, x, 1);
    }
    
    public void putNOT(int y, int x)
    {
        setCell(y, x, 1);
        setCell(y+1, x+1, 1);
        setCell(y+2, x, 1);
        setCell(y+2, x+1, 1);
        setCell(y+2, x+2, 1);
        setCell(y+3, x+1, 1);
        setCell(y+4, x, 1);
        setCell(y+4, x+2, 1);
    }
    
    public void putFlip(int y, int x)
    {
        setCell(y, x, 1);
        setCell(y, x+1, 1);
        setCell(y+1, x+2, 1);
        setCell(y+2, x+1, 1);
        setCell(y+2, x+2, 1);
        setCell(y+2, x+3, 1);
        setCell(y+3, x+2, 1);
        setCell(y+4, x+1, 1);
        setCell(y+4, x+3, 1);
        setCell(y+5, x+1, 1);
        setCell(y+5, x+3, 1);
        setCell(y+5, x+4, 1);
        setCell(y+6, x, 1);
        setCell(y+6, x+1, 1);
        setCell(y+6, x+2, 1);
        setCell(y+7, x+1, 1);
        setCell(y+8, x, 1);
        
    }
}
