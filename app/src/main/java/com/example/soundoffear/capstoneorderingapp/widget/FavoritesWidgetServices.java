package com.example.soundoffear.capstoneorderingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoritesWidgetServices extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListProvider(getApplicationContext(), intent);
    }

}
