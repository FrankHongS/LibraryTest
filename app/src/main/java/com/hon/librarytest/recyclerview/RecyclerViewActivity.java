package com.hon.librarytest.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.hon.librarytest.R;

import java.util.Arrays;

/**
 * Created by Frank_Hon on 9/30/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class RecyclerViewActivity extends AppCompatActivity{

    private RecyclerView recyclerView;

    private TestAdapter testAdapter;

    private String[] mData={
            "Ross","Joey","Reachel","Chandler","Phoebe","Monica"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
    }

    private void initView() {
        recyclerView=findViewById(R.id.rv_test);
        testAdapter=new TestAdapter(this, Arrays.asList(mData));
//        testAdapter.notifyItemRangeChanged();
//        testAdapter.notifyItemInserted();
    }
}
