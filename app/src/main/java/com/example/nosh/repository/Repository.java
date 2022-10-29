package com.example.nosh.repository;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.entity.Hashable;

import java.util.Observable;
import java.util.Observer;


abstract public class Repository extends Observable implements Observer {

    protected final DBController dbController;

    public Repository(DBController dbController) {
        this.dbController = dbController;

        this.dbController.addObserver(this);
    }

    protected void add(Hashable o) {
        dbController.create(o);

        notifyObservers();
    }

    protected void delete(Hashable o) {
        dbController.delete(o);

        notifyObservers();
    }

    protected void update(Hashable o) {
        dbController.update(o);

        notifyObservers();
    }

    /**
     * Only call this method when first time instantiate an instance of this
     * class. It's for retrieving all data from database when app initially
     * start since there's no data locally.
     */
    public void sync() {
        dbController.retrieve();
    }

    @Override
    public void notifyObservers() {
        setChanged();

        super.notifyObservers();
    }
}
