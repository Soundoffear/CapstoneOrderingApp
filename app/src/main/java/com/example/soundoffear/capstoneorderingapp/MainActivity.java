package com.example.soundoffear.capstoneorderingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.soundoffear.capstoneorderingapp.databases.FavoritesDatabase;
import com.example.soundoffear.capstoneorderingapp.fragments.CouponsAndPromosFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.DeliveryAddressEntryFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.FavoritesFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.MainPageFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.OrderHistoryFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.OrderStatusFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.RedeemRewardsFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.SettingsFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.UserDataOutputFragment;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.UserDataModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.example.soundoffear.capstoneorderingapp.widget.FavoritesWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String USER_DATA_BUNDLE = "user_data_bundle";

    public static final String DATA_FAV = "start_fav_fragment";

    public static final String SAVING_FRAGMENT = "saving_fragment_state";

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private String intentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawerLayout = findViewById(R.id.nav_drawer_layout);
        Log.d("TEST TEST TEST", "===============================================");

        FavoritesDatabase favoritesDatabase = new FavoritesDatabase(getApplicationContext());
        favoritesDatabase.deleteAll();

        if(FavoritesFragment.isAddingFav) {
            FavoritesFragment.isAddingFav = false;
        } else {
            updateFavorites();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(savedInstanceState != null) {
            Fragment mFragment = fragmentManager.getFragment(savedInstanceState, SAVING_FRAGMENT);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, mFragment).commit();
        } else {
            Intent intent = getIntent();

            if (intent != null) {
                intentString = intent.getStringExtra(DATA_FAV);
                if (!TextUtils.isEmpty(intentString)) {
                    if (intentString.equals(Constants.DATABASE_FAVORITES)) {
                        fragmentTransaction.add(R.id.main_frameLayout, new FavoritesFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } else {
                    fragmentTransaction.add(R.id.main_frameLayout, new MainPageFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        }

        navigationView = findViewById(R.id.main_navigationView);
        setSelectedNavDrawerItem(R.id.main_screen);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);

                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.main_screen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new MainPageFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("New Order");
                        break;
                    case R.id.coupons_and_promo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new CouponsAndPromosFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("Coupons and Promo");
                        break;
                    case R.id.order_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new OrderHistoryFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("Order History");
                        break;
                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new FavoritesFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("Favorites");
                        break;
                    case R.id.redeem_rewards:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new RedeemRewardsFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("Redeem Rewards");
                        break;
                    case R.id.order_status:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new OrderStatusFragment()).addToBackStack(null).commit();
                        actionBar.setTitle("Order Status");
                        break;
                    case R.id.delivery_address:
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        assert firebaseUser != null;
                        final String userID = firebaseUser.getUid();

                        actionBar.setTitle("User Data");
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child("user_data");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
                                    UserDataModel userDataModel;
                                    if (String.valueOf(userData.get("userAddressStreet2")).equals("null")) {
                                        userDataModel = new UserDataModel(userData.get("userName").toString(),
                                                userData.get("userSurname").toString(),
                                                userData.get("userPhone").toString(),
                                                userData.get("userEmail").toString(),
                                                userData.get("userAddressStreet").toString(),
                                                userData.get("userAddressNumber").toString(),
                                                userData.get("userAddressCity").toString());
                                    } else {
                                        userDataModel = new UserDataModel(userData.get("userName").toString(),
                                                userData.get("userSurname").toString(),
                                                userData.get("userPhone").toString(),
                                                userData.get("userEmail").toString(),
                                                userData.get("userAddressStreet").toString(),
                                                userData.get("userAddressNumber").toString(),
                                                userData.get("userAddressCity").toString(),
                                                userData.get("userAddressStreet2").toString(),
                                                userData.get("userAddressNumber2").toString(),
                                                userData.get("userAddressCity2").toString());
                                    }
                                    Bundle userBundle = new Bundle();
                                    userBundle.putParcelable(USER_DATA_BUNDLE, userDataModel);

                                    UserDataOutputFragment userDataOutputFragment = new UserDataOutputFragment();
                                    userDataOutputFragment.setArguments(userBundle);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, userDataOutputFragment).addToBackStack(null).commit();

                                } else {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new DeliveryAddressEntryFragment()).addToBackStack(null).commit();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new SettingsFragment()).addToBackStack(null).commit();

                        actionBar.setTitle("Settings");
                        break;
                }

                return true;
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_frameLayout);
                if (f instanceof MainPageFragment) {
                    setSelectedNavDrawerItem(R.id.main_screen);
                } else if (f instanceof CouponsAndPromosFragment) {
                    setSelectedNavDrawerItem(R.id.coupons_and_promo);
                } else if (f instanceof OrderHistoryFragment) {
                    setSelectedNavDrawerItem(R.id.order_history);
                } else if (f instanceof FavoritesFragment) {
                    setSelectedNavDrawerItem(R.id.favorites);
                } else if (f instanceof RedeemRewardsFragment) {
                    setSelectedNavDrawerItem(R.id.redeem_rewards);
                } else if (f instanceof OrderStatusFragment) {
                    setSelectedNavDrawerItem(R.id.order_status);
                } else if (f instanceof DeliveryAddressEntryFragment) {
                    setSelectedNavDrawerItem(R.id.delivery_address);
                } else if (f instanceof SettingsFragment) {
                    setSelectedNavDrawerItem(R.id.settings);
                }
            }
        });

    }
    // method to update favorites widget in order to make sure latest data is being loaded
    private void updateFavorites() {
        Log.d("Update DB", "update database" + ";;;;;;4;;4;4;4;4;;42;34;23;2;35;23;;23;tw;;f;sg;w;egw;");
        final FavoritesDatabase favoritesDatabase = new FavoritesDatabase(getApplicationContext());
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_USERS).child(userID).child(Constants.DATABASE_FAVORITES);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null) {
                    Map<String, Object> favMap = (Map<String, Object>) dataSnapshot.getValue();

                    for(Map.Entry<String, Object> favData: favMap.entrySet()) {
                        Log.d("KEY ???", favData.getKey());
                        Map<String, Object> finalSandwichModel = (Map<String, Object>) favData.getValue();
                        favoritesDatabase.insertToFavoritesDB(finalSandwichModel.get("sandwich").toString());
                        Log.d("Instert to DB", "inserting to DB: " + finalSandwichModel.get("sandwich").toString());
                    }

                    updateWidget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateWidget();
    }

    private void updateWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoritesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView);
    }

    void setSelectedNavDrawerItem(int id) {
        navigationView.setCheckedItem(id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                drawerLayout.openDrawer(Gravity.START);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment lastOpenFragment = getSupportFragmentManager().findFragmentById(R.id.main_frameLayout);

        getSupportFragmentManager().putFragment(outState, SAVING_FRAGMENT, lastOpenFragment);
    }
}
