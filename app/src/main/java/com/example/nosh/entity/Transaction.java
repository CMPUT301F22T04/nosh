package com.example.nosh.entity;

import java.util.HashMap;

public class Transaction {

    private final String tag;
    private final HashMap<String, Object> contents;

    public Transaction(String tag) {
        this.tag = tag;
        this.contents = new HashMap<>();
    }

    public String getTag() {
        return tag;
    }

    public HashMap<String, Object> getContents() {
        return contents;
    }

    public void putContents(String key, Object content) {
        this.contents.put(key, content);
    }

}
