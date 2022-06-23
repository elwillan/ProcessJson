package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process/v1/process")
public class ProcessController {

    @Autowired
    ProcessService processService;

    @PostMapping
    public String process(@RequestBody JsonNode obj) {
        return processService.procesarTexto(obj).toString();
    }
}
