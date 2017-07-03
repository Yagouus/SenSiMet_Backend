package sensimet.dataTypes;

import java.util.ArrayList;

/**
Author: Yago Fontenla Seco
 **/

public class Result {

    Sentence S1;
    Sentence S2;
    ArrayList<Relation> relationsArrayList = new ArrayList<>();

    //Constructors
    public Result(){
        System.out.println("Create empty result");
    }
    public Result(Sentence s1, Sentence s2){
        System.out.println("Create empty result");
        setS1(s1);
        setS2(s2);
    }

    //Setters
    public void setS1(Sentence s1) {
        System.out.println("Set sentence 1");
        System.out.println(s1.getTerms());
        S1 = s1;
    }

    public void setS2(Sentence s2) {
        System.out.println("Set sentence 2");
        System.out.println(s2.getTerms());
        S2 = s2;
    }

    public void setRelationsArrayList(ArrayList<Relation> relationsArrayList) {
        this.relationsArrayList = relationsArrayList;
    }


    //Getters
    public Sentence getS1() {
        return S1;
    }

    public Sentence getS2() {
        return S2;
    }

    public ArrayList<Relation> getRelationsArrayList() {
        return relationsArrayList;
    }


    //Custom methods
    public void addRelation(Relation r) {

        boolean c = false;
        if (this.getRelationsArrayList().size() > 0) {
            for (Relation e : this.getRelationsArrayList()) {
                if (e.getT1().equals(r.getT1()) || e.getT2().equals(r.getT2()) || e.getT2().equals(r.getT1()) || e.getT1().equals(r.getT2())) {
                    if (e.getMetric() < r.getMetric()) {
                        this.getRelationsArrayList().remove(this.getRelationsArrayList().indexOf(e));
                        this.getRelationsArrayList().add(r);
                        return;
                    }
                }
            }

            this.getRelationsArrayList().add(r);
            return;

        } else {
            this.getRelationsArrayList().add(r);
        }


    }
}
