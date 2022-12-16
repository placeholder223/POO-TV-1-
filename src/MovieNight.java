import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class MovieNight {

   public MovieNight() {

   }

   public void thePopcorn(Input inputData, ObjectMapper objMapper, ArrayNode output) {
      UserDatabase userDatabase = UserDatabase.getInstance();
      userDatabase.setUsers(inputData.getUsers());
      userDatabase.getUsers().forEach(userDatabase::addUserToHash);

      MovieDatabase movieDatabase = MovieDatabase.getInstance();
      movieDatabase.setMovies(inputData.getMovies());

      boolean isAuthenticated = false;
      Pages pages = new Pages();
      User currUser = null;
      SubPages currPage = pages.getHomepage(false);
      Errors errors = new Errors();
      OnPageActions onPageActions = new OnPageActions();
      ChangePage changePage = new ChangePage();
      for (Action action : inputData.getActions()) {
         boolean needsChange = false;
         ObjectNode temp = objMapper.createObjectNode();
         if (action.getType().compareTo(Action.ON_PAGE) == 0) {
            if (!currPage.getOnPage().contains(action.getFeature())) {

               ArrayList<Movie> auxEmptyList = new ArrayList<>();
               User copyCurrUser = null;
               needsChange = errors.errorOutput(Errors.ERROR, auxEmptyList, copyCurrUser, temp);
            } else {

               onPageActions.onPageActions(temp, action, currUser, currPage, pages, isAuthenticated
                     , userDatabase, movieDatabase);
               needsChange = onPageActions.getNeedsChange();
               currUser = onPageActions.getCurrUser();
               currPage = onPageActions.getCurrPage();
            }
         } else {
            SubPages tempPage = currPage.changeOnSubPage(action.getPage());
            if (tempPage == null) {

               ArrayList<Movie> auxEmptyList = new ArrayList<>();
               User copyCurrUser = null;


               needsChange = errors.errorOutput(Errors.ERROR, auxEmptyList, copyCurrUser, temp);
            } else {
               ArrayList<Movie> auxEmptyList = new ArrayList<>();
               User copyCurrUser = null;
               if (currUser != null)
                  copyCurrUser = new User(currUser);
               changePage.changePageActions(temp, action, copyCurrUser, currPage,
                     pages, movieDatabase, tempPage);
               needsChange = changePage.getNeedsChange();
               currPage = changePage.getActualPage();
               currUser = changePage.getCurrUser();
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
