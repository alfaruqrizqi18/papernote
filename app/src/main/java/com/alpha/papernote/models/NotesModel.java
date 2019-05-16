package com.alpha.papernote.models;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NotesModel extends RealmObject {
    @PrimaryKey
    private Integer id;
    private String title;
    private String content;
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
