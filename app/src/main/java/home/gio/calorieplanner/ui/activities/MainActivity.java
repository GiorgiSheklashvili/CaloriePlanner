package home.gio.calorieplanner.ui.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import home.gio.calorieplanner.R;
import home.gio.calorieplanner.ui.fragments.CalorieCalculatorFragment;
import home.gio.calorieplanner.ui.fragments.MainFragment;
import home.gio.calorieplanner.ui.fragments.PersonsListFragment;
import home.gio.calorieplanner.ui.fragments.ShoppingListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container, mainFragment, "Main").commit();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_icon:
                Fragment fragment = new ShoppingListFragment();
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "ShoppingList").addToBackStack(fragment.getClass().getName()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_icons, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Fragment mainFragment = getSupportFragmentManager().findFragmentByTag("Main");
            if (getSupportFragmentManager().findFragmentByTag("PersonsList") != null || getSupportFragmentManager().findFragmentByTag("Calculator") != null || getSupportFragmentManager().findFragmentByTag("ShoppingList") != null || (getSupportFragmentManager().findFragmentByTag("GroceriesViewpager") != null && getSupportFragmentManager().findFragmentByTag("GroceriesViewpager").isVisible())) {
                Fragment frag = getSupportFragmentManager().findFragmentByTag("PersonsList");
                if (frag != null && frag.isVisible()) {
                    while (mainFragment != null) {
                        getSupportFragmentManager().popBackStackImmediate();
                        if (mainFragment.isVisible()) {
                            break;
                        }
                    }
                }
                frag = getSupportFragmentManager().findFragmentByTag("Calculator");
                if (frag != null && frag.isVisible()) {
                    while (mainFragment != null) {
                        getSupportFragmentManager().popBackStackImmediate();
                        if (mainFragment.isVisible()) {
                            break;
                        }
                    }
                }
                frag = getSupportFragmentManager().findFragmentByTag("ShoppingList");
                if (frag != null && frag.isVisible()) {
                    while (mainFragment != null) {
                        getSupportFragmentManager().popBackStackImmediate();
                        if (mainFragment.isVisible()) {
                            break;
                        }
                    }
                }
                frag = getSupportFragmentManager().findFragmentByTag("GroceriesViewpager");
                if (frag != null && frag.isVisible()) {
                    while (mainFragment != null) {
                        getSupportFragmentManager().popBackStackImmediate();
                        if (mainFragment.isVisible()) {
                            break;
                        }
                    }
                }
            } else {
                if (count != 0)
                    getSupportFragmentManager().popBackStack();
                else {
                    finish();
                }
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.main) {
            Fragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "Main").commit();
        }
        if (id == R.id.nav_calculator) {
            Fragment fragment = new CalorieCalculatorFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "Calculator").addToBackStack(fragment.getClass().getName()).commit();
        }
        if (id == R.id.persons_list) {
            Fragment fragment = new PersonsListFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "PersonsList").addToBackStack(fragment.getClass().getName()).commit();
        }
        if (id == R.id.shopping_list) {
            Fragment fragment = new ShoppingListFragment();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter,R.anim.exit,R.anim.pop_enter,R.anim.pop_exit).replace(R.id.fragment_main_container, fragment, "ShoppingList").addToBackStack(fragment.getClass().getName()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
