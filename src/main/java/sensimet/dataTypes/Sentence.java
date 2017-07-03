package sensimet.dataTypes;

import sensimet.RESTController;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelnet.*;
import it.uniroma1.lcl.babelnet.data.BabelPointer;
import java.io.IOException;
import java.util.*;
import static it.uniroma1.lcl.jlt.util.Language.*;

/**
 Author: Yago Fontenla Seco
 **/

public class Sentence {

    //Attributes
    private String string;                                      //Plain text string of sentence
    private HashMap<String, Term> terms = new HashMap<>();      //Terms composing the sentence

    //Constructors
    public Sentence(String string) {
        setString(string);
        System.out.println("SENTENCE SETTED: " + string);
    }

    //Setters
    public void setString(String string) {
        this.string = string;
    }

    //Getters
    public String getString() {
        return string;
    }
    public ArrayList<Term> getTerms() {

        //Result Array
        ArrayList<Term> result = new ArrayList<>();

        Iterator it = terms.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            result.add((Term) pair.getValue());
        }

        return result;
    }

    //Custom methods
    public void Disambiguate() throws InvalidBabelSynsetIDException, IOException {

        String sentence = this.getString();

        //Babelfy settings
        BabelfyParameters bp = new BabelfyParameters();
        //bp.setMCS(BabelfyParameters.MCS.OFF);
        bp.setScoredCandidates(BabelfyParameters.ScoredCandidates.TOP);
        bp.setMatchingType(BabelfyParameters.MatchingType.EXACT_MATCHING);
        bp.setAnnotationType(BabelfyParameters.SemanticAnnotationType.NAMED_ENTITIES);

        //Babelfy instance
        Babelfy bfy = new Babelfy(bp);
        ArrayList<String> w = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();

        //Call bfy API
        ArrayList<SemanticAnnotation> bfyEntities = (ArrayList<SemanticAnnotation>) bfy.babelfy(string, EN);

        //SOUT
        System.out.println("\nDISAMBIGUATE SENTENCE: " + string);
        System.out.println("ANNOTATIONS OBTAINED: ");

        //Iterate over list
        //bfyAnnotations is the result of Babelfy.babelfy() call
        for (SemanticAnnotation annotation : bfyEntities) {

            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = sentence.substring(annotation.getCharOffsetFragment().getStart(), annotation.getCharOffsetFragment().getEnd() + 1);

            w.add(frag);

            if (!ids.contains(annotation.getBabelSynsetID())) {

                //Set Term and BFY info
                this.terms.put(frag, new Term(frag));
                terms.get(frag).setBfy(annotation);

                ids.add(annotation.getBabelSynsetID());

                System.out.print(frag + "\t" + annotation.getBabelSynsetID());
                System.out.print("\t" + annotation.getBabelNetURL() + "\n");

                BabelSynset synset = RESTController.bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
                System.out.println("MAIN SENSE: " + synset.getMainSense(EN));
                terms.get(frag).setBnt(synset);
                terms.get(frag).setPOS(synset.getPOS());
                System.out.println("POS: " + terms.get(frag).getPOS());

                getEdges(terms.get(frag));

            }
        }


        //Remove concepts from the string
        for (String word : w) {
            sentence = sentence.replace(word, "");
        }

        bp.setAnnotationType(BabelfyParameters.SemanticAnnotationType.CONCEPTS);
        bp.setMultiTokenExpression(false);
        ArrayList<SemanticAnnotation> bfyConcepts = (ArrayList<SemanticAnnotation>) bfy.babelfy(sentence, EN);

        for (SemanticAnnotation annotation : bfyConcepts) {

            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = sentence.substring(annotation.getCharOffsetFragment().getStart(), annotation.getCharOffsetFragment().getEnd() + 1);

            w.add(frag);

            if (!ids.contains(annotation.getBabelSynsetID())) {

                //Set Term and BFY info
                this.terms.put(frag, new Term(frag));
                terms.get(frag).setBfy(annotation);

                ids.add(annotation.getBabelSynsetID());

                System.out.print(frag + "\t" + annotation.getBabelSynsetID());
                System.out.print("\t" + annotation.getBabelNetURL() + "\n");

                BabelSynset synset = RESTController.bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));
                System.out.println("MAIN SENSE: " + synset.getMainSense(EN));

                terms.get(frag).setBnt(synset);
                terms.get(frag).setPOS(synset.getPOS());
                System.out.println("POS: " + terms.get(frag).getPOS());

                getEdges(terms.get(frag));


            }
        }

        for (String word : w) {
            sentence = sentence.replace(word, "");
        }

        //Tokenize the rest of the sentence
        String[] rest = sentence.split(" ");
        for (String word : rest) {
            if (!word.isEmpty())
                terms.put(word, new Term(word));
        }


        //Create term


    }
    private void getEdges(Term t) throws InvalidBabelSynsetIDException, IOException {
        BabelSynset synset = null;

        synset = RESTController.bn.getSynset(new BabelSynsetID(t.getBfy().getBabelSynsetID()));

        ArrayList<BabelSynsetIDRelation> added = new ArrayList<>();

        //Set first level relations
        //t.setBow((ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.REGION_MEMBER));
        //t.addBow((ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.HYPERNYM));
        //t.addBow((ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.HYPONYM));
        //t.addBow((ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.REGION_MEMBER));
        //t.addBow((ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.HOLONYM_MEMBER));


        ArrayList<BabelSynsetIDRelation> edges = (ArrayList<BabelSynsetIDRelation>) synset.getEdges(BabelPointer.HYPERNYM);
        ArrayList<BabelSynsetIDRelation> cedges = new ArrayList<>();
        BabelSynsetIDRelation cH = edges.get(0);
        String entity = "bn:00031027n";
        Integer i = 0;

        do {
            i++;

            System.out.println(cH.getBabelSynsetIDTarget().toString());
            cedges = (ArrayList<BabelSynsetIDRelation>) RESTController.bn.getSynset(cH.getBabelSynsetIDTarget()).getEdges(BabelPointer.HYPERNYM);
            if(cedges.size() > 0){
                cH = cedges.get(0);
            }else{
                break;
            }
            t.returnBow().add(cH);

        } while (!cH.getBabelSynsetIDTarget().toString().equals(entity));

    }


}

