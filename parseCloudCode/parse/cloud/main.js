/* --------------------- Constants ------------------------- */
var USER_NOT_EXISTS_CONSTANT = "User does not exists.";
var LOGOUT_FAILED_EXISTS_CONSTANT = "Log out failed.";
var LOGIN_FAILED_EXISTS_CONSTANT = "Log in failed.";
var RECORD_FOUND_CONSTANT = "Record found.";
var RECORD_NOT_FOUND_CONSTANT = "No record found.";
var RECORD_UPDATED_CONSTANT = "Record updated.";
var COLUMN_DOES_NOT_EXISTS_CONSTANT = "Column does not exists.";
var CHAINED_FUNCTION_FAILED_CONSTANT = "Chained function failed.";
var TABLE_NOT_EXISTS_CONSTANT = "Table does not exists.";
var USER_REGISTER_FAILED_CONSTANT = "User register failed.";
var USER_REGISTER_SUCCESS_CONSTANT = "User register success.";
 
/* ----------------- End of Constants ---------------------- */
 
//Delete
// Parse.Cloud.define("deleteRecordInTable", function(request,response){
//     var tableName = Parse.Object.extend(request.params.tableName);
//     var column = request.params.columnName;
//     var columnValue = request.params.columnValue;
 
//     var query = new Parse.Query(tableName);
//     query.equalTo(column,columnValue);
//     query.find({
//         success: function(results) {
//             Parse.Object.destroyAll(results, {
//                 success: function(myObject){
//                     response.success(true);
//                 },
//                 error: function(myObject,error){
//                     response.error("Error " + error.code + " : " + error.message);
//                 }
//             });//destory
//         },
//         error: function(error) {
//           response.error("Error " + error.code + " : " + error.message);
//         }
//      });
// });

Parse.Cloud.define("updateEventDetails", function(request,response){
    var _tableName = Parse.Object.extend(request.params.tableName);
    var _key = request.params.key;

    var _eventDescription = request.params.EventDescription;
    var _eventVenue = request.params.EventVenue;
    var _eventNote = request.params.EventNote;
    var _eventLocation = new Parse.GeoPoint(request.params.EventLocationLongitude,request.params.EventLocationLatitude);
    var _eventRecurrence = request.params.EventRecurrence;
    var _eventStartDateTime = request.params.EventStartDateTime;
    var _eventEndDateTime = request.params.EventEndDateTime;
 
    var query = new Parse.Query(_tableName);
    query.get(_key, {
        success: function(results) {
            results[0].set("EventDescription",_eventDescription);
            results[0].set("EventVenue",_eventVenue);
            results[0].set("EventNote",_eventNote);
            results[0].set("EventLocation",_eventLocation);
            results[0].set("EventRecurrence",_eventRecurrence);
            results[0].set("EventStartDateTime",_eventStartDateTime);
            results[0].set("EventEndDateTime",_eventEndDateTime);
            results[0].save(null, {
                success: function(results){
                    response.success(true);
                },
                error: function(results,error){
                    response.error("Error " + error.code + " : " + error.message);
                }
            });
        },
        error: function(error) {
          response.error("Error " + error.code + " : " + error.message);
        }
    });
});

Parse.Cloud.define("updateEventAttendeeDetails", function(request,response){
    var _tableName = Parse.Object.extend(request.params.tableName);
    var _key = request.params.key;

    var _attendeeTask = request.params.AttendeeTask;
    var _attendeeSplittedBill = request.params.AttendeeSplittedBill;
    var _attendeeLocation = new Parse.GeoPoint(request.params.AttendeeLocationLongitude,request.params.AttendeeLocationLatitude);
    var _attendeeStatus = request.params.AttendeeStatus;
 
    var query = new Parse.Query(_tableName);
    query.get(_key, {
        success: function(results) {
            results[0].set("AttendeeTask",_attendeeTask);
            results[0].set("AttendeeSplittedBill",_attendeeSplittedBill);
            results[0].set("AttendeeLocation",_attendeeLocation);
            results[0].set("AttendeeStatus",_attendeeStatus);
            results[0].save(null, {
                success: function(results){
                    response.success(true);
                },
                error: function(results,error){
                    response.error("Error " + error.code + " : " + error.message);
                }
            });
        },
        error: function(error) {
          response.error("Error " + error.code + " : " + error.message);
        }
    });
});
 
 Parse.Cloud.define("getEventAttendee", function(request,response){
    var _tableName = Parse.Object.extend(request.params.tableName);
    // var _key = request.params.key;
 
    var nameArray = [];
    var statusArray = [];
 
    var query = new Parse.Query(_tableName);
    query.get(/*_key, */{
        success: function(results) {
            for(i = 0; i < results.length; i++){
                statusArray[i] = results[i].get("Status");
                nameArray[i] = results[i].get("AttendeeName")   
            }
            var jsonName = JSON.parse(nameArray);
            var jsonStatus = JSON.parse(statusArray);
            var finalData = jsonName.concat(jsonStatus);
            response.success(finalData);
        },
        error: function(error) {
          response.error("Error " + error.code + " : " + error.message);
        }
    });
});

// Parse.Cloud.define("insertUsersIntoEvent", function(request,response){
//     var _tableName = Parse.Object.extend(request.params.tableName);
//     var _column = request.params.columnName;
//     var _columnValue = request.params.columnValue;
//     var _updateColumn = request.params.updateColumn;
//     var _updateValue = request.params.updateValue;
//     var userId = request.params.userId;
 
//     //query parse.user
//     var query = new Parse.Query(Parse.User);
//     query.equalTo("objectId", userId);
//     query.find({
//       success: function(user) {
//         var userDetails = new Array(user.id,user.nickname);
 
//         Parse.Cloud.run("updateRecordInTable", {tableName:_tableName,columnName:_column,columnValue:_columnValue,updateColumn:_updateColumn,updateValue:_updateValue},{
//             success: function(resultsUser){
//                 response.success(true);
//             },
//             error: function(resultsUser,error){
//                 response.error("Error " + error.code + " : " + error.message);
//             }
//         });
//       },
//       error: function(error){
//         response.error("Error " + error.code + " : " + error.message);
//       }
//     });
// });