package com.example.sankalpheranjal.feed4me;

public class Tags {

    String title;
    String link;
    String description;
    String pubDate;

    public Tags(){

    }

    public Tags(String title, String link, String description, String pubDate){
        this.title = title;
        this.link = link;
        this.description = description;
        this.pubDate = pubDate;

    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setLink(String link){
        this.link = link;
    }

    public String getLink(){
        return link;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setPubDate(String pubDate){
        this.pubDate = pubDate;
    }

    public String getPubDate(){
        return pubDate;
    }

}