import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProcessService {
    static ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode procesarTexto(JsonNode jsonNode) {

        String text = jsonNode.get("text").asText();
        String token = jsonNode.get("token").asText();

        String[] lineas = text.split("\n");

        ObjectNode result = objectMapper.createObjectNode();
        result.set("occurrences", objectMapper.createObjectNode());


        int totalOcurrencies = 0;

        for (int i = 0; i < lineas.length; i++) {
            String lineaActual = lineas[i];
            int indexActual = lineaActual.indexOf(token, 0);
            List<Integer> indices = new ArrayList<>();
            while (indexActual != -1) {
                totalOcurrencies++;
                indices.add(indexActual);
                indexActual = lineaActual.indexOf(token, indexActual + 1);
            }
            String key = "Line " + (i + 1);
            ((ObjectNode) result.get("occurrences")).putArray(key).add(Arrays.toString(indices.toArray()));
        }
        result.put("total_occurrences", totalOcurrencies);
        return result;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ProcessService test = new ProcessService();
        String jsonString = "{\n" +
                "\"text\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad\\nminim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit\\nin voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia\\ndeserunt mollit anim id est laborum.\",\n" +
                "\"token\": \"re\"\n" +
                "}";

        JsonNode json = objectMapper.readTree(jsonString);

        JsonNode result = test.procesarTexto(json);

        System.out.println(result.asText());

    }


}
