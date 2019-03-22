package com.hon.librarytest.pagerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hon.librarytest.R;

/**
 * Created by Frank_Hon on 3/22/2019.
 * E-mail: v-shhong@microsoft.com
 */
public class ItemViewHolder extends BaseViewHolder<String> {

    private TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);

        textView=itemView.findViewById(R.id.tv_text);
    }

    @Override
    public void bindView(String item) {
        textView.setText(item);
    }
}
