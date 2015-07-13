package com.jeenya.yfb.webConnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jeenya.yfb.R;

import java.util.ArrayList;

public class SearchDonar extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar_userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donar);
        configureToolBar();
        configureSpinner();

    }

    private void configureSpinner() {
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Select Blood Group");
        categories.add("AB-");
        categories.add("AB+");
        categories.add("B-");
        categories.add("B+");
        categories.add("A-");
        categories.add("A+");
        categories.add("O-");
        categories.add("O+");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    private void configureToolBar() {
        //Adding Custom Tool Bar
        toolbar_userInfo = (Toolbar) findViewById(R.id.app_bar_userDetails); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_userInfo);  // Setting toolbar as the ActionBar with setSupportActionBar() call
        //configureNavigationDrawer(toolbar_userInfo);

        //UP NAVIGATION
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_donar, menu);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
