package com.android.bigserj.myPlaces;


import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.bigserj.PlaceForm.PlaceFormActivity;
import com.android.bigserj.PlaceForm.PlaceFormViewModel;
import com.android.bigserj.R;
import com.android.bigserj.TestApplication;
import com.android.bigserj.base.BaseViewModel;
import com.android.bigserj.domain.entity.LatLon;
import com.android.bigserj.domain.interaction.GetSizeDBUseCase;
import com.android.bigserj.domain.interaction.SetArrayLatLonToTheDBWhenDeleteUseCase;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static com.android.bigserj.PlaceForm.PlaceFormViewModel.pageCount;


public class MyPlacesViewModel implements BaseViewModel {


    private MyPlacesActivity myPlacesActivity;
    MyPlacesViewModel(MyPlacesActivity myPlacesActivity) {
        this.myPlacesActivity = myPlacesActivity;
        TestApplication.appComponent.inject(this);
    }

    @Inject
    GetSizeDBUseCase getSizeDBUseCase;
    @Inject
    SetArrayLatLonToTheDBWhenDeleteUseCase setArrayLatLonToTheDBWhenDeleteUseCase;

    private MyPlacesRecyclerViewAdapter adapter;

    private RecyclerView recyclerView;

    public enum STATE {ARROW, RECYCLER}
    public ObservableField<STATE> state = new ObservableField<>(STATE.RECYCLER);



    @Override
    public void init() {

        recyclerView = (RecyclerView) myPlacesActivity.findViewById(R.id.recyclerPlaces);
        // тут блок отвечающий за расстановку (форму) на экране
        GridLayoutManager gm = new GridLayoutManager(myPlacesActivity, 1);
        gm.canScrollHorizontally();
        recyclerView.setLayoutManager(gm);
        adapter = new MyPlacesRecyclerViewAdapter(new ArrayList<String>());

    }


    @Override
    public void release() {
    }

    @Override
    public void resume() {

        // считываем из дб и отображаем
        getSizeDBUseCase.execute(null, new DisposableObserver<ArrayList<LatLon>>() {
            @Override
            public void onNext(@NonNull ArrayList<LatLon> latLonArrayList) {

                sheckState(latLonArrayList);

                final ArrayList<LatLon> latLonArrayList2 = new ArrayList<>();
                latLonArrayList2.addAll(latLonArrayList);

                final ArrayList<String> latLonArrayListCities = new ArrayList<>();

                for(int i=0;i<latLonArrayList.size();i++)
                    latLonArrayListCities.add(latLonArrayList.get(i).getCity());

                adapter.swap(latLonArrayListCities);

                adapter.setListener(new MyPlacesRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Integer position) {
                        Log.i("AAA","onBindViewHolder() position = "+position);

                        pageCount = position;
                        Intent intent = new Intent(myPlacesActivity, PlaceFormActivity.class);
                        myPlacesActivity.startActivity(intent);
                    }
                });

                adapter.setDeleteListener(
                        new MyPlacesRecyclerViewAdapter.OnItemClickDeleteListener() {
                    @Override
                    public void onItemClickDelete(Integer pageCount) {
                        Log.i("AAA","Delete = "+pageCount);
                        // удаляем соотв место

                        ArrayList<LatLon> latLons = new ArrayList<>();
                        latLons.addAll(latLonArrayList2);


                        //==================================================================
                        // от текущего до последнего смещаем влево на 1
                        for (int i = pageCount; i < latLons.size() - 1; i++) {
                            latLons.set(i, latLons.get(i + 1));
                            // и понижаем id на 1
                            latLons.get(i).setId(latLons.get(i).getId() - 1);
                        }
                        // последний удаляем
                        latLons.remove(latLons.size() - 1);
                        // и сохраняем весь лист в бд
                        saveAll(latLons);
                        //==================================================================
                        // пересохраняем рабочий массив
                        latLonArrayList2.clear();
                        latLonArrayList2.addAll(latLons);
                        // создаем массив новых item-ов
                        ArrayList<String> latLonArrayListCities2 = new ArrayList<>();
                        for(int i=0;i<latLonArrayList2.size();i++)
                            latLonArrayListCities2.add(latLonArrayList2.get(i).getCity());

                        adapter.swap(latLonArrayListCities2);

                        sheckState(latLonArrayList2);

                    }
                });

                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onError(@NonNull Throwable e) {

            }
            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void pause() {
        getSizeDBUseCase.dispose();
        setArrayLatLonToTheDBWhenDeleteUseCase.dispose();
    }


    public void plusOnePlace(){
        Intent intent = new Intent(myPlacesActivity, PlaceFormActivity.class);
        myPlacesActivity.startActivity(intent);
    }

    private void sheckState(ArrayList<LatLon> latLonArrayList){
        if (!latLonArrayList.isEmpty())
            state.set(STATE.RECYCLER);
        else
            state.set(STATE.ARROW);
    }

    // сохранение всей бд
    private void saveAll(ArrayList<LatLon> fragmentsDelete){
        // и сохраняем весь лист в бд
        setArrayLatLonToTheDBWhenDeleteUseCase.execute(fragmentsDelete,
                new DisposableObserver<Void>() {
                    @Override
                    public void onNext(@NonNull Void aVoid) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
