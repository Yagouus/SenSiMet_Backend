package sensimet.analysis;

import sensimet.dataTypes.Relation;
import sensimet.dataTypes.Result;
import sensimet.dataTypes.Sentence;
import sensimet.dataTypes.Term;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;

import java.io.IOException;

/**
 * Author: Yago Fontenla Seco
 **/

public class SentenceAnalyser {

    public static Result analyse(String s1, String s2) throws InvalidBabelSynsetIDException, IOException {

        //Init the sentences
        Sentence S1 = new Sentence(s1);
        Sentence S2 = new Sentence(s2);

        //Create result
        Result result = new Result(S1, S2);

        //Disambiguate the terms in the sentences
        S1.Disambiguate();
        S2.Disambiguate();

        //Apply metrics
        bowMetric(S1, S2, result);
        pathLength(S1, S2, result);

        return result;
    }

    private static void bowMetric(Sentence S1, Sentence S2, Result result) {

        System.out.println("\n----DICE----");

        //Final result
        float metric;

        //Compare terms
        //For terms in sentence 1
        for (Term t : S1.getTerms()) {

            //For terms in sentence 2
            for (Term u : S2.getTerms()) {

                //If the terms are the same POS
                if (t.getPOS() != null && u.getPOS() != null && t.getPOS().equals(u.getPOS())) {

                    //Number of common terms
                    int c = 0;

                    //Sout
                    System.out.println("COMPARE: " + t.getString() + " - " + u.getString());

                    //Initialize relation
                    Relation r = new Relation();
                    r.setT1(t);
                    r.setT2(u);


                    //If they are the same term
                    if (t.getBfy().getBabelSynsetID().equals(u.getBfy().getBabelSynsetID()) || t.getString().equals(u.getString())) {
                        metric = 1;
                        System.out.println("RESULT: " + metric);
                        //Add result to relations
                        r.setMetric(metric);
                        result.addRelation(r);
                        continue;
                    }

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

    private static void pathLength(Sentence S1, Sentence S2, Result result) {

        System.out.println("\n----PATH LENGTH----");

        //Final result
        float metric;

        //Compare terms
        //For terms in sentence 1
        for (Term t : S1.getTerms()) {

            //For terms in sentence 2
            for (Term u : S2.getTerms()) {

                //If the terms are the same POS
                if (t.getPOS() != null && u.getPOS() != null && t.getPOS().equals(u.getPOS())) {

                    //If they are the same term
                    if (t.getBfy().getBabelSynsetID().equals(u.getBfy().getBabelSynsetID())) {
                        metric = 1;
                        System.out.println("RESULT: " + metric);
                        continue;
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
                    for (BabelSynsetIDRelation edge : t.returnHypers()) {

                        i++;
                        j = 0;

                        //For each connected node in term 2
                        for (BabelSynsetIDRelation edge2 : u.returnHypers()) {

                            j++;

                            if (edge.toString().equals(edge2.toString())) {
                                System.out.println("COMMON TERM: " + edge + " - " + edge2);
                                System.out.println("Distance t1: " + i);
                                System.out.println("Distance t2: " + j);
                                float sum = i + j;
                                metric = 1 / sum;
                                //Add result to relations
                                r.setPath(metric);
                                result.addRelation(r);
                                System.out.println("PathLength: " + metric);
                            }
                        }
                    }

                }
            }
        }
    }

}

