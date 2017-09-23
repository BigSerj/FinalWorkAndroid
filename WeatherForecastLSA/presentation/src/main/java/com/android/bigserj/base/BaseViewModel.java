package com.android.bigserj.base;


import android.view.View;

public interface BaseViewModel {

    public void init();

    public void release();

    public void resume();

    public void pause();

}
