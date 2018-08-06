package com.example.soundoffear.capstoneorderingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.soundoffear.capstoneorderingapp.contracts.CateringOrderContract;
import com.example.soundoffear.capstoneorderingapp.databases.CateringOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.databases.SidesOrderDatabase;
import com.example.soundoffear.capstoneorderingapp.fragments.FavoritesFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.MainPageFragment;
import com.example.soundoffear.capstoneorderingapp.fragments.OrderTypesFragment;
import com.example.soundoffear.capstoneorderingapp.models.BreadModel;
import com.example.soundoffear.capstoneorderingapp.models.CarrierModel;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.OrderTypeModel;
import com.example.soundoffear.capstoneorderingapp.models.PaidAddsModel;
import com.example.soundoffear.capstoneorderingapp.models.SandwichModel;
import com.example.soundoffear.capstoneorderingapp.models.SaucesModel;
import com.example.soundoffear.capstoneorderingapp.models.SidesModel;
import com.example.soundoffear.capstoneorderingapp.models.VegetableModel;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.BreadTypeFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.CarrierChooserFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.CateringFragments;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.PaidAddsFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.SandwichChoicesFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.SaucesFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.SidesFragment;
import com.example.soundoffear.capstoneorderingapp.ordering_fragments.VegetableFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderPlacingActivity extends AppCompatActivity {

    public static final String SANDWICH_CARRIERS = "sandwich_carriers_data";
    public static final String SANDWICH_CHOICES = "sandwich_names_choices";
    public static final String SANDWICH_BREAD_TYPES = "bread_types_for_subs";
    public static final String SANDWICH_VEGETABLES = "sandwich_vegetable_data";
    public static final String SANDWICH_SAUCE_BUNDLE = "sandwich_sauce_data";
    public static final String SANDWICH_PAID_ADDS_BUNDLE = "sandwich_paid_adds_data";
    public static final String SANDWICH_CARRIER_CHOSEN = "sandwich_carrier_chosen";

    public static final String LAST_FRAGMENT_STATE = "last_fragment_state";

    public static boolean isOrderingAdditionalSandwich = false;
    public static boolean isOrderingAdditionalSides = false;
    public static boolean isOrderingAdditionalCatering = false;

    //Strings for holding sandwich data throughout building sandwich by User
    private String carrierChosen;
    private String sandwichChosen;
    private String sandwichPriceChosen;
    private String vegetableChosen;
    private String breadChosen;
    private String sauceChosen;
    private String paidAddsChosen;

    // required for back control of ordering
    private SandwichModel sandwichModel;
    private BreadModel breadModel;
    private List<VegetableModel> selectedVegetablesList;
    private List<SaucesModel> selectedSaucesList;
    private List<PaidAddsModel> selectedPaidAddsList;

    @BindView(R.id.order_placing_toolbar)
    Toolbar orderPlacingToolbar;
    @BindView(R.id.order_placing_next_button)
    Button next_button;
    @BindView(R.id.order_placing_frameLayout)
    FrameLayout order_placing_frameLayout;

    private List<CarrierModel> carriersList;
    private List<SandwichModel> sandwichModelList;
    private List<BreadModel> sandwichBreadTypesList;
    private List<VegetableModel> vegetableModelList;
    private List<SaucesModel> saucesModelList;
    private List<PaidAddsModel> paidAddsModelList;

    private List<FinalSandwichModel> finalSandwichModelList;

    public static boolean isSelectedCarrier;

    Fragment initializeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placing);

        final FinalSandwichDataBase finalSandwichDataBase = new FinalSandwichDataBase(getApplicationContext());
        final CateringOrderDatabase cateringOrderDatabase = new CateringOrderDatabase(getApplicationContext());
        final SidesOrderDatabase sidesOrderDatabase = new SidesOrderDatabase(getApplicationContext());
        isSelectedCarrier = false;
        ButterKnife.bind(this);

        selectedVegetablesList = new ArrayList<>();
        selectedSaucesList = new ArrayList<>();
        selectedPaidAddsList = new ArrayList<>();

        carriersList = new ArrayList<>();
        sandwichModelList = new ArrayList<>();
        sandwichBreadTypesList = new ArrayList<>();
        vegetableModelList = new ArrayList<>();
        saucesModelList = new ArrayList<>();
        paidAddsModelList = new ArrayList<>();

        finalSandwichModelList = new ArrayList<>();
        finalSandwichModelList = finalSandwichDataBase.getAllFinalSandwichData();

        if (finalSandwichModelList.size() > 0) {
            for (int i = 0; i < finalSandwichModelList.size(); i++) {
                FinalSandwichModel finalSandwichModel = finalSandwichModelList.get(i);
                carrierChosen = finalSandwichModel.getCarrier();
                sandwichChosen = finalSandwichModel.getSandwich();
                sandwichPriceChosen = finalSandwichModel.getFinalPrice();
                vegetableChosen = finalSandwichModel.getVegetables();
                breadChosen = finalSandwichModel.getBread();
                sauceChosen = finalSandwichModel.getSauces();
                paidAddsChosen = finalSandwichModel.getPaidAddOns();
            }
        }

        setSupportActionBar(orderPlacingToolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.placing_order);

        Intent intentWithCarriers = getIntent();
        List<OrderTypeModel> orderTypesList = intentWithCarriers.getParcelableArrayListExtra(MainPageFragment.LIST_OF_ORDER_TYPES);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState != null) {

            carrierChosen = savedInstanceState.getString(SANDWICH_CARRIERS);
            sandwichChosen = savedInstanceState.getString(SANDWICH_CHOICES);
            sandwichPriceChosen = savedInstanceState.getString(SANDWICH_PRICE_CHOSEN);
            vegetableChosen = savedInstanceState.getString(SANDWICH_VEGETABLES);
            breadChosen = savedInstanceState.getString(SANDWICH_BREAD_TYPES);
            sauceChosen = savedInstanceState.getString(SANDWICH_SAUCE_BUNDLE);
            paidAddsChosen = savedInstanceState.getString(SANDWICH_PAID_ADDS_BUNDLE);

            Fragment restoredFragment = fragmentManager.getFragment(savedInstanceState, LAST_FRAGMENT_STATE);
            fragmentManager.beginTransaction().replace(R.id.order_placing_frameLayout, restoredFragment).addToBackStack(null).commit();

        } else {

            if (isOrderingAdditionalSandwich || FavoritesFragment.isAddingFav) {
                initializeFragment = new CarrierChooserFragment();
            } else if (isOrderingAdditionalSides) {
                initializeFragment = new SidesFragment();
            } else if (isOrderingAdditionalCatering) {
                initializeFragment = new CateringFragments();
            } else {
                initializeFragment = new OrderTypesFragment();
            }

            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MainPageFragment.LIST_OF_ORDER_TYPES, (ArrayList<OrderTypeModel>) orderTypesList);
            initializeFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.order_placing_frameLayout, initializeFragment);
            fragmentTransaction.commit();

        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("sandwiches");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> carriersData = (Map<String, Object>) dataSnapshot.child("types").getValue();
                assert carriersData != null;
                for (Map.Entry<String, Object> carrier : carriersData.entrySet()) {
                    String carrierName = String.valueOf(carrier.getValue());
                    CarrierModel carrierModel = new CarrierModel(carrierName);
                    carriersList.add(carrierModel);
                }

                Map<String, Object> sandwichData = (Map<String, Object>) dataSnapshot.child("s_name_desc").getValue();

                assert sandwichData != null;
                for (Map.Entry<String, Object> data : sandwichData.entrySet()) {
                    String sandwichName = data.getKey();
                    String sandwichValue = (String) data.getValue();
                    String[] sandwichArray = sandwichValue.split("_");
                    String sandwichPrice = sandwichArray[1];
                    String sandwichDesc = sandwichArray[0];
                    SandwichModel sandwichModel = new SandwichModel(sandwichName, sandwichPrice, sandwichDesc);
                    sandwichModelList.add(sandwichModel);
                }

                Map<String, String> breadTypesData = (Map<String, String>) dataSnapshot.child("breadTypes").getValue();
                assert breadTypesData != null;
                for (Map.Entry<String, String> breadData : breadTypesData.entrySet()) {
                    BreadModel breadModel = new BreadModel(breadData.getValue());
                    sandwichBreadTypesList.add(breadModel);
                }

                Map<String, String> vegetableData = (Map<String, String>) dataSnapshot.child("veges").getValue();
                assert vegetableData != null;
                for (Map.Entry<String, String> vegeData : vegetableData.entrySet()) {
                    VegetableModel vegetableModel = new VegetableModel(vegeData.getValue());
                    vegetableModelList.add(vegetableModel);
                }

                Map<String, String> saucesData = (Map<String, String>) dataSnapshot.child("sauces").getValue();
                assert saucesData != null;
                for (Map.Entry<String, String> sauceData : saucesData.entrySet()) {
                    SaucesModel saucesModel = new SaucesModel(sauceData.getValue());
                    saucesModelList.add(saucesModel);
                }

                Map<String, String> paidData = (Map<String, String>) dataSnapshot.child("paid").getValue();
                assert paidData != null;
                for (Map.Entry<String, String> paid : paidData.entrySet()) {
                    String paidRAW = paid.getValue();
                    String[] paidSplit = paidRAW.split("_");
                    PaidAddsModel paidAddsModel = new PaidAddsModel(paidSplit[0], paidSplit[1]);
                    paidAddsModelList.add(paidAddsModel);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction ft = fm.beginTransaction();
                Fragment loadedFragment = fm.findFragmentById(R.id.order_placing_frameLayout);
                if (loadedFragment instanceof OrderTypesFragment) {
                    OrderTypesFragment orderTypesFragment1 = (OrderTypesFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (orderTypesFragment1.getOrderType() != null) {
                        String orderType = orderTypesFragment1.getOrderType();
                        switch (orderType) {
                            case "drinks":
                                Intent intent = new Intent(OrderPlacingActivity.this, OrderDrinksActivity.class);
                                startActivity(intent);
                                break;
                            case "sandwiches":
                                CarrierChooserFragment carrierChooserFragment = new CarrierChooserFragment();
                                Bundle carrierBundle = new Bundle();
                                carrierBundle.putParcelableArrayList(SANDWICH_CARRIERS, (ArrayList<CarrierModel>) carriersList);
                                carrierChooserFragment.setArguments(carrierBundle);
                                ft.replace(R.id.order_placing_frameLayout, carrierChooserFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "catering":
                                CateringFragments cateringFragments = new CateringFragments();
                                ft.replace(R.id.order_placing_frameLayout, cateringFragments);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                            case "sides":
                                SidesFragment sidesFragment = new SidesFragment();
                                ft.replace(R.id.order_placing_frameLayout, sidesFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                                break;
                        }
                    } else {
                        Snackbar.make(order_placing_frameLayout, R.string.please_choose_order, Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (loadedFragment instanceof CateringFragments) {
                    CateringFragments cateringFragments = (CateringFragments) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (cateringFragments.getCateringModel() != null) {
                        Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                        cateringOrderDatabase.deleteDatabase(CateringOrderContract.CateringOrderEntry.CATERING_TABLE_NAME);
                        cateringOrderDatabase.insertCateringToDatabase(cateringFragments.getCateringModel());
                        startActivity(intent);
                    }
                }

                if (loadedFragment instanceof SidesFragment) {
                    SidesFragment sidesFragment = (SidesFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (sidesFragment.getAllSidesChoosen() != null) {
                        for (SidesModel sidesModel : sidesFragment.getAllSidesChoosen()) {
                            sidesOrderDatabase.insertSidesToDB(sidesModel);
                        }
                        Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                        startActivity(intent);
                    }
                }

                if (loadedFragment instanceof CarrierChooserFragment) {
                    /* Load SandwichChoicesFragment if CarrierChooserFragment has been previously loaded
                      if carrier is not chosen then SnackBar will inform user **/
                    CarrierChooserFragment carrierChooserFragment = ((CarrierChooserFragment) fm.findFragmentById(R.id.order_placing_frameLayout));

                    if (carrierChooserFragment.getSelectedCarrier() != null) {
                        carrierChosen = carrierChooserFragment.getSelectedCarrier();
                        SandwichChoicesFragment sandwichChoicesFragment = new SandwichChoicesFragment();
                        Bundle sandwichChoicesBundle = new Bundle();
                        sandwichChoicesBundle.putParcelableArrayList(SANDWICH_CHOICES, (ArrayList<SandwichModel>) sandwichModelList);
                        sandwichChoicesBundle.putString(SANDWICH_CARRIER_CHOSEN, carrierChosen);
                        sandwichChoicesFragment.setArguments(sandwichChoicesBundle);
                        ft.replace(R.id.order_placing_frameLayout, sandwichChoicesFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        Snackbar.make(order_placing_frameLayout, R.string.please_choose_type, Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (loadedFragment instanceof SandwichChoicesFragment) {
                    /* Load BreadTypeFragment if SandwichChoicesFragment has been previously loaded **/
                    SandwichChoicesFragment sandwichChoicesFragment = (SandwichChoicesFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (sandwichChoicesFragment.getSandwichModelSelected() != null) {
                        sandwichModel = sandwichChoicesFragment.getSandwichModelSelected();
                        sandwichChosen = sandwichChoicesFragment.getSandwichModelSelected().getSandwichName();
                        sandwichPriceChosen = sandwichChoicesFragment.getSandwichModelSelected().getSandwichPrice();
                        if (carrierChosen.equals("SALAD") || carrierChosen.equals("WRAP")) {
                            VegetableFragment vegetableFragment = new VegetableFragment();
                            Bundle vegetableBundle = new Bundle();
                            vegetableBundle.putParcelableArrayList(SANDWICH_VEGETABLES, (ArrayList<VegetableModel>) vegetableModelList);
                            vegetableFragment.setArguments(vegetableBundle);
                            ft.replace(R.id.order_placing_frameLayout, vegetableFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        } else {
                            BreadTypeFragment breadTypeFragment = new BreadTypeFragment();
                            Bundle breadTypeBundle = new Bundle();
                            breadTypeBundle.putParcelableArrayList(SANDWICH_BREAD_TYPES, (ArrayList<BreadModel>) sandwichBreadTypesList);
                            breadTypeBundle.putString(SANDWICH_CARRIER_CHOSEN, carrierChosen);
                            breadTypeFragment.setArguments(breadTypeBundle);
                            ft.replace(R.id.order_placing_frameLayout, breadTypeFragment);
                            ft.addToBackStack(null);
                            ft.commit();
                        }
                    } else {
                        Snackbar.make(order_placing_frameLayout, R.string.please_choose_sub, Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (loadedFragment instanceof BreadTypeFragment) {
                    /* Load VegetableFragment if BreadTypeFragment has been previously loaded **/
                    BreadTypeFragment breadTypeFragment = (BreadTypeFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (breadTypeFragment.breadModelSelected != null) {
                        breadModel = breadTypeFragment.getBreadModelSelected();
                        breadChosen = breadTypeFragment.getBreadModelSelected().getBreadName();
                        VegetableFragment vegetableFragment = new VegetableFragment();
                        Bundle vegetableBundle = new Bundle();
                        vegetableBundle.putParcelableArrayList(SANDWICH_VEGETABLES, (ArrayList<VegetableModel>) vegetableModelList);
                        vegetableFragment.setArguments(vegetableBundle);
                        ft.replace(R.id.order_placing_frameLayout, vegetableFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.please_choose_bread, Toast.LENGTH_SHORT).show();
                    }
                }
                if (loadedFragment instanceof VegetableFragment) {
                    VegetableFragment vegetableFragment = (VegetableFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (vegetableFragment.getSelectedForSandwichVegetablesList().size() > 0) {
                        selectedVegetablesList = vegetableFragment.getSelectedForSandwichVegetablesList();
                    }
                    if (!TextUtils.isEmpty(vegetableFragment.getAllVegesChosen())) {
                        vegetableChosen = vegetableFragment.getAllVegesChosen();
                        SaucesFragment saucesFragment = new SaucesFragment();
                        Bundle sauceBundle = new Bundle();
                        sauceBundle.putParcelableArrayList(SANDWICH_SAUCE_BUNDLE, (ArrayList<SaucesModel>) saucesModelList);
                        saucesFragment.setArguments(sauceBundle);
                        ft.replace(R.id.order_placing_frameLayout, saucesFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(new ContextThemeWrapper(OrderPlacingActivity.this, R.style.alert_dialog_background));
                        builder.setTitle(R.string.no_veges)
                                .setMessage(getString(R.string.no_veges_message))
                                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton(getString(R.string.no_veges_please), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SaucesFragment saucesFragment = new SaucesFragment();
                                Bundle sauceBundle = new Bundle();
                                sauceBundle.putParcelableArrayList(SANDWICH_SAUCE_BUNDLE, (ArrayList<SaucesModel>) saucesModelList);
                                saucesFragment.setArguments(sauceBundle);
                                ft.replace(R.id.order_placing_frameLayout, saucesFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                        builder.show();
                    }
                }
                if (loadedFragment instanceof SaucesFragment) {
                    SaucesFragment saucesFragment = (SaucesFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    if (saucesFragment.getSaucesModelsSelected().size() > 0) {
                        selectedSaucesList = saucesFragment.getSaucesModelsSelected();
                    }
                    if (!TextUtils.isEmpty(saucesFragment.getAllSaucesChosen())) {
                        sauceChosen = saucesFragment.getAllSaucesChosen();
                        PaidAddsFragment paidAddsFragment = new PaidAddsFragment();
                        Bundle paidAddsBundle = new Bundle();
                        paidAddsBundle.putParcelableArrayList(SANDWICH_PAID_ADDS_BUNDLE, (ArrayList<PaidAddsModel>) paidAddsModelList);
                        paidAddsBundle.putString(SANDWICH_CARRIER_CHOSEN, carrierChosen);
                        paidAddsFragment.setArguments(paidAddsBundle);
                        ft.replace(R.id.order_placing_frameLayout, paidAddsFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(OrderPlacingActivity.this, R.style.alert_dialog_background));
                        builder.setTitle(R.string.no_sauces)
                                .setMessage(getString(R.string.no_sauces_message))
                                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton(getString(R.string.no_sauces_please), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PaidAddsFragment paidAddsFragment = new PaidAddsFragment();
                                Bundle paidAddsBundle = new Bundle();
                                paidAddsBundle.putParcelableArrayList(SANDWICH_PAID_ADDS_BUNDLE, (ArrayList<PaidAddsModel>) paidAddsModelList);
                                paidAddsBundle.putString(SANDWICH_CARRIER_CHOSEN, carrierChosen);
                                paidAddsFragment.setArguments(paidAddsBundle);
                                ft.replace(R.id.order_placing_frameLayout, paidAddsFragment);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                if (loadedFragment instanceof PaidAddsFragment) {
                    PaidAddsFragment paidAddsFragment = (PaidAddsFragment) fm.findFragmentById(R.id.order_placing_frameLayout);
                    paidAddsChosen = paidAddsFragment.getPaidAddsData();
                    //Calculate final total price for sandwich
                    if (paidAddsFragment.getPaidAddsModelList().size() > 0) {
                        selectedPaidAddsList = paidAddsFragment.getPaidAddsModelList();
                    }
                    double d_sandwichPrice = Double.parseDouble(sandwichPriceChosen);
                    if (carrierChosen.equals("SUB30")) {
                        d_sandwichPrice = d_sandwichPrice + 8;
                    } else if (carrierChosen.equals("SALAD")) {
                        d_sandwichPrice = d_sandwichPrice + 2;
                    }
                    String[] paidArray = paidAddsChosen.split("-");
                    String[][] fullySplitPaidArray = new String[paidArray.length][];
                    for (int i = 0; i < paidArray.length; i++) {
                        fullySplitPaidArray[i] = paidArray[i].split("_");
                    }
                    // add all paid addOns
                    double final_sandwichPrice = d_sandwichPrice;
                    for (String[] aFullySplitPaidArray : fullySplitPaidArray) {
                        if (aFullySplitPaidArray.length > 1) {
                            final_sandwichPrice = final_sandwichPrice + Double.parseDouble(aFullySplitPaidArray[2]);
                        } else {
                            final_sandwichPrice = d_sandwichPrice;
                        }
                    }
                    FinalSandwichModel finalSandwichModel;
                    if (carrierChosen.equals("SUB30") || carrierChosen.equals("SUB15")) {
                        finalSandwichModel = new FinalSandwichModel(carrierChosen,
                                sandwichChosen,
                                breadChosen,
                                vegetableChosen,
                                sauceChosen,
                                paidAddsChosen,
                                String.valueOf(final_sandwichPrice));
                    } else {
                        finalSandwichModel = new FinalSandwichModel(carrierChosen,
                                sandwichChosen,
                                "null",
                                vegetableChosen,
                                sauceChosen,
                                paidAddsChosen,
                                String.valueOf(final_sandwichPrice));
                    }
                    finalSandwichDataBase.insertsDataIntoBuildSandwichDatabase(finalSandwichModel);
                    Intent startOrderSummary = new Intent(getApplicationContext(), OrderSummaryActivity.class);
                    startActivity(startOrderSummary);
                }
            }
        });

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.order_placing_frameLayout);
                if (fragment instanceof CarrierChooserFragment) {
                    CarrierChooserFragment carrierChooserFragment = (CarrierChooserFragment) fragment;
                    if (carrierChosen != null) {
                        carrierChooserFragment.setSelectedCarrier(carrierChosen);
                        Log.d("CARRIER CHOSEN", carrierChooserFragment.getSelectedCarrier());
                    }
                } else if (fragment instanceof SandwichChoicesFragment) {
                    SandwichChoicesFragment sandwichChoicesFragment = (SandwichChoicesFragment) fragment;
                    if (sandwichModel != null) {
                        sandwichChoicesFragment.setSandwichModelSelected(sandwichModel);
                        Log.d("SANDWICH CHOSEN OPA", sandwichChoicesFragment.getSandwichModelSelected().getSandwichName());
                    }
                } else if (fragment instanceof BreadTypeFragment) {
                    BreadTypeFragment breadTypeFragment = (BreadTypeFragment) fragment;
                    if (breadModel != null) {
                        breadTypeFragment.setBreadModelSelected(breadModel);
                        Log.d("BREAD TYPE OPA", breadTypeFragment.getBreadModelSelected().getBreadName());
                    }
                } else if (fragment instanceof VegetableFragment) {
                    VegetableFragment vegetableFragment = (VegetableFragment) fragment;
                    if (selectedVegetablesList.size() > 0) {
                        vegetableFragment.setSelectedForSandwichVegetablesList(selectedVegetablesList);
                        Log.d("VEGETABLE OPS", String.valueOf(selectedVegetablesList.size()));
                    }
                } else if (fragment instanceof SaucesFragment) {
                    SaucesFragment saucesFragment = (SaucesFragment) fragment;
                    if (selectedSaucesList.size() > 0) {
                        saucesFragment.setSaucesModelsSelected(selectedSaucesList);
                        Log.d("SAUCES OPS", String.valueOf(selectedSaucesList.size()));
                    }
                } else if (fragment instanceof PaidAddsFragment) {
                    PaidAddsFragment paidAddsFragment = (PaidAddsFragment) fragment;
                    if (selectedPaidAddsList.size() > 0) {
                        paidAddsFragment.setPaidAddsModelList(paidAddsModelList);
                        Log.d("PAID ADDS OPS", String.valueOf(selectedPaidAddsList.size()));
                    }
                }
            }
        });

    }

    public static final String SANDWICH_PRICE_CHOSEN = "sandwich_price_chosen";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment lastFragment = getSupportFragmentManager().findFragmentById(R.id.order_placing_frameLayout);

        if (carrierChosen != null) {
            outState.putString(SANDWICH_CARRIERS, carrierChosen);
        }
        if (sandwichChosen != null) {
            outState.putString(SANDWICH_CHOICES, sandwichChosen);
        }
        if (sandwichPriceChosen != null) {
            outState.putString(SANDWICH_PRICE_CHOSEN, sandwichPriceChosen);
        }
        if (vegetableChosen != null) {
            outState.putString(SANDWICH_VEGETABLES, vegetableChosen);
        }
        if (breadChosen != null) {
            outState.putString(SANDWICH_BREAD_TYPES, breadChosen);
        }
        if (sauceChosen != null) {
            outState.putString(SANDWICH_SAUCE_BUNDLE, sauceChosen);
        }
        if (paidAddsChosen != null) {
            outState.putString(SANDWICH_PAID_ADDS_BUNDLE, paidAddsChosen);
        }

        getSupportFragmentManager().putFragment(outState, LAST_FRAGMENT_STATE, lastFragment);
    }
}
