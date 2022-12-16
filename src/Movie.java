import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.Hashtable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
   public static final int NUM_TOKENS_FOR_BUYING = 2;
   private String name;
   private Integer year;
   private Integer duration;
   private ArrayList<String> genres;
   private ArrayList<String> actors;
   private ArrayList<String> countriesBanned;
   private Integer numLikes;
   private double rating;
   private Integer numRatings;
   @JsonIgnore
   private Hashtable<User, Integer> ratings;

   public void addRating(User user, Integer rating) {
      //System.out.println("prob addRating not finished");
      this.ratings.put(user, rating);
      this.numRatings++;
      this.rating
            = ((double) ratings.values().stream().mapToInt(Integer::intValue).sum())
            / (double) this.numRatings;
   }

   public Movie() {
      this.numLikes = 0;
      this.rating = 0;
      this.numRatings = 0;
      this.ratings = new Hashtable<User, Integer>();
   }

   public Movie(Movie other) {
      this.name = other.name;
      this.year = other.year;
      this.duration = other.duration;
      this.genres = other.genres;
      this.actors = other.actors;
      this.countriesBanned = other.countriesBanned;
      this.numLikes = other.numLikes;
      this.rating = other.rating;
      this.numRatings = other.numRatings;
      this.ratings = other.ratings;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Integer getYear() {
      return year;
   }

   public void setYear(Integer year) {
      this.year = year;
   }

   public Integer getDuration() {
      return duration;
   }

   public void setDuration(Integer duration) {
      this.duration = duration;
   }

   public ArrayList<String> getGenres() {
      return genres;
   }

   public void setGenres(ArrayList<String> genres) {
      this.genres = genres;
   }

   public ArrayList<String> getActors() {
      return actors;
   }

   public void setActors(ArrayList<String> actors) {
      this.actors = actors;
   }

   public ArrayList<String> getCountriesBanned() {
      return countriesBanned;
   }

   public void setCountriesBanned(ArrayList<String> countriesBanned) {
      this.countriesBanned = countriesBanned;
   }

   public Integer getNumLikes() {
      return numLikes;
   }

   public void setNumLikes(Integer numLikes) {
      this.numLikes = numLikes;
   }

   public double getRating() {
      return rating;
   }

   public void setRating(Double rating) {
      this.rating = rating;
   }

   public int getNumRatings() {
      return numRatings;
   }

   public void setNumRatings(Integer numRatings) {
      this.numRatings = numRatings;
   }

   public void addToNumLikes(int number) {
      this.numLikes += number;
   }

   public void addToNumRatings(int number) {
      this.numRatings += number;
   }

   @Override
   @JsonIgnore
   public String toString() {
      return "Movie{" +
            "name='" + name + '\'' +
            ", year=" + year +
            ", duration=" + duration +
            ", genres=" + genres +
            ", actors=" + actors +
            ", countriesBanned=" + countriesBanned +
            ", numLikes=" + numLikes +
            ", rating=" + rating +
            ", numRatings=" + numRatings +
            '}';
   }
}
