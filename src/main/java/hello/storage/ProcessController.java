package hello.storage;

import hello.analysis.SentenceAnalyser;
import hello.dataTypes.*;


import hello.parser.parserCSV;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProcessController {

    private static final String template = "Your file is, %s!";
    public static final BabelNet bn = BabelNet.getInstance();
    private final AtomicLong counter = new AtomicLong();

    private final StorageService storageService;

    @Autowired
    public ProcessController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Calculates the resemblance between two sentences
    @CrossOrigin
    @RequestMapping(value = "/Process", method = RequestMethod.POST)
    public Result process(String s1, String s2) throws IOException, InvalidBabelSynsetIDException {
        return SentenceAnalyser.analyse(s1, s2);
    }

    //Accepts a file and saves it to the server
    @CrossOrigin
    @RequestMapping("/processFile")
    public Result processFile(@RequestParam("file") MultipartFile file) throws InvalidBabelSynsetIDException, IOException {

        //Save file
        storageService.store(file);

        //Extract sentences
        ArrayList<String> sentences = parserCSV.Stringify(storageService.load(file.getOriginalFilename()).toString());

        return SentenceAnalyser.analyse(sentences.get(0), sentences.get(1));
    }

    //Lists all files in the server
    @CrossOrigin
    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public Result processSentences(@RequestParam("s1") String s1, @RequestParam("s2") String s2) throws IOException, InvalidBabelSynsetIDException {
        return SentenceAnalyser.analyse(s1, s2);
    }



}

