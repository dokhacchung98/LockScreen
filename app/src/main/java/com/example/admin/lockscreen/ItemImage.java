package com.example.admin.lockscreen;

/**
 * Created by Admin on 1/15/2018.
 */

public class ItemImage {
    private int n;
    private int[] img;
    private boolean chose = false;

    public boolean isChose() {
        return chose;
    }

    public void setChose(boolean chose) {
        this.chose = chose;
    }

    public ItemImage(int n, int[] img, boolean chose) {

        this.n = n;
        this.img = img;
        this.chose = chose;
    }

    public ItemImage(int n, int[] img) {
        this.n = n;
        this.img = img;
    }

    public int getN() {
        return n;
    }

    public int[] getImg() {
        return img;
    }
}
