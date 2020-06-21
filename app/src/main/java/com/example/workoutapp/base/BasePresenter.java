package com.example.workoutapp.base;


public abstract class BasePresenter {

    protected BaseView view;

    public BasePresenter(BaseView view) {
        this.view = view;
    }
}
