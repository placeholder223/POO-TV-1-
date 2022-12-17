import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import databases.MovieDatabase;
import databases.UserDatabase;
import error.handling.Errors;
import resources.primary.Action;
import resources.primary.Pages;
import resources.primary.SubPages;
import resources.primary.User;

public final class WhereIs {

   /**
    * default constructor
    */
   private WhereIs() {

   }

   /**
    * basically the main handler, I initialiaze the user and movie database
    *
    * @param inputData where all the input information is
    * @param objMapper pretty self explanatory
    * @param output    where the output is
    */
   public static void thePopcorn(final Input inputData, final ObjectMapper objMapper,
                                 final ArrayNode output) {

      UserDatabase userDatabase = UserDatabase.getInstance();
      userDatabase.setUsers(inputData.getUsers());
      userDatabase.getUsers().forEach(userDatabase::addUserToHash);

      MovieDatabase movieDatabase = MovieDatabase.getInstance();
      movieDatabase.setMovies(inputData.getMovies());

      Pages pages = new Pages(); // the page hierarchy
      User currUser = null; // there is no current user
      SubPages currPage = pages.getHomepage(false); // the current page is the
      // not authenticated one
      for (Action action : inputData.getActions()) { // we take each action and handle it
         boolean needsChange; // whether or not the output needs to be updated
         ObjectNode temp = objMapper.createObjectNode();
         if (action.getType().compareTo(Action.ON_PAGE) == 0) {
            if (!currPage.getOnPage().contains(action.getFeature())) {
               // if there's no action with the given name on the current page
               needsChange = Errors.errorOutput(Errors.ERROR, null, null, temp);
            } else {
               OnPageActions.onPageActions(temp, action, currUser, currPage, pages, userDatabase,
                     movieDatabase);
               needsChange = OnPageActions.getNeedsChange();
               currPage = OnPageActions.getCurrPage();
               currUser = OnPageActions.getCurrUser();
            }
         } else {
            // gets the subPage from the current page with the given name
            SubPages tempPage = currPage.changeOnSubPage(action.getPage());
            if (tempPage == null) {
               // if there's no subPage with the given name on the current page
               needsChange = Errors.errorOutput(Errors.ERROR, null, null, temp);
            } else {
               ChangePage.changePageActions(temp, action, currUser, currPage,
                     pages, movieDatabase, tempPage);
               needsChange = ChangePage.getNeedsChange();
               currPage = ChangePage.getActualPage();
               currUser = ChangePage.getCurrUser();
               if (action.getPage().equals("logout")) {
                  currUser = null;
               }
            }
         }

         if (needsChange) {
            output.add(temp);
         }
      }
   }
}
