import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.json.JSONArray;
//needs open api and json dependencies

public class ChatbotEngine {

    static String apiKey = "";


    public static void main(String[] args) throws Exception {

        apiKey = loadEnv("APIKEY.env");

        System.out.println(createResponse("how many turtles in ocean?"));

    }
    /**
     * creates a response to the given input string using open ai api calls
     *
     * @param userInput : the message to generate a response to
     *
     * @return a string response from chatbot
     */
    public static String createResponse(String userInput) throws Exception{

        String body = String.format("""
        {
          "model": "gpt-4",
          "messages": [{"role": "user", "content": "%s"}]
        }
        """,userInput);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();

        JSONObject obj = new JSONObject(responseBody);
        JSONArray choices = obj.getJSONArray("choices");
        JSONObject message = choices.getJSONObject(0).getJSONObject("message");
        return message.getString("content");

    }
    /**
     *
     * loads the api key from a .env file
     *
     * @param filePath path to the file
     *
     * @return api key
     */

    public static String loadEnv(String filePath) throws Exception {
        String env = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            env = line.substring(8).strip();
        }

        return env;
    }
}
    
