package hello.dataTypes;

public class DataSet {

    private Sentence s1;
    private Sentence s2;

    //CONSTRUCTOR
    public DataSet(){}

    //GETTERS
    public Sentence getS1() {
        return s1;
    }
    public Sentence getS2() {
        return s2;
    }

    //SETTERS
    public void setS1(Sentence S1) {
        System.out.println("Set Sentence 1");
        System.out.println(S1.getTerms());
        this.s1 = S1;
    }
    public void setS2(Sentence S2) {
        this.s2 = S2;
    }
}
