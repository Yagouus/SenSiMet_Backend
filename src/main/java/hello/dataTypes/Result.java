package hello.dataTypes;

/**
 * Created by yagouus on 23/04/17.
 */
public class Result {

    Sentence S1;
    Sentence S2;

    public Result(){
        System.out.println("Create empty result");
    }

    public Result(Sentence s1, Sentence s2){
        System.out.println("Create empty result");
        setS1(s1);
        setS2(s2);
    }

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

    public Sentence getS1() {
        return S1;
    }

    public Sentence getS2() {
        return S2;
    }
}
