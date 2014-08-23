package com.typee.typee.network.base;

// import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

// import java.util.HashMap;
import java.util.Iterator;
// import java.util.List;
import java.util.Map;
// import java.util.Set;

public class BaseParseService {
	public final String TAG = this.getClass().getSimpleName();
	public static final String usernameKey = "username";
	private static BaseParseService instance;

	public static BaseParseService getBaseParseService() {
		if (instance == null)
			instance = new BaseParseService();

		return instance;
	}

	public void insertRowIntoParseTable(String tableName,Map<String, String> columnsNameAndValues,final SuccessListener successListener) {
		ParseObject parseTable = new ParseObject(tableName);
		// Set set = columnsNameAndValues.entrySet();
		Iterator i = set.entrySet().iterator();

		while (i.hasNext()) {
			Map.Entry entry = (Map.Entry) i.next();
			parseTable.put((String) entry.getKey(), entry.getValue());
		}
		parseTable.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					// FAILURE
					successListener.unsuccessful(e);
				} else{
				// SUCCESS
					successListener.successful();
				}
			}
		});
	}

	public void deleteObject(String objectID,String tableName,final EventsParseListener eventsParseListener) {
		Map<String,String> map = new HashMap();
		map.put("key",objectID);
		map.put("tableName",tableName);
		
        ParseCloud.callFunctionInBackground("deleteEvent", map, new FunctionCallback<String>() {
            public void done(String object, ParseException e) {
                if (e == null) {
                    eventsParseListener.successful();
                } else {
                    eventsParseListener.unsuccessful(e);
                }
            }
        });
	}

	// public void setData(String tableName, HashMap columnsNameAndValuesHM) throws ParseException {
	// 	ParseObject parseTable = new ParseObject(tableName);
	// 	parseTable = initParseTable(parseTable, columnsNameAndValuesHM);
	// 	parseTable.save();
	// }

	// public ParseObject setRelationalData(String tableName, HashMap columnsNameAndValuesHM) {
	// 	ParseObject parseTable = new ParseObject(tableName);
	// 	return initParseTable(parseTable, columnsNameAndValuesHM);
	// }

	// public ParseObject setRelation(String parentColumn, ParseObject parentObject, ParseObject childObject) {
	// 	return null;
	// }

	// public void saveRelation(ParseObject childObject) {

	// }

	// public List<ParseObject> getAllTableValues(String tableName) throws ParseException {
	// 	ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
	// 	return query.find();
	// }

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

	

	/*
	//Do not need for now
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

	public void resetPassword(String email, final ErrorListener errorListener) {
		ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
			public void done(ParseException e) {
				if (e == null) {
					errorListener.successful();
				} else {
					errorListener.unsuccessful(e);
				}
			}
		});
	}

	  private void removeAccount(int username){
	      final ParseQuery<ParseObject> query = ParseQuery.getQuery("UserInformations");

	      query.whereEqualTo("Username", username);
	      // Retrieve the object by id
	      query.findInBackground(new FindCallback<ParseObject>() {
	          public void done(List<ParseObject> queryData, ParseException e) {
	              if (e == null) {
	                  //only one result is expected to return
	                  queryData.get(0).deleteInBackground();
	              } else {
	                  Log.d("score", "Error: " + e.getMessage());
	              }
	          }
	      });

	  }
	  */
}
