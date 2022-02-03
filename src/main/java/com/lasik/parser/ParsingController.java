package com.lasik.parser;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.serialization.JavaParserJsonSerializer;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.json.Json;

@Controller
public final class ParsingController {

    @PostMapping(value = "/parse", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://lasik.michaelepps.me/", methods = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> parse(@RequestBody String code) throws ParseProblemException {
        final var parsed = StaticJavaParser.parse(code);

        StreamingResponseBody responseBody = outputStream -> {
            try(final var generator = Json.createGenerator(outputStream)) {
                new JavaParserJsonSerializer().serialize(parsed, generator);
            }
        };
        return ResponseEntity.ok(responseBody);
    }
}