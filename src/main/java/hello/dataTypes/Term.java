package hello.dataTypes;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;

//This class represents each word from a sentence
//It contains the basic information and methods needed to process it
public class Term {

    //Attributes
    private String string;              //Plain text string
    private SemanticAnnotation bfy;     //Bfy disambiguation
    private CoreLabel core;            //CoreNLP token

    //CONSTRUCTOR
    public Term() {

    }
    public Term(String word) {
        setString(word);
    }

    //SETTERS
    public void setString(String string) {
        this.string = string;
    }
    public void setBfy(SemanticAnnotation bfy) {
        this.bfy = bfy;
    }
    public void setCore(CoreLabel core) {
        this.core = core;
    }

    //GETTERS
    public String getString() {
        return string;
    }
    public SemanticAnnotation getBfy() {
        return bfy;
    }

    @Override
    public String toString() {
        return this.string;
    }
}
