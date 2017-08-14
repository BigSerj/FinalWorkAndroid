package com.android.bigserj.domain.interaction.base;


public abstract class UseCase<InParam, OutParam> {


    // ищем данные по городу, который ввели

    protected abstract OutParam buildUseCase();

    public OutParam execute(InParam param){
        return buildUseCase();
    }



}