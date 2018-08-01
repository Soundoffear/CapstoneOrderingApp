package com.example.soundoffear.capstoneorderingapp.contracts;

import android.provider.BaseColumns;

public class SidesOrderContract {

    public class SidesContractEntry implements BaseColumns {
        public static final String SIDES_TABLE_NAME = "table_sides";
        public static final String SIDES_ITEM_NAME = "sides_item_name";
        public static final String SIDES_ITEM_NUMBER = "sides_item_number";
        public static final String SIDES_ITEM_PRICE = "sides_item_price";
    }

}
