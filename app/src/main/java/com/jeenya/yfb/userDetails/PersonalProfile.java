package com.jeenya.yfb.userDetails;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeenya.yfb.R;
import com.jeenya.yfb.sharedPreferences.SessionManagement;

import java.util.HashMap;

public class PersonalProfile extends AppCompatActivity {

    Toolbar toolbar_userInfo;
    private static Drawable sBackground;
    TextView label;
    EditText n, p, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_profile);
        label = (TextView) findViewById(R.id.txt_personalProfile_roundBlood);
        n = (EditText) findViewById(R.id.personalProfile_editText_name);
        p = (EditText) findViewById(R.id.personalProfile_editText_ph);
        b = (EditText) findViewById(R.id.personalProfile_editText_blood);

        toolbar_userInfo = (Toolbar) findViewById(R.id.app_bar_userDetails); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_userInfo);  // Setting toolbar as the ActionBar with setSupportActionBar() call


        //UP NAVIGATION
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        sBackground = ElevationBackground.getDrawable(getApplicationContext());
        label.setBackground(sBackground);

        configureUserInfo();

    }

    private void configureUserInfo() {
        HashMap<String, String> pData = SessionManagement.GetPersonalInfo(getApplicationContext());
        n.setText(pData.get("name"));
        b.setText(pData.get("blood"));
        p.setText(pData.get("phone"));
        label.setText(pData.get("blood"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveProfile(View view) {
        String name, phone, blood;
        name = n.getText().toString();
        phone = p.getText().toString();
        blood = b.getText().toString();


        if (name.equals("") || phone.equals("") || blood.equals("")) {
            Toast.makeText(this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
        } else {

            // remove all non-digit characters
            String nonParenthesesNumber = phone.replaceAll("[^0-9]", "");
            Log.d("number", nonParenthesesNumber);

            SessionManagement.SavePersonalInfo(getApplicationContext(), name,nonParenthesesNumber, blood);
            label.setText(blood);

        }


    }
}
