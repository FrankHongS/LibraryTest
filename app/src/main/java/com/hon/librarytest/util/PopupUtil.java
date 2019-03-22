package com.hon.librarytest.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import com.hon.librarytest.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank_Hon on 10/18/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class PopupUtil {

    private static String[] data={"Monica","Chandler","Joey"};

    public static void showListPopupWindow(final Context context, View view){
        List<String> list= Arrays.asList(data);
        ListPopupWindow popupWindow=new ListPopupWindow(context);
        popupWindow.setAnchorView(view);
        popupWindow.setWidth(Util.dip2px(150));
        popupWindow.setHorizontalOffset(-view.getWidth()/2);
        popupWindow.setVerticalOffset(-view.getHeight()/2);
        popupWindow.setModal(true);
        popupWindow.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if(convertView==null){
                    view= LayoutInflater.from(context).inflate(R.layout.item_popup,parent,false);
                }else{
                    view=convertView;
                }

                ImageView imageView=view.findViewById(R.id.iv_icon);
                TextView textView=view.findViewById(R.id.tv_text);

                imageView.setImageResource(R.drawable.ic_file_download_black_24dp);
                textView.setText(list.get(position));
                textView.setOnClickListener(
                        v->ToastUtil.showToast("position: "+position)
                );

                return view;
            }
        });
        popupWindow.show();
    }
}
