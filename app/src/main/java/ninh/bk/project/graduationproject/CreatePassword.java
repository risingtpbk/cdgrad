package ninh.bk.project.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nguyen Ke Ninh on 5/27/2015.
 */
public class CreatePassword extends ActionBarActivity{

    EditText password;
    EditText retype;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String pass = "pass";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createpass);

        password = (EditText)findViewById(R.id.password);
        retype = (EditText)findViewById(R.id.retype);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("tempallow", "");
        editor.commit();
    }

    public void create(View v)
    {
        Log.e("PASS", password.getText().toString());
        Log.e("PASS", retype.getText().toString());

        if(!password.getText().toString().equals(retype.getText().toString()))
            Toast.makeText(this, "Two fields didn't match", Toast.LENGTH_LONG).show();
        else
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("pass", password.getText().toString());
            editor.commit();
            Toast.makeText(this, "Create password successful", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {

        if(!sharedpreferences.contains("pass"))
        {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        else
            super.onBackPressed();
    }
}
