package com.orangeTree.bigserj.base;


abstract class BaseItemViewModel<Model> implements BaseViewModel {

    abstract void setItem(Model item, int posotoin);

    @Override
    public void init() {

    }

    @Override
    public void release() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
