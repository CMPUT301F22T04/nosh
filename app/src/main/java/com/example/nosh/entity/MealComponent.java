package com.example.nosh.entity;


import androidx.annotation.NonNull;

public abstract class MealComponent {

    // TODO: what detail need to be show ? All of them ?
    public MealComponent getDetails() {
        throw new UnsupportedOperationException();
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
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
