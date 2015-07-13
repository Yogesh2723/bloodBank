package com.jeenya.yfb;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jeenya.yfb.accessContacts.ContactsProvider;
import com.jeenya.yfb.accessContacts.IndividualContactInfo;
import com.jeenya.yfb.db.DatabaseHandler;
import com.jeenya.yfb.db.UserDataFromDb;
import com.jeenya.yfb.expandableRecyclerList.ExpandableExampleActivity;
import com.jeenya.yfb.listView.CustomListAdapter;
import com.jeenya.yfb.sharedPreferences.SessionManagement;
import com.jeenya.yfb.userDetails.IndividualDetails;
import com.jeenya.yfb.userDetails.PersonalProfile;
import com.jeenya.yfb.webConnection.SyncWithServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements CustomListAdapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {
    private String contactID;
    private ArrayList<IndividualContactInfo> contactList;
    private ArrayList<UserDataFromDb> contactListFromDb;
    private RecyclerView viewAsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CustomListAdapter customListAdapter;
    Toolbar toolbar_userInfo;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView navHearderBlood, navHearderNAme, navHearderPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureRecyclerView();
        configureToolBar();
        configureSwipeToRefresh();
        configureNavHeader();
    }

    private void configureNavHeader() {
        navHearderNAme = (TextView) findViewById(R.id.txt_header_name);
        navHearderBlood = (TextView) findViewById(R.id.txt_header_blood);
        navHearderPhone = (TextView) findViewById(R.id.txt_header_phone);

        HashMap<String, String> pData = SessionManagement.GetPersonalInfo(getApplicationContext());
        navHearderNAme.setText(pData.get("name"));
        navHearderBlood.setText(pData.get("blood"));
        navHearderPhone.setText(pData.get("phone"));

    }

    private void configureSwipeToRefresh() {
        //PULL TO REFRESH
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void getContact() {
        contactList = new ContactsProvider(getApplicationContext()).getContactInfo(getContentResolver());
        Iterator itr = contactList.iterator();
        if (contactList != null && !contactList.isEmpty()) {
            while (itr.hasNext()) {
                IndividualContactInfo st = (IndividualContactInfo) itr.next();
                Log.d("NAME", st.getContactName());
                Log.d("N0.", st.getPhoneNumber());
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.phoneToDb(st.getContactName(), st.getPhoneNumber(), "N/A");
            }
        }

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

    }


    private void configureRecyclerView() {
        viewAsList = (RecyclerView) findViewById(R.id.recycler_contactList);
        viewAsList.setLayoutManager(new LinearLayoutManager(this));
        customListAdapter = new CustomListAdapter(getApplicationContext());
        viewAsList.setAdapter(customListAdapter);
        contactListFromDb = getContactsFromDb();
        customListAdapter.setContactData(contactListFromDb);
        customListAdapter.setClickListener(this);//For Item Click of RecyclerView


    }

    private ArrayList<UserDataFromDb> getContactsFromDb() {
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        return db.retriveUserData();
    }

    @Override
    public void itemClicked(View view, int position, ArrayList<UserDataFromDb> data) {

        UserDataFromDb currentUser = data.get(position);
        String userName = currentUser.getName();
        String userPh = currentUser.getPhNumber();
        String userBlood = currentUser.getBloodGroup();
        String userId = String.valueOf(currentUser.getId());

        Intent i = new Intent(this, IndividualDetails.class);
        if (userName != null && !userName.isEmpty()) {
            i.putExtra("Name", userName);
        }
        if (userPh != null && !userPh.isEmpty()) {
            i.putExtra("Ph", userPh);
        }
        if (userBlood != null && !userBlood.isEmpty()) {
            i.putExtra("Blood", userBlood);
        }
        i.putExtra("id", userId);

        startActivity(i);
    }


    private void configureToolBar() {
        //Adding Custom Tool Bar
        toolbar_userInfo = (Toolbar) findViewById(R.id.app_bar_userDetails); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar_userInfo);  // Setting toolbar as the ActionBar with setSupportActionBar() call
        configureNavigationDrawer(toolbar_userInfo);

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
    }

    private void configureNavigationDrawer(Toolbar toolbar_userInfo) {

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_userInfo, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

    }


    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        getContact();
        configureRecyclerView();

    }

    public void setUpProfile(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), PersonalProfile.class);
        startActivity(i);
    }

    public void hit(MenuItem item) {

        new SyncWithServer().contactSync();
    }

    public void searchBlood(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), ExpandableExampleActivity.class);
        startActivity(i);
    }
}
