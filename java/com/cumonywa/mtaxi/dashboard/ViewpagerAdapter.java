package com.cumonywa.mtaxi.dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewpagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images={R.mipmap.customer,R.mipmap.dblogo,R.mipmap.ic_launcher_round};
    public ViewpagerAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.activity_view_pager,null);
        ImageView imageView=view.findViewById(R.id.imgPager);
        imageView.setImageResource(images[position]);
        ViewPager pager= (ViewPager) container;
        pager.addView(view,0);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager pager= (ViewPager) container;
        View view= (View) object;
        pager.removeView(view);
    }
}
