import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;


public class Main {
   static Integer number = 1;

   public static void main(String[] args) throws IOException {
      ObjectMapper objectMapper = new ObjectMapper();
      ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
      ArrayNode output = objectMapper.createArrayNode();
      Input inputData = new Input();
      inputData = objectMapper.readValue(new File(args[0]),
            Input.class);
      MovieNight whereIs = new MovieNight();
      whereIs.thePopcorn(inputData, objectMapper, output);
      objectWriter.writeValue(new File(args[1]), output);
      String last5 = args[0].substring(args[0].length() - 6);
      if (last5.equals("0.json"))
         last5 = "10.json";
      objectWriter.writeValue(new File(CheckerFiles.RESULT_PATH + "out_basic_" + last5),
            output);
      UserDatabase userDatabase = UserDatabase.getInstance();
      userDatabase.destroyDatabase();
      MovieDatabase movieDatabase = MovieDatabase.getInstance();
      movieDatabase.destroyDatabase();
   }
}

