package com.android.bigserj.PlaceForm;


import android.content.Intent;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.bigserj.MyLocality;
import com.android.bigserj.R;
import com.android.bigserj.TestApplication;
import com.android.bigserj.ToastMessage;
import com.android.bigserj.base.BaseViewModel;
import com.android.bigserj.domain.entity.LatLon;
import com.android.bigserj.domain.interaction.DeleteAllDBUseCase;
import com.android.bigserj.domain.interaction.GetLatLonUseCase;
import com.android.bigserj.domain.interaction.GetSizeDBUseCase;
import com.android.bigserj.domain.interaction.SetArrayLatLonToTheDBWheneDeleteUseCase;
import com.android.bigserj.domain.interaction.SetLatLonToTheDBUseCase;
import com.android.bigserj.inLocality.Location1ViewModel;
import com.android.bigserj.myPlaces.MyPlacesActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

import static com.android.bigserj.Constants.*;

public class PlaceFormViewModel implements BaseViewModel {

    public PlaceFormActivity placeFormActivity;

    public PlaceFormViewModel(PlaceFormActivity placeFormActivity) {
        this.placeFormActivity = placeFormActivity;
        TestApplication.appComponent.inject(this);
    }

    @Inject
    SetArrayLatLonToTheDBWheneDeleteUseCase setArrayLatLonToTheDBWheneDeleteUseCase;
    @Inject
    GetLatLonUseCase getLatLonUseCase;
    @Inject
    GetSizeDBUseCase getSizeDBUseCase;
    @Inject
    DeleteAllDBUseCase deleteAllDBUseCase;
    @Inject
    SetLatLonToTheDBUseCase setLatLonToTheDBUseCase;


    // текущая геолокация
    private MyLocality myLocality;

    private PlaceFormMyAdapter adapter;
    private ViewPager pager;

    public static int currentPage=0;
    public static String cityPlaceForm;
    public static String latPlaceForm;
    public static String lonPlaceForm;

    // для хранения фраментов из бд
    ArrayList<LatLon> fragmentsFromDatabase;



    public enum STATE_SEARCH {SEARCH, CLOSE_SEARCH}

    public ObservableField<STATE_SEARCH> state_search = new ObservableField<>(STATE_SEARCH.SEARCH);

    public enum STATE_ADD {ADD, DELETE}

    public ObservableField<STATE_ADD> state_add = new ObservableField<>(STATE_ADD.ADD);

    public enum STATE_SETTINGS {ON, OFF}

    public ObservableField<STATE_SETTINGS> state_settings =
            new ObservableField<>(STATE_SETTINGS.OFF);


    @Override
    public void init() {
    }

    @Override
    public void release() {
        currentPage = 0;
    }

