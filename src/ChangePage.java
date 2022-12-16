import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class ChangePage {
   //use getter of actualPage as a second needsChange =
   private SubPages actualPage;
   private boolean needsChange;
   private User currUser;
   /**
    * initializer
    */
   public ChangePage() {

   }

   /**
    * if the page is one other than movies, see details or logout, it just changes the page
    *
    * @needsChange = it needsChange =s whether or not there needs to be any change in the json
    */
   public void changePageActions(final ObjectNode temp,final  Action action,
                                    final User currentUser, final SubPages currentPage,
                                    final Pages pages,final MovieDatabase movieDatabase,
                                    final SubPages tempPage) {
      User copyUser = new User(currentUser);
      Errors errors = new Errors();
      this.needsChange = false;
      this.currUser = currentUser;
      if (!action.getPage().equals("see details"))
         OnPageActions.setDetailedMovie(null);
      switch (action.getPage()) {
         case "movies" -> {
            this.actualPage = tempPage;
            movieDatabase.getAvailableMovies(currentUser);
            ArrayList<Movie> copyMovies = new ArrayList<>();
            for (Movie movie : movieDatabase.getCurrentMovies()) {
               copyMovies.add(new Movie(movie));
            }
            this.needsChange = errors.errorOutput(null, copyMovies, copyUser, temp);
         }
         case "see details" -> {
            for (Movie movie : movieDatabase.getCurrentMovies()) {
               if (movie.getName().equals(action.getMovie())) {
                  this.actualPage = tempPage;
                  ArrayList<Movie> copyMovies = new ArrayList<>();
                  copyMovies.add(new Movie(movie));
                  OnPageActions.setDetailedMovie(action.getMovie());
                  this.needsChange = errors.errorOutput(null, copyMovies, copyUser, temp);
                  break;
               }
            }
            if(needsChange)
               break;
            this.actualPage = currentPage;
            ArrayList<Movie> copyMovies = new ArrayList<>();
            this.needsChange = errors.errorOutput(Errors.ERROR, copyMovies, null, temp);
         }
         case "logout" -> {
            this.actualPage = pages.getHomepage(false);
            this.currUser = null;
         }
         default -> {
            this.actualPage = tempPage;
         }
      }
   }

   public SubPages getActualPage() {
      return actualPage;
   }
   public boolean getNeedsChange(){
      return needsChange;
   }

   public User getCurrUser() {
      return currUser;
   }
}
