package com.kmutt.dailyemofinal;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import static android.support.v4.graphics.drawable.IconCompat.getResources;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    String str;

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
        R.drawable.sleep1, R.drawable.hr1, R.drawable.steps1

    };


    public String[] lst_title ={
         "Sufficient sleep is important for your health, well-being and happiness. \n" +
          "When you sleep better, you feel better. If you’re still having trouble sleeping, \n" +
          "try to do some suggestions : \n" +
                 "\n" +
                 "Tip 1 : Exercise during the day which is a great stress reliever and has been \n" +
                 "\t\t shown to improve the quality of sleep, particularly for insomniacs.\n" +
                 "\n" +
                 "Tip 2 : Be smart about what you eat and drink. Your daytime eating habits \n" +
                 "\t\tplay a role in how well you sleep. Try to make dinnertime earlier in \n" +
                 "\t\tthe evening, and avoid heavy, rich foods within two hours of bed. \n" +
                 "\t\tMoreover, avoid alcohol, caffeine, and nicotine before bed.   \n" +
                 "\n" +
                 "Tip 3 : Wind down and clear your head. Your body needs time to shift into \n" +
                 "\t\tsleep mode, so spend the last hour before bed doing a calming \n" +
                 "\t\tactivity such as reading, and avoid using an electronic device. ",




            "Breathe in for 5-8 seconds, hold that breath for 3-5 seconds, then exhale \n" +
                    "slowly. Repeat several times. Raising your aortic pressure in this way will \n" +
                    "lower your heart rate.",




         "\t\tWalking  can help reduce anxiety and gives you time to think, as well as \n" +
                 "time to get away from stressors. Getting out of the stressful environment, \n" +
                 "breathing the air, and feeling your body move is natural stress-relief.  \n" +
                 "Try walking outside for 20 -30 minutes several times per week to alleviate\n" +
                 "stress and give your mind a boost. Whether it’s a slow stroll in the park with\n" +
                 "friends or a brisk power-walk around the neighborhood, make walking a \n" +
                 "part of your daily routine to reduce tension and promote feelings of calm."

    };

   // String str = this.context.getResources().getString(R.string.des_sleep);
//    // list of description
//    public String[] lst_des = {
//        this.context.getResources().getString(R.string.des_sleep),
//
//
//    };


//    public int[] lst_des_image ={
//            R.drawable.tt
//            , R.drawable.ww
//            , R.drawable.takeawalk1
//
//    };

    public int[] lst_bg = {

            Color.rgb(240, 240, 240),
            Color.rgb(240, 240, 240),
            Color.rgb(240, 240, 240)

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
        View view = inflater.inflate(R.layout.suggestion_one,container,false);
        LinearLayout layoutslide =  (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
        ImageView titleimg = (ImageView) view.findViewById(R.id.imgtitle);
        //ImageView txtdes = (ImageView) view.findViewById(R.id.imgdes);

        TextView txtdes = (TextView)  view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_bg[position]);
        imgslide.setImageResource(lst_image[position]);
        titleimg.setImageResource(lst_title_image[position]);

        txtdes.setText(lst_title[position]);
       //txtdes.setImageResource(lst_des_image[position]);
        container.addView(view);
        return view;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

}
