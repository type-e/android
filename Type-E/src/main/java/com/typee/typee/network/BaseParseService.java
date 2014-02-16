package com.typee.typee.network;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;
import com.parse.SaveCallback;
import java.util.*;

public class BaseParseService {

    public static BaseParseService getBaseParseService() {
        if (instance == null)
            instance = new BaseParseService();

        return instance;
    }

    private ParseObject initParseTable(ParseObject parseTable, HashMap columnsNameAndValuesHM)
    {
        Set set = columnsNameAndValuesHM.entrySet();
        Iterator  i = set.iterator();
        while(i.hasNext()){
            Map.Entry entry = (Map.Entry)i.next();
            parseTable.put(entry.getKey(), entry.getValue());
        }
        return parseTable;
    }

    public boolean setData(String tableName, HashMap columnsNameAndValuesHM, BaseParseListener baseParseListener)
    {
        ParseObject parseTable = new ParseObject(tableName);
        parseTable = initParseTable(parseTable, columnsNameAndValuesHM);

        parseTable.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Success!
                    baseParseListener.successful();

                } else {
                    // Failure!; connetion error probably
                    baseParseListener.unsuccessful();
                }
            }
        });
    }

    public ParseObject setRelationalData(String tableName, HashMap columnsNameAndValuesHM, BaseParseListener baseParseListener)
    {
        ParseObject parseTable = new ParseObject(tableName);
        return initParseTable(parseTable, columnsNameAndValuesHM);
    }

    public ParseObject setRelation(String parentColumn, ParseObject parentObject, ParseObject childObject){

    }

    public boolean saveRelation(ParseObject childOject){

    }

    public boolean deleteDataGeneric(String tableName, String columnToCompare, String colunmValue){

    }

    public boolean deleteDataBaseOnUniqueKey(String tableName, String uniqueColumn, String uniqueKey, String columnToCompare, String columnValue){

    }

    public boolean updateDataGeneric(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String colunmValue){

    }

    public boolean updateDataBaseOnUserKey(String tableName, String uniqueColumn, String uniqueKey, String columnToUpdate, String updateValue, String columnToCompare, String colunmValue){

    }
/*
    public void signUp(String firstName, String lastName, String emailAddress, int addressFK, String birthday, String gender, String country, BaseParseListener baseParseListener) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
          // do stuff with the user
        } else {
          // show the signup or login screen
        }

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
                    baseParseListener.successful();
                } else {
                    baseParseListener.unsuccessful();
                }
            }
        });
    }
*/
    public boolean logout() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            return true;
        } else {
            return false;
        }
    }

    public void login(String username, String password, BaseParseListener baseParseListener) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    baseParseListener.successful();
                } else {
                    baseParseListener.unsuccessful();
                }
            }
        });
    }

    public void resetPassword(String email, BaseParseListener baseParseListener) {
        ParseUser.requestPasswordResetInBackground(email,
                new RequestPasswordResetCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            this.successful();
                        } else {
                            this.unsuccessful();
                        }
                    }
                });
    }

    //	private void removeAccount(int username){
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
