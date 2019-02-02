package com.cumonywa.mtaxi.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.cumonywa.mtaxi.dashboard.Adapter.CustomerAdapter;
import com.cumonywa.mtaxi.dashboard.model.Customer;
import com.cumonywa.mtaxi.dashboard.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends BaseActivity {

    List<User> list=new ArrayList<>();
    ListView customerListView;
    long childrenCount=0;
    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users").child("Customers");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getSupportActionBar().setTitle("Customer");
        customerListView=findViewById(R.id.customerList);

        showProgressDialog();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot entry:dataSnapshot.getChildren()){
                    User customer=entry.getValue(User.class);

                    list.add(customer);

                }

                if(list.size()>0) {
                    CustomerAdapter customerAdapter = new CustomerAdapter(getApplicationContext(), list);
                    customerListView.setAdapter(customerAdapter);
                }

                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}
