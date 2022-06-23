package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
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

}
