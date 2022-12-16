import java.util.ArrayList;

public class SubPages {
   private String name;
   private ArrayList<String> onPage;
   private ArrayList<SubPages> changePage;

   SubPages() {
      this.onPage = new ArrayList<>();
      this.changePage = new ArrayList<>();
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public ArrayList<String> getOnPage() {
      return onPage;
   }

   public void setOnPage(ArrayList<String> onPage) {
      this.onPage.addAll(onPage);
   }

   public void setOnPage(String onPage) {
      this.onPage.add(onPage);
   }

   public ArrayList<SubPages> getChangePage() {
      return changePage;
   }

   public void setChangePage(ArrayList<SubPages> changePage) {
      this.changePage = changePage;
   }

   public SubPages changeOnSubPage(String name) {
      for (SubPages subPage : this.changePage) {
         if (subPage.getName().compareTo(name) == 0)
            return subPage;
      }
      return null;
   }

   @Override
   public String toString() {
      String allOnPage = new String();
      for (String s : onPage) {
         allOnPage += s + " ";
      }
      String allChangePage = new String();
      for (SubPages s : changePage) {
         allChangePage += s.getName() + " ";
      }
      return "SubPages{" +
            "name='" + name + '\n' +
            ", onPage=" + allOnPage + "\n" +
            ", changePage=" + allChangePage +
            '}';
   }
}
