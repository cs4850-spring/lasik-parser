package com.lasik.parser;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.Problem;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.serialization.JavaParserJsonSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.json.Json;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public final class ParsingController {

    @PostMapping(value = "/parse", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    @ResponseBody
    public ResponseEntity<StreamingResponseBody> parse(@RequestBody String code) throws ParseProblemException {
        final var parsed = StaticJavaParser.parse(code);
        StreamingResponseBody responseBody = outputStream -> {
            final var generator = Json.createGenerator(outputStream);
            new JavaParserJsonSerializer().serialize(parsed, generator);

        };
        return ResponseEntity.ok(responseBody);
    }
    @ExceptionHandler(value = {ParseProblemException.class })
    public ResponseEntity<List<String>> handleParseException(ParseProblemException e)
    {
        List<String> problems = e.getProblems().stream().map(Problem::getVerboseMessage).collect(Collectors.toList());
        System.out.println(problems);

        return new ResponseEntity<List<String>>(problems, HttpStatus.BAD_REQUEST);
    }


}
