package com.example.soundoffear.capstoneorderingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.soundoffear.capstoneorderingapp.MainActivity;
import com.example.soundoffear.capstoneorderingapp.R;
import com.example.soundoffear.capstoneorderingapp.databases.FavoritesDatabase;
import com.example.soundoffear.capstoneorderingapp.utilities.Constants;

import java.util.List;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private List<String> itemsList;
    private Context context;

    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        FavoritesDatabase favoritesDatabase = new FavoritesDatabase(context);

        itemsList = favoritesDatabase.getAllFavValues();

        Log.d("Test database", String.valueOf(itemsList.size()));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.d("Test Count", String.valueOf(itemsList.size()));
        if(itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.favorites_item_list_row);
        Log.d("Test data", itemsList.get(position));
        remoteViews.setTextViewText(R.id.item_fav_tv, itemsList.get(position));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(MainActivity.DATA_FAV, Constants.DATABASE_FAVORITES);
        remoteViews.setOnClickFillInIntent(R.id.item_fav_tv, fillInIntent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
