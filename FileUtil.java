import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * Module containing utility methods.
 * 
 * @author Stephan Jamieson
 * @version 24/4/2015
 */
public class FileUtil {

    private FileUtil() {}
    
    
    /**
     * Load the dictionary with the word definitions in the given file. <br>
     * 
     * &lt;lexicion&gt; ::= {<entry>} <br>  
     * &lt;entry&gt; ::=  &lt;word type&gt; ‘:’ &lt;word&gt; ‘:’ [&lt;description&gt;] <br> 
     * &lt;word type&gt; ::= ‘ a’|’v’|’n’ <br>
     * &lt;word&gt; ::=  {&lt;letter&gt;}+ <br>
     * &lt;description&gt; ::=  {&lt;character&gt;} <br>
     * <br>
     * The lexicion contains 0 or more entries. <br>
     * An entry consists of word type followed by a colon, followed by the word, followed by a colon, optionally followed by a description. <br> 
     * The word type is represented by a single character; either ‘a’, ‘v’, or ‘n’. <br>
     * A word consists of a sequence of one or more letters. <br>
     * A description consists of 1 or more characters (generally, it’s a word phrase). <br>
     */
    public static void load(Dictionary dictionary, String filename) throws FileNotFoundException, IOException { 
        BufferedReader b = new BufferedReader(new FileReader(filename));
        String line = null;
        while((line = b.readLine()) != null){
        	String[] arrThings = line.split(":");
        	WordType wordType = WordType.toWordType(arrThings[0].trim());  //match to wordType enum
        	String word = arrThings[1].trim();
        	String description = " ";
        	if(arrThings.length==3){									   //if this word has a definition, then add it, otherwise leave empty string
        		description = arrThings[2];
        	}
        	Definition d = new Definition(wordType, description);		   //make new definition object
        	dictionary.insert(word, d);									   //insert word into the dictionary
        	
        }
        SCHashtable.debug_print((SCHashtable)dictionary);
    }
    
}
