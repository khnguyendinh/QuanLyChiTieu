package app.lampstudio.com.quanlychitieu.database;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import app.lampstudio.com.quanlychitieu.Model.ModelDeal;
import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;

/**
 * Created by VS9 X64Bit on 30/04/2017.
 */

public class DataApp {
    public ArrayList<ModelTypeAcc> ListTypeAcc;
    public ArrayList<ModelDeal> listModelDeal;
    public HashMap<Integer,ModelTypeAcc> hashMap_type_Acc;
    public HashMap<Integer,String> hashMap_type_Deal;
    static DataApp instance = new DataApp();

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
