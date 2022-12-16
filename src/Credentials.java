import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credentials {
   private String name;
   private String password;
   private String accountType;
   private String Country;
   private String balance = "0";

   public Credentials() {
   }

   public Credentials(Credentials other) {
      this.name = other.name;
      this.password = other.password;
      this.accountType = other.accountType;
      this.Country = other.Country;
      this.balance = other.balance;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getAccountType() {
      return accountType;
      //return "hatz";
   }

   public void setAccountType(String accountType) {
      this.accountType = accountType;
   }

   public String getCountry() {
      return Country;
   }

   public void setCountry(String country) {
      Country = country;
   }

   public String getBalance() {
      return balance;
   }

   public void setBalance(String balance) {
      this.balance = balance;
   }
}
