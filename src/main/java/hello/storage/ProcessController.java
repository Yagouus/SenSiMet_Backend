package hello.storage;

import hello.FileUploadController;
import hello.dataTypes.Headers;
import hello.dataTypes.Result;
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

    //Removes the non selected columns from a log
    @RequestMapping(value = "/Process", method = RequestMethod.POST)
    public Result complexGreeting(String s1, String s2) {
        System.out.println(s1);
        System.out.println(s2);
        return null;
    }


}

