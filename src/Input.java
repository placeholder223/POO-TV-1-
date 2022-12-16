import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Input {
   private ArrayList<User> users;
   private ArrayList<Movie> movies;
   private ArrayList<Action> actions;

   public ArrayList<User> getUsers() {
      return users;
   }

   public void setUsers(ArrayList<User> users) {
      this.users = users;
   }

   public ArrayList<Movie> getMovies() {
      return movies;
   }

   public void setMovies(ArrayList<Movie> movies) {
      this.movies = movies;
   }

   public ArrayList<Action> getActions() {
      return actions;
   }

   public void setActions(ArrayList<Action> actions) {
      this.actions = actions;
   }
}
