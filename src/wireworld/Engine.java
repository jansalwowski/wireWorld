package wireworld;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author james8
 */
public class Engine {

    public int[][] t;
    private int empty = 0;
    private int conductor = 1;
    private int head = 2;
    private int tail = 3;
    public int count;

    public Engine() {
        this.t = new int[30][30];
        this.count = 0;
    }

    public Engine(int y, int x) {
        this.t = new int[y][x];
        this.count = 0;
    }

    private void checkNeighbour(int tab[][], int y, int x) {
        if (tab[y][x] == 2) {
            t[y][x] = 3;
        } else if (tab[y][x] == 3) {
            t[y][x] = 1;
        } else if (tab[y][x] == 0) {
            t[y][x] = 0;
        } else {
            int n = 0;
            for (int i = (y - 1); i <= (y + 1); i++) {
                if (i < 0 || i >= tab.length) {
                    continue;
                }
                for (int j = (x - 1); j <= (x + 1); j++) {
                    if (j < 0 || j >= tab[i].length || (i == y && j == x)) {
                        continue;
                    }
                    if (tab[i][j] == 2) {
                        n++;
                    }
                }
            }
            if (tab[y][x] == 1 && (n == 1 || n == 2)) {
                t[y][x] = 2;
            } else {
                t[y][x] = 1;
            }
        }
    }

    public void nextGeneration() {
        int[][] nt = new int[t.length][t[0].length];
        for (int i = 0; i < t.length; i++) {
            System.arraycopy(t[i], 0, nt[i], 0, t[0].length);
        }
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[i].length; j++) {
                checkNeighbour(nt, i, j);
            }
        }
        this.count++;
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

    public void setTest() {
        this.t = new int[60][60];
        setCell(2, 1, 2);
        setCell(1, 2, 1);
        setCell(2, 2, 1);
        setCell(1, 3, 1);
        setCell(1, 4, 1);
        setCell(1, 5, 1);
    }
    
    public void loadGeneration(int [][]tab)
    {
        if( t.length != tab.length || t[0].length != tab[0].length)
            t = new int[tab.length][tab[0].length];
        for (int i = 0; i < t.length; i++) {
            System.arraycopy(tab[i], 0, t[i], 0, tab[0].length);
        }
    }
}
