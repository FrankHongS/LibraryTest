package com.hon.librarytest.paging;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import com.hon.librarytest.AppExecutors;

/**
 * Created by Frank_Hon on 9/21/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class CheeseViewModel extends AndroidViewModel{

    private final CheeseDao dao=CheeseDb.getInstance().cheeseDao();

    private static final int PAGE_SIZE=30;
    private static final boolean ENABLE_PLACEHOLDERS=true;

//    private final LiveData<PagedList<Cheese>> allCheeses;

    public CheeseViewModel(@NonNull Application application) {
        super(application);

//        allCheeses=dao.allCheeseByName().create(0,
//                new PagedList.Config.Builder()
//        .setPageSize(PAGE_SIZE)
//        .setEnablePlaceholders(ENABLE_PLACEHOLDERS)
//        .build());
    }

//    public LiveData<PagedList<Cheese>> getAllCheeses() {
//        return allCheeses;
//    }

    public void insert(String text){
        AppExecutors.getInstance().getIoExecutors().execute(
                ()->dao.insert(new Cheese(text))
        );
    }

    public void remove(Cheese cheese){
        AppExecutors.getInstance().getIoExecutors().execute(
                ()->dao.delete(cheese)
        );
    }
}
