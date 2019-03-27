package com.hon.librarytest.pagerecyclerview;

import com.hon.optimizedrecyclerviewlib.item.PageItem;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class NormalItem implements PageItem {

    private String text;

    public NormalItem(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int itemType() {
        return PageItem.NORMAL_ITEM;
    }
}
