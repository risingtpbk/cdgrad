package ninh.bk.project.graduationproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nguyen Ke Ninh on 5/27/2015.
 */
public class ChangePassword extends ActionBarActivity{

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    EditText curpass;
    EditText newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        this.getWindow().setAttributes(params);

        curpass = (EditText)findViewById(R.id.curpass);
        newpass = (EditText)findViewById(R.id.newpass);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void change(View v)
    {
        if(!curpass.getText().toString().equals(sharedpreferences.getString("pass", "")))
            Toast.makeText(this, "Current password was wrong", Toast.LENGTH_LONG).show();
        else
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("pass", newpass.getText().toString());
            editor.commit();
            Toast.makeText(this, "Change password successful", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

    }
}
