package com.cumonywa.mtaxi.dashboard.Adapter;

import android.content.Context;
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
import com.cumonywa.mtaxi.dashboard.model.DriverAvailable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DriverAvailableAdapter extends BaseAdapter {
    Context context;
    List<Driver> list;
    TextView dName;
    TextView dPhone;
    TextView dCarType;
    TextView dCarNo;


    public DriverAvailableAdapter(Context context, List<Driver> list) {
        this.context = context;
        this.list = list;
    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater IF = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = IF.inflate(R.layout.available_driver_view, null);
            dName = convertView.findViewById(R.id.availableDName);
            dPhone = convertView.findViewById(R.id.availableDPhone);

            dCarType = convertView.findViewById(R.id.availaleDCarType);
            dCarNo = convertView.findViewById(R.id.availableDCarNo);

            final ImageView driverPhoto = convertView.findViewById(R.id.availableDPhoto);


            Log.e("driverAvailble:", list.size()+"$$$$$$$$$$$$$$$$$$$$$"+list.get(position).getName());

            dName.setText(list.get(position).getName());
            dPhone.setText(list.get(position).getPhone());

            dCarNo.setText(list.get(position).getCarNo());
            dCarType.setText(list.get(position).getCarType());


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("images");
            String imageName = list.get(position).getPhone().toString().trim().substring(3) + ".jpg";
            StorageReference imgReference = storageReference.child(list.get(position).getDriverId()).child(imageName);


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
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
        }



        return convertView;
    }
}
