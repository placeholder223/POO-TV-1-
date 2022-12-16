import java.util.ArrayList;

public class MovieDatabase {
   private static MovieDatabase instance = null;
   private ArrayList<Movie> moviesTotal;
   private ArrayList<Movie> currentMovies;

   private MovieDatabase() {
      moviesTotal = new ArrayList<>();
      currentMovies = new ArrayList<>();
   }

   public static MovieDatabase getInstance() {
      if (instance == null) {
         instance = new MovieDatabase();
      }
      return instance;
   }

   public Movie get(String name) {
      for (Movie movie : currentMovies) {
         if (movie.getName().equals(name)) {
            return movie;
         }
      }
      return null;
   }

   public ArrayList<Movie> search(String prefix) {
      ArrayList<Movie> searchedMovies = new ArrayList<>();
      for (Movie movie : currentMovies) {
         if (movie.getName().startsWith(prefix)) {
            searchedMovies.add(movie);
         }
      }
      return searchedMovies;
   }

   public void setMovies(ArrayList<Movie> newMovies) {
      moviesTotal = newMovies;
   }

   public void getAvailableMovies(User user) {
      currentMovies = new ArrayList<>();
      for (Movie movie : moviesTotal) {
         if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
            currentMovies.add(movie);
         }
      }
   }

   public ArrayList<Movie> getCurrentMovies() {
      return currentMovies;
   }

   public ArrayList<Movie> getMoviesTotal() {
      return moviesTotal;
   }

   public void destroyDatabase() {
      instance = null;
   }

   public void contains(Contains criterias) {
      ArrayList<Movie> containedMovies = new ArrayList<>();
      for (Movie movie : currentMovies) {
         if (criterias.getActors() != null) {
            if (movie.getActors().containsAll(criterias.getActors())) {
               if (criterias.getGenre() != null) {
                  if (movie.getGenres().containsAll(criterias.getGenre())) {
                     containedMovies.add(movie);
                  }
               } else {
                  containedMovies.add(movie);
               }
            }
         } else {
            if (movie.getGenres().containsAll(criterias.getGenre())) {
               containedMovies.add(movie);
            }
         }
      }
      currentMovies = containedMovies;
   }

   public void filter(String duration, String rating) {
      if (duration == null) {
         // sort by rating only
         boolean ratingAscending = rating.equals("increasing");
         currentMovies.sort((movie1, movie2) -> {
            double rating1 = movie1.getRating();
            double rating2 = movie2.getRating();
            if (rating1 < rating2) return ratingAscending ? -1 : 1;
            else if (rating1 > rating2) return ratingAscending ? 1 : -1;
            else return 0;
         });
      } else if (rating == null) {
         // sort by duration only
         boolean durationAscending = duration.equals("increasing");
         currentMovies.sort((movie1, movie2) -> {
            int duration1 = movie1.getDuration();
            int duration2 = movie2.getDuration();
            if (duration1 < duration2) return durationAscending ? -1 : 1;
            else if (duration1 > duration2) return durationAscending ? 1 : -1;
            else return 0;
         });
      } else {
         // sort by both duration and rating
         boolean durationAscending = duration.equals("increasing");
         boolean ratingAscending = rating.equals("increasing");
         currentMovies.sort((movie1, movie2) -> {
            int duration1 = movie1.getDuration();
            double rating1 = movie1.getRating();
            int duration2 = movie2.getDuration();
            double rating2 = movie2.getRating();
            if (duration1 < duration2) return durationAscending ? -1 : 1;
            else if (duration1 > duration2) return durationAscending ? 1 : -1;
            else {
               if (rating1 < rating2) return ratingAscending ? -1 : 1;
               else if (rating1 > rating2) return ratingAscending ? 1 : -1;
               else {
                  return 0;
               }
            }
         });
      }
   }

}
