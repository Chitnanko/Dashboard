package com.cumonywa.mtaxi.dashboard;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListView;

import com.cumonywa.mtaxi.dashboard.Adapter.ActiveJobAdapter;
import com.cumonywa.mtaxi.dashboard.Adapter.CustomerAdapter;
import com.cumonywa.mtaxi.dashboard.model.ActiveJob;
import com.cumonywa.mtaxi.dashboard.model.Driver;
import com.cumonywa.mtaxi.dashboard.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActiveJobAcivity extends BaseActivity {

    ListView mylist;
    List<ActiveJob> list=new ArrayList<>();

    long childrenCount=0;

    DatabaseReference userDb= FirebaseDatabase.getInstance().getReference("Users").child("Customers");
    DatabaseReference activeJob=FirebaseDatabase.getInstance().getReference("activeJob");
    DatabaseReference driverDb=FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activejob);
        mylist=findViewById(R.id.activeJobList);

        showProgressDialog();
        activeJob.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                childrenCount=dataSnapshot.getChildrenCount();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    final String user=snapshot.getKey();
                    String driverId=snapshot.getValue(String.class);

                    final ActiveJob activeJobData=new ActiveJob();

                    driverDb.child(driverId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot driverDataSnap) {

                            Driver driver=driverDataSnap.getValue(Driver.class);
                            activeJobData.setDName(driver.getName());
                            activeJobData.setDPhone(driver.getPhone());
                            activeJobData.setDCarNo(driver.getCarNo());
                            activeJobData.setDId(driver.getDriverId());

                            userDb.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    User user1=dataSnapshot.getValue(User.class);
                                    activeJobData.setCName(user1.getUser_name());
                                    activeJobData.setCPhone(user1.getPhone());

                                    list.add(activeJobData);

                                    if(list.size()>0 && childrenCount==0){

                                        ActiveJobAdapter customerAdapter=new ActiveJobAdapter(getApplicationContext(),list);
                                        mylist.setAdapter(customerAdapter);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


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
