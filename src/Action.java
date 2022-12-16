import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Action {
   public static final String ON_PAGE = "on page";

   public Action() {

   }

   private String type;
   private String feature;
   private String movie;
   private Credentials credentials;
   private String startsWith;
   private Filters filters;
   private String count;
   private String page;
   private Integer rate;

   public String getType() {
      return type;
   }

   public void setType(final String type) {
      this.type = type;
   }

   public String getFeature() {
      return feature;
   }

   public void setFeature(final String feature) {
      this.feature = feature;
   }

   public String getMovie() {
      return movie;
   }

   public void setMovie(final String movie) {
      this.movie = movie;
   }

   public Credentials getCredentials() {
      return credentials;
   }

   public void setCredentials(Credentials credentials) {
      this.credentials = credentials;
   }

   public String getStartsWith() {
      return startsWith;
   }

   public void setStartsWith(final String startsWith) {
      this.startsWith = startsWith;
   }

   public Filters getFilters() {
      return filters;
   }

   public void setFilters(final Filters filters) {
      this.filters = filters;
   }

   public String getCount() {
      return count;
   }

   public void setCount(final String count) {
      this.count = count;
   }

   public Integer getRate() {
      return rate;
   }

   public void setRate(final Integer rate) {
      this.rate = rate;
   }

   public String getPage() {
      return page;
   }

   public void setPage(final String page) {
      this.page = page;
   }
}
