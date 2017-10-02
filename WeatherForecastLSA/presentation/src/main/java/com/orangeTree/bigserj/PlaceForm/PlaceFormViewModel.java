package com.orangeTree.bigserj.PlaceForm;


import android.content.Intent;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.orangeTree.bigserj.R;
import com.orangeTree.bigserj.MyLocalityAdapter;

import com.orangeTree.bigserj.TestApplication;
import com.orangeTree.bigserj.ToastMessage;
import com.orangeTree.bigserj.base.BaseViewModel;
import com.orangeTree.bigserj.Constants;
import com.orangeTree.bigserj.domain.entity.LatLon;
import com.orangeTree.bigserj.domain.interaction.DeleteAllDBUseCase;
import com.orangeTree.bigserj.domain.interaction.GetLatLonUseCase;
import com.orangeTree.bigserj.domain.interaction.GetSizeDBUseCase;
import com.orangeTree.bigserj.domain.interaction.SetArrayLatLonToTheDBWhenDeleteUseCase;
import com.orangeTree.bigserj.myPlaces.MyPlacesActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


public class PlaceFormViewModel implements BaseViewModel {

    public static String cityPlaceForm;
    public static String latPlaceForm;
    public static String lonPlaceForm;
    public static int pageCount=-1;
    private PlaceFormActivity placeFormActivity;

    PlaceFormViewModel(PlaceFormActivity placeFormActivity) {
        this.placeFormActivity = placeFormActivity;
        TestApplication.appComponent.inject(this);
    }

    @Inject
    SetArrayLatLonToTheDBWhenDeleteUseCase setArrayLatLonToTheDBWhenDeleteUseCase;
    @Inject
    GetLatLonUseCase getLatLonUseCase;
    @Inject
    GetSizeDBUseCase getSizeDBUseCase;
    @Inject
    DeleteAllDBUseCase deleteAllDBUseCase;



    // текущая геолокация
    private MyLocalityAdapter myLocality;
    // пейджер
    private ViewPager pager;
    // адапетр для пейджера
    private PlaceFormMyAdapter adapter;
    // лист с объектами, показывающимися в фрагментах
    private ArrayList<LatLon> fragments2 = new ArrayList<>();


    // для отображение/сокрытия на экране фрагментов
    public enum STATE_SEARCH {
        SEARCH, CLOSE_SEARCH
    }

    public ObservableField<STATE_SEARCH> state_search = new ObservableField<>(STATE_SEARCH.SEARCH);

    public enum STATE_ADD {ADD, DELETE}

    public ObservableField<STATE_ADD> state_add = new ObservableField<>(STATE_ADD.ADD);

    public enum STATE_SETTINGS {ON, OFF}

    public ObservableField<STATE_SETTINGS> state_settings =
            new ObservableField<>(STATE_SETTINGS.OFF);

    PlaceFormPageFragment getCurrentFragment(){
        return (PlaceFormPageFragment)adapter.getItem(pager.getCurrentItem());
    }

    @Override
    public void init() {

        // инициализируем объект класса для работы с текущей геолокацией
        myLocality = new MyLocalityAdapter(placeFormActivity);

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

        // инициализируем адаптер для листания фрагментов
        pager = (ViewPager) placeFormActivity.findViewById(R.id.pager);
        adapter = new PlaceFormMyAdapter(placeFormActivity.getSupportFragmentManager());
    }

    @Override
    public void release() {
    }

