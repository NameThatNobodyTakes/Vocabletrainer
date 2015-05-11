package com.rubengees.vocables.core.mode;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.rubengees.vocables.R;
import com.rubengees.vocables.core.test.Test;
import com.rubengees.vocables.core.testsettings.layout.PairTestSettingsLayout;
import com.rubengees.vocables.core.testsettings.layout.TestSettingsLayout;

/**
 * Created by ruben on 28.04.15.
 */
public class PairMode extends Mode {

    public PairMode(int played, int correct, int incorrect, int perfectInRow, int bestTime, int averageTime) {
        super(played, correct, incorrect, perfectInRow, bestTime, averageTime);
    }

    @Override
    public int getColor(final Context context) {
        return context.getResources().getColor(R.color.pair_mode);
    }

    @Override
    public int getDarkColor(final Context context) {
        return context.getResources().getColor(R.color.pair_mode_dark);
    }

    @Override
    public int getMinAmount() {
        return 5;
    }

    @Override
    public String getHelpText(final Context context) {
        return "Test";
    }

    @Override
    public String getTitle(final Context context) {
        return "Pair Mode";
    }

    @Override
    public String getShortTitle(final Context context) {
        return "Pair";
    }

    @Override
    public Drawable getIcon(final Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_mode_pair);
    }

    @Override
    public TestSettingsLayout getTestSettingsLayout(Context context, TestSettingsLayout.OnTestSettingsListener listener) {
        return new PairTestSettingsLayout(context, listener);
    }

    @Override
    public Test getTest() {
        return null;
    }
}