package com.cumonywa.mtaxi.dashboard.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cumonywa.mtaxi.dashboard.R;
import com.cumonywa.mtaxi.dashboard.model.DriverWorking;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DriverWorkingAdapter extends BaseAdapter {


    Context context;
    List<DriverWorking> list;
    TextView  workingDName,workingDPhno,workingDCarType,workingDCarNo;
    List<ImageView> imageViewList=new ArrayList<>();

    public DriverWorkingAdapter(Context context, List<DriverWorking> list) {
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

    static class ViewHolder{

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater IF = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = IF.inflate(R.layout.working_driver_view, null);

        workingDName = convertView.findViewById(R.id.workingDName);
        workingDPhno = convertView.findViewById(R.id.workingDPhno);

        workingDCarNo = convertView.findViewById(R.id.workingDCarno);
        workingDCarType = convertView.findViewById(R.id.workingDcarType);
        final ImageView workingDPhoto=convertView.findViewById(R.id.workingDPhoto);

        workingDName.setText(list.get(position).getDriver().getName());
        workingDPhno.setText(list.get(position).getDriver().getPhone());

        workingDCarNo.setText(list.get(position).getDriver().getCarNo());
        workingDCarType.setText(list.get(position).getDriver().getCarType());



        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference storageReference=storage.getReference("images");
        String imageName=list.get(position).getDriver().getPhone().toString().trim().substring(3)+".jpg";
        StorageReference imgReference=storageReference.child(list.get(position).getDriver().getDriverId()).child(imageName);


        try {

            imgReference.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {


                            Glide.with(context)
                                    .load(uri.toString())
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(workingDPhoto);


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
        return convertView;
    }
}
