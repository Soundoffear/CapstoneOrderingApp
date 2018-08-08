package com.example.soundoffear.capstoneorderingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.adapters.CateringSummaryAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.DrinksAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.DrinksOrderSummaryAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.FinalSandwichAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.adapters.SidesSummaryAdapter_RV;
import com.example.soundoffear.capstoneorderingapp.databases.CateringOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.DrinksOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FavoritesDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.databases.SidesOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.fragments.FavoritesFragment;
import com.example.soundoffear.capstoneorderingapp.models.CateringModel;
import com.example.soundoffear.capstoneorderingapp.models.DrinksModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.example.soundoffear.capstoneorderingapp.utilities.PointsSystemClass;
import com.example.soundoffear.capstoneorderingapp.widget.FavoritesWidget;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderSummaryActivity extends AppCompatActivity {

    public static final String LAST_ORDER_SAVED = "last_order_value";

    @BindView(R.id.order_summary_toolbar)
    Toolbar order_summary_toolbar;

    @BindView(R.id.order_summary_recyclerView)
    RecyclerView order_summary_recyclerView;
    @BindView(R.id.order_summary_drinks_recyclerView)
    RecyclerView order_summary_drinks_recyclerView;
    @BindView(R.id.order_summary_catering_recyclerView)
    RecyclerView order_summary_catering_recyclerView;
    @BindView(R.id.order_summary_sides_recyclerView)
    RecyclerView order_summary_sides_recyclerView;
    @BindView(R.id.order_summary_total_price_output)
    TextView order_summary_total_price_output;

    @BindView(R.id.order_summary_fabMenu)
    FloatingActionsMenu floatingActionsMenu;
    @BindView(R.id.order_summary_button_add_drink)
    FloatingActionButton fab_add_drink;
    @BindView(R.id.order_summary_button_add_sandwich)
    FloatingActionButton fab_add_sandwich;
    @BindView(R.id.order_summary_button_add_sides)
    FloatingActionButton fab_add_sides;
    @BindView(R.id.order_summary_button_add_catering)
    FloatingActionButton fab_add_catering;

    @BindView(R.id.order_summary_send_order)
    Button order_summary_send_button;

    @BindView(R.id.order_summary_cancel_order)
    Button order_summary_cancel_button;

    private String drinksString;
    private String subsString;
    private String cateringString;
    private String sidesString;

    private int countDrinks = 0;
    private int countSubs = 0;
    private int countCatering = 0;
    private int countSides = 0;

    private double countTotalPrice;

    private String favoriteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);

        setSupportActionBar(order_summary_toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Order Summary");
        actionBar.setDisplayHomeAsUpEnabled(false);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        final String userID = firebaseUser.getUid();

        FinalSandwichDataBase finalSandwichDataBase = new FinalSandwichDataBase(getApplicationContext());
        DrinksOrderDatabase drinksOrderDatabase = new DrinksOrderDatabase(getApplicationContext());
        CateringOrderDatabase cateringOrderDatabase = new CateringOrderDatabase(getApplicationContext());
        SidesOrderDatabase sidesOrderDatabase = new SidesOrderDatabase(getApplicationContext());

        final List<FinalSandwichModel> finalSandwichModelList = finalSandwichDataBase.getAllFinalSandwichData();
        final List<DrinksModel> drinksModelList = drinksOrderDatabase.getAllDrinksData();
        if (finalSandwichModelList.size() > 0) {
            order_summary_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            order_summary_recyclerView.setLayoutManager(linearLayoutManager);
            FinalSandwichAdapter_RV finalSandwichAdapter_rv = new FinalSandwichAdapter_RV(this, finalSandwichModelList);
            order_summary_recyclerView.setAdapter(finalSandwichAdapter_rv);
            for (FinalSandwichModel finalSandwichModel : finalSandwichModelList) {
                countTotalPrice = countTotalPrice + Double.parseDouble(finalSandwichModel.getFinalPrice());
            }
        } else {
            order_summary_recyclerView.setVisibility(View.GONE);
        }

        if (drinksModelList.size() > 0) {
            order_summary_drinks_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
            order_summary_drinks_recyclerView.setLayoutManager(linearLayoutManager1);
            DrinksOrderSummaryAdapter_RV drinksOrderSummaryAdapter_rv = new DrinksOrderSummaryAdapter_RV(this, drinksModelList);
            order_summary_drinks_recyclerView.setAdapter(drinksOrderSummaryAdapter_rv);
            for (DrinksModel drinksModel : drinksModelList) {
                countTotalPrice = countTotalPrice + Double.parseDouble(drinksModel.getPrice());
            }
        } else {
            order_summary_drinks_recyclerView.setVisibility(View.GONE);
        }

        final List<CateringModel> cateringModelList = cateringOrderDatabase.getCateringData();
        if (cateringModelList.size() > 0) {
            order_summary_catering_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            order_summary_catering_recyclerView.setLayoutManager(linearLayoutManager);
            CateringSummaryAdapter_RV cateringSummaryAdapter_rv = new CateringSummaryAdapter_RV(getApplicationContext(), cateringModelList);
            order_summary_catering_recyclerView.setAdapter(cateringSummaryAdapter_rv);
            for (CateringModel cateringModel : cateringModelList) {
                countTotalPrice = countTotalPrice + Double.parseDouble(cateringModel.getCateringPrice());
            }
        } else {
            order_summary_catering_recyclerView.setVisibility(View.GONE);
        }

        final List<SidesModel> sidesModelList = sidesOrderDatabase.getAllSides();
        if (sidesModelList.size() > 0) {
            order_summary_sides_recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            order_summary_sides_recyclerView.setLayoutManager(linearLayoutManager);
            SidesSummaryAdapter_RV sidesSummaryAdapter_rv = new SidesSummaryAdapter_RV(getApplicationContext(), sidesModelList);
            order_summary_sides_recyclerView.setAdapter(sidesSummaryAdapter_rv);
            for (SidesModel sidesModel : sidesModelList) {
                countTotalPrice = countTotalPrice + Double.parseDouble(sidesModel.getSidePrice());
            }
        } else {
            order_summary_sides_recyclerView.setVisibility(View.GONE);
        }

        order_summary_total_price_output.setText(new DecimalFormat("0.00").format(countTotalPrice));

        if (FavoritesFragment.isAddingFav) {
            floatingActionsMenu.setVisibility(View.GONE);
            fab_add_drink.setVisibility(View.GONE);
            fab_add_sides.setVisibility(View.GONE);
            fab_add_sandwich.setVisibility(View.GONE);
            fab_add_catering.setVisibility(View.GONE);
            order_summary_send_button.setText(getString(R.string.add_to_fav));

        } else {

            fab_add_drink.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
            fab_add_drink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrinksAdapter_RV.isOrderingMoreDrinks = true;
                    Intent intent = new Intent(OrderSummaryActivity.this, OrderDrinksActivity.class);
                    startActivity(intent);
                }
            });

            fab_add_sandwich.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
            fab_add_sandwich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderPlacingActivity.isOrderingAdditionalSandwich = true;
                    Intent intent = new Intent(OrderSummaryActivity.this, OrderPlacingActivity.class);
                    startActivity(intent);
                }
            });

            fab_add_sides.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
            fab_add_sides.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderPlacingActivity.isOrderingAdditionalSides = true;
                    Intent intent = new Intent(OrderSummaryActivity.this, OrderPlacingActivity.class);
                    startActivity(intent);
                }
            });

            fab_add_catering.setIconDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_add_circle_outline_black_24dp));
            fab_add_catering.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OrderPlacingActivity.isOrderingAdditionalCatering = true;
                    Intent intent = new Intent(OrderSummaryActivity.this, OrderPlacingActivity.class);
                    startActivity(intent);
                }
            });

        }

        order_summary_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(new ContextThemeWrapper(OrderSummaryActivity.this, R.style.alert_dialog_background));
                alertBuilder.setTitle(R.string.cancel_order)
                        .setMessage("Do you want to cancel your order?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

        order_summary_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDrinks = 0;
                countSubs = 0;
                countSides = 0;
                insertToFavDB(finalSandwichModelList.get(0).getSandwich());

                updateWidget();
                if (FavoritesFragment.isAddingFav) {

                    databaseReference.child(Constants.DATABASE_USERS).child(userID).child("favorites").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d("FAV DATA 1", dataSnapshot.getKey());
                            Map<String, Object> datas = (Map<String, Object>) dataSnapshot.getValue();
                            if (datas != null) {
                                String[] keySplit = String.valueOf(datas.keySet().toArray()[0]).split(" ");
                                int keyNumber = Integer.parseInt(keySplit[1]);
                                favoriteName = "Favorite " + (keyNumber + 1);
                            }
                            if (TextUtils.isEmpty(favoriteName)) {
                                favoriteName = "Favorite 1";
                            }

                            databaseReference.child(Constants.DATABASE_USERS).child(userID).child("favorites").child(favoriteName).setValue(finalSandwichModelList.get(0));
                            Toast.makeText(getApplicationContext(), R.string.item_added_to_fav, Toast.LENGTH_SHORT).show();
                            Intent intentToStartMainActivity = new Intent(OrderSummaryActivity.this, MainActivity.class);
                            intentToStartMainActivity.putExtra(MainActivity.DATA_FAV, Constants.DATABASE_FAVORITES);
                            startActivity(intentToStartMainActivity);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    String orderNumber = String.valueOf(new Date(System.currentTimeMillis()));
                    if (drinksModelList.size() > 1) {
                        for (int i = 0; i < drinksModelList.size(); i++) {
                            countDrinks++;
                            drinksString = "Drink" + countDrinks;
                            databaseReference.child(Constants.DATABASE_USERS)
                                    .child(userID)
                                    .child(Constants.DATABASE_ORDERS)
                                    .child(orderNumber)
                                    .child(drinksString)
                                    .setValue(drinksModelList.get(i));
                        }
                        databaseReference.child(Constants.DATABASE_USERS)
                                .child(userID)
                                .child(Constants.DATABASE_ORDERS)
                                .child(orderNumber)
                                .child(Constants.DATABASE_ORDER_STATUS)
                                .setValue(Constants.DATABASE_ORDER_RECEIVED);
                    }

                    if (finalSandwichModelList.size() > 0) {
                        for (int i = 0; i < finalSandwichModelList.size(); i++) {
                            countSubs++;
                            subsString = "Sub" + countSubs;
                            databaseReference.child(Constants.DATABASE_USERS)
                                    .child(userID)
                                    .child(Constants.DATABASE_ORDERS)
                                    .child(orderNumber)
                                    .child(subsString)
                                    .setValue(finalSandwichModelList.get(i));
                        }
                        databaseReference.child(Constants.DATABASE_USERS)
                                .child(userID)
                                .child(Constants.DATABASE_ORDERS)
                                .child(orderNumber)
                                .child(Constants.DATABASE_ORDER_STATUS)
                                .setValue(Constants.DATABASE_ORDER_RECEIVED);
                    }

                    if (cateringModelList.size() > 0) {
                        for (int i = 0; i < cateringModelList.size(); i++) {
                            countCatering++;
                            cateringString = "Catering" + countCatering;
                            databaseReference.child(Constants.DATABASE_USERS)
                                    .child(userID)
                                    .child(Constants.DATABASE_ORDERS)
                                    .child(orderNumber)
                                    .child(cateringString)
                                    .setValue(cateringModelList.get(i));
                        }
                        databaseReference.child(Constants.DATABASE_USERS)
                                .child(userID)
                                .child(Constants.DATABASE_ORDERS)
                                .child(orderNumber)
                                .child(Constants.DATABASE_ORDER_STATUS)
                                .setValue(Constants.DATABASE_ORDER_RECEIVED);
                    }

                    if (sidesModelList.size() > 0) {
                        for (int i = 0; i < sidesModelList.size(); i++) {
                            countSides++;
                            sidesString = "Sides" + countSides;
                            databaseReference.child(Constants.DATABASE_USERS)
                                    .child(userID)
                                    .child(Constants.DATABASE_ORDERS)
                                    .child(orderNumber)
                                    .child(sidesString)
                                    .setValue(sidesModelList.get(i));
                        }
                        databaseReference.child(Constants.DATABASE_USERS)
                                .child(userID)
                                .child(Constants.DATABASE_ORDERS)
                                .child(orderNumber)
                                .child(Constants.DATABASE_ORDER_STATUS)
                                .setValue(Constants.DATABASE_ORDER_RECEIVED);
                    }

                    databaseReference.child(Constants.DATABASE_USERS)
                            .child(userID)
                            .child(Constants.DATABASE_LAST_ORDER)
                            .setValue(orderNumber);
                    PointsSystemClass.addPoints(new DecimalFormat("#").format(Math.round(countTotalPrice)), userID);
                    Intent goToMainPageIntent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                    startActivity(goToMainPageIntent);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    sharedPreferences.edit().putString(LAST_ORDER_SAVED, orderNumber).apply();
                }

            }
        });
    }

    private void updateWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, FavoritesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView);
    }

    private void insertToFavDB(String favoriteName) {
        FavoritesDatabase favoritesDatabase = new FavoritesDatabase(getApplicationContext());
        favoritesDatabase.insertToFavoritesDB(favoriteName);
    }
}
