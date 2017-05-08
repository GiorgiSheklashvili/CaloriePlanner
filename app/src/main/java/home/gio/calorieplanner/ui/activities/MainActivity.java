package home.gio.calorieplanner.ui.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;



import home.gio.calorieplanner.R;
import home.gio.calorieplanner.ui.fragments.CalorieCalculatorFragment;
import home.gio.calorieplanner.ui.fragments.MainFragment;
import home.gio.calorieplanner.ui.fragments.PersonsListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main_container, mainFragment).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }



    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();
            if (count != 0)
                getFragmentManager().popBackStack();
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.main) {
            Fragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).addToBackStack(null).commit();
        }
        if (id == R.id.nav_calculator) {
            Fragment fragment = new CalorieCalculatorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).addToBackStack(null).commit();
        }
        if (id == R.id.persons_list) {
            Fragment fragment = new PersonsListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, fragment).addToBackStack(null).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
