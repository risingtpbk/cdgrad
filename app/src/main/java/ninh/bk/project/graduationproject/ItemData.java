package ninh.bk.project.graduationproject;

import android.graphics.drawable.Drawable;

/**
 * Created by Nguyen Ke Ninh on 5/20/2015.
 */
public class ItemData {
    String name;
    Double sent;
    Double received;

    public ItemData(String name, Double sent, Double received) {
        super();
        this.name = name;
        this.sent = sent;
        this.received = received;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Double getSent() {
        return sent;
    }
    public void setSent(Double sent) {
        this.sent = sent;
    }

    public Double getReceived() {
        return received;
    }
    public void setReceived(Double received) {
        this.received = received;
    }
}
