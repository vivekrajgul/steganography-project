package model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vrg
 */
public class HaarWaveletTransform {
    
   
    public static int[][] doHaar2DFWTransform(int [][] pixels, int cycles) {
        int w = pixels[0].length;
        int h = pixels.length;
        int maxCycle = getHaarMaxCycles(w);
        boolean isCycleAllowed = isCycleAllowed(maxCycle, cycles);
        if (isCycleAllowed) {
            int[][] ds = new int[h][w];
            int[][] tempds = new int[h][w];
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    ds[i][j] = pixels[i][j];
                }
            }
            for (int i = 0; i < cycles; i++) {
                w /= 2;
                for (int j = 0; j < h; j++) {
                    for (int k = 0; k < w; k++) {
                        int a = ds[j][2 * k];
                        int b = ds[j][2 * k + 1];
                        int add = a + b;
                        int sub = a - b;
                        int avgAdd = add / 2;
                        int avgSub = sub / 2;
                        tempds[j][k] = avgAdd;
                        tempds[j][k + w] = avgSub;
                    }
                }
                for (int j = 0; j < h; j++) {
                    for (int k = 0; k < w; k++) {
                        ds[j][k] = tempds[j][k];
                        ds[j][k + w] = tempds[j][k + w];
                    }
                }
                h /= 2;
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < h; k++) {
                        int a = ds[2 * k][j];
                        int b = ds[2 * k + 1][j];
                        int add = a + b;
                        int sub = a - b;
                        int avgAdd = add / 2;
                        int  avgSub = sub / 2;
                        tempds[k][j] = avgAdd;
                        tempds[k + h][j] = avgSub;
                    }
                }
                for (int j = 0; j < w; j++) {
                    for (int k = 0; k < h; k++) {
                        ds[k][j] = tempds[k][j];
                        ds[k + h][j] = tempds[k + h][j];
                    }
                }
            }
            return ds;
        }
        return null;
    }
 
    public static int[][] doHaar2DInvTransform(int[][] pixels, int cycles) {
        int w = pixels[0].length;
        int h = pixels.length;
        int maxCycle = getHaarMaxCycles(w);
        boolean isCycleAllowed = isCycleAllowed(maxCycle, cycles);
        if (isCycleAllowed) {
            int[][] ds = new int[h][w];
            int[][] tempds = new int[h][w];
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[0].length; j++) {
                    ds[i][j] = pixels[i][j];
                }
            }
            int hh = h / (int) Math.pow(2, cycles);
            int ww = w / (int) Math.pow(2, cycles);
            for (int i = cycles; i > 0; i--) {
                for (int j = 0; j < ww; j++) {
                    for (int k = 0; k < hh; k++) {
                        int a = ds[k][j];
                        int b = ds[k + hh][j];
                        int add = a + b;
                        int sub = a - b;
                        tempds[2 * k][j] = add;
                        tempds[2 * k + 1][j] = sub;
                    }
                }
                for (int j = 0; j < ww; j++) {
                    for (int k = 0; k < hh; k++) {
                        ds[2 * k][j] = tempds[2 * k][j];
                        ds[2 * k + 1][j] = tempds[2 * k + 1][j];
                    }
                }
                hh *= 2;
                for (int j = 0; j < hh; j++) {
                    for (int k = 0; k < ww; k++) {
                        int a = ds[j][k];
                        int b = ds[j][k + ww];
                        int add = a + b;
                        int sub = a - b;
                        tempds[j][2 * k] = add;
                        tempds[j][2 * k + 1] = sub;
                    }
                }
                for (int j = 0; j < hh; j++) {
                    for (int k = 0; k < ww; k++) {
                        ds[j][2 * k] = tempds[j][2 * k];
                        ds[j][2 * k + 1] = tempds[j][2 * k + 1];
                    }
                }
                ww *= 2;
            }
            return ds;
        }
        return null;
    }
 
    private static int getHaarMaxCycles(int hw) {
        int cycles = 0;
        while (hw > 1) {
            cycles++;
            hw /= 2;
        }
        return cycles;
    }
 
    private static boolean isCycleAllowed(int maxCycle, int cycles) {
        return cycles <= maxCycle;
    }
 
}
