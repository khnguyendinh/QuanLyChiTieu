package app.lampstudio.com.manager_money.database;

import android.content.Context;
import android.content.SharedPreferences;

import static app.lampstudio.com.manager_money.Constant.Constant.DATE_ADS;
import static app.lampstudio.com.manager_money.Constant.Constant.NUMBER_FULL_ADS;
import static app.lampstudio.com.manager_money.Constant.Constant.NUMBER_VIDEO_ADS;
import static app.lampstudio.com.manager_money.Constant.Constant.PREF_ADS;

/**
 * Created by VS9 X64Bit on 29/04/2017.
 */

public class MyPref  {
    public static MyPref instance = new MyPref();

    public MyPref() {
    }
    public static MyPref getInstance(){
        return instance;
    }
    public void saveFullAds(int fullAds, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(NUMBER_FULL_ADS, fullAds);
        mEditor.commit();
    }
    public void saveVideoAds(int videoAds, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt(NUMBER_VIDEO_ADS, videoAds);
        mEditor.commit();
    }
    public int getFullAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(NUMBER_FULL_ADS, 0);
    }
    public int getVideoAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        return mSharedPreferences.getInt(NUMBER_VIDEO_ADS, 0);
    }
    public String getDateAds(Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        if(mSharedPreferences != null){
            return mSharedPreferences.getString(DATE_ADS, "");
        }
        return "";
    }

    public void saveDateAds(String date_ads, Context context) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(PREF_ADS, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(DATE_ADS, date_ads);
        mEditor.commit();
    }
}
