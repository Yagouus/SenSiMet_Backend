package hello.dataTypes;


import hello.RESTController;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelnet.*;
import it.uniroma1.lcl.babelnet.data.BabelPointer;

import java.io.IOException;
import java.util.*;

import static it.uniroma1.lcl.jlt.util.Language.*;

public class Sentence {

    //Attributes
    private String string;                                      //Plain text string of sentence
    private HashMap<String, Term> terms = new HashMap<>();      //Terms composing the sentence
    //private Tree tree;                                          //Tree structure of the sentence
    //private SemanticGraph dependencies;                         //Graph of the sentence dependencies

    //CONSTRUCTORS
    public Sentence() {
    }

    public Sentence(String string) {

        //Set the sentence string
        setString(string);
        System.out.println("SENTENCE SETTED: " + string);

        //Tokenize and POS
        //Tokenize();

        //Disambiguate terms
        //Disambiguate();
    }

    //SETTERS
    public void setString(String string) {
        this.string = string;
    }

    //GETTERS
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

    /*public SemanticGraph getDependencies() {
        return dependencies;
    }*/

    //OTHER METHODS
   /* public void Tokenize() {

        //Creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        //props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
        props.setProperty("annotators", "tokenize");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        //Create an empty Annotation just with the given text
        Annotation document = new Annotation(this.string);

        //Run all Annotators on this text
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        //SOUT
        System.out.println("TOKENIZE SENTENCE: " + string);

        //For each term in the sentence
        for (CoreMap sentence : sentences) {
            // a CoreLabel is a CoreMap with additional token-specific methods
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                //Create term
                Term t = new Term();

                //Set String
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println("--WORD: " + word);
                t.setString(word);

                //Set CoreLabel
                t.setCore(token);

                // this is the POS tag of the token
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("pos: " + pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("ner: " + ne);

                //Add term to MAP
                this.terms.put(word, t);
            }

            //This is the parse tree of the current sentence
            //this.tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            //This is the Stanford dependency graph of the current sentence
            //this.dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
        }

    }*/

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


    }//Disambiguate

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
        BabelSynsetIDRelation cH = edges.get(0);
        String entity = "bn:00031027n";
        Integer i = 0;

        do {

            i++;

            System.out.println(cH.getBabelSynsetIDTarget().toString());
            cH = RESTController.bn.getSynset(cH.getBabelSynsetIDTarget()).getEdges(BabelPointer.HYPERNYM).get(0);
            t.returnBow().add(cH);

        } while (!cH.getBabelSynsetIDTarget().toString().equals(entity));


        //for (BabelSynsetIDRelation edge : synset.getEdges(BabelPointer.HYPERNYM)) {
            //if (edge.getLanguage() == Language.EN && edge.getWeight() != 0.0) {

            //System.out.println(edge);

            //t.returnBow().add(edge);

            //System.out.println(synset.getId() + "\t" + synset.getMainSense(Language.EN).getLemma() + " - " + edge.getPointer() + " - " + edge.getBabelSynsetIDTarget() + " - " + edge.getLanguage());

            //Add second level edges
            //for (BabelSynsetIDRelation subedge : ProcessController.bn.getSynset(new BabelSynsetID(edge.getBabelSynsetIDTarget().toString())).getEdges()) {
            //  if (subedge.getLanguage() == Language.EN && subedge.getWeight() != 0.0) {

            //    t.returnBow().add(subedge);

            //  System.out.println(synset.getId() + "\t" + synset.getMainSense(Language.EN).getLemma() + " - " + subedge.getPointer() + " - " + subedge.getBabelSynsetIDTarget() + " - " + subedge.getLanguage());

            //}


            //  System.out.println(edge);
            //Add second level relations
            //added.addAll((ArrayList<BabelSynsetIDRelation>) ProcessController.bn.getSynset(new BabelSynsetID(edge.getBabelSynsetIDTarget().toString())).getEdges());
            //}

            //System.out.println(synset.getId() + "\t" + synset.getMainSense(Language.EN).getLemma() + " - "+ edge.getPointer() + " - "+ edge.getBabelSynsetIDTarget() + " - "+ edge.getLanguage());


            //}


            //for(String form : synset.getOtherForms(Language.EN)) {
            //  System.out.println(synset.getId()+"\t"+synset.getMainSense(Language.EN).getLemma()+" - "
            //        + form);
        }

        //t.addBow(added);

        //  }


}

