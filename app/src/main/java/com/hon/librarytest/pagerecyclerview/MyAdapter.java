package com.hon.librarytest.pagerecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.librarytest.R;
import com.hon.optimizedrecyclerviewlib.BasePageViewHolder;
import com.hon.optimizedrecyclerviewlib.PageAdapter;
import com.hon.optimizedrecyclerviewlib.item.PageItem;

import java.util.List;

/**
 * Created by Frank_Hon on 3/27/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class MyAdapter extends PageAdapter {
    public MyAdapter(Context context, List<PageItem> itemList) {
        super(context, itemList);
    }

    @Override
    protected BasePageViewHolder<PageItem> createNormalViewHolder(ViewGroup parent) {
        return new NormalViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_page_recycler,parent,false));
    }

    static class NormalViewHolder extends BasePageViewHolder<PageItem>{

        private TextView textView;

        NormalViewHolder(View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.tv_text);
        }

        @Override
        public void bindView(PageItem item) {
            NormalItem normalItem= (NormalItem) item;
            textView.setText(normalItem.getText());
        }
    }
}
