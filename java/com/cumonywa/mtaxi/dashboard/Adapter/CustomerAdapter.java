package com.cumonywa.mtaxi.dashboard.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cumonywa.mtaxi.dashboard.R;
import com.cumonywa.mtaxi.dashboard.model.Customer;
import com.cumonywa.mtaxi.dashboard.model.User;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    Context context;
    List<User> list;

    public CustomerAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){
            LayoutInflater IF=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=IF.inflate(R.layout.customer_list_view,null);

            TextView customerName=view.findViewById(R.id.customerName);
            TextView customerPhone=view.findViewById(R.id.customerPhone);

            TextView customerGmail=view.findViewById(R.id.customerGmail);
            customerName.setText(list.get(i).getUser_name());
            customerPhone.setText(list.get(i).getPhone());
            customerGmail.setText(list.get(i).getGmail());
        }

        return view;
    }
}
