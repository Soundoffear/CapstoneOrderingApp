package com.example.soundoffear.capstoneorderingapp.adapters;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.soundoffear.capstoneorderingapp.OrderSummaryActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.contracts.BuildSandwichContract;
import com.example.soundoffear.capstoneorderingapp.databases.FavoritesDatabase;
import com.example.soundoffear.capstoneorderingapp.databases.FinalSandwichDataBase;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.widget.FavoritesWidget;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter_RecyclerView extends RecyclerView.Adapter<FavoritesAdapter_RecyclerView.FavoritesViewHolder> {

    private Context fContext;
    private List<FinalSandwichModel> finalSandwichModelList;
    private double priceSandwichFinal = 0;

    public FavoritesAdapter_RecyclerView(Context fContext, List<FinalSandwichModel> finalSandwichModelList) {
        this.fContext = fContext;
        this.finalSandwichModelList = finalSandwichModelList;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(fContext).inflate(R.layout.item_favourites, parent, false);

        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritesViewHolder holder, final int position) {
        final FinalSandwichModel finalSandwichModel = finalSandwichModelList.get(position);
        holder.item_fav_carrier.setText(finalSandwichModel.getCarrier());
        holder.item_fav_name.setText(finalSandwichModel.getSandwich());
        String bread;
        if(finalSandwichModel.getBread().equals("null")) {
            bread = "brak";
        } else {
            bread = finalSandwichModel.getBread();
        }
        holder.item_fav_bread_output.setText(bread);
        String[] vegetables = finalSandwichModel.getVegetables().split("_");
        StringBuilder vegeString = new StringBuilder();
        for(String vegetable: vegetables) {
            vegeString.append(vegetable).append(" ");
        }
        holder.item_fav_vegetable_output.setText(vegeString.toString());
        String[] sauces = finalSandwichModel.getSauces().split("_");
        StringBuilder sauceString = new StringBuilder();
        for(String sauce: sauces) {
            sauceString.append(sauce).append(" ");
        }
        holder.item_fav_sauce_output.setText(sauceString);
        String[] paidAddons = finalSandwichModel.getPaidAddOns().split("-");
        StringBuilder paidString = new StringBuilder();
        for(String paidAddon: paidAddons) {
            String[] paidAdd = paidAddon.split("_");
            if(paidAdd.length > 1) {
                paidString.append(paidAdd[0]).append(" ").append("x").append(" ").append(paidAdd[1]);
            }
        }
        holder.item_fav_paid_addons_output.setText(paidString.toString());

        holder.item_fav_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Selected Item", finalSandwichModelList.get(holder.getAdapterPosition()).getFavNumber());
                FavoritesDatabase favoritesDatabase = new FavoritesDatabase(fContext);
                List<String> stringList = favoritesDatabase.getAllFavValues();
                Log.d("TEST DB ID", stringList.get(position) + " P:" + position + " H:" + holder.getAdapterPosition() + " L:" + holder.getLayoutPosition() + " S:" + stringList.size() );
                favoritesDatabase.deleteItem((holder.getAdapterPosition() + 1));
                stringList = favoritesDatabase.getAllFavValues();
                Log.d("TEST 2", stringList.get(position) + position + " " + stringList.size());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                databaseReference.child("users").child(userID).child("favorites").child(finalSandwichModelList.get(holder.getAdapterPosition()).getFavNumber()).removeValue();
                notifyDataSetChanged();
                Log.d("position", holder.getAdapterPosition() + " " + holder.getLayoutPosition());
                finalSandwichModelList.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), finalSandwichModelList.size());

                favoritesDatabase.deleteAll();
                for(int i = 0; i < finalSandwichModelList.size(); i++) {
                    favoritesDatabase.insertToFavoritesDB(finalSandwichModelList.get(i).getSandwich());
                }

                updateWidget();
            }
        });

        holder.item_fav_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToPlaceOrder = new Intent(fContext, OrderSummaryActivity.class);
                getSandwichData(finalSandwichModelList.get(holder.getAdapterPosition()));
                fContext.startActivity(intentToPlaceOrder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return finalSandwichModelList.size();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_fav_carrier)
        TextView item_fav_carrier;
        @BindView(R.id.item_fav_name) TextView item_fav_name;
        @BindView(R.id.item_fav_bread_output) TextView item_fav_bread_output;
        @BindView(R.id.item_fav_vegetable_output) TextView item_fav_vegetable_output;
        @BindView(R.id.item_fav_sauces_output) TextView item_fav_sauce_output;
        @BindView(R.id.item_fav_paid_addons_output) TextView item_fav_paid_addons_output;
        @BindView(R.id.item_fav_delete)
        Button item_fav_delete;
        @BindView(R.id.item_fav_save) Button item_fav_save;

        FavoritesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    private void getSandwichData(final FinalSandwichModel finalSandwichModel) {
        DatabaseReference fireRef = FirebaseDatabase.getInstance().getReference().child("sandwiches");
        fireRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FinalSandwichModel finalSandwichModel1;

                String carrierChosen = finalSandwichModel.getCarrier();
                String sandwichChosen = finalSandwichModel.getSandwich();
                String sandwichChosenPrice;
                for(Map.Entry<String, String>sandwich: ((Map<String, String>) dataSnapshot.child("s_name_desc").getValue()).entrySet()) {
                    if(sandwich.getKey().contains(sandwichChosen)) {
                        sandwichChosenPrice = sandwich.getValue().split("_")[1];
                        if(carrierChosen.equals("SUB30")) {
                            priceSandwichFinal = Double.parseDouble(sandwichChosenPrice) + 8;
                        } else if (carrierChosen.equals("SALAD")) {
                            priceSandwichFinal = Double.parseDouble(sandwichChosenPrice) + 2;
                        } else {
                            priceSandwichFinal = Double.parseDouble(sandwichChosenPrice);
                        }
                    }
                }
                String breadChosen = finalSandwichModel.getBread();
                String vegetableChosen = finalSandwichModel.getVegetables();
                String sauceChosen = finalSandwichModel.getSauces();
                String paidAddsChosen = finalSandwichModel.getPaidAddOns();
                String[] paidDetails = new String[2];
                Log.d("FULL_PAID_STRING", paidAddsChosen);
                String[] paidSplit = paidAddsChosen.split("-");
                for(int i = 0; i< paidSplit.length; i++) {
                    Log.d("PAID TEST", paidSplit[i]);
                    paidDetails = paidSplit[i].split("_");
                    
                }
                priceSandwichFinal = priceSandwichFinal + Double.parseDouble(paidDetails[2]);

                Log.d("FINAL PRICE", String.valueOf(priceSandwichFinal));
                if (carrierChosen.equals("SUB30") || carrierChosen.equals("SUB15")) {
                    finalSandwichModel1 = new FinalSandwichModel(carrierChosen,
                            sandwichChosen,
                            breadChosen,
                            vegetableChosen,
                            sauceChosen,
                            paidAddsChosen,
                            String.valueOf(new DecimalFormat("0.00").format(priceSandwichFinal)));
                } else {
                    finalSandwichModel1 = new FinalSandwichModel(carrierChosen,
                            sandwichChosen,
                            "null",
                            vegetableChosen,
                            sauceChosen,
                            paidAddsChosen,
                            String.valueOf(new DecimalFormat("0.00").format(priceSandwichFinal)));
                }

                FinalSandwichDataBase finalSandwichDataBase = new FinalSandwichDataBase(fContext);
                finalSandwichDataBase.deleteTable(BuildSandwichContract.BuildSandwichEntry.SANDWICH_TABLE_NAME);
                finalSandwichDataBase.insertsDataIntoBuildSandwichDatabase(finalSandwichModel1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateWidget() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(fContext);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(fContext, FavoritesWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listView);
    }

}
