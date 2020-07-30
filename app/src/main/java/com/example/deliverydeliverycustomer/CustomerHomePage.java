package com.example.deliverydeliverycustomer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class CustomerHomePage extends AppCompatActivity {

    //from FM
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;
    FlareBar bottomBar;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home_page);
        flareBar();
    }
    private void flareBar() {
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.plus_50px),"New","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.list_50px),"History","#80DEEA"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.profile_48px),"Expense","#B39DDB"));
        //tabs.add(new Flaretab(getResources().getDrawable(R.drawable.debt),"Debts","#EF9A9A"));
        //tabs.add(new Flaretab(getResources().getDrawable(R.drawable.settingsb),"Credit","#B2DFDB"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(CustomerHomePage.this);
        bottomBar.setTabChangedListener(new TabEventObject.TabChangedListener() {
            @Override
            public void onTabChanged(LinearLayout selectedTab, int selectedIndex, int oldIndex) {
                //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3

                Fragment fragment = null;

                switch (selectedIndex) {
                    case 0:
                        fragment = new AddTaskFragment();
                        break;

                    case 1:
                        fragment = new HistoryFragment();
                        break;

                    case 2:
                        fragment = new ProfileFragment();
                        break;

                    case 3:
                        break;
                    case 4:
                        //fragment = new ProfileFragment();
                        break;
                    case 5:
                        //fragment = new SettingsFragment();
                        break;
                }
                loadFragment(fragment);

            }
        });
        loadFragment(new AddTaskFragment());
    }
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerParent, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}