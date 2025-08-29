package kz.bailanysta.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "healthz",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class Healthz {

    @GetMapping
    public String getTest() {
        return "OK~";
    }
}
