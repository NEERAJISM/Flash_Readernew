package com.knightandday.neeraj.flash_reader;

/**
 * Created by Neeraj on 04-Mar-16.
 */
public class RowItem {
    private String title;
    private String size;
    private String date;

    public RowItem(String title, String size, String date) {
        this.title = title;
        this.size = size;
        this.date = date;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}