package com.cumonywa.mtaxi.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cumonywa.mtaxi.dashboard.Adapter.DriverAvailableAdapter;
import com.cumonywa.mtaxi.dashboard.Adapter.DriverWorkingAdapter;
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

public class DriverWorkingActivity  extends BaseActivity{

    List<DriverWorking> list=new ArrayList<>();
    ListView listView;
    long childrenCount=0;
    DatabaseReference driver= FirebaseDatabase.getInstance().getReference("Users").child("Drivers");
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("driversWorking");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverworking);

        listView=findViewById(R.id.driverWorkingList);

        showProgressDialog();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                childrenCount=dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                String driverId=snapshot.getValue(String.class);


                driver.child(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Driver driver = dataSnapshot.getValue(Driver.class);
                        DriverWorking driverWorking=new DriverWorking();
                        driverWorking.setDriver(driver);

                        list.add(driverWorking);

                        if(list.size()>0 && childrenCount==0){

                            DriverWorkingAdapter adapter=new DriverWorkingAdapter( getApplicationContext(),list);
                            listView.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                childrenCount--;
                }


                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
