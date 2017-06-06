package hello.dataTypes;


import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import hello.storage.ProcessController;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelnet.*;
import it.uniroma1.lcl.jlt.util.Language;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
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
    public void Tokenize() {

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

    }

    public void Disambiguate() throws InvalidBabelSynsetIDException, IOException {

        //Babelfy instance
        Babelfy bfy = new Babelfy();

        //Call bfy API
        ArrayList<SemanticAnnotation> bfyAnnotations = (ArrayList<SemanticAnnotation>) bfy.babelfy(string, EN);

        //SOUT
        System.out.println("DISAMBIGUATE SENTENCE: " + string);

        //Iterate over list
        //bfyAnnotations is the result of Babelfy.babelfy() call
        for (SemanticAnnotation annotation : bfyAnnotations) {

            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = string.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1);
            System.out.print(frag + "\t" + annotation.getBabelSynsetID());
            System.out.print("\t" + annotation.getBabelNetURL());
            System.out.print("\t" + annotation.getDBpediaURL());
            System.out.println("\t" + annotation.getSource());

            BabelSynset synset = ProcessController.bn.getSynset(new BabelSynsetID(annotation.getBabelSynsetID()));

            System.out.println("MAIN SENSE: " + synset.getMainSense(Language.EN));
            System.out.println("POS: " + synset.getPOS());
            //System.out.println("EDGES: " + synset.getEdges());


            /*for(BabelSynsetIDRelation edge : synset.getEdges()) {
                System.out.println(synset.getId()+"\t"+synset.getMainSense(Language.EN).getLemma()+" - "
                        + edge.getPointer()+" - "
                        + edge.getBabelSynsetIDTarget());
            }*/

            //Add the info to the terms contained in the sentence
            if (terms.containsKey(frag))
                terms.get(frag).setBfy(annotation);


        }

        //Tokenize


        //Create term
        System.out.println(string.substring(0, bfyAnnotations.get(0).getCharOffsetFragment().getStart()));

        for (int i = 0; i < bfyAnnotations.size(); i++) {

            System.out.println(string.substring(bfyAnnotations.get(i).getCharOffsetFragment().getStart(),
                    bfyAnnotations.get(i).getCharOffsetFragment().getEnd() + 1));

            //splitting the input text using the CharOffsetFragment start and end anchors
            System.out.println(string.substring(bfyAnnotations.get(i).getCharOffsetFragment().getEnd() + 1,
                    bfyAnnotations.get(i+1).getCharOffsetFragment().getStart()));


        }

    }//Disambiguate
}
