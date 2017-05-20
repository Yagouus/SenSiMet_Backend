package hello.storage;

import hello.FileUploadController;
import hello.dataTypes.Headers;
import hello.dataTypes.Result;
import hello.dataTypes.Sentence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@RestController
public class ProcessController {

    private static final String template = "Your file is, %s!";
    private final AtomicLong counter = new AtomicLong();

    private final StorageService storageService;

    @Autowired
    public ProcessController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Calculates the resemblance between two sentences
    @RequestMapping(value = "/Process", method = RequestMethod.POST)
    public Result complexGreeting(String s1, String s2) {

        //Init the sentences
        Sentence S1 = new Sentence(s1);
        Sentence S2 = new Sentence(s2);

        //POS tag the sentence
        S1.Tokenize();
        S2.Tokenize();

        //Disambiguate the terms in the sentences
        S1.Disambiguate();
        S2.Disambiguate();


        return null;
    }


}

