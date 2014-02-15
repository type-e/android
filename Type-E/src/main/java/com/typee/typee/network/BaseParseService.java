package com.typee.typee.network;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;

public class BaseParseService {

    private static boolean successfulSignUp() {
        return true;
    }

    private static boolean unsuccessfulSignUp() {
        return false;
    }

    public static boolean setData(String tableName, HashMap columnsNameAndValues){
        return null;
    }

    public static ParseObject setRelationalData(String tableName, HashMap columnsNameAndValues){

    }

    public static ParseObject setRelation(String parentColumn, ParseObject parentObject, ParseObject childObject){

    }

    public static boolean saveRelation(ParseObject childOject){

    }

    public static boolean deleteDataGeneric(String tableName, String columnToCompare, String colunmValue){

    }

    public static boolean deleteDataBaseOnUniqueKey(String tableName, String uniqueColumn, String uniqueKey, String columnToCompare, String columnValue){

    }

    public static boolean updateDataGeneric(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String colunmValue){

    }

    public static boolean updateDataBaseOnUserKey(String tableName, String uniqueColumn, String uniqueKey, String columnToUpdate, String updateValue, String columnToCompare, String colunmValue){

    }

    public static void signUp(String username, String firstName, String lastName,
                              String password, String emailAddress, int mobileNumber, int addressFK, String birthday,
                              String gender, String country) {

        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(emailAddress);

        // normal ParseObject
        user.put("FirstName", firstName);
        user.put("LastName", lastName);
        user.put("MobileNumber", mobileNumber);
        user.put("AddressFK", addressFK);
        user.put("Birthday", birthday);
        user.put("Gender", gender);
        user.put("Country", country);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    successfulSignUp();
                } else {
                    unsuccessfulSignUp();
                }
            }
        });
    }

    public static boolean logout() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            return true;
        } else {
            return false;
        }
    }

    public static void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    successfulSignUp();
                } else {
                    unsuccessfulSignUp();
                }
            }
        });
    }

    public static void resetPassword(String email) {
        ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            successfulSignUp();
                        } else {
                            unsuccessfulSignUp();
                        }
                    }
                });
    }

    //	private static void removeAccount(int username){
    //		final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInformations");
    //
    //		query.whereEqualTo("Username", username);
    //		// Retrieve the object by id
    //		query.findInBackground(new FindCallback<ParseObject>() {
    //			public void done(List<ParseObject> queryData, ParseException e) {
    //				if (e == null) {
    //					//only one result is expected to return
    //					queryData.get(0).deleteInBackground();
    //		        } else {
    //		            Log.d("score", "Error: " + e.getMessage());
    //		        }
    //		    }
    //		});
    //
    //	}
}
