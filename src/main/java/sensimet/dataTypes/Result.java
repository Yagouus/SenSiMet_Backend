package sensimet.dataTypes;

import java.util.ArrayList;

/**
 * Author: Yago Fontenla Seco
 **/

public class Result {

    Sentence S1;
    Sentence S2;
    ArrayList<Relation> relationsArrayList = new ArrayList<>();

    //Constructors
    public Result() {
        System.out.println("Create empty result");
    }

    public Result(Sentence s1, Sentence s2) {
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

        boolean f = false;
        boolean cont = false;
        ArrayList<Relation> c = (ArrayList<Relation>) this.getRelationsArrayList().clone();


        //If relations exist
        if (this.getRelationsArrayList().size() > 0) {

            //For each relation
            for (Relation e : this.getRelationsArrayList()) {

                //Existing relations
                if (e.getT1().equals(r.getT1()) && e.getT2().equals(r.getT2())) {
                    System.out.println("EXISTING RELATION");
                    if (e.getMetric() < r.getMetric()) {
                        e.setMetric(r.getMetric());
                        return;
                    } else if (e.getPath() < r.getPath()) {
                        e.setPath(r.getPath());
                        return;
                    } else {
                        return;
                    }
                }

                //If any node is related
                if (e.getT1().equals(r.getT1()) || e.getT2().equals(r.getT2())) {
                    System.out.println("TERM REPEATS");
                    cont = true;
                    //If relation is better
                    if (e.getMetric() < r.getMetric() || e.getPath() < r.getPath()) {
                        if (!f) {
                            System.out.println("BETTER RESULT");
                            System.out.println("REMOVE: " + e.getT1() + " - " + e.getT2());
                            c.remove(c.indexOf(e));
                            c.add(r);
                            f = true;
                        } else {
                            System.out.println("REMOVE: " + e.getT1() + " - " + e.getT2());
                            c.remove(c.indexOf(e));
                        }
                        //return;
                    }
                }

            }

            this.setRelationsArrayList(c);

            if (!cont) {
                this.getRelationsArrayList().add(r);
            }


        } else {
            this.getRelationsArrayList().add(r);
        }

    }
}
