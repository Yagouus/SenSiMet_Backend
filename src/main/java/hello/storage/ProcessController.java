package hello.storage;

import hello.dataTypes.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

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
    public Result process(String s1, String s2) {

        //Init the sentences
        Sentence S1 = new Sentence(s1);
        Sentence S2 = new Sentence(s2);

        //POS tag the sentence
        S1.Tokenize();
        S2.Tokenize();

        //Disambiguate the terms in the sentences
        S1.Disambiguate();
        S2.Disambiguate();

        //Show terms
        System.out.println(S1.getTerms());
        System.out.println(S2.getTerms());

        //Result
        Result result = new Result(S1, S2);
        //result.setS1(S1);
        //result.setS2(S2);

        return result;
    }


}

