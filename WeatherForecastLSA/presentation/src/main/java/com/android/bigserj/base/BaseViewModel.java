package com.android.bigserj.base;


public interface BaseViewModel {

    public void init();

    public void release();

    public void resume();

    public void pause();
}
