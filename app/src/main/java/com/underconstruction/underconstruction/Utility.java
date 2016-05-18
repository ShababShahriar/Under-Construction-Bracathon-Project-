package com.underconstruction.underconstruction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Shabab on 12/5/2015.
 */

public class Utility {


    static class Settings
    {

        public static String get_language(Context context)
        {
            String lang;
            SharedPreferences pref = context.getSharedPreferences("LangPref", 0); // 0 - for private mode
            lang = pref.getString("Language", "en");
            return lang;
        }
        public static void set_language(Context context, String lang)
        {
            SharedPreferences pref = context.getSharedPreferences("LangPref", 0); // 0 - for private mode
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Language", lang);

            editor.commit();
        }

        public static void set_app_language(String lang, Context context)
        {
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            context.getResources().updateConfiguration(config, null);
        }

    }


    public static class CategoryList
    {

        public CategoryList() {
//            categoryMap.put("Others", -1);
        }

        //Given a category name, returns its ID
        public static HashMap<String, Integer> categoryMap = new HashMap<String, Integer> ();
        //Given an ID, returns the category name
        public static HashMap<Integer, String> IdMap = new HashMap<Integer, String> ();





        /**
         * Adds a new category name and id. It is used when category list is received from server
         * @param name
         * @param id
         */
        public static void add(String name, int id)
        {
            categoryMap.put(name, id);
            IdMap.put(id, name);
        }



        /**
         *  Used when populating category list dynamically
         * @return an arraylist of the names of category
         */
        public static ArrayList<String> getCategoryList()
        {
            ArrayList<String> temp = new ArrayList<String>();
            Object [] t = categoryMap.keySet().toArray();
            for (int i = 0 ; i< t.length; i++)
                if (!(((String)t[i]).equals("Others")))
                    temp.add((String)t[i]);
            temp.add("Others"); //display Others as the last element in Category list
            Log.d("getCategoryList()", temp.toString());
            return temp;
        }


        /**
         * given a category id, returns its name
         *  //f(id) = CategoryName
         * @param id the id of the category
         * @return the name of the category
         */
        public static String get(int id)
        {
            return IdMap.get(id);
        }



        /**
         *  given a category name, returns its id
         * f(CategoryName) = id
         * @param cat the name of the category
         * @return the id of the category
         */
        public static int get(String cat) {
            return categoryMap.get(cat);
        }


        public void copyCategoryList(CategoryList cat) {
            ArrayList<String> tempCategoryName = new ArrayList<String>();
            tempCategoryName.addAll(cat.getCategoryList());

            ArrayList<Integer> tempCategoryIds = new ArrayList<Integer>();
            tempCategoryIds.addAll(cat.getCategoryIds());

            for(int i=0; i<tempCategoryName.size(); i++) {
                categoryMap.put(tempCategoryName.get(i), tempCategoryIds.get(i));
            }
        }

//        public static ArrayList<String> getCategoryList()
//        {
//            ArrayList<String> temp = new ArrayList<String>();
//            Object [] t = categoryMap.keySet().toArray();
//            for (int i = 0 ; i< t.length; i++)
//                if (!(((String)t[i]).equals("Others")))
//                    temp.add((String)t[i]);
//            temp.add("Others");
//            return temp;
//        }

        public static ArrayList<Integer> getCategoryIds() {
            ArrayList<Integer> temp = new ArrayList<Integer>();
            ArrayList<String> categoryNames = new ArrayList<String>();
            categoryNames.addAll(getCategoryList());
            for (int i = 0 ; i< categoryNames.size(); i++) {
                String categoryName = categoryNames.get(i);
                temp.add(get(categoryName));
            }

            Log.d("getCategoryIds()", temp.toString());

            return temp;
        }

        @Override
        public String toString() {
            ArrayList<String> tempCategoryName = new ArrayList<String>();
            tempCategoryName.addAll(getCategoryList());
            ArrayList<Integer> tempCategoryId = new ArrayList<Integer>();
            tempCategoryId.addAll(getCategoryIds());
            return (tempCategoryName.toString() + "\n" + tempCategoryId.toString());
        }
    }

    public static class CurrentUser{

        private static String userId = "1";
        private static int id=Integer.valueOf(userId), displayPage = 0;
        private static String ip, apiKey,username="Onix";
        public static boolean ipOK = false;
        private static boolean valid=false;

        public static void setUser(int i,String uName,String apikey){
            id=i;
            username=uName;
            apiKey=apikey;
            valid=true;

        }

        public static String getUsername() {
            return username;
        }

        public static void setUsername(String username) {
            CurrentUser.username = username;
        }

        public static String getUserId() {
            return userId;
        }

        public static void setUserId(String userId) {
            CurrentUser.userId = userId;
            CurrentUser.id = Integer.valueOf(userId);
        }

        public static String getIp() {
            return ip;
        }

        public static void setIp(String ip) {
            CurrentUser.ip = ip;
        }

        public static void invalidate(){
            valid=false;
        }

        public static  boolean isTheUserValid(){
            return valid;
        }

        public static int getId(){return id;}
        public static String getName(){return username;}

        public static String getApiKey() {
            return apiKey;
        }

        public static String makeString(){
            return ""+id+" "+username+" "+apiKey;
        }

        public static int getDisplayPage() {
            return displayPage;
        }

        public static void setDisplayPage(int i) {
            displayPage = i;
        }

        public static void showConnectionError(Context context) {
            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }

        public static String parsePostTime (String dbString) {
            int hr = Integer.parseInt("" + dbString.charAt(11) + dbString.charAt(12));
            String min = "" +  dbString.charAt(14) + dbString.charAt(15);
            String timeOfDay;
            if(hr>12) {
                hr = hr%12;
                timeOfDay = "pm";
            }
            else if(hr == 12) timeOfDay = "pm";
            else timeOfDay = "am";

            if(hr == 0) hr = 12;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            String today = dateFormat.format(cal.getTime());
            String date = dbString.substring(0, 10);

            if (today.equals(date)) {
                date = " Today";
            }
            else {
                cal.add(Calendar.DAY_OF_MONTH, -1);
                String yesterday = dateFormat.format((cal.getTime()));
//                Log.d("yesterday", yesterday);
                if (yesterday.equals(date)) {
                    date = " Yesterday";
                } else {
                    String monthString = new DateFormatSymbols().getMonths()[Integer.parseInt(date.substring(5,7))-1];
//                    Log.d("monthString", monthString);
                    date = " " + monthString.substring(0,3) + " " + date.substring(8,10);
                }
            }

            String timeOfUpdate = hr + ":" + min + timeOfDay + date;
            return timeOfUpdate;
        }

    }

    public static class HazardTags{
        private static String[] hazardTags={"Occupied Footpath","Open Dustbin","Open Manhole","Cluttered Electric Wires","Water Logging","Risky Intersection","No Street Light","Crime Prone Area","Damaged Road","Wro+" +
                "ngway Traffic"};

        public static String[] getHazardTags(){
            return hazardTags;
        }
    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }


    public interface UploadDecision{
        int UPLOAD_REPORT = 3;
        int DONT_UPLOAD_REPORT = 4;

    }


}
