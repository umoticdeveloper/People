package com.umotic.people;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


public class ImageAdapter extends PagerAdapter {

    //Variable definition
    private Context context;
    private int[] imageIDs = new int[] {R.drawable.ic_generic_male, R.drawable.ic_generic_female};

    //Constructors
    ImageAdapter(Context context) {
        this.context = context;
    }


    /**
     * Get the number of images to scroll in the view
     * @return
     */
    @Override
    public int getCount() {
        return imageIDs.length;
    }


    /**
     * Check if the view is of type object
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    /**
     * Set all the parameters for the image view and set the images in the correct positions
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(imageIDs[position]);
        container.addView(imageView, 0);
        return imageView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
