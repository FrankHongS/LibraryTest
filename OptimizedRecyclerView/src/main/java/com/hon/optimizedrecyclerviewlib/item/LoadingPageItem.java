package com.hon.optimizedrecyclerviewlib.item;

import com.hon.optimizedrecyclerviewlib.item.PageItem;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class LoadingPageItem implements PageItem {
    @Override
    public int itemType() {
        return PageItem.LOADING_ITEM;
    }
}
