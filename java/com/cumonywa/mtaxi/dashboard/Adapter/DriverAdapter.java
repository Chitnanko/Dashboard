package com.cumonywa.mtaxi.dashboard.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestOptions;
import com.cumonywa.mtaxi.dashboard.R;
import com.cumonywa.mtaxi.dashboard.model.Driver;
import com.cumonywa.mtaxi.dashboard.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.cumonywa.mtaxi.dashboard.R.drawable.bstyle_enable;

public class DriverAdapter extends BaseAdapter {

    public DriverAdapter(Context context, List<Driver> list) {
        this.context = context;
        this.list = list;
    }

    Context context;
    List<Driver> list;
    TextView dName;
    TextView dPhone;
    TextView dCarType;
    TextView dCarNo;
    Button btn_enable_disable;


    DatabaseReference driverDb=FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater IF = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = IF.inflate(R.layout.driver_list_view, null);

            dName = convertView.findViewById(R.id.driverName);
            dPhone = convertView.findViewById(R.id.driverPhone);

            dCarType = convertView.findViewById(R.id.driverCarType);
            dCarNo = convertView.findViewById(R.id.driverCarNo);

            btn_enable_disable=convertView.findViewById(R.id.btn_enable_diable);
            final ImageView driverPhoto=convertView.findViewById(R.id.driverPhoto);


            btn_enable_disable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            Button btn=(Button) v;

                            Driver driver=list.get(position);
                            String st=btn.getText().toString().trim();
                            if(st.equals("Enable")){

                                try {
                                    driverDb.child(list.get(position).getDriverId()).child("active").setValue(true);
                                    btn.setText("Disable");
                                    btn.setBackgroundResource(R.drawable.bstyle_disable);
                                }catch (Exception e){

                                }


                            }else if(st.equals("Disable")){

                                try {
                                    driverDb.child(list.get(position).getDriverId()).child("active").setValue(false);
                                    btn.setText("Enable");
                                    btn.setBackgroundResource(R.drawable.bstyle_enable);
                                }catch (Exception e){

                                }

                            }
                        }


            });


            if(list.get(position).isActive()){

                btn_enable_disable.setText("Disable");
                btn_enable_disable.setBackgroundResource(R.drawable.bstyle_disable);
            }else {
                btn_enable_disable.setText("Enable");
                btn_enable_disable.setBackgroundResource(R.drawable.bstyle_enable);
            }

            dName.setText(list.get(position).getName());
            dPhone.setText(list.get(position).getPhone());
            dCarNo.setText(list.get(position).getCarNo());
            dCarType.setText(list.get(position).getCarType());

            FirebaseStorage storage=FirebaseStorage.getInstance();
            StorageReference storageReference=storage.getReference("images");
            String imageName=list.get(position).getPhone().toString().trim().substring(3)+".jpg";
            final StorageReference imgReference=storageReference.child(list.get(position).getDriverId()).child(imageName);

            //Toast.makeText(getContext(),driver.getName().toString()+driver.getPhone().toString(),Toast.LENGTH_LONG).show();


            try {

                imgReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                    Glide.with(context)
                                            .load(uri.toString())
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(driverPhoto);


                                Log.e("error", "photo downloaded/////////////////////////////");

                                //hideProgressDialog();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("error message", e.getMessage());

                        //Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.e("error", e.getMessage() + "/////////////////////////////////////");
                        //hideProgressDialog();
                    }
                });
            }catch (Exception e){
                Log.e("error",e.getMessage());
            }

        }
        return convertView;
    }
}
