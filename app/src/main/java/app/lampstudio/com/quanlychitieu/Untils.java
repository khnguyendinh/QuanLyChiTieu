package app.lampstudio.com.quanlychitieu;

import java.util.ArrayList;

import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;

/**
 * Created by VS9 X64Bit on 30/04/2017.
 */

public class Untils {
    public static Untils instance = new Untils();

    public Untils() {
    }

    public static Untils getInstance() {
        return instance;
    }
    public ArrayList<ModelTypeAcc> Clone(ArrayList<ModelTypeAcc> original){
        ArrayList<ModelTypeAcc> clone = new ArrayList<>();
        clone.addAll(original);
        return clone;
    }
}
