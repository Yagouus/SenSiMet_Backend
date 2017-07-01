package hello.parser;


import hello.dataTypes.DataSet;


import java.io.*;

import java.util.ArrayList;

public class parserCSV {

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