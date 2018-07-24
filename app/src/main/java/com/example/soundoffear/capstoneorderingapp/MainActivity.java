package com.example.soundoffear.capstoneorderingapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;

import com.example.soundoffear.capstoneorderingapp.fragments.CouponsAndPromosFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.DeliveryAddressEntryFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.FavoritesFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.MainPageFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.OrderHistoryFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.OrderStatusFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.RedeemRewardsFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.SettingsFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.UserDataOutputFragment;
import com.example.soundoffear.capstoneorderingapp.models.UserDataModel;
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

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.nav_drawer_layout);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_frameLayout, new MainPageFragment());
        fragmentTransaction.commit();

        NavigationView navigationView = findViewById(R.id.main_navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                item.setChecked(true);

                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.main_screen:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new MainPageFragment()).commit();
                        break;
                    case R.id.coupons_and_promo:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new CouponsAndPromosFragment()).commit();
                        break;
                    case R.id.order_history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new OrderHistoryFragment()).commit();
                        break;
                    case R.id.favorites:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new FavoritesFragment()).commit();
                        break;
                    case R.id.redeem_rewards:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new RedeemRewardsFragment()).commit();
                        break;
                    case R.id.order_status:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new OrderStatusFragment()).commit();
                        break;
                    case R.id.delivery_address:
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        final String userID = firebaseUser.getUid();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("user_data");
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                //UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);
                                if(dataSnapshot.getValue() != null) {
                                    Log.d("TEST USER", ((Map<String, Object>) dataSnapshot.getValue()).get("userName").toString());
                                    Map<String, Object> userData = (Map<String, Object>) dataSnapshot.getValue();
                                    UserDataModel userDataModel = new UserDataModel(userData.get("userName").toString(),
                                            userData.get("userSurname").toString(),
                                            userData.get("userPhone").toString(),
                                            userData.get("userEmail").toString(),
                                            userData.get("userAddressStreet").toString(),
                                            userData.get("userAddressNumber").toString(),
                                            userData.get("userAddressCity").toString());
                                    Bundle userBundle = new Bundle();
                                    userBundle.putParcelable(USER_DATA_BUNDLE, userDataModel);

                                    UserDataOutputFragment userDataOutputFragment = new UserDataOutputFragment();
                                    userDataOutputFragment.setArguments(userBundle);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, userDataOutputFragment).commit();

                                } else {
                                    Log.d("USER TEST DATA", "dataSnapshot empty");
                                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new DeliveryAddressEntryFragment()).commit();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        break;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new SettingsFragment()).commit();
                        break;
                }

                return true;
            }
        });

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
}
