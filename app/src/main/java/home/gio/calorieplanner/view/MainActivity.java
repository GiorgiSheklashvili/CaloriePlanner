package home.gio.calorieplanner.view;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;


import home.gio.calorieplanner.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //    private TextView txt1;
    private DrawerLayout mDrawerLayout;
    private String[] mNavigationList;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_main_container, mainFragment).addToBackStack(null).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        mNavigationList = getResources().getStringArray(R.array.navigation_array);
//        mDrawerList=(ListView)findViewById(R.id.left_drawer);
//        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mNavigationList));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        mDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close){
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                getSupportActionBar().setTitle(getTitle());
//                invalidateOptionsMenu();
//            }
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                getSupportActionBar().setTitle(getTitle());
//                invalidateOptionsMenu();
//            }
//        };
//        mDrawerLayout.addDrawerListener(mDrawerToggle);

//        txt1=(TextView) findViewById(R.id.textView);
//        Realm realm = Realm.getDefaultInstance();
//        realm.beginTransaction();
//        Product product= realm.createObject(Product.class,"Burger");
//        product.setAlcohol(0);
//        product.setQuantity(1);
//        product.setCarbohydrates(200);
//        product.setProtein(40);
//        product.setFat(10);
//        product.setCalories(product.totalCalories(product.getQuantity()));
//        realm.commitTransaction();
//        RealmQuery<Product> query=realm.where(Product.class);
//        query.equalTo("name","Burger");
//        RealmResults<Product> result1=query.findAll();
//        txt1.setText(result1.get(0).getName());
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int count=getFragmentManager().getBackStackEntryCount();
            if(count!=0)
                getFragmentManager().popBackStack();
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculator) {
            Fragment fragment = new CalorieCalculatorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main_container,fragment).addToBackStack(null).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
