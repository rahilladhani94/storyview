package com.storyview.Model;

/**
 * Created by abc on 01-06-2017.
 */
public class Contact {

    //private variables
    int _id;
    String status;
    String fontstyle;
    String textsize;
    String bgcolor;
    String imagepath;
    String date;



    public Contact(int id, String status,String fontstyle,String textsize,String bgcolor,String imagepath,String date){
        this._id = id;
        this.status = status;
        this.fontstyle = fontstyle;
        this.textsize = textsize;
        this.bgcolor = bgcolor;
        this.imagepath = imagepath;
        this.date = date;

    }
    // Empty constructor

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Contact(){

    }

    // constructor
    public Contact( String status,String fontstyle,String textsize,String bgcolor,String imagepath,String date){

        this.status = status;
        this.fontstyle = fontstyle;
        this.textsize = textsize;
        this.bgcolor = bgcolor;
        this.imagepath = imagepath;
        this.date = date;
    }


    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFontstyle() {
        return fontstyle;
    }

    public void setFontstyle(String fontstyle) {
        this.fontstyle = fontstyle;
    }

    public String getTextsize() {
        return textsize;
    }

    public void setTextsize(String textsize) {
        this.textsize = textsize;
    }

    public String getBgcolor() {
        return bgcolor;
    }

    public void setBgcolor(String bgcolor) {
        this.bgcolor = bgcolor;
    }
}
