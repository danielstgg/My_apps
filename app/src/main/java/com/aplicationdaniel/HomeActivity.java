package com.aplicationdaniel;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.aplicationdaniel.Adapter.CustomExpandablelistAdapter;
import com.aplicationdaniel.Helper.FragmentNavigationManager;
import com.aplicationdaniel.Intervace.NavigationManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Init View
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = (ExpandableListView)findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getmInstance(this);


        intitItems();

        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setupDrawer();

        if (savedInstanceState == null)
            selectFirstItemAsDefault();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Daniel App");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if (navigationManager != null)
        {
            String firstItem = lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }
    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Daniel Dev");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
    mDrawerToggle.setDrawerIndicatorEnabled(true);
    mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawersItem() {
        adapter = new CustomExpandablelistAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString()); //Set title for toolbar when open menu
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("Daniel DEV");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //Change fragment when click on item
                String selectedItem = ((List)(lstChild.get(lstTitle.get(groupPosition))))
                        .get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);

                if (items[0].equals(lstTitle.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
                else
                    throw new IllegalArgumentException("Not supported fragment");

                mDrawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });

    }

    private void genData() {

        List<String> title = Arrays.asList("Home", "Daily Activity", "Gallery","Music","Profile");
        List<String> childitem0 = Arrays.asList("Profile","Interest");
        List<String> childitem1 = Arrays.asList("Daily Activity","Friend List");
        List<String> childitem2 = Arrays.asList("");
        List<String> childitem3 = Arrays.asList("Daily Activity","Friend List");
        List<String> childitem4 = Arrays.asList("Profile","Contact","Find Me", "About");




        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),childitem0);
        lstChild.put(title.get(1),childitem1);
        lstChild.put(title.get(2),childitem2);
        lstChild.put(title.get(3),childitem3);
        lstChild.put(title.get(4),childitem4);

        lstTitle = new ArrayList<>(lstChild.keySet());
    }

    private void intitItems() {

        items = new String[]{"Home", "Daily Activity", "Gallery","Music","Profile"};

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
