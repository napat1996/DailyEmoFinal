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
            R.drawable.carr,

    };
// list of titles
//    public String[] lst_title ={
//         "Sleep",
//         "Heart rate",
//         "Step"
//
//    };

    public int[] lst_title_image ={
        R.drawable.sleep1, R.drawable.hr1, R.drawable.steps1, R.drawable.trafficc

    };


    public String[] lst_title ={

            // suggest- sleep
            "\n"+
            "\t\t\t\t\t\t\t\t\t Sufficient sleep is important for your \n" +
                    "\t\t\t\t\t health, well-being and happiness. When you \n"+
                    "\t\t\t\t\t sleep better ,you feel better. If you are still \n" +
                    "\t\t\t\t\t having trouble sleeping try to do some  \n"+
                    "\t\t\t\t\t suggestions : \n" +

                 "\n" +
                    "\t\t\t\t\t Tip 1: Exercise during the day which is a \n" +
                    "\t\t\t\t\t great sleep. Stress reliever and has been \n" +
                    "\t\t\t\t\t shown to improve the quality of sleep.\n" +
                 "\n" +

                    "\t\t\t\t\t Tip 2: Eat a healthy, balanced diet: Eating a \n" +
                    "\t\t\t\t\t varied diet rich in fruits, vegetables, lean \n" +
                    "\t\t\t\t\t proteins, nuts, and legumes can help to \n" +
                    "\t\t\t\t\t improve the overall health. \n",

            //suggest-hr
            "\n"+
            "\t\t\t\t\t\t\t\t\t A normal resting heart rate is between \n" +
                    "\t\t\t\t\t 60 and 100 beats per minute. By doing these \n"+
                    "\t\t\t\t\t suggestion, you can start to lower your \n"+
                    "\t\t\t\t\t resting heart rate and also help maintain \n"+
                    "\t\t\t\t\t a healthy heart: \n" +
            "\n"+
                    "\t\t\t\t\t Tip 1: Avoid tobacco products. \n"+
                    "\t\t\t\t\t Tip 2: Take up yoga or meditation. \n"+
                    "\t\t\t\t\t Tip 3: Get your potassium.  \n"+
                    "\t\t\t\t\t Tip 4: Increase your physical activity.  \n"+
                    "\t\t\t\t\t Tip 5: Laugh out loud. \n"+
                    "\t\t\t\t\t Tip 6: Let the music move you. \n"+
                    "\t\t\t\t\t Tip 7: Get a good night’s sleep. \n",

        // suggest-step
            "\n"+
            "\t\t\t\t\t\t\t\t\t Walking  can help reduce anxiety and \n" +
                    "\t\t\t\t\t gives you time to think, as well as time to get\n" +
                    "\t\t\t\t\t away from stress. Getting out of the stressful\n" +
                    "\t\t\t\t\t environment, breathing the air, and feeling \n" +
                    "\t\t\t\t\t your body move is natural stress-relief. \n" +
                    "\t\t\t\t\t Try walking outside for 20 -30 minutes \n" +
                    "\t\t\t\t\t several times per week to alleviate stress \n" +
                    "\t\t\t\t\t and give your mind a boost. Whether it is\n" +
                    "\t\t\t\t\t slow stroll in the park with friends or a brisk \n" +
                    "\t\t\t\t\t power-walk around the neighborhood ,make  \n" +
                    "\t\t\t\t\t walking a part of your daily routine to reduce \n"+
                    "\t\t\t\t\t tension and promote feelings of calm.\n",

            // suggest-traffic
            "\t\t\t\t\t\t\t\t\t Driving can be an incredibly stressful \n" +
                    "\t\t\t\t\t activity,especially when you're dealing with \n" +
                    "\t\t\t\t\t stop and go highway traffic or navigating  \n" +
                    "\t\t\t\t\t chaotic city streets. Here are some key tips \n" +
                    "\t\t\t\t\t to help you manage stress while driving: \n" +
                    "\n"+
                    "\t\t\t\t\t Tip 1: Learn the traffic patterns.\n" +
                    "\t\t\t\t\t Tip 2: Always have an alternate plan. \n" +
                    "\t\t\t\t\t Tip 3: Find enjoyable activities to pass \n" +
                    "\t\t\t\t\t the time. \n" +
                    "\t\t\t\t\t Tip 4: De-stress with a sounds and thoughts \n" +
                    "\t\t\t\t\t Meditation. \n" +
                    "\t\t\t\t\t Tip 5: Listen to music. \n" +
                    "\t\t\t\t\t Tip 6: Keep away from aggressive drivers.\n" +
                    "\t\t\t\t\t Tip 7: Have a break, have a snack.\n",

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
