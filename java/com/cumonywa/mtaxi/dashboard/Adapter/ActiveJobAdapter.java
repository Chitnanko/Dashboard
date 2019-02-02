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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cumonywa.mtaxi.dashboard.R;
import com.cumonywa.mtaxi.dashboard.model.ActiveJob;
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

import java.util.List;

public class ActiveJobAdapter extends BaseAdapter {
    Context context;
    List<ActiveJob> list;
    Button btnDelete;

    DatabaseReference activeJob= FirebaseDatabase.getInstance().getReference("activeJob");
    DatabaseReference operator=FirebaseDatabase.getInstance().getReference("operator");

    public ActiveJobAdapter(Context context,List<ActiveJob>list){
        this.context=context;
        this.list=list;

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
    public View getView(final int position, View view, ViewGroup parent) {

        if(view==null){
            LayoutInflater IF=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=IF.inflate(R.layout.active_job_view,null);

            TextView txtDName=view.findViewById(R.id.txtDName);
            TextView txtDPhone=view.findViewById(R.id.txtDPhone);
            TextView txtDCarType=view.findViewById(R.id.txtCarType);
            TextView txtCName=view.findViewById(R.id.txtCname);
            TextView txtCPhone=view.findViewById(R.id.txtCPhone);
            final ImageView driverPhoto=view.findViewById(R.id.ImgDPhoto);
            btnDelete=view.findViewById(R.id.btnDelete);


            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Hello",Toast.LENGTH_LONG).show();

                   activeJob.addListenerForSingleValueEvent(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                               String driverId=snapshot.getValue(String.class);
                               String customerId=snapshot.getKey();

                               Log.e("listSize:",list.size()+"////////////////////////////////");

                               if(list.size()>0)
                               if(list.get(position).getDId().equals(driverId)){
                                   snapshot.getRef().removeValue();

                                   operator.child(driverId).setValue(true);
                                   operator.child(customerId).setValue(true);

                                   list.remove(position);
                                   notifyDataSetChanged();
                                   return;
                               }
                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
                }
            });

            Log.e("activeJobData:",list.get(position).getDName()+"$$$$$"+list.get(position).getCName()+"$$");

            txtDName.setText(list.get(position).getDName());
            txtDPhone.setText(list.get(position).getDPhone());
            txtDCarType.setText(list.get(position).getDCarNo());
            txtCName.setText(list.get(position).getCName());
            txtCPhone.setText(list.get(position).getCPhone());

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference("images");
            String imageName = list.get(position).getDPhone().toString().trim().substring(3) + ".jpg";
            StorageReference imgReference = storageReference.child(list.get(position).getDId()).child(imageName);

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
        return view;
    }
}
