package com.example.soundoffear.capstoneorderingapp.contracts;

import android.provider.BaseColumns;

public class DrinksOrderContract {

    public class DrinksOrderEntry implements BaseColumns {

        public static final String DRINKS_ORDER_TABLE_NAME = "table_name_for_drinks_order";
        public static final String DRINKS_NAME = "drinks_name";
        public static final String DRINKS_NUMBER = "drinks_number";
        public static final String DRINKS_PRICE = "drinks_price";

    }

}
