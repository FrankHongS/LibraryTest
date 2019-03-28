package com.hon.optimizedrecyclerviewlib;

import com.hon.optimizedrecyclerviewlib.item.PageItem;

/**
 * Created by Frank_Hon on 3/28/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class BottomPageItem implements PageItem {
    @Override
    public int itemType() {
        return PageItem.BOTTOM_ITEM;
    }
}
