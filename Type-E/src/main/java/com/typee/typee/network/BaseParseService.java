package com.typee.typee.network;

import com.parse.*;
import java.util.*;

public class BaseParseService{
	public static final String usernameKey = "username";

    private static BaseParseService instance;
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
            parseTable.put((String)entry.getKey(), entry.getValue());
        }
        return parseTable;
    }

    public void setData(String tableName, HashMap columnsNameAndValuesHM) throws ParseException {
        ParseObject parseTable = new ParseObject(tableName);
        parseTable = initParseTable(parseTable, columnsNameAndValuesHM);
        parseTable.save();
    }

    public ParseObject setRelationalData(String tableName, HashMap columnsNameAndValuesHM)
    {
        ParseObject parseTable = new ParseObject(tableName);
        return initParseTable(parseTable, columnsNameAndValuesHM);
    }

    public ParseObject setRelation(String parentColumn, ParseObject parentObject, ParseObject childObject){
        return null;
    }

    public void saveRelation(ParseObject childObject){

    }

    public List<ParseObject> getAllTableValues(String tableName) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        return query.find();
    }

    public void deleteData(String tableName, String columnToCompare, String columnValue) throws ParseException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tableName", tableName);
        params.put("columnName", columnToCompare);
        params.put("columnValue", columnValue);

        ParseCloud.callFunction("deleteRecordInTable", params);

//        ParseCloud.callFunctionInBackground("deleteRecordInTable", params, new FunctionCallback<String>() {
//            public void done(String object, ParseException e) {
//                if (e == null) {
//                    baseParseListener.successful(e);
//                } else {
//                    baseParseListener.unsuccessful(e);
//                }
//            }
//        });
    }

    public void updateColumnValue(String tableName, String columnToUpdate, String updateValue, String columnToCompare, String columnValue) throws ParseException {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tableName", tableName);
        params.put("columnName", columnToCompare);
        params.put("columnValue", columnValue);
        params.put("updateColumn", columnToUpdate);
        params.put("updateValue", updateValue);

        ParseCloud.callFunction("updateRecordInTable", params);
    }

//    public void queryData(String tableName, String columnToQuery, String columnValueToCompare){
//        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
//        query.whereEqualTo(columnToQuery, columnValueToCompare);
//        query.findInBackground(new FindCallback<ParseObject>() {
//            public void done(List<ParseObject> scoreList, ParseException e) {
//                if (e == null) {
//                    Log.d("score", "Retrieved " + scoreList.size() + " scores");
//                } else {
//                    Log.d("score", "Error: " + e.getMessage());
//                }
//            }
//        });
//
//    }

    public boolean logout() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            return true;
        } else {
            return false;
        }
    }

    public void login(String username, String password, final BaseParseListener baseParseListener) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    baseParseListener.successful();
                } else {
                    baseParseListener.unsuccessful(e);
                }
            }
        });
    }
    /*
    //NOT NEEDED
    public boolean changePassword(final String username, final String password, final String newPassword, BaseParseListener baseParseListener){
        ParseUser user = ParseUser.logIn(username, password);
        user.setPassword(newPassword);
        user.saveInBackground(new SaveCallback() {
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
    */
    public void resetPassword(String email, final BaseParseListener baseParseListener) {
        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    baseParseListener.successful();
                } else {
                    baseParseListener. unsuccessful(e);
                }
            }
        });
    }

    //  private void removeAccount(int username){
    //      final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInformations");
    //
    //      query.whereEqualTo("Username", username);
    //      // Retrieve the object by id
    //      query.findInBackground(new FindCallback<ParseObject>() {
    //          public void done(List<ParseObject> queryData, ParseException e) {
    //              if (e == null) {
    //                  //only one result is expected to return
    //                  queryData.get(0).deleteInBackground();
    //              } else {
    //                  Log.d("score", "Error: " + e.getMessage());
    //              }
    //          }
    //      });
    //
    //  }
}
