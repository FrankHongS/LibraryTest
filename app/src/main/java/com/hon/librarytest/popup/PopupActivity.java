package com.hon.librarytest.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.MenuPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.hon.librarytest.R;
import com.hon.librarytest.util.PopupUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank_Hon on 10/18/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class PopupActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
    }

    private void test(View view){
        PopupMenu popupMenu=new PopupMenu(this,view);
        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_popup_menu,popupMenu.getMenu());
//        popupMenu.inflate(R.menu.menu_popup_menu);
        popupMenu.show();

    }

    public void onClick(View view){
//        MenuPopupWindow
//        ListPopupWindow
        PopupUtil.showListPopupWindow(this,view);
    }
}
