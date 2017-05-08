package app.lampstudio.com.manager_money.presenter;

import android.content.Context;

import app.lampstudio.com.manager_money.Model.ModelTypeAcc;
import app.lampstudio.com.manager_money.modelMvp.Model_manager_acc;
import app.lampstudio.com.manager_money.modelMvp.RPListener_Manager_acc;
import app.lampstudio.com.manager_money.view.View_Manager_acc;

/**
 * Created by VS9 X64Bit on 27/04/2017.
 */

public class Presenter_Manager_Acc implements RPListener_Manager_acc{
    Model_manager_acc model_manager_acc;
    View_Manager_acc view_manager_acc;

    public Presenter_Manager_Acc(View_Manager_acc view_manager_acc, Context context) {
        this.view_manager_acc = view_manager_acc;
        model_manager_acc = new Model_manager_acc(this,context);
    }
    public void AddAcc(ModelTypeAcc modelTypeAcc) {
        model_manager_acc.AddAcc(modelTypeAcc);
    }
    public void RemoveAcc(int indexDB,int index) {
        model_manager_acc.RemoveAcc(indexDB,index);
    }
    @Override
    public void RemoveAccSuccess(int index) {
        view_manager_acc.RemoveAccSuccess(index);
    }

    @Override
    public void AddAccSuccess(ModelTypeAcc modelTypeAcc) {
        view_manager_acc.AddAccSuccess(modelTypeAcc);
    }
}
