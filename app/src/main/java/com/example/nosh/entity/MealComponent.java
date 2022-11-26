package com.example.nosh.entity;


import androidx.annotation.NonNull;

public abstract class MealComponent extends Hashable {

    protected MealComponent() {
        super();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    // TODO: how scaling should work ?
    public void scale(int factor) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
