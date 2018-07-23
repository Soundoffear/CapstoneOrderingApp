package com.example.soundoffear.capstoneorderingapp.contracts;

import android.provider.BaseColumns;

public class BuildSandwichContract {

    public class BuildSandwichEntry implements BaseColumns {

        public static final String SANDWICH_TABLE_NAME = "final_sandwich_table_name";
        public static final String SANDWICH_CARRIERS = "final_sandwich_carriers";
        public static final String SANDWICH_NAME = "final_sandwich_name";
        public static final String SANDWICH_BREAD = "final_sandwich_bread";
        public static final String SANDWICH_VEGETABLE = "final_sandwich_vegetables";
        public static final String SANDWICH_SAUCE = "final_sandwich_sauces";
        public static final String SANDWICH_PAID_ADDON = "final_sandwich_paid_addons";
        public static final String SANDWICH_FINAL_PRICE = "final_sandwich_final_price";

    }

}
