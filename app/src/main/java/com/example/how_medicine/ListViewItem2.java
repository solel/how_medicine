package com.example.how_medicine;

public class ListViewItem2 {
    private int iconDrawble;
    private String text3Str;
    private String titleStr;

    public void setText3Str(String getText3) {
        this.text3Str = getText3;
    }

    public void setTitleStr(String titleStr) {
        this.titleStr = titleStr;
    }


    /*public int getIcon(){
        return this.iconDrawble;
    }*/
    public String getText3(){
        return this.text3Str;
    }
    public String getTitle(){
        return this.titleStr;
    }
}
