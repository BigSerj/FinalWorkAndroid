package com.android.bigserj.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;


abstract public class BaseFragment extends Fragment{

    protected BaseViewModelFragment viewModelFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFragment.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModelFragment.destroyView();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity();
        viewModelFragment.viewCreated();

    }



    @Override
    public void onStart() {
        super.onStart();
        viewModelFragment.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModelFragment.stop();
    }

    /**
     * Вызывается когда активити в которой лежит фрагмент создана.
     * После этого метода можно использовать метод getActivity().
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelFragment.activityCreated();
    }




}
