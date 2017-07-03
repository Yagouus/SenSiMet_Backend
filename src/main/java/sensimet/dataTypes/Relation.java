package sensimet.dataTypes;

import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import java.util.ArrayList;

/**
 Author: Yago Fontenla Seco
 **/

public class Relation {

    private Term t1;
    private Term t2;
    private float metric;
    private ArrayList<BabelSynsetIDRelation> cWords = new ArrayList<>();
    private ArrayList<BabelSynsetIDRelation>  tBow = new ArrayList<>();

    //Setters
    public void setT1(Term t1) {
        this.t1 = t1;
    }
    public void setT2(Term t2) {
        this.t2 = t2;
    }
    public void setMetric(float metric) {
        this.metric = metric;
    }
    public void settBow(ArrayList<BabelSynsetIDRelation> tBow) {
        this.tBow = tBow;
    }
    public void setcWords(ArrayList<BabelSynsetIDRelation> cWords) {
        this.cWords = cWords;
    }


    //Getters
    public ArrayList<BabelSynsetIDRelation> gettBow() {
        return tBow;
    }
    public ArrayList<BabelSynsetIDRelation> getcWords() {
        return cWords;
    }
    public Term getT1() {
        return t1;
    }
    public Term getT2() {
        return t2;
    }
    public float getMetric() {
        return metric;
    }


}
