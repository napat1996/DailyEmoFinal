package com.kmutt.dailyemofinal;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

// list of images
    public int [] lst_image ={
            R.drawable.icon_moon,
            R.drawable.icon_heart,
            R.drawable.icon_step,

    };
// list of titles
//    public String[] lst_title ={
//         "Sleep",
//         "Heart rate",
//         "Step"
//
//    };

    public int[] lst_title_image ={
        R.drawable.Sleep1, R.drawable.hr1, R.drawable.steps1

    };

    // list of description
    public String[] lst_des = {

            "Des1",
            "Des2",
            "Des3"

    };

    public int[] lst_bg = {

            Color.rgb(213, 197, 198),
            Color.rgb(213, 197, 198),
            Color.rgb(213, 197, 198)

    };



  public SlideAdapter (Context context){
      this.context = context;
  }


    @Override
    public int getCount() {
        return  lst_title_image.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       // return super.instantiateItem(container, position);
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_one,container,false);
        LinearLayout layoutslide =  (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
        ImageView titleimg = (ImageView) view.findViewById(R.id.imgtitle);
        TextView txtdes = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_bg[position]);
        imgslide.setImageResource(lst_image[position]);
        titleimg.setImageResource(lst_title_image[position]);

        txtdes.setText(lst_des[position]);
        container.addView(view);
        return view;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

}
