package co.edu.udea.rappigarage_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import co.edu.udea.rappigarage_android.Home.Home;
import co.edu.udea.rappigarage_android.Notifications.Notifications;
import co.edu.udea.rappigarage_android.Product.Publish.ProductFormActivity;
import co.edu.udea.rappigarage_android.User.Favorites;
import co.edu.udea.rappigarage_android.User.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }




    public  void initializeViews(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intentProductForm = new Intent(this, ProductFormActivity.class);
        fragmentManager = getSupportFragmentManager();
        fragment = new Home();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentMain, fragment);
        transaction.commit();


        menu = (BottomNavigationView) findViewById(R.id.menu);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.publichProduct:
                        startActivity(intentProductForm);
                        break;
                    case R.id.home:
                        item.setChecked(true);
                        fragment = new Home();
                        break;
                    case R.id.notification:
                        item.setChecked(true);
                        fragment = new Notifications();
                        break;
                    case R.id.favorites:
                        item.setChecked(true);
                        fragment = new Favorites();
                        break;
                    case R.id.profile:
                        item.setChecked(true);
                        fragment = new Profile();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragmentMain, fragment);
                transaction.commit();
                return  false;
            }
        });
    }



}
