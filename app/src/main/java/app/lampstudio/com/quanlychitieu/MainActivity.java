package app.lampstudio.com.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static app.lampstudio.com.quanlychitieu.R.id.btn_add_deal;
import static app.lampstudio.com.quanlychitieu.R.id.btn_manager_acc;
import static app.lampstudio.com.quanlychitieu.R.id.btn_statis_deal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAbout;
    Button btnAddDeal;
    Button btnStatiticDeal;
    Button btnManagerAcc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAbout = (Button) findViewById(R.id.btn_about);
        btnAddDeal = (Button) findViewById(btn_add_deal);
        btnStatiticDeal = (Button) findViewById(btn_statis_deal);
        btnManagerAcc = (Button) findViewById(btn_manager_acc);

        btnAbout.setOnClickListener(this);
        btnAddDeal.setOnClickListener(this);
        btnStatiticDeal.setOnClickListener(this);
        btnManagerAcc.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_about:
                Intent intent = new Intent(MainActivity.this,Add_transaction.class);
                startActivity(intent);
                break;
            case btn_add_deal:
                break;
            case btn_statis_deal:
                break;
            case btn_manager_acc:
                break;
        }
    }
}
