package app.lampstudio.com.manager_money;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import app.lampstudio.com.manager_money.Adapter.AdpterAcc_ManagerAcc;
import app.lampstudio.com.manager_money.Constant.Constant;
import app.lampstudio.com.manager_money.Model.ModelTypeAcc;
import app.lampstudio.com.manager_money.database.DataApp;
import app.lampstudio.com.manager_money.database.MyPref;
import app.lampstudio.com.manager_money.database.SqliteDatabase;
import app.lampstudio.com.manager_money.presenter.Presenter_Manager_Acc;
import app.lampstudio.com.manager_money.until.NetworkController;
import app.lampstudio.com.manager_money.view.View_Manager_acc;

public class Manager_acc extends AppCompatActivity implements View_Manager_acc{
    SqliteDatabase sqliteDatabase;
    ListView listView;
    ArrayList<ModelTypeAcc> arrayList;
    AdpterAcc_ManagerAcc adapterTypeAccount;
    EditText type_acc_add;
    EditText type_acc_money;
    Button btn_add;
    Presenter_Manager_Acc presenter_manager_acc;
    private AdView mBannerAdView;
    String TAG = "Manager_acc";
    InterstitialAd mInterstitialAd;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter_manager_acc = new Presenter_Manager_Acc(this,this);
        setContentView(R.layout.activity_manager_acc);
        sqliteDatabase = new SqliteDatabase(Manager_acc.this);
        listView = (ListView) findViewById(R.id.list_acc);
        arrayList = new ArrayList<>();
        type_acc_add = (EditText) findViewById(R.id.type_acc_add);
        type_acc_money = (EditText) findViewById(R.id.type_acc_money);
        btn_add = (Button) findViewById(R.id.btn_add);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(getString(R.string.manager_acc));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ShowDataAccount();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cotent = type_acc_add.getText().toString();
                long money = Long.parseLong(type_acc_money.getText().toString());
                ModelTypeAcc modelTypeAcc = new ModelTypeAcc(1,cotent,money);
                presenter_manager_acc.AddAcc(modelTypeAcc);
            }
        });

        mBannerAdView = (AdView) findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(this.getString(R.string.id_full_admob));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                showFullAds();
            }
        });
        showAds();
    }
    private void showAds() {
        loadFullAd();
    }
    private void showFullAds() {
        Log.d(TAG, "showFullAds: num_Full_Ads "+DataApp.getInstance().num_Full_Ads+" getNumfull "+
                DataApp.getInstance().inforAds.getNumfull());
        if (DataApp.getInstance().num_Full_Ads < DataApp.getInstance().inforAds.getNumfull()) {
            DataApp.getInstance().num_Full_Ads++;
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }
    private void loadFullAd() {
        if (NetworkController.isNetworkAvailable(this)) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                    .build();
            mInterstitialAd.loadAd(adRequest);
        }
    }
    private void loadAdView(AdView mBannerAdView) {
        Log.e(TAG, "==========> load Adview");
        mBannerAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.loadAd(adRequest);
    }
    public void ShowDataAccount(){
        Cursor cursor = sqliteDatabase.Rawquery(Constant.selectAllAcc);
        if(cursor != null && cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(cursor.getColumnIndex(Constant.columID));
                String name = cursor.getString(cursor.getColumnIndex(Constant.columName_Account));
                long sum_money = cursor.getLong(cursor.getColumnIndex(Constant.columMoney_Account));
                ModelTypeAcc item = new ModelTypeAcc(ID,name,sum_money);
                arrayList.add(item);
            }
            while(cursor.moveToNext());
        }
        adapterTypeAccount = new AdpterAcc_ManagerAcc(Manager_acc.this,arrayList,presenter_manager_acc);
        listView.setAdapter(adapterTypeAccount);
    }

    @Override
    public void RemoveAccSuccess(int index) {
        arrayList.remove(index);
        adapterTypeAccount.notifyDataSetChanged();
    }

    @Override
    public void AddAccSuccess(ModelTypeAcc modelTypeAcc) {
        arrayList.add(modelTypeAcc);
        adapterTypeAccount.notifyDataSetChanged();
        type_acc_add.setText("");
        type_acc_money.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkController.isNetworkAvailable(this)) {
            loadAdView(mBannerAdView);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        MyPref.getInstance().saveFullAds(DataApp.getInstance().num_Full_Ads,this);
        MyPref.getInstance().saveVideoAds(DataApp.getInstance().num_Video_Ads,this);
    }
}
