package hello.parser;


import java.io.*;

import java.util.ArrayList;

public class parserCSV {

    public static ArrayList<String> Stringify(String file) {
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String> result = new ArrayList<>();
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            //If file is not empty
            while ((line = br.readLine()) != null && i < 2) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

   /* public static void Tokenize(DataSet Data) {

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
    }*/

}