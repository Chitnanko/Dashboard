package com.cumonywa.mtaxi.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.cumonywa.mtaxi.dashboard.Adapter.ActiveJobAdapter;
import com.cumonywa.mtaxi.dashboard.Adapter.CustomerAdapter;
import com.cumonywa.mtaxi.dashboard.Adapter.DriverAdapter;
import com.cumonywa.mtaxi.dashboard.model.Driver;
import com.cumonywa.mtaxi.dashboard.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverActivity extends BaseActivity {

    List<Driver> list=new ArrayList<>();
    ListView driverList;
    long childrenCount=0;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users").child("Drivers");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        getSupportActionBar().setTitle("Driver");
        driverList = findViewById(R.id.driverList);

        showProgressDialog();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                childrenCount=dataSnapshot.getChildrenCount();

                for (DataSnapshot entry : dataSnapshot.getChildren()) {
                    Driver driver = entry.getValue(Driver.class);
                    list.add(driver);


                }

                if(list.size()>0 ) {

                    DriverAdapter driverAdapter = new DriverAdapter(getApplicationContext(), list);
                    driverList.setAdapter(driverAdapter);
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hideProgressDialog();

            }
        });


    }
    }
