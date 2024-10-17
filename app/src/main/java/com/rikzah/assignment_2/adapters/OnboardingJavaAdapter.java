package com.rikzah.assignment_2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.rikzah.assignment_2.Activities.ScreenItem;
import com.rikzah.assignment_2.R;

import java.util.List;
public class OnboardingJavaAdapter extends PagerAdapter {
        Context mContext ; List<ScreenItem> mListScreen;
        public OnboardingJavaAdapter (Context mContext, List<ScreenItem> mListScreen) {
            this.mContext = mContext;
            this.mListScreen = mListScreen;}
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layoutScreen = inflater.inflate(R.layout.intro_slider_layout,null);
            ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
            TextView title = layoutScreen.findViewById(R.id.intro_title);
            TextView description = layoutScreen.findViewById(R.id.intro_description);
            title.setText(mListScreen.get(position).getTitle());
            description.setText(mListScreen.get(position).getDescription());
            imgSlide.setImageResource(mListScreen.get(position).getScreenImg());
            container.addView(layoutScreen);
            return layoutScreen;}

        @Override
        public int getCount() {
            return mListScreen.size();
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);}}

