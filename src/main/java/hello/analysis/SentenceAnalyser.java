package hello.analysis;

import hello.dataTypes.Relation;
import hello.dataTypes.Result;
import hello.dataTypes.Sentence;
import hello.dataTypes.Term;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

import java.io.IOException;

/**
 * Created by yagouus on 23/04/17.
 */
public class SentenceAnalyser {

    public Result Analyse() {
        return null;
    }


    public static Result analyse(String s1, String s2) throws InvalidBabelSynsetIDException, IOException {

        //Init the sentences
        Sentence S1 = new Sentence(s1);
        Sentence S2 = new Sentence(s2);

        //Create result
        Result result = new Result(S1, S2);

        //POS tag the sentence
        //S1.Tokenize();
        //S2.Tokenize();

        //Disambiguate the terms in the sentences
        S1.Disambiguate();
        S2.Disambiguate();


        //Apply bow metric
        //bowMetric(S1, S2, result);
        pathLength(S1, S2, result);

        return result;
    }

    public static void bowMetric(Sentence S1, Sentence S2, Result result) {

        //Final result
        float metric;

        //Compare terms
        //For terms in sentence 1
        for (Term t : S1.getTerms()) {

            //For terms in sentence 2
            for (Term u : S2.getTerms()) {

                //If the terms are the same POS
                if (t.getPOS() != null && u.getPOS() != null && t.getPOS() == u.getPOS()) {

                    //If they are the same term
                    if (t.getBfy().getBabelSynsetID().equals(u.getBfy().getBabelSynsetID())) {
                        metric = 1;
                        System.out.println("RESULT: " + metric);
                        return;
                    }

                    //Number of common terms
                    int c = 0;

                    //Sout
                    System.out.println("COMPARE: " + t.getString() + " - " + u.getString());

                    //Initialize relation
                    Relation r = new Relation();
                    r.setT1(t);
                    r.setT2(u);

                    //For each connected node in term 1
                    for (BabelSynsetIDRelation edge : t.returnBow()) {

                        //For each connected node in term 2
                        for (BabelSynsetIDRelation edge2 : u.returnBow()) {

                            if (edge.toString().equals(edge2.toString())) {
                                System.out.println(edge + " - " + edge2);
                                //r.getcWords().add(edge);
                                c++;
                            }
                            //r.gettBow().add(edge2);
                        }
                    }

                    //Calculate result
                    float common = (c * 2);
                    float dif = (t.returnBow().size() + u.returnBow().size());
                    metric = common / dif;

                    /*if(u.relationsContain(t) || t.relationsContain(u)){
                        metric = 1;
                    }*/

                    System.out.println("SHARED: " + common);
                    System.out.println("SIZE: " + (t.returnBow().size() + u.returnBow().size()));
                    System.out.println("RESULT: " + c + " / " + (t.returnBow().size() + u.returnBow().size()) + " = " + metric);


                    //Add result to relations
                    r.setMetric(metric);
                    result.addRelation(r);

                }
            }
        }
    }

    public static void pathLength(Sentence S1, Sentence S2, Result result){
        //Final result
        float metric;

        //Compare terms
        //For terms in sentence 1
        for (Term t : S1.getTerms()) {

            //For terms in sentence 2
            for (Term u : S2.getTerms()) {

                //If the terms are the same POS
                if (t.getPOS() != null && u.getPOS() != null && t.getPOS() == u.getPOS()) {

                    //If they are the same term
                    if (t.getBfy().getBabelSynsetID().equals(u.getBfy().getBabelSynsetID())) {
                        metric = 1;
                        System.out.println("RESULT: " + metric);
                        return;
                    }

                    //Number of common terms
                    int c = 0;
                    float i = 0;
                    float j = 0;

                    //Sout
                    System.out.println("COMPARE: " + t.getString() + " - " + u.getString());

                    //Initialize relation
                    Relation r = new Relation();
                    r.setT1(t);
                    r.setT2(u);

                    //For each connected node in term 1
                    for (BabelSynsetIDRelation edge : t.returnBow()) {

                        i++;
                        j=0;

                        //For each connected node in term 2
                        for (BabelSynsetIDRelation edge2 : u.returnBow()) {

                            j++;

                            if (edge.toString().equals(edge2.toString())) {
                                System.out.println("COMMON TERM: " + edge + " - " + edge2);
                                System.out.println("Distance t1: " + i);
                                System.out.println("Distance t2: " + j);
                                float sum = i+j;
                                metric = 1 / sum;
                                //Add result to relations
                                r.setMetric(metric);
                                result.addRelation(r);
                                System.out.println("METRIC: " + metric);
                                return;
                            }
                            //r.gettBow().add(edge2);
                        }
                    }

                    //Calculate result
                    float common = (c * 2);
                    float dif = (t.returnBow().size() + u.returnBow().size());
                    metric = common / dif;

                    /*if(u.relationsContain(t) || t.relationsContain(u)){
                        metric = 1;
                    }*/

                    System.out.println("SHARED: " + common);
                    System.out.println("SIZE: " + (t.returnBow().size() + u.returnBow().size()));
                    System.out.println("RESULT: " + c + " / " + (t.returnBow().size() + u.returnBow().size()) + " = " + metric);


                    //Add result to relations
                    r.setMetric(metric);
                    result.addRelation(r);

                }
            }
        }
    }

}

