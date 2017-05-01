package app.lampstudio.com.quanlychitieu;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.lampstudio.com.quanlychitieu.Adapter.AdapterTypeAccount;
import app.lampstudio.com.quanlychitieu.Constant.Constant;
import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;
import app.lampstudio.com.quanlychitieu.database.DataApp;
import app.lampstudio.com.quanlychitieu.database.SqliteDatabase;

import static app.lampstudio.com.quanlychitieu.R.id.btn_add_deal;
import static app.lampstudio.com.quanlychitieu.R.id.btn_manager_acc;
import static app.lampstudio.com.quanlychitieu.R.id.btn_statis_deal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAbout;
    Button btnAddDeal;
    Button btnStatiticDeal;
    Button btnManagerAcc;
    Intent intent;
    String TAG = "MainActivity";
    SqliteDatabase sqliteDatabase;
    ListView listView;
    AdapterTypeAccount adapterTypeAccount;
    public ArrayList<ModelTypeAcc> myArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAbout = (Button) findViewById(R.id.btn_about);
        btnAddDeal = (Button) findViewById(btn_add_deal);
        btnStatiticDeal = (Button) findViewById(btn_statis_deal);
        btnManagerAcc = (Button) findViewById(btn_manager_acc);
        listView = (ListView) findViewById(R.id.list_acc);
        myArrayList = new ArrayList<>();

        btnAbout.setOnClickListener(this);
        btnAddDeal.setOnClickListener(this);
        btnStatiticDeal.setOnClickListener(this);
        btnManagerAcc.setOnClickListener(this);
        sqliteDatabase = new SqliteDatabase(MainActivity.this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_about:
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_infor_app);
                dialog.setTitle(R.string.title_dialog);
                TextView textView = (TextView) dialog.findViewById(android.R.id.title);
                if(textView != null) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                TextView name = (TextView) dialog.findViewById(R.id.name_app);
                name.setText(Html.fromHtml("<b>App name</b> : Manager money"));
                TextView version = (TextView) dialog.findViewById(R.id.version_app);
                version.setText(Html.fromHtml("<b>Version</b> : 1.0"));
                TextView dev = (TextView) dialog.findViewById(R.id.dev_app);
                dev.setText(Html.fromHtml("<b>Developer</b> : Nguyễn Đình Khoa"));
                TextView mail = (TextView) dialog.findViewById(R.id.mail_dev);
                mail.setText(Html.fromHtml("<b>Email Support</b> : khnguyendinh@gmail.com"));
                Button btn_close = (Button) dialog.findViewById(R.id.btn_close);
                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case btn_add_deal:
                intent = new Intent(MainActivity.this,Add_transaction.class);
                startActivity(intent);
                break;
            case btn_statis_deal:
                intent = new Intent(MainActivity.this,Statis_deal.class);
                startActivity(intent);
                break;
            case btn_manager_acc:
                intent = new Intent(MainActivity.this,Manager_acc.class);
                startActivity(intent);
                break;
        }
    }
    public void ShowDataAccount(){
        DataApp.getInstance().ListTypeAcc.clear();
        DataApp.getInstance().hashMap_type_Acc.clear();
        Cursor cursor = sqliteDatabase.Rawquery(Constant.selectAllAcc);
        long sum_sum = 0;
        if(cursor != null && cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(cursor.getColumnIndex(Constant.columID));
                String name = cursor.getString(cursor.getColumnIndex(Constant.columName_Account));
                long sum_money = cursor.getLong(cursor.getColumnIndex(Constant.columMoney_Account));
                ModelTypeAcc item = new ModelTypeAcc(ID,name,sum_money);
                sum_sum += sum_money;
                DataApp.getInstance().ListTypeAcc.add(item);
                DataApp.getInstance().hashMap_type_Acc.put(item.getId(),item);
            }
            while(cursor.moveToNext());
        }
        myArrayList = Untils.getInstance().Clone(DataApp.getInstance().ListTypeAcc);

        ModelTypeAcc item = new ModelTypeAcc(10,getResources().getString(R.string.sum_money),sum_sum);
        myArrayList.add(item);
        adapterTypeAccount = new AdapterTypeAccount(MainActivity.this,myArrayList);
        listView.setAdapter(adapterTypeAccount);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShowDataAccount();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
