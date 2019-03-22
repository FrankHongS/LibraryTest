package com.hon.librarytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hon.librarytest.arouter.ARouterActivity;
import com.hon.librarytest.diffutil.DiffAdapter;
import com.hon.librarytest.diffutil.DiffUtilActivity;
import com.hon.librarytest.disklrucache.DiskLruCacheActivity;
import com.hon.librarytest.eventbus.EventBusActivity;
import com.hon.librarytest.glide.GlideActivity;
import com.hon.librarytest.notificationoreo.NotificationActivity;
import com.hon.librarytest.pagerecyclerview.PageRecyclerViewActivity;
import com.hon.librarytest.photoview.PhotoViewActivity;
import com.hon.librarytest.popup.PopupActivity;
import com.hon.librarytest.swipebacklayout.SwipeBackTestActivity;
import com.hon.librarytest.util.PopupUtil;
import com.hon.librarytest.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank on 2018/3/25.
 * E-mail:frank_hon@foxmail.com
 */

public class MainActivity extends AppCompatActivity{
    private RecyclerView mMainRecyclerView;
    private List<String> mItemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
    }

    private void initData() {
        mItemList.add("Glide");
        mItemList.add("DiskLruCache in OkHttp");
        mItemList.add("EventBus");
        mItemList.add("Swipe Back Layout");
        mItemList.add("Web View");
        mItemList.add("Photo View");
        mItemList.add("Notification Oreo");
        mItemList.add("DiffUtil");
        mItemList.add("ARouter");
        mItemList.add("Popup");
        mItemList.add("Page RecyclerView");
        mItemList.add("DiskLruCache in OkHttp");
        mItemList.add("hi");

    }

    private void initViews() {

        mMainRecyclerView=findViewById(R.id.rv_main);

        RecyclerView.Adapter mainAdapter=new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MainViewHolder(parent);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MainViewHolder viewHolder= (MainViewHolder) holder;
                viewHolder.mTextView.setText(mItemList.get(position));

                viewHolder.mTextView.setOnClickListener(
                        v ->{
                            switch (position){
                                case 0:
                                    startActivity(new Intent(MainActivity.this, GlideActivity.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(MainActivity.this, DiskLruCacheActivity.class));
                                    break;
                                case 2:
                                    startActivity(EventBusActivity.class);
                                    break;
                                case 3:
                                    startActivity(SwipeBackTestActivity.class);
                                    break;
                                case 4:
                                    startActivity(WebViewActivity.class);
                                    break;
                                case 5:
                                    startActivity(PhotoViewActivity.class);
                                    break;
                                case 6:
                                    startActivity(NotificationActivity.class);
                                    break;
                                case 7:
                                    startActivity(DiffUtilActivity.class);
                                    break;
                                case 8:
                                    startActivity(ARouterActivity.class);
                                    break;
                                case 9:
                                    startActivity(PopupActivity.class);
                                    break;
                                case 10:
                                    startActivity(PageRecyclerViewActivity.class);
                                    break;
                                default:
                                    break;
                            }
                        }
                );

                viewHolder.mTextView.setOnLongClickListener(
                        v -> {
//                            PopupMenu popupMenu=new PopupMenu(MainActivity.this,v);
//                            popupMenu.inflate(R.menu.menu_popup_menu);
//                            popupMenu.show();
                            PopupUtil.showListPopupWindow(MainActivity.this,v);
                            return true;
                        }
                );
            }

            @Override
            public int getItemCount() {
                return mItemList.size();
            }
        };

        mMainRecyclerView.setAdapter(mainAdapter);
        mMainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mMainRecyclerView.addItemDecoration(itemDecoration);
    }

    private class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;

        public MainViewHolder(ViewGroup parent) {
            super(getLayoutInflater().inflate(R.layout.main_item_layout,parent,false));

            mTextView=itemView.findViewById(R.id.tv_main);
        }
    }

    private void startActivity(Class<? extends AppCompatActivity> target){
        startActivity(new Intent(MainActivity.this, target));
    }
}
