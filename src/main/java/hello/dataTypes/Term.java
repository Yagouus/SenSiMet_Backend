package hello.dataTypes;

//This class represents each word from a sentence
//It contains the basic information and methods needed to process it
public class Term {

    String string;

    public Term (String word){
        setString(word);
    }

    //SETTERS
    public void setString(String string) {
        this.string = string;
    }
}
