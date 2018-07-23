package com.storyview.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;


import com.storyview.R;
import com.storyview.Model.Contact;
import com.storyview.Database.DatabaseHandler;
import com.storyview.Lisener.onClickStatus;
import com.storyview.Adapter.DataBaseAdapter;

import java.util.Collections;
import java.util.List;


public class StatusLisActivity extends AppCompatActivity implements onClickStatus {


    private RecyclerView recyclerView;
    private DataBaseAdapter mAdapter;
    DatabaseHandler db;
    Button btnDelete, btnUpdate;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        db = new DatabaseHandler(this);


        init();
        setOnclick();

    }

    private void setOnclick() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent ii = new Intent(StatusLisActivity.this,CreateStatus.class);
                startActivity(ii);
            }
        });

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        fab = findViewById(R.id.fab);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Contact> contacts = db.getAllContacts();
        if(contacts.size()>0){

            Collections.reverse(contacts);
            mAdapter = new DataBaseAdapter(StatusLisActivity.this, contacts,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }



    }

    @Override
    public void onClickStatusRow(int requestId) {
        Intent i = new Intent(StatusLisActivity.this, ViewFlipperSampleActivity.class);
        i.putExtra("position",requestId);
        startActivity(i);
    }
}
