import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Errors {

   public static final String ERROR = "Error";

   public boolean errorOutput(String error, ArrayList<Movie> movies, User user, ObjectNode temp) {
      temp.put("error", error);
      temp.putPOJO("currentMoviesList", movies);
      if (user != null) {
         temp.putPOJO("currentUser", user);
      } else {
         temp.put("currentUser", (String) null);
      }
      return true;
   }
}
