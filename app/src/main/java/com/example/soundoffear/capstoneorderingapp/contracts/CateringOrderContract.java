package com.example.soundoffear.capstoneorderingapp.contracts;

import android.provider.BaseColumns;

public class CateringOrderContract {

    public class CateringOrderEntry implements BaseColumns {
        public static final String CATERING_TABLE_NAME = "catering_table_name";
        public static final String CATERING_ITEM_NAME = "catering_item_name";
        public static final String CATERING_ITEM_PRICE = "catering_item_price";
    }

}
