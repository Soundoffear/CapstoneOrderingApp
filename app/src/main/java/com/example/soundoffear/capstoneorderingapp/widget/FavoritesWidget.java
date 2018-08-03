package com.example.soundoffear.capstoneorderingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.example.soundoffear.capstoneorderingapp.MainActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.models.FinalSandwichModel;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of App Widget functionality.
 */
public class FavoritesWidget extends AppWidgetProvider {

    RemoteViews remoteViews;

    public static final String DATA_TRANSFER = "data_transfer_to_list_view";
    RemoteViews views;

    private RemoteViews updateAppWidgetWithRemoteViews(final Context context, final int appWidgetId, List<String> itemsList) {

        views = new RemoteViews(context.getPackageName(), R.layout.favorites_widget);

        final Intent intent = new Intent(context, FavoritesWidgetServices.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putStringArrayListExtra(DATA_TRANSFER, (ArrayList<String>) itemsList);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(appWidgetId, R.id.appwidget_listView, intent);
        views.setEmptyView(R.id.appwidget_listView, R.id.item_fav_tv);

        return views;

    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child(Constants.DATABASE_USERS)
                .child(userID)
                .child(Constants.DATABASE_FAVORITES);
        final List<String> finalItemsList = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> favourites = (Map<String, Object>) dataSnapshot.getValue();
                for(Map.Entry<String, Object> fav: favourites.entrySet()) {
                    Map<String, Object> favModel = (Map<String, Object>) fav.getValue();
                    FinalSandwichModel finalSandwichModel = new FinalSandwichModel(fav.getKey(),
                            favModel.get("carrier").toString(),
                            favModel.get("sandwich").toString(),
                            favModel.get("bread").toString(),
                            favModel.get("vegetables").toString(),
                            favModel.get("sauces").toString(),
                            favModel.get("paidAddOns").toString(),
                            favModel.get("finalPrice").toString());

                    finalItemsList.add(finalSandwichModel.getSandwich());
                }
                for (int appWidgetID : appWidgetIds) {

                    remoteViews = updateAppWidgetWithRemoteViews(context, appWidgetID, finalItemsList);

                    Intent intent1 = new Intent(context, MainActivity.class);
                    intent1.putExtra(MainActivity.DATA_FAV, Constants.DATABASE_FAVORITES);
                    PendingIntent startPendingIntentActivity = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setPendingIntentTemplate(R.id.appwidget_listView, startPendingIntentActivity);
                    appWidgetManager.updateAppWidget(appWidgetID, remoteViews);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

