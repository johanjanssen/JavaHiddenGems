package com.examples.mapstruct;

//@Entity
public class BookEntity {
    private int id;
    private String title;
    private String entityValue;

    public BookEntity() {
    }

    public BookEntity(int id, String title, String entityValue) {
        this.id = id;
        this.title = title;
        this.entityValue = entityValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntityValue() {
        return entityValue;
    }

    public void setEntityValue(String entityValue) {
        this.entityValue = entityValue;
    }
}
