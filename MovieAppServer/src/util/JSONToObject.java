package util;

import bean.Recommendation;
import bean.User;
import bean.UserPreference;

import com.google.gson.Gson;

/**
 * 
 * Use the Gson the parse the json
 *
 */
public class JSONToObject {
  public User GsonToObject(String stringGson) {
    Gson gson = new Gson();
    User user = (User)gson.fromJson(stringGson, User.class);
    return user;
  }
  
  public String ObjectToGson(User user) {
    Gson gson = new Gson();
    String str = gson.toJson(user);
    return str;
  }
  
  public Recommendation GsonToRec(String stringGson) {
    Gson gson = new Gson();
    Recommendation rec = (Recommendation)gson.fromJson(stringGson, Recommendation.class);
    return rec;
  }
  
  public UserPreference GsonToUp(String stringGson) {
	    Gson gson = new Gson();
	    UserPreference up = (UserPreference)gson.fromJson(stringGson, UserPreference.class);
	    return up;
	  }
}
