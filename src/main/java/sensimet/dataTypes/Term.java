package sensimet.dataTypes;

import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.data.BabelPOS;

import java.util.ArrayList;

/**
 Author: Yago Fontenla Seco
 **/


//This class represents each word from a sentence
//It contains the basic information and methods needed to process it
public class Term {

    //Attributes
    private String string;              //Plain text string
    private SemanticAnnotation bfy;     //Bfy disambiguation
    private BabelSynset bnt;            //Babelnet synset
    private ArrayList<BabelSynsetIDRelation> bow = new ArrayList<>(); //Related babelnet synsets
    private BabelPOS POS;

    //Constructor
    public Term(String word) {
        setString(word);
    }

    //Setters
    public void setString(String string) {
        this.string = string;
    }
    public void setBfy(SemanticAnnotation bfy) {
        this.bfy = bfy;
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

    //Getters
    public String getString() {
        return string;
    }
    public SemanticAnnotation getBfy() {
        return bfy;
    }
    public BabelPOS getPOS() {
        return POS;
    }

    //Custom methods
    public void addBow(ArrayList<BabelSynsetIDRelation> bow) {
        this.bow.addAll(bow);
    }
    public ArrayList<BabelSynsetIDRelation> returnBow(){
        return this.bow;
    }
    public BabelSynset returnBnt(){
        return this.bnt;
    }

    @Override
    public String toString() {
        return this.string;
    }

}