    @Override
    public void resume() {

        // считываем состояние первой страницы
        loadFirstPageSharedPreferences();

        // запрашиваем весь список LatLon из бд
        getSizeDBUseCase.execute(null, new DisposableObserver<ArrayList<LatLon>>() {
            @Override
            public void onNext(@NonNull ArrayList<LatLon> arrayOfLatLonDomain) {

                // устанавливаем геолокацию
                if (latPlaceForm==null || lonPlaceForm==null)
                    getMyLocality();

                // заполнение пейджера
                ArrayList<Fragment> fragments = new ArrayList<>();

                // если открываем из поиска фрагмента - очищаем коллекцию фрагментов
                if (!fragments2.isEmpty())
                    fragments2.clear();

                // если заходим из поиска - создаем страницу для поиска
                if (pageCount==-1) {
                    fragments.add(PlaceFormPageFragment
                            .newInstance(placeFormActivity.getSupportFragmentManager(),
                                    -1, latPlaceForm, lonPlaceForm, cityPlaceForm));
                    // добавляем первым в массив LatLon-ов
                    LatLon latLon = new LatLon();
                    latLon.setId(-1);
                    latLon.setCity(cityPlaceForm);
                    latLon.setLat(latPlaceForm);
                    latLon.setLon(lonPlaceForm);
                    fragments2.add(latLon);
                    state_add.set(STATE_ADD.ADD);
                }else{
                    // меняем статус кнопки на "удалить"
                    state_add.set(STATE_ADD.DELETE);
                }
                // заполняем коллекции фрагментов из памяти
                if (!arrayOfLatLonDomain.isEmpty()) {
                    for (int i = 0; i < arrayOfLatLonDomain.size(); i++) {
                        fragments.add(PlaceFormPageFragment
                                .newInstance(placeFormActivity.getSupportFragmentManager(),
                                        arrayOfLatLonDomain.get(i).getId(),
                                        arrayOfLatLonDomain.get(i).getLat(),
                                        arrayOfLatLonDomain.get(i).getLon(),
                                        arrayOfLatLonDomain.get(i).getCity()));
                        fragments2.add(arrayOfLatLonDomain.get(i));
                    }
                }

                // мониторим какой пейдж сейчас на экране
                pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset,
                                               int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (fragments2.get(position).getId() == -1)
                            state_add.set(STATE_ADD.ADD);
                        else
                            // меняем статус кнопки на "удалить"
                            state_add.set(STATE_ADD.DELETE);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });


                // заполняем и обновляем пейджер данными
                adapter.setFragmentList(fragments);
                adapter.notifyDataSetChanged();
                pager.setAdapter(adapter);
                if (pageCount>-1) {
                    pager.setCurrentItem(pageCount);
                    pageCount=-1;
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                ToastMessage.showToast(placeFormActivity, Constants.TOAST_PLACE_DATABASE_BUG);
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
        getLatLonUseCase.dispose();
        deleteAllDBUseCase.dispose();

        // сохраняем состояние первой страницы
        saveFirstPageSharedPreferences();

        fragments2.clear();
    }




//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------


// --------------------------------------------------SAVE/READ SharedPrefs
    private void loadFirstPageSharedPreferences(){

    }
    private void saveFirstPageSharedPreferences(){

    }
// --------------------------------------------------





// --------------------------------------------------ДОБАВИТЬ/УДАЛИТЬ
    // НАЖАТИЕ ДОБАВИТЬ
    public void addFragment() {

        ArrayList<LatLon> fragmentsDelete = new ArrayList<>();

        fragmentsDelete.addAll(fragments2);

        // увеличиваем все id на 1
        for (int i = 0; i < fragmentsDelete.size(); i++)
            fragmentsDelete.get(i).setId(fragmentsDelete.get(i).getId() + 1);


        // и сохраняем весь лист в бд
        saveAll(fragmentsDelete);


        // создаем рабочую коллекцию фрагментов пейджера
        ArrayList<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < fragmentsDelete.size(); i++) {
            fragments.add(PlaceFormPageFragment
                    .newInstance(placeFormActivity.getSupportFragmentManager(),
                            fragmentsDelete.get(i).getId(),
                            fragmentsDelete.get(i).getLat(),
                            fragmentsDelete.get(i).getLon(),
                            fragmentsDelete.get(i).getCity()));
        }

        // обновляем вьюху
        adapter.setFragmentList(fragments);
        adapter.notifyDataSetChanged();
        pager.setAdapter(adapter);

        fragments2.clear();
        fragments2.addAll(fragmentsDelete);

        // меняем статус кнопки на "удалить"
        state_add.set(STATE_ADD.DELETE);

        // выводим сообщение о том что добавили новое место
        ToastMessage.showToast(placeFormActivity, Constants.TOAST_PLACE_ADD_NEW_1+fragments2.get(0).getCity()
                + Constants.TOAST_PLACE_ADD_NEW_2);

    }
    // НАЖАТИЕ УДАЛИТЬ
    public void deleteFragment() {

        ArrayList<LatLon> fragmentsDelete = new ArrayList<>();
//        for (int i = 0; i < fragments2.size(); i++)
//            fragmentsDelete.add(fragments2.get(i));
        fragmentsDelete.addAll(fragments2);


        int pageCount = pager.getCurrentItem();
        if (fragments2.get(0).getId() == -1) {
            fragmentsDelete.remove(0);
            pageCount--;
        }

        // от текущего до последнего смещаем влево на 1
        for (int i = pageCount; i < fragmentsDelete.size() - 1; i++) {
            fragmentsDelete.set(i, fragmentsDelete.get(i + 1));
            // и понижаем id на 1
            fragmentsDelete.get(i).setId(fragmentsDelete.get(i).getId() - 1);
        }

        // последний удаляем
        fragmentsDelete.remove(fragmentsDelete.size() - 1);


        // и сохраняем весь лист в бд
        saveAll(fragmentsDelete);


        ArrayList<LatLon> fragmentsDelete2 = new ArrayList<>();
        for (int i = 0; i < fragmentsDelete.size(); i++)
            fragmentsDelete2.add(fragmentsDelete.get(i));


        if (fragments2.get(0).getId() == -1) {
            fragmentsDelete2.add(fragments2.get(0));
            for (int i = fragmentsDelete2.size() - 1; i >= 1; i--)
                fragmentsDelete2.set(i, fragmentsDelete2.get(i - 1));
            fragmentsDelete2.set(0, fragments2.get(0));
        }

        // создаем промежуточную коллекцию фрагментов пейджера
        ArrayList<Fragment> fragments = new ArrayList<>();

        String deletingName = fragments2.get(pager.getCurrentItem()).getCity();

        fragments2.clear();
        if (!fragmentsDelete2.isEmpty()) {
            for (int i = 0; i < fragmentsDelete2.size(); i++) {
                fragments.add(PlaceFormPageFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager(),
                                fragmentsDelete2.get(i).getId(),
                                fragmentsDelete2.get(i).getLat(),
                                fragmentsDelete2.get(i).getLon(),
                                fragmentsDelete2.get(i).getCity()));
                fragments2.add(fragmentsDelete2.get(i));
            }
        } else {
            fragments.add(PlaceFormPageFragment
                    .newInstance(placeFormActivity.getSupportFragmentManager(),
                            -1, latPlaceForm, lonPlaceForm, cityPlaceForm));
            // добавляем первым в массив LatLon-ов
            LatLon latLon = new LatLon();
            latLon.setId(-1);
            latLon.setCity(cityPlaceForm);
            latLon.setLat(latPlaceForm);
            latLon.setLon(lonPlaceForm);
            fragments2.add(latLon);
        }

        // обновляем вьюху
        adapter.setFragmentList(fragments);
        adapter.notifyDataSetChanged();
        pager.setAdapter(adapter);


        if (fragments2.get(0).getId() == -1)
            state_add.set(STATE_ADD.ADD);

        // выводим сообщение о том что удалили место
        ToastMessage.showToast(placeFormActivity, Constants.TOAST_PLACE_DELETE_1+deletingName+
                Constants.TOAST_PLACE_DELETE_2);

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
// --------------------------------------------------Правка названия города,при поиске по геолокации
    void onChangeCity(String city) {
        cityPlaceForm = city;
        fragments2.get(0).setCity(city);
        saveAll(fragments2);
    }
// --------------------------------------------------



// --------------------------------------------------ПОИСК
    // НАЖАТИЕ ПОИСК
    public void inflateSearchFragment() {

        testStateSettings();
        PlaceFormActivity.showFragment(placeFormActivity.getSupportFragmentManager(),
                PlaceFormSearchFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager()),
                true,  R.id.containerSearchForm);

        state_search.set(STATE_SEARCH.CLOSE_SEARCH);
    }
    // НАЖАТИЕ ЗАКРЫТЬ ПОИСК
    public void closeSearchFragment() {
        closeLastFragment();
        state_search.set(STATE_SEARCH.SEARCH);
    }
    // обработчик нажатия в ПОИСКЕ
    void onTouchSearch() {

        closeLastFragment();
        state_search.set(STATE_SEARCH.SEARCH);
        state_add.set(STATE_ADD.ADD);
    }
// --------------------------------------------------




// --------------------------------------------------НАСТРОЙКИ
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
// --------------------------------------------------










// --------------------------------------------------
    // НАЖАТИЕ КНОПКИ ТЕКУЩАЯ ГЕОЛОКАЦИЯ
    public void getMyLocality() {

        Location myLastLocation = myLocality.getLastLocation();
        if (myLastLocation != null) {

            // заполняем инфой соотв поля нового места
            cityPlaceForm = Constants.EMPTY_PLACE;
            latPlaceForm = String.valueOf(myLastLocation.getLatitude());
            lonPlaceForm = String.valueOf(myLastLocation.getLongitude());

            if (!fragments2.isEmpty()) {
                // если заходим из поиска - создаем страницу для поиска
                ArrayList<Fragment> fragments = new ArrayList<>();
                ArrayList<LatLon> latLons = new ArrayList<>();

                // если небыло -1 пейджа - создаем и помечаем откуда начинать копировать далее
                int ii = 1;
                if (fragments2.get(0).getId() != -1)
                    ii = 0;

                fragments.add(PlaceFormPageFragment
                        .newInstance(placeFormActivity.getSupportFragmentManager(),
                                -1, latPlaceForm, lonPlaceForm, cityPlaceForm));
                // добавляем первым в массив LatLon-ов
                LatLon latLon = new LatLon();
                latLon.setId(-1);
                latLon.setCity(cityPlaceForm);
                latLon.setLat(latPlaceForm);
                latLon.setLon(lonPlaceForm);
                latLons.add(latLon);

                // заполняем коллекции фрагментов из памяти
                for (int i = ii; i < fragments2.size(); i++) {
                    fragments.add(PlaceFormPageFragment
                            .newInstance(placeFormActivity.getSupportFragmentManager(),
                                    fragments2.get(i).getId(),
                                    fragments2.get(i).getLat(),
                                    fragments2.get(i).getLon(),
                                    fragments2.get(i).getCity()));
                    latLons.add(fragments2.get(i));
                }

                fragments2.clear();
                for (int i = 0; i < latLons.size(); i++)
                    fragments2.add(latLons.get(i));


                // заполняем и обновляем пейджер данными
                adapter.setFragmentList(fragments);
                adapter.notifyDataSetChanged();
                pager.setAdapter(adapter);

            }
            // меняем статус кнопки на "удалить"
            state_add.set(STATE_ADD.ADD);
            testStateSettings();
            testStateSearch();
            ToastMessage.showToast(placeFormActivity, Constants.TOAST_MY_LOCATION_PUSH);
        } else
            ToastMessage.showToast(placeFormActivity, Constants.PERMISSION_RATIONAL);
    }
// --------------------------------------------------




    // --------------------------------------------------Управление фрагментами
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
// --------------------------------------------------

}
