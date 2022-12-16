import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class OnPageActions {

   private boolean needsChange;
   private SubPages currPage;
   private User currUser;

   private ArrayList<Movie> currMovies;
   private static String detailedMovie;

   public static void setDetailedMovie(String detailedMovie) {
      OnPageActions.detailedMovie = detailedMovie;
   }

   public OnPageActions() {

   }

   public void onPageActions(ObjectNode temp, Action action, User currentUser,
                             SubPages currentPage, Pages pages, boolean isAuthenticated,
                             UserDatabase userDatabase, MovieDatabase movieDatabase) {
      this.currUser = currentUser;
      this.currPage = currentPage;
      this.needsChange = false;
      Errors errors = new Errors();
      switch (action.getFeature()) {
         case "login" -> {
            String name = action.getCredentials().getName();
            String password = action.getCredentials().getPassword();
            if (userDatabase.isLoginValid(name, password)) {
               this.currUser = userDatabase.retrieveUser(name, password);
               this.currPage = pages.getHomepage(true);
               ArrayList<Movie> auxEmptyList = new ArrayList<>();

               User copyCurrentUser = new User(currUser);

               needsChange = errors.errorOutput(null, auxEmptyList, copyCurrentUser, temp);
               movieDatabase.getAvailableMovies(currUser);

            } else {
               this.currPage = pages.getHomepage(false);


               ArrayList<Movie> auxEmptyList = new ArrayList<>();
               User copyCurrentUser = null;
               needsChange = errors.errorOutput(Errors.ERROR, auxEmptyList, copyCurrentUser, temp);
            }
         }
         case "register" -> {
            User newUser = new User(action.getCredentials());
            userDatabase.addUserToHash(newUser);

            ArrayList<Movie> auxEmptyList = new ArrayList<>();
            User copyCurrentUser = new User(newUser);

            needsChange = errors.errorOutput(null, auxEmptyList, copyCurrentUser, temp);
            this.currPage = pages.getHomepage(true);
            this.currUser = newUser;
            userDatabase.addUser(newUser);
            movieDatabase.getAvailableMovies(newUser);
         }
         case "buy premium account" -> {
            int tokens = currentUser.getTokensCount();
            if (tokens < User.NUMBER_TOKENS_FOR_PREMIUM
                  || currentUser.getCredentials().getAccountType().equals(User.PREMIUM_STATUS)) {


               ArrayList<Movie> copyMovies = new ArrayList<>();
               for (Movie movie : movieDatabase.getCurrentMovies()) {
                  copyMovies.add(new Movie(movie));
               }
               User copyCurrentUser = new User(currentUser);
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser, temp);

               break;
            }
            currentUser.addToTokensCount(-User.NUMBER_TOKENS_FOR_PREMIUM);
            currentUser.getCredentials().setAccountType(User.PREMIUM_STATUS);

         }
         case "buy tokens" -> {
            int balance = Integer.parseInt(currentUser.getCredentials().getBalance());
            int boughtCount = Integer.parseInt(action.getCount());
            if (balance < boughtCount) {

               ArrayList<Movie> copyMovies = new ArrayList<>();
               for (Movie movie : movieDatabase.getCurrentMovies()) {
                  copyMovies.add(new Movie(movie));
               }
               User copyCurrentUser = new User(currentUser);
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser, temp);
               break;
            }
            balance -= boughtCount;
            currentUser.getCredentials().setBalance(Integer.toString(balance));
            currentUser.addToTokensCount(Integer.parseInt(action.getCount()));
         }
         case "search" -> {
            ArrayList<Movie> searchedMovies = movieDatabase.search(action.getStartsWith());
            User copyCurrentUser = new User(currentUser);
            needsChange = errors.errorOutput(null, searchedMovies,
                  copyCurrentUser, temp);
         }

         case "filter" -> {
            movieDatabase.getAvailableMovies(currentUser);
            if (action.getFilters().getContains() != null) {
               movieDatabase.contains(action.getFilters().getContains());
            }
            if (action.getFilters().getSort() != null) {
               movieDatabase.filter(action.getFilters().getSort().getDuration(),
                     action.getFilters().getSort().getRating());
            }
            User copyCurrentUser = new User(currentUser);
            ArrayList<Movie> copyMovies = new ArrayList<>();
            for (Movie movie : movieDatabase.getCurrentMovies()) {
               copyMovies.add(new Movie(movie));
            }
            needsChange = errors.errorOutput(null, copyMovies,
                  copyCurrentUser, temp);
         }
         case "purchase" -> {
            Movie wantedMovie;
            if (action.getMovie() != null) {
               wantedMovie = movieDatabase.get(action.getMovie());
            } else {
               wantedMovie = movieDatabase.get(detailedMovie);
            }
            if (wantedMovie == null || !movieDatabase.getCurrentMovies().contains(wantedMovie)) {

               ArrayList<Movie> copyMovies = new ArrayList<>();
               for (Movie movie : movieDatabase.getCurrentMovies()) {
                  copyMovies.add(new Movie(movie));
               }
               User copyCurrentUser = new User(currentUser);

               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser, temp);
               break;
            }


            if (!currentUser.getCredentials().getAccountType().equals(User.PREMIUM_STATUS)) {
               currentUser.addToNumFreeMovies(-User.STARTING_FREE_MOVIES);
            }
            boolean hasUsedFreeMovie = false;
            if (currentUser.getNumFreePremiumMovies() > 0) {
               currentUser.addToNumFreeMovies(-1);
               currentUser.getPurchasedMovies().add(wantedMovie);
               hasUsedFreeMovie = true;
            }
            if (!currentUser.getCredentials().getAccountType().equals(User.PREMIUM_STATUS)) {
               currentUser.addToNumFreeMovies(User.STARTING_FREE_MOVIES);
            }
            if (hasUsedFreeMovie) {
               ArrayList<Movie> copyMovies = new ArrayList<>();
               copyMovies.add(new Movie(wantedMovie));
               User copyCurrentUser = new User(currentUser);
               needsChange = errors.errorOutput(null, copyMovies, copyCurrentUser, temp);
               break;
            }
            if (currentUser.getTokensCount() >= Movie.NUM_TOKENS_FOR_BUYING) {
               currentUser.addToTokensCount(-Movie.NUM_TOKENS_FOR_BUYING);
               currentUser.getPurchasedMovies().add(wantedMovie);

               ArrayList<Movie> copyMovies = new ArrayList<>();
               copyMovies.add(new Movie(wantedMovie));
               User copyCurrentUser = new User(currentUser);
               needsChange = errors.errorOutput(null, copyMovies, copyCurrentUser, temp);
            } else {

               ArrayList<Movie> copyMovies = new ArrayList<>();
               for (Movie movie : movieDatabase.getCurrentMovies()) {
                  copyMovies.add(new Movie(movie));
               }
               User copyCurrentUser = new User(currentUser);
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser, temp);
            }
         }
         case "watch" -> {
            Movie wantedMovie;
            if (action.getMovie() != null) {
               wantedMovie = movieDatabase.get(action.getMovie());
            } else {
               wantedMovie = movieDatabase.get(detailedMovie);
            }
            if (wantedMovie == null || !currentUser.getPurchasedMovies().contains(wantedMovie)) {

               ArrayList<Movie> copyMovies = new ArrayList<>();
               User copyCurrentUser = null;
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser,
                     temp);
               break;
            }


            currentUser.getWatchedMovies().add(wantedMovie);

            ArrayList<Movie> copyMovies = new ArrayList<>();
            copyMovies.add(new Movie(wantedMovie));
            User copyCurrentUser = new User(currentUser);
            needsChange = errors.errorOutput(null, copyMovies, copyCurrentUser, temp);
         }
         case "like" -> {
            Movie wantedMovie;
            if (action.getMovie() != null) {
               wantedMovie = movieDatabase.get(action.getMovie());
            } else {
               wantedMovie = movieDatabase.get(detailedMovie);
            }
            if (wantedMovie == null || !currUser.getWatchedMovies().contains(wantedMovie)) {
               ArrayList<Movie> copyMovies = new ArrayList<>();
               User copyCurrentUser = null;
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser,
                     temp);
               break;
            }
            currUser.getLikedMovies().add(wantedMovie);
            wantedMovie.addToNumLikes(1);
            ArrayList<Movie> copyMovies = new ArrayList<>();
            copyMovies.add(new Movie(wantedMovie));
            User copyCurrentUser = new User(currentUser);
            needsChange = errors.errorOutput(null, copyMovies, copyCurrentUser, temp);
         }
         case "rate" -> {
            Movie wantedMovie;
            if (action.getMovie() != null) {
               wantedMovie = movieDatabase.get(action.getMovie());
            } else {
               wantedMovie = movieDatabase.get(detailedMovie);
            }
            if (wantedMovie == null || !currUser.getWatchedMovies().contains(wantedMovie)
                  || action.getRate() > 5 || action.getRate() < 0) {
               ArrayList<Movie> copyMovies = new ArrayList<>();
               User copyCurrentUser = null;
               needsChange = errors.errorOutput(Errors.ERROR, copyMovies, copyCurrentUser,
                     temp);
               break;
            }


            currentUser.getRatedMovies().add(wantedMovie);
            wantedMovie.addRating(currentUser, action.getRate());
            ArrayList<Movie> copyMovies = new ArrayList<>();
            copyMovies.add(new Movie(wantedMovie));
            User copyCurrentUser = new User(currentUser);
            needsChange = errors.errorOutput(null, copyMovies, copyCurrentUser, temp);


         }
         default -> {
            System.out.println("hatz");
         }
      }
   }

   public boolean getNeedsChange() {
      return needsChange;
   }

   public SubPages getCurrPage() {
      return currPage;
   }

   public User getCurrUser() {
      return currUser;
   }
}
