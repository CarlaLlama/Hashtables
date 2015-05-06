import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ADT for Entry.
 * @author Carla Wilby
 * @version 29/04/2015
 */

public class EntryImpl implements Entry{
	
	private List<Definition> def = new ArrayList<Definition>();
	private String word;
	
	public EntryImpl(String word, Definition definition){
		this.word = word;
		def.add(definition);
	}
	
	/**
     * Obtain the word defined in this entry.
     */
	
    public String getWord(){
    	return word;
    }
    
    /**
     * Obtain the definitions for the word defined in this entry.
     */
    public List<Definition> getDefinitions(){
    	return def;
    }
    
    /**
     * Add a definition consisting of the given word type and word description.
     */
    public void addDefinition(WordType wordType, String description){
    	Definition d = new Definition(wordType, description);
    	def.add(d);
    }
    /**
     * Add the given definition.
     */
    public void addDefinition(Definition definition){
    	def.add(definition);
    }
    
    /**
     * Determine whether this entry is for the given word.
     */
    public boolean isEntryFor(String word){
    	if(this.word.equals(word)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public String toString(){ 
    	String out = "[";
    	for(int i = 0; i < def.size(); i++){
    		out+= "("+def.get(i).getType()+")"+ def.get(i).getDescription()+", ";
    	}
    	out = out.substring(0, out.length()-2)+"]\n";
    	return out;
    }
	
}
