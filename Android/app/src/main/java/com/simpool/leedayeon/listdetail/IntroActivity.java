package com.simpool.leedayeon.listdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int colorPrimary;
        int colorPrimaryDark;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colorPrimary = getColor(R.color.colorPrimary);
            colorPrimaryDark = getColor(R.color.colorPrimary);
        }
        else {
            colorPrimary = getResources().getColor(R.color.colorPrimary);
            colorPrimaryDark = getResources().getColor(R.color.colorPrimaryDark);
        }
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro1_title), getString(R.string.intro1_desc),
                R.drawable.intro1,
                Color.parseColor("#ffffff"), colorPrimary, colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(getString(R.string.intro2_title), getString(R.string.intro2_desc),
                R.drawable.intro1,
                Color.parseColor("#ffffff"), colorPrimary, colorPrimaryDark));

        setSkipTextTypeface(getResources().getString(R.string.skip));
        setColorSkipButton(Color.parseColor("#000000"));
        setDoneTextTypeface(getResources().getString(R.string.done));
        setColorDoneText(Color.parseColor("#000000"));
        setNextArrowColor(Color.parseColor("#000000"));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
