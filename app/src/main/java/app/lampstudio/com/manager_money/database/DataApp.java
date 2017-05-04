package app.lampstudio.com.manager_money.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import app.lampstudio.com.manager_money.Model.InforAds;
import app.lampstudio.com.manager_money.Model.ModelDeal;
import app.lampstudio.com.manager_money.Model.ModelTypeAcc;

/**
 * Created by VS9 X64Bit on 30/04/2017.
 */

public class DataApp {
    public ArrayList<ModelTypeAcc> ListTypeAcc;
    public ArrayList<ModelDeal> listModelDeal;
    public HashMap<Integer,ModelTypeAcc> hashMap_type_Acc;
    public HashMap<Integer,String> hashMap_type_Deal;
    static DataApp instance = new DataApp();
    public static InforAds inforAds = new InforAds(5000,7,3);
    public int num_Full_Ads = 0;
    public int num_Video_Ads = 0;
    public String date_Ads ;
    public boolean justShowVideo = false;

    DataApp() {
        Log.d("CRETAE", "DataApp: ");
        ListTypeAcc = new ArrayList<>();
        listModelDeal = new ArrayList<>();
        hashMap_type_Acc = new HashMap<Integer,ModelTypeAcc>();
        hashMap_type_Deal = new HashMap<Integer,String>();
        hashMap_type_Deal.put(0,"Thu");
        hashMap_type_Deal.put(1,"Chi");
    }

    public static DataApp getInstance() {
        return instance;
    }

}
