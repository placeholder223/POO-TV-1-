import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
   public static final int NUMBER_TOKENS_FOR_PREMIUM = 10;
   public static final int STARTING_FREE_MOVIES = 15;
   public static final String PREMIUM_STATUS = "premium";
   private Credentials credentials;
   private Integer tokensCount = 0;
   private Integer numFreePremiumMovies = STARTING_FREE_MOVIES;
   private ArrayList<Movie> purchasedMovies;
   private ArrayList<Movie> watchedMovies;
   private ArrayList<Movie> likedMovies;
   private ArrayList<Movie> ratedMovies;

   public User() {
      this.credentials = new Credentials();
      this.purchasedMovies = new ArrayList<>();
      this.watchedMovies = new ArrayList<>();
      this.likedMovies = new ArrayList<>();
      this.ratedMovies = new ArrayList<>();
   }

   public User(User other) {
      if (other == null) {
         return;
      }
      this.credentials = new Credentials(other.credentials);
      this.tokensCount = other.tokensCount;
      this.numFreePremiumMovies = other.numFreePremiumMovies;
      this.purchasedMovies = new ArrayList<>();
      if (other.purchasedMovies != null)
         for (Movie movie : other.purchasedMovies) {
            this.purchasedMovies.add(new Movie(movie));
         }
      this.watchedMovies = new ArrayList<>();
      if (other.watchedMovies != null)
         for (Movie movie : other.watchedMovies) {
            this.watchedMovies.add(new Movie(movie));
         }
      this.likedMovies = new ArrayList<>();
      if (other.likedMovies != null)
         for (Movie movie : other.likedMovies) {
            this.likedMovies.add(new Movie(movie));
         }
      this.ratedMovies = new ArrayList<>();
      if (other.ratedMovies != null)
         for (Movie movie : other.ratedMovies) {
            this.ratedMovies.add(new Movie(movie));
         }
   }

   public User(Credentials other) {
      if (other == null) {
         return;
      }
      this.credentials = new Credentials(other);
      if (credentials.getAccountType().compareTo(PREMIUM_STATUS) == 0)
         this.numFreePremiumMovies = 15;
      this.purchasedMovies = new ArrayList<>();
      this.watchedMovies = new ArrayList<>();
      this.likedMovies = new ArrayList<>();
      this.ratedMovies = new ArrayList<>();
   }

   public Credentials getCredentials() {
      return credentials;
   }

   public void setCredentials(Credentials credentials) {
      this.credentials = new Credentials(credentials);
   }

   public Integer getTokensCount() {
      return tokensCount;
   }

   public void setTokensCount(Integer tokens) {
      this.tokensCount = tokens;
   }

   public Integer getNumFreePremiumMovies() {
      return numFreePremiumMovies;
   }

   public void setNumFreePremiumMovies(Integer numFreePremiumMovies) {
      this.numFreePremiumMovies = numFreePremiumMovies;
   }

   public ArrayList<Movie> getPurchasedMovies() {
      return purchasedMovies;
   }

   public void setPurchasedMovies(ArrayList<Movie> purchasedMovies) {
      this.purchasedMovies = purchasedMovies;
   }

   public ArrayList<Movie> getWatchedMovies() {
      return watchedMovies;
   }

   public void setWatchedMovies(ArrayList<Movie> watchedMovies) {
      this.watchedMovies = watchedMovies;
   }

   public ArrayList<Movie> getLikedMovies() {
      return likedMovies;
   }

   public void setLikedMovies(ArrayList<Movie> likedMovies) {
      this.likedMovies = likedMovies;
   }

   public ArrayList<Movie> getRatedMovies() {
      return ratedMovies;
   }

   public void setRatedMovies(ArrayList<Movie> ratedMovies) {
      this.ratedMovies = ratedMovies;
   }

   public void addToNumFreeMovies(int number) {
      this.numFreePremiumMovies += number;
   }

   public void addToTokensCount(int number) {
      this.tokensCount += number;
   }

   public Movie getFromWatchedMovies(String name) {
      for (Movie movie : watchedMovies) {
         if (movie.getName().equals(name))
            return movie;
      }
      return null;
   }

}
