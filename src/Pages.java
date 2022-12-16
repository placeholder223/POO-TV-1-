import java.util.ArrayList;

public class Pages {
   public static final String AUTHENTICATED_NOT = "not authenticated";
   public static final String AUTHENTICATED = "authenticated";
   public static final String LOGIN = "login";
   public static final String REGISTER = "register";
   public static final String SEARCH = "search";
   public static final String FILTER = "filter";
   public static final String PURCHASE = "purchase";
   public static final String WATCH = "watch";
   public static final String LIKE = "like";
   public static final String RATE = "rate";
   public static final String BUY_PREMIUM_ACCOUNT = "buy premium account";
   public static final String BUY_TOKENS = "buy tokens";
   private ArrayList<SubPages> changePage;

   public Pages() {
      this.changePage = new ArrayList<>();
      SubPages child = new SubPages();
      child.setName(AUTHENTICATED_NOT);
      this.changePage.add(child);
      child = new SubPages();
      child.setName("login");
      child.setOnPage(LOGIN);
      getHomepage(false).getChangePage().add(child);
      child = new SubPages();
      child.setName("register");
      child.setOnPage(REGISTER);
      getHomepage(false).getChangePage().add(child);
      getHomepage(false).getChangePage().add(getHomepage(false));
      SubPages curr = new SubPages();
      curr.setName(AUTHENTICATED);
      this.changePage.add(curr);
      child = new SubPages();
      child.setName("logout");
      SubPages logout = child;
      getHomepage(true).getChangePage().add(logout);
      child = new SubPages();
      child.setName("upgrades");
      SubPages upgrades = child; // saving it
      getHomepage(true).getChangePage().add(0, upgrades);
      upgrades.getChangePage().add(upgrades);
      upgrades.getChangePage().add(getHomepage(true));
      upgrades.getChangePage().add(logout);
      upgrades.setOnPage(BUY_PREMIUM_ACCOUNT);
      upgrades.setOnPage(BUY_TOKENS);
      upgrades.setOnPage(PURCHASE);
      child = new SubPages();
      child.setName("movies");
      getHomepage(true).getChangePage().add(0,child);
      getHomepage(true).getChangePage().add(upgrades);
      SubPages movies = child;
      movies.getChangePage().add(getHomepage(true));
      movies.getChangePage().add(movies);
      movies.setOnPage(SEARCH);
      movies.setOnPage(FILTER);
      movies.getChangePage().add(logout);
      child = new SubPages();
      child.setName("see details");
      movies.getChangePage().add(child);
      SubPages seeDetails = child;
      seeDetails.getChangePage().add(seeDetails);
      seeDetails.getChangePage().add(getHomepage(true));
      seeDetails.getChangePage().add(movies);
      seeDetails.getChangePage().add(upgrades);
      seeDetails.getChangePage().add(logout);
      seeDetails.getOnPage().add(PURCHASE);
      seeDetails.getOnPage().add(WATCH);
      seeDetails.getOnPage().add(LIKE);
      seeDetails.getOnPage().add(RATE);
      upgrades.getChangePage().add(movies);
   }

   public ArrayList<SubPages> getChangePage() {
      return changePage;
   }

   public SubPages getHomepage(boolean isAuthenticated) {
      if (isAuthenticated)
         return this.changePage.get(1);
      return this.changePage.get(0);
   }
}
