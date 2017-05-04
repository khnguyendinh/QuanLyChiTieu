package app.lampstudio.com.manager_money.Model;

/**
 * Created by VS9 X64Bit on 29/04/2017.
 */

public class InforAds {
    int numbanner;
    int numfull;
    int numvideo;

    public InforAds(int numbanner, int numfull, int numvideo) {
        this.numbanner = numbanner;
        this.numfull = numfull;
        this.numvideo = numvideo;
    }

    public int getNumbanner() {
        return numbanner;
    }

    public void setNumbanner(int numbanner) {
        this.numbanner = numbanner;
    }

    public int getNumfull() {
        return numfull;
    }

    public void setNumfull(int numfull) {
        this.numfull = numfull;
    }

    public int getNumvideo() {
        return numvideo;
    }

    public void setNumvideo(int numvideo) {
        this.numvideo = numvideo;
    }
}
