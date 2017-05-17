package hello.dataTypes;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.jlt.util.Language;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Sentence {

    private String string;
    private ArrayList<Term> terms = new ArrayList<>();

    public Sentence(String string) {
        setString(string);
        Tokenize();
        babel();
    }

    //Tokenize
    public void Tokenize() {

        //Initialize OpenNLP tokenizer
        Tokenizer tokenizer = null;
        try {
            InputStream is = new FileInputStream("lib/en-token.bin");
            TokenizerModel model = new TokenizerModel(is);
            tokenizer = new TokenizerME(model);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Tokenize the sentence
        String[] words = tokenizer.tokenize(this.string);

        //Create the terms
        for (String word : words) {
            this.terms.add(new Term(word));
            System.out.println(word);
        }
    }

    public void babel() {
        Babelfy bfy = new Babelfy();
        String inputText = "BabelNet is both a multilingual encyclopedic dictionary and a semantic network";
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN);
    }

    //SETTERS
    public void setString(String string) {
        this.string = string;
    }
}
