package ninh.bk.project.graduationproject;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by ninh on 12/03/2015.
 */
public class Item {
    Drawable image;
    String stt;
    String name;
    String status;

    public Item(Drawable image, String stt, String name, String status) {
        super();
        this.image = image;
        this.stt = stt;
        this.name = name;
        this.status = status;
    }

    public Drawable getImage() {
        return image;
    }
    public void setImage(Drawable image) {
        this.image = image;
    }
    public String getStt() {
        return stt;
    }
    public void setStt(String stt) {
        this.stt = stt;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
