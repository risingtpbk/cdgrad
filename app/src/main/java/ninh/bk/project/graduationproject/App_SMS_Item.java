package ninh.bk.project.graduationproject;

/**
 * Created by Nguyen Ke Ninh on 5/26/2015.
 */
public class App_SMS_Item {

    String name;
    int count;

    public App_SMS_Item(String name, int count) {
        super();
        this.name = name;
        this.count = count;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
