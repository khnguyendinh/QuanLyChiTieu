package app.lampstudio.com.manager_money;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.lampstudio.com.manager_money.Adapter.StaticDealAdapter;
import app.lampstudio.com.manager_money.database.DataApp;
import app.lampstudio.com.manager_money.database.MyPref;
import app.lampstudio.com.manager_money.database.SqliteDatabase;
import app.lampstudio.com.manager_money.until.NetworkController;

import static app.lampstudio.com.manager_money.Add_transaction.TYPE_DEAL.CHI;
import static app.lampstudio.com.manager_money.Add_transaction.TYPE_DEAL.THU;

public class Statis_deal extends AppCompatActivity implements View.OnClickListener{
    Button btn_Start_date;
    Button btn_End_date;
    Spinner spin_type_money;
    Spinner spin_type_spend;
    RecyclerView recyclerView;
    StaticDealAdapter staticDealAdapter;

    private String[] arraySpinner;
    Calendar mStartDate;
    Calendar mEndDate;
    int id_type_acc_cur = 0;
    Add_transaction.TYPE_DEAL type_spend = THU;
    private AdView mBannerAdView;
    String TAG = "Statis_deal";
    InterstitialAd mInterstitialAd;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statis_deal);
        initView();
    }

    private void initView() {
        mStartDate = Calendar.getInstance();
        mEndDate = Calendar.getInstance();
        btn_Start_date = (Button) findViewById(R.id.btn_start_date);
        btn_End_date = (Button) findViewById(R.id.btn_end_date);
        spin_type_money = (Spinner) findViewById(R.id.spn_type_money);
        spin_type_spend = (Spinner) findViewById(R.id.spn_type_spend);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        btn_Start_date.setOnClickListener(this);
        btn_End_date.setOnClickListener(this);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.statis_deal));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        arraySpinner = new String[DataApp.getInstance().ListTypeAcc.size()];
        for (int i = 0; i < DataApp.getInstance().ListTypeAcc.size(); i++) {
            arraySpinner[i] = DataApp.getInstance().ListTypeAcc.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin_type_money.setAdapter(adapter);
        id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(0).getId();
        spin_type_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(i).getId();
                showListDeal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_type_spend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        type_spend = THU;
                        break;
                    case 1:
                        type_spend = CHI;
                        break;
                }
                showListDeal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ShowDateAndTime();
        showListDeal();
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

    private void showListDeal() {
        SqliteDatabase sqliteDatabase = new SqliteDatabase(Statis_deal.this);
        sqliteDatabase.getDeals(id_type_acc_cur,type_spend.ordinal(),btn_Start_date.getText().toString(),
                btn_End_date.getText().toString());
        staticDealAdapter = new StaticDealAdapter(this,DataApp.getInstance().listModelDeal);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(staticDealAdapter);
    }

    private void ShowDateAndTime() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        btn_Start_date.setText(dateInString);
        btn_End_date.setText(dateInString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_date:
                DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mStartDate.set(Calendar.YEAR, year);
                        mStartDate.set(Calendar.MONTH, monthOfYear);
                        mStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBtnDate();
                    }

                };
                DatePickerDialog pic = new DatePickerDialog(this,dateCallBack, mStartDate.get(Calendar.YEAR),
                        mStartDate.get(Calendar.MONTH), mStartDate.get(Calendar.DATE));
                pic.setTitle(getString(R.string.start_date));
                pic.show();
                break;
            case R.id.btn_end_date:
                DatePickerDialog.OnDateSetListener dateCallBackEnd = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mEndDate.set(Calendar.YEAR, year);
                        mEndDate.set(Calendar.MONTH, monthOfYear);
                        mEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBtnDate();
                    }

                };
                DatePickerDialog picEnd = new DatePickerDialog(this,dateCallBackEnd, mEndDate.get(Calendar.YEAR),
                        mEndDate.get(Calendar.MONTH), mEndDate.get(Calendar.DATE));
                picEnd.setTitle(getString(R.string.end_date));
                picEnd.show();
                break;
        }
    }

    private void updateBtnDate() {
        String pattern = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        btn_Start_date.setText(sdf.format(mStartDate.getTime()));
        btn_End_date.setText(sdf.format(mEndDate.getTime()));
        if(mEndDate.after(mStartDate)){
            showListDeal();
        }else {
            Toast.makeText(this, "Please select end date after start date", Toast.LENGTH_SHORT).show();
        }
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
