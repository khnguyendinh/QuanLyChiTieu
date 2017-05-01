package app.lampstudio.com.quanlychitieu.presenter;

import android.content.Context;

import app.lampstudio.com.quanlychitieu.Model.ModelDeal;
import app.lampstudio.com.quanlychitieu.modelMvp.Model_add_deals;
import app.lampstudio.com.quanlychitieu.modelMvp.RPListener_add_deal;
import app.lampstudio.com.quanlychitieu.view.ViewAddDeal;

/**
 * Created by VS9 X64Bit on 27/04/2017.
 */

public class Presenter_Add_deal implements RPListener_add_deal{
    Model_add_deals model_add_deals;
    ViewAddDeal viewAddDeal;

    public Presenter_Add_deal(ViewAddDeal viewAddDeal, Context context) {
        this.viewAddDeal = viewAddDeal;
        model_add_deals = new Model_add_deals(this,context);
    }
    public void AddDeal(ModelDeal modelDeal) {
        model_add_deals.AddDeal(modelDeal);
        model_add_deals.UpdateMoneyAcc(modelDeal.getType_acc_deal(),modelDeal.getBalance());
    }

    @Override
    public void AddDealSuccess() {
        viewAddDeal.AddDealSuccess();
    }
}