    @Override
    public void resume() {

        // инициализируем объект класса для работы с текущей геолокацией
        myLocality = new MyLocality(placeFormActivity);

        // инициализируем верхний тулбар
        Toolbar toolbarTop = (Toolbar) placeFormActivity.findViewById(R.id.toolbar2);
        placeFormActivity.setSupportActionBar(toolbarTop);
        toolbarTop.setNavigationIcon(R.drawable.ic_view_list_white_24dp);
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeLastFragment();
                state_search.set(STATE_SEARCH.SEARCH);
                Intent intent = new Intent(placeFormActivity, MyPlacesActivity.class);
                placeFormActivity.startActivity(intent);
            }
        });



        // создаем рабочую коллекцию фрагментов пейджера
        final List<Fragment> fragments = new ArrayList<>();
        // в любом случае 1 страница будет
        fragments.add(PlaceFormPageFragment.newInstance(placeFormActivity
                        .getSupportFragmentManager(),
                latPlaceForm, lonPlaceForm, cityPlaceForm));

        // запрашиваем весь список LatLon из бд
        getSizeDBUseCase.execute(null, new DisposableObserver<ArrayList<LatLon>>() {
            @Override
            public void onNext(@NonNull ArrayList<LatLon> arrayOfLatLonDomain) {

                fragmentsFromDatabase = arrayOfLatLonDomain;

//                заполняем массив формами фрагментов в обратном порядке
                for (int i = arrayOfLatLonDomain.size()-1; i >= 0; i--) {
                    fragments.add(PlaceFormPageFragment
                            .newInstance(placeFormActivity.getSupportFragmentManager(),
                                    arrayOfLatLonDomain.get(i).getLat(),
                                    arrayOfLatLonDomain.get(i).getLon(),
                                    arrayOfLatLonDomain.get(i).getCity()));
                }

                // объявляем адаптер для листания фрагментов
                pager = (ViewPager) placeFormActivity.findViewById(R.id.pager);
                adapter = new PlaceFormMyAdapter(placeFormActivity.getSupportFragmentManager());
                adapter.setmFragmentList(fragments);
                adapter.notifyDataSetChanged();
                pager.setAdapter(adapter);
                // считываем текущую страницу
                pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }
                    @Override
                    public void onPageSelected(int position) {
//                        Toast.makeText(placeFormActivity, adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                        currentPage = position;
                    }
                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
            @Override
            public void onError(@NonNull Throwable e) {
                ToastMessage.showToast(placeFormActivity, TOAST_PLACE_DATABASE_BUG);
            }
            @Override
            public void onComplete() {

            }
        });








    }

    @Override
    public void pause() {
        getSizeDBUseCase.dispose();
    }


    // НАЖАТИЕ ПОИСК
    public void inflateSearchFragment() {
        testStateSettings();
        PlaceFormActivity.showFragment(placeFormActivity.getSupportFragmentManager(),
                PlaceFormSearchFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager()),
                true, R.id.containerSearchForm);
        state_search.set(STATE_SEARCH.CLOSE_SEARCH);
    }

    public void closeSearchFragment() {
        closeLastFragment();
        state_search.set(STATE_SEARCH.SEARCH);
    }

    // НАЖАТИЕ ДОБАВИТЬ/УДАЛИТЬ
    public void addFragment() {

////         УДАЛЕНИЕ ВСЕЙ БД (!!!   АККУРАТНО   !!!)
//        deleteAllDBUseCase.execute(null, new DisposableObserver<Void>() {
//            @Override
//            public void onNext(@NonNull Void aVoid) {
//
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                ToastMessage.showToast(placeFormActivity, "ERROR");
//            }
//
//            @Override
//            public void onComplete() {
//                ToastMessage.showToast(placeFormActivity, "OK");
//            }
//        });




        LatLon latLon = new LatLon();
        latLon.setId(fragmentsFromDatabase.size()+1); // добавляем последним в конец массива
        latLon.setCity(cityPlaceForm);
        latLon.setLat(latPlaceForm);
        latLon.setLon(lonPlaceForm);

        // добавляем новое место в бд
        setLatLonToTheDBUseCase.execute(latLon, new DisposableObserver<Void>() {
            @Override
            public void onNext(@NonNull Void aVoid) {

            }
            @Override
            public void onError(@NonNull Throwable e) {
                // грустное уведомление :(
                ToastMessage.showToast(placeFormActivity, TOAST_ADD_DELETE_PLACE_PUSH_MISTAKE);
            }
            @Override
            public void onComplete() {
                // радостное уведомление :)
                ToastMessage.showToast(placeFormActivity, TOAST_ADD_DELETE_PLACE_PUSH);

                // обновляем пейджер
                List<Fragment> fragments = adapter.getmFragmentList();
                fragments.add(PlaceFormPageFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager(),
                                latPlaceForm, lonPlaceForm, cityPlaceForm));
                adapter.setmFragmentList(fragments);
                adapter.notifyDataSetChanged();

                state_add.set(STATE_ADD.DELETE);
            }
        });

    }

    public void deleteFragment() {

        // если в бд хоть что-то есть
        if (fragmentsFromDatabase.size()>1) {

            System.out.println("EEEEE "+fragmentsFromDatabase.size());

            ArrayList<LatLon> fragmentsFromDBTry = fragmentsFromDatabase;

            // перекопируем все по очереди в копии из бд до конца, последнюю затираем
            for (int i=fragmentsFromDBTry.size()-currentPage+1;
                 i<fragmentsFromDBTry.size();i++) {
                fragmentsFromDBTry.get(i).setId(fragmentsFromDBTry.get(i).getId()-1);
            }
            fragmentsFromDBTry.remove(fragmentsFromDBTry.size()-currentPage);

            setArrayLatLonToTheDBWheneDeleteUseCase.execute(fragmentsFromDBTry,
                    new DisposableObserver<Void>() {
                @Override
                public void onNext(@NonNull Void aVoid) {

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    ToastMessage.showToast(placeFormActivity, TOAST_ADD_DELETE_PLACE_PULL_MISTAKE);
                }

                @Override
                public void onComplete() {
                    List<Fragment> latLons = adapter.getmFragmentList();
                    for(int i=1;i<currentPage-1;i++)
                        fragmentsFromDatabase.get(i).setId(fragmentsFromDatabase.get(i).getId()-1);
                    latLons.remove(currentPage-1);
                    adapter.setmFragmentList(latLons);
                    adapter.notifyDataSetChanged();

                    ToastMessage.showToast(placeFormActivity, TOAST_ADD_DELETE_PLACE_PULL);
                }
            });





        }else
            ToastMessage.showToast(placeFormActivity, TOAST_ADD_DELETE_PLACE_PULL_EMPTY);


        state_add.set(STATE_ADD.ADD);
    }

    // НАЖАТИЕ НАСТРОЙКИ
    public void inflateSettingsFragment() {
        testStateSearch();
        PlaceFormActivity.showFragment(placeFormActivity.getSupportFragmentManager(),
                PlaceFormSettingsFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager()),
                true, R.id.containerSettings);
        state_settings.set(STATE_SETTINGS.ON);
    }

    // НАЖАТИЕ СКРЫТЬ НАСТРОЙКИ
    public void deflateSettingsFragment() {
        closeLastFragment();
        state_settings.set(STATE_SETTINGS.OFF);
    }

    // НАЖАТИЕ КНОПКИ ТЕКУЩАЯ ГЕОЛОКАЦИЯ
    public void getMyLocality() {

        Location myLastLocation = myLocality.getLastLocation();
        if (myLastLocation != null) {
            ToastMessage.showToast(placeFormActivity, TOAST_MY_LOCATION_PUSH);
            testStateSettings();
            testStateSearch();

            // заполняем инфой соотв поля нового места
            PlaceFormViewModel.cityPlaceForm = EMPTY_PLACE;
            PlaceFormViewModel.latPlaceForm = String.valueOf(myLastLocation.getLatitude());
            PlaceFormViewModel.lonPlaceForm = String.valueOf(myLastLocation.getLongitude());

        } else {
            ToastMessage.showToast(placeFormActivity, PERMISSION_RATIONAL);
        }
    }


    private void testStateSettings() {
        if (state_settings.get() == STATE_SETTINGS.ON) {
            closeLastFragment();
            state_settings.set(STATE_SETTINGS.OFF);
        }
    }

    private void testStateSearch() {
        if (state_search.get() == STATE_SEARCH.CLOSE_SEARCH) {
            closeLastFragment();
            state_search.set(STATE_SEARCH.SEARCH);
        }
    }


    private void closeLastFragment() {
        try {
            placeFormActivity.getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            Log.e("Exception", "No fragment");
        }
    }


}
