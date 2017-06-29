package hello.storage;

import hello.dataTypes.*;


import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetIDRelation;
import it.uniroma1.lcl.babelnet.InvalidBabelSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelSynsetIDRelationComparator;
import it.uniroma1.lcl.jlt.util.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ProcessController {

    private static final String template = "Your file is, %s!";
    public static final BabelNet bn = BabelNet.getInstance();
    private final AtomicLong counter = new AtomicLong();

    private final StorageService storageService;

    @Autowired
    public ProcessController(StorageService storageService) {
        this.storageService = storageService;
    }

    //Calculates the resemblance between two sentences
    @CrossOrigin
    @RequestMapping(value = "/Process", method = RequestMethod.POST)
    public Result process(String s1, String s2) throws IOException, InvalidBabelSynsetIDException {


        //Init the sentences
        Sentence S1 = new Sentence(s1);
        Sentence S2 = new Sentence(s2);

        //POS tag the sentence
        //S1.Tokenize();
        //S2.Tokenize();

        //Disambiguate the terms in the sentences
        S1.Disambiguate();
        S2.Disambiguate();

        //Result
        Result result = new Result(S1, S2);

        //Compare terms
        for (Term t : S1.getTerms()) {
            for (Term u : S2.getTerms()) {
                if (t.getPOS() != null && u.getPOS() != null && t.getPOS() == u.getPOS()) {

                    //int c = 0;
                    //int i = 0;

                    //Compare edges
                    System.out.println("COMPARE: " + t.getString() + " - " + u.getString());
                    Relation r = new Relation();
                    r.setT1(t);
                    r.setT2(u);

                    for (BabelSynsetIDRelation edge : t.returnBow()) {
                        //System.out.println(i++);
                        //BabelSynsetIDRelationComparator bc = new BabelSynsetIDRelationComparator();
                        //if(u.returnBow().contains(edge))

                        //r.gettBow().add(edge);

                        for (BabelSynsetIDRelation edge2 : u.returnBow()) {


                            if (edge.toString().equals(edge2.toString())) {

                                System.out.println(edge + " - " + edge2);
                                //r.getcWords().add(edge);
                                //c++;

                            }
                                //r.gettBow().add(edge2);

                            //i++;
                        }
                    }

                    //float metric = (c) / (t.returnBow().size() + u.returnBow().size());

                    //System.out.println("SHARED: " + c);
                    System.out.println("SIZE: " + (t.returnBow().size() + u.returnBow().size()));
                    //System.out.println("RESULT: " + c + " / " + (t.returnBow().size() + u.returnBow().size()) + " = " + metric);

                    result.getRelationsArrayList().add(r);
                }
            }
        }

        //Show terms
        //System.out.println(S1.getTerms());
        //System.out.println(S2.getTerms());


        //result.setS1(S1);
        //result.setS2(S2);



        return result;
    }


}

