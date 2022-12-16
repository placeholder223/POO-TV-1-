import java.util.ArrayList;

public class Filters {
   private Sort sort;
   private Contains contains;

   public Sort getSort() {
      return sort;
   }

   public void setSort(Sort sort) {
      this.sort = sort;
   }

   public Contains getContains() {
      return contains;
   }

   public void setContains(Contains contains) {
      this.contains = contains;
   }
}
class Sort{
   private String rating;
   private String duration;

   public String getRating() {
      return rating;
   }

   public void setRating(String rating) {
      this.rating = rating;
   }

   public String getDuration() {
      return duration;
   }

   public void setDuration(String duration) {
      this.duration = duration;
   }
}
class Contains{
   private ArrayList<String> actors;
   private ArrayList<String> genre;

   public ArrayList<String> getActors() {
      return actors;
   }

   public void setActors(ArrayList<String> actors) {
      this.actors = actors;
   }

   public ArrayList<String> getGenre() {
      return genre;
   }

   public void setGenre(ArrayList<String> genre) {
      this.genre = genre;
   }
}
