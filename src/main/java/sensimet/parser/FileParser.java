package sensimet.parser;

import java.io.*;
import java.util.ArrayList;

/**
 Author: Yago Fontenla Seco
 **/

public class FileParser {

    public static ArrayList<String> Stringify(String file) {
        String line = "";

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

}