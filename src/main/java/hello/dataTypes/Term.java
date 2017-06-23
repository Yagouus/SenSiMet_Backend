package hello.dataTypes;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.semgraph.semgrex.SemgrexMatcher;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.data.BabelPOS;

import java.util.ArrayList;

//This class represents each word from a sentence
//It contains the basic information and methods needed to process it
public class Term {

    //Attributes
    private String string;              //Plain text string
    private SemanticAnnotation bfy;     //Bfy disambiguation
    private BabelSynset bnt;            //Babelnet synset
    private ArrayList<BabelSynsetIDRelation> bow = new ArrayList<>(); //Related babelnet synsets
    private CoreLabel core;            //CoreNLP token
    private BabelPOS POS;

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

    public void setBnt(BabelSynset bnt) {
        this.bnt = bnt;
    }

    public void setBow(ArrayList<BabelSynsetIDRelation> bow) {
        this.bow = bow;
    }

    public void setPOS(BabelPOS POS) {
        this.POS = POS;
    }

    //GETTERS
    public String getString() {
        return string;
    }

    public SemanticAnnotation getBfy() {
        return bfy;
    }

    public BabelPOS getPOS() {
        return POS;
    }

    @Override
    public String toString() {
        return this.string;
    }

    public void addBow(ArrayList<BabelSynsetIDRelation> bow) {
        this.bow.addAll(bow);
    }

    public ArrayList<BabelSynsetIDRelation> returnBow(){
        return this.bow;
    }

    public BabelSynset returnBnt(){
        return this.bnt;
    }

}


