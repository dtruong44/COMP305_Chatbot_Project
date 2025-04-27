public class AzureChatbot {
    
}
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class AzureChatBot {

    // DONT CHANGE, UPDATED WITH MY AZURE ACCOUNT
    static String endpoint = "https://305chatbot.openai.azure.com/";   
    static String apiKey = "WILL PUT API KEY HERE";
    static String deploymentName = "305chatbot";  // NOT the model name!
    static String apiVersion = "2023-05-15";

    public static String askBot(String userInput) throws Exception {
        String url = endpoint + "openai/deployments/" + deploymentName + "/chat/completions?api-version=" + apiVersion;

        JSONObject messageObj = new JSONObject();
        messageObj.put("role", "user");
        messageObj.put("content", userInput);

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."));
        messages.put(messageObj);

        JSONObject payload = new JSONObject();
        payload.put("messages", messages);
        payload.put("temperature", 0.7);
        payload.put("max_tokens", 100);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("api-key", apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject responseJson = new JSONObject(response.body());
        return responseJson.getJSONArray("choices")
                           .getJSONObject(0)
                           .getJSONObject("message")
                           .getString("content");
    }

    public static void main(String[] args) throws Exception {
        System.out.println(askBot("What is the capital of France?"));
    }
}
