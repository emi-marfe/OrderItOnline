package com.surfacetech.orderitonline.Common;

import com.surfacetech.orderitonline.Model.Restaurant;
import com.surfacetech.orderitonline.Model.User;

public class Common {
    public static final String API_RESTAURANT_ENDPOINT = "http://192.168.43.80:3000/";
    public static final String API_KEY = "1234";
    public static final int DEFAULT_COLUMN_COUNT = 0;
    public static final int FULL_WIDTH_COLUMN = 1;

    public static User currentUser;
    public static Restaurant currentRestaurant;
}
