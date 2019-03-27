package com.hon.optimizedrecyclerviewlib.item;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public interface PageItem {

    int NORMAL_ITEM=0;
    int ERROR_ITEM=1;
    int LOADING_ITEM=2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NORMAL_ITEM,ERROR_ITEM,LOADING_ITEM})
    @interface ItemType{ }

    @ItemType int itemType();
}
