package com.cumonywa.mtaxi.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.cumonywa.mtaxi.dashboard.Adapter.DriverAvailableAdapter;
import com.cumonywa.mtaxi.dashboard.model.Driver;
import com.cumonywa.mtaxi.dashboard.model.DriverAvailable;
import com.cumonywa.mtaxi.dashboard.model.DriverWorking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DriverAvailableActivity extends BaseActivity{
    ListView listView;
    long childrenCount=0;

    List<Driver> list=new ArrayList<>();
    DatabaseReference driver=FirebaseDatabase.getInstance().getReference("Users").child("Drivers");
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("driversAvailable");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driveravailable);
        listView=findViewById(R.id.driverAvailableList);

        showProgressDialog();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                childrenCount=dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                String driverId=snapshot.getKey();

                driver.child(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Driver driverData = dataSnapshot.getValue(Driver.class);
                        //driverAvailable.setDriver(driver);

                        Log.e("driverData:",driverData.getName()+"$$$$$$$$$$$$$$$$$$$$$$444");

                            list.add(driverData);

                        if(list.size()>0 && childrenCount==0){
                            DriverAvailableAdapter adapter = new DriverAvailableAdapter(getApplicationContext(), list);
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    hideProgressDialog();
                    }
                });

                childrenCount--;
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
