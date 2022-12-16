import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Hashtable;

public class UserDatabase {
   @JsonIgnore
   private static UserDatabase instance = null;
   private static ArrayList<User> users;
   @JsonIgnore
   private static Hashtable<String, String> checkingPassword;
   @JsonIgnore
   private static Hashtable<String, User> retrievingAccount;

   private UserDatabase() {
      users = new ArrayList<>();
      checkingPassword = new Hashtable<>();
      retrievingAccount = new Hashtable<>();
   }
   public static UserDatabase getInstance(){
      if( instance == null ){
         instance = new UserDatabase();
      }
      return instance;
   }

   @JsonIgnore
   public void addUserToHash(String name, String password) {
      checkingPassword.put(name, password);
   }
   @JsonIgnore
   public void addUserToHash(User user) {
      checkingPassword.put(user.getCredentials().getName(), user.getCredentials().getPassword());
      retrievingAccount.put(user.getCredentials().getName() + user.getCredentials().getPassword(),user);
      if(!users.contains(user))
         users.add(user);
   }
   @JsonIgnore
   public User retrieveUser(String name, String password){
      return retrievingAccount.get(name + password);
   }
   @JsonIgnore
   public boolean isLoginValid(String name, String password) {
      if (!checkingPassword.containsKey(name))
         return false;
      return password.compareTo(checkingPassword.get(name)) == 0;
   }

   public void setUsers(ArrayList<User> newUsers) {
      users = newUsers;
      for (User user : newUsers){
         addUserToHash(user);
      }
   }

   public ArrayList<User> getUsers() {
      return users;
   }
   public void addUser(User user){
      users.add(user);
   }

   public void destroyDatabase(){
      instance = new UserDatabase();
   }
}
