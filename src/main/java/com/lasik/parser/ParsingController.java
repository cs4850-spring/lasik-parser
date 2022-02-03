package com.lasik.parser;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.serialization.JavaParserJsonSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.json.Json;

@RestController
public final class ParsingController {

    @RequestMapping(value = "/parse", method = RequestMethod.POST)
    @CrossOrigin
    public @ResponseBody ResponseEntity<StreamingResponseBody> parse(@RequestBody String code) throws ParseProblemException {
        final var parsed = StaticJavaParser.parse(code);

        StreamingResponseBody responseBody = outputStream -> {
            try(final var generator = Json.createGenerator(outputStream)) {
                new JavaParserJsonSerializer().serialize(parsed, generator);
            }
        };
        return ResponseEntity.ok(responseBody);
    }

    @ExceptionHandler({ ParseProblemException.class })
    public void handleParseException() {

    }
}