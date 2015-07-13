package com.jeenya.yfb.userDetails;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jeenya.yfb.R;
import com.jeenya.yfb.db.DatabaseHandler;

public class IndividualDetails extends AppCompatActivity {

    private static Drawable sBackground;
    String name, blood, ph, id;
    EditText editName, editPh, editBlood;
    TextView label;
    Toolbar toolbar_userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_details);

        editName = (EditText) findViewById(R.id.editText_name);
        editPh = (EditText) findViewById(R.id.editText_ph);
        editBlood = (EditText) findViewById(R.id.editText_blood);
        //Adding Custom Tool Bar
        toolbar_userInfo = (Toolbar) findViewById(R.id.app_bar_userDetails); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_userInfo);  // Setting toolbar as the ActionBar with setSupportActionBar() call

//UP NAVIGATION
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            //Changing status bar color programatically
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.primaryColorDark));
            }
        } catch (Exception e) {
            Log.d("Status color error", "What a Error");
        }


        label = (TextView) findViewById(R.id.txt);
        //label.setText("Leaks are bad");

        //  if (sBackground == null) {
        //      sBackground = getResources().getDrawable(R.drawable.shape_circle);
        //  }
        sBackground = ElevationBackground.getDrawable(getApplicationContext());

        label.setBackground(sBackground);
        // label.setBackgroundDrawable(sBackground);

        setUpTextView();
    }

    private void setUpTextView() {
        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra("Name");
            ph = intent.getStringExtra("Ph");
            blood = intent.getStringExtra("Blood");
            id = intent.getStringExtra("id");
            try {
                Log.d("BLOOD", blood);
            } catch (Exception c) {
                c.printStackTrace();
            }
        }

        if (name != null) {
            editName.setText(name);
        }
        if (ph != null) {
            editPh.setText(ph);
        }
        if (blood != null) {
            editBlood.setText(blood);
            label.setText(blood);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual_details, menu);
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

    public void update(View view) {


        String n = null, p = null, b = null;
        n = editName.getText().toString();
        p = editPh.getText().toString();
        b = editBlood.getText().toString();

        if (n.equals("") || p.equals("") || b.equals("")) {
            Toast.makeText(this, "Field Missing", Toast.LENGTH_LONG).show();
        } else {
            // remove all non-digit characters
            String nonParenthesesNumber = p.replaceAll("[^0-9]", "");
            Log.d("number", nonParenthesesNumber);
            // USE UPDADATE CODE HERE.........................
            DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
            databaseHandler.updateContact(n, p, b, Integer.parseInt(id));

            editName.setText(n);
            editPh.setText(p);
            editBlood.setText(b);
            label.setText(b);

        }


    }


}
