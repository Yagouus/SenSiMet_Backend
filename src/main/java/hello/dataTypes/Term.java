package hello.dataTypes;

import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;

//This class represents each word from a sentence
//It contains the basic information and methods needed to process it
public class Term {

    String string;
    SemanticAnnotation bfy;

    public Term (String word){
        setString(word);
    }

    //SETTERS
    public void setString(String string) {
        this.string = string;
    }
}
