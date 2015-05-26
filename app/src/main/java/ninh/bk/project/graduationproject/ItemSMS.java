package ninh.bk.project.graduationproject;

/**
 * Created by Nguyen Ke Ninh on 5/26/2015.
 */
public class ItemSMS {
    String address;
    String time;
    String body;
    String process;

    public ItemSMS(String address, String time, String body, String process) {
        this.address = address;
        this.time = time;
        this.body = body;
        this.process = process;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }

    public String getProcess() {
        return process;
    }
    public void setProcess(String process) {
        this.process = process;
    }
}