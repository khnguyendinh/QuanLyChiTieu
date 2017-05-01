package app.lampstudio.com.quanlychitieu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.lampstudio.com.quanlychitieu.Model.ModelDeal;
import app.lampstudio.com.quanlychitieu.database.DataApp;
import app.lampstudio.com.quanlychitieu.database.SqliteDatabase;
import app.lampstudio.com.quanlychitieu.presenter.Presenter_Add_deal;
import app.lampstudio.com.quanlychitieu.view.ViewAddDeal;

public class Add_transaction extends AppCompatActivity implements ViewAddDeal, View.OnClickListener {


    public static enum TYPE_DEAL {THU, CHI} ;
    Spinner spinner;
    RadioButton rd_thu, rd_chi;
    RadioGroup rd_select;
    EditText money;
    EditText content;
    Button btn_date;
    Button btn_time;
    Button btn_save;
    Button btn_save_close;
    SqliteDatabase sqliteDatabase;
    private String[] arraySpinner;
    Calendar mcurrentTime;

    Presenter_Add_deal presenter_add_deal;
    int id_type_acc_cur = 0;
    TYPE_DEAL type_deal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        initView();
    }

    private void initView() {
        spinner = (Spinner) findViewById(R.id.spinner);
        rd_thu = (RadioButton) findViewById(R.id.rd_thu);
        rd_chi = (RadioButton) findViewById(R.id.rd_chi);
        rd_select = (RadioGroup) findViewById(R.id.rd_select);
        money = (EditText) findViewById(R.id.money);
        content = (EditText) findViewById(R.id.content);
        btn_date = (Button) findViewById(R.id.btn_date);
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save_close = (Button) findViewById(R.id.btn_saveAndClose);
        presenter_add_deal = new Presenter_Add_deal(this, this);
        sqliteDatabase = new SqliteDatabase(this);
        money.setText("0");
        btn_save.setOnClickListener(this);
        btn_save_close.setOnClickListener(this);
        ShowTypeAccount();
        ShowTypeSpend();
        ShowDateAndTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mcurrentTime = Calendar.getInstance();

    }

    private void ShowDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        btn_time.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        btn_date.setText(dateInString);
        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
    }

    private void ShowTypeSpend() {
        rd_thu.setChecked(true);
        type_deal = TYPE_DEAL.THU;
        rd_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rd_thu:
                        type_deal = TYPE_DEAL.THU;
                        break;
                    case R.id.rd_chi:
                        type_deal = TYPE_DEAL.CHI;
                        break;
                }
            }
        });
    }

    public void ShowTypeAccount() {
        arraySpinner = new String[DataApp.getInstance().ListTypeAcc.size()];
        for (int i = 0; i < DataApp.getInstance().ListTypeAcc.size(); i++) {
            arraySpinner[i] = DataApp.getInstance().ListTypeAcc.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spinner.setAdapter(adapter);
        id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(0).getId();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(i).getId();
//                id_type_acc_cur = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void AddDealSuccess() {
        Toast.makeText(this, "Add Deal success", Toast.LENGTH_LONG).show();
        money.setText("0");
        content.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_date:
                DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mcurrentTime.set(Calendar.YEAR, year);
                        mcurrentTime.set(Calendar.MONTH, monthOfYear);
                        mcurrentTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBtnDate();
                    }

                };
                DatePickerDialog pic = new DatePickerDialog(this,dateCallBack, mcurrentTime.get(Calendar.YEAR),
                        mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DATE));
                pic.setTitle("Chọn ngày giao dịch");
                pic.show();
                break;
            case R.id.btn_time:
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        btn_time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                break;
            case R.id.btn_save:
                AddDealToDB();
                break;
            case R.id.btn_saveAndClose:
                AddDealToDB();
                Intent intent = new Intent(Add_transaction.this,MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void AddDealToDB() {
        if(Integer.parseInt(money.getText().toString()) == 0){
            Toast.makeText(this, getString(R.string.add_money), Toast.LENGTH_SHORT).show();
            return;
        }
        long sum_money = DataApp.getInstance().hashMap_type_Acc.get(id_type_acc_cur).getSum_money();
        switch (type_deal){
            case CHI:
                sum_money = sum_money - Integer.parseInt(money.getText().toString());
                break;
            case THU:
                sum_money = sum_money + Integer.parseInt(money.getText().toString());
                break;
        }
        DataApp.getInstance().hashMap_type_Acc.get(id_type_acc_cur).setSum_money(sum_money);
        ModelDeal mo1 = new ModelDeal(0,btn_date.getText().toString(),btn_time.getText().toString()
                ,content.getText().toString(),Integer.parseInt(money.getText().toString()),sum_money,
                id_type_acc_cur,type_deal.ordinal());
        presenter_add_deal.AddDeal(mo1);
    }

    private void updateBtnDate() {
        String pattern = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        btn_date.setText(sdf.format(mcurrentTime.getTime()));
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(Add_transaction.this,MainActivity.class);
//        startActivity(intent);
//    }
}
