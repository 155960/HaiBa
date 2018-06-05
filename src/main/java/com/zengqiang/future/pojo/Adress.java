package com.zengqiang.future.pojo;

public class Adress {
    private Integer id;

    private String name;

    public Adress(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Adress() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}