package hello.parser;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import hello.dataTypes.DataSet;
import hello.dataTypes.Headers;
import hello.persistence.MongoJDBC;
import hello.storage.StorageService;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;

public class parserCSV {

    private static MongoJDBC mongo = new MongoJDBC();

    public static DataSet Stringify(String file) {
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            //If file is not empty
            while ((line = br.readLine()) != null) {


                String[] columns = line.split(cvsSplitBy);

                //Add headers to array and remove quotes
                for (String header : columns)
                    result.add(header.replace("\"", ""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void Tokenize(DataSet Data) {

        //Initialize OpenNLP tokenizer
        InputStream is = null;
        try {
            is = new FileInputStream("lib/en-token.bin");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TokenizerModel model = null;
        try {
            model = new TokenizerModel(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tokenizer tokenizer = new TokenizerME(model);
    }

}