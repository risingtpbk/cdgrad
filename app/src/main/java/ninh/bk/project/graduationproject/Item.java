package ninh.bk.project.graduationproject;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by ninh on 12/03/2015.
 */
public class Item {

    Drawable image;
    String name;
    String apppackage;
    String status;

    public Item(String name, String apppackage, Drawable image, String status) {
        super();
        this.name = name;
        this.image = image;
        this.apppackage = apppackage;
        this.status = status;
    }
//
    public Drawable getImage() {
        return image;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }
//    public String getStt() {
//        return stt;
//    }
//    public void setStt(String stt) {
//        this.stt = stt;
//    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getApppackage() {
        return apppackage;
    }
    public void setApppackage(String apppackage) {
        this.apppackage = apppackage;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
