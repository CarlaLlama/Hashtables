import java.util.Arrays;
import java.util.List;

/**
 * Simple hash table implementation of Dictionary using sequential chaining
 * @author Carla Wilby
 * @version 02/05/2015
 */

public class SCHashtable implements Dictionary{
	private final static int DEFAULT_SIZE = 50;
    
    private ChainedEntry[] table;
    private int entries;
    private static int probeInsert;		// for performance testing
 
    public SCHashtable() { this(DEFAULT_SIZE); }
    
    public SCHashtable(int size) { 
        this.table = new SCEntryImpl[size+1];
        for(int i = 0; i < table.length;  i++){
        	table[i] = null;
        }
        this.entries = 0;      
    }
    
    /**
     * Convert the key to a int position using a hash function
     * @param key
     * @return hash value of the key (used for position in table)
     */
    public int hashFunction(String key) {
    	int hashInt = 0;
        for(int i = 0; i < key.length();i++){
        	hashInt = hashInt*37 + key.charAt(i);
        }
        hashInt %= table.length;
        if(hashInt<0){
        	hashInt += table.length;
        }
        return hashInt;
    }
    

    /**
     * Insert a word and its definition into appropriate position
     * @param word, definition
     */
    public void insert(String word, Definition definition) {       	
    	int hkey = hashFunction(word);							//Get hash index
    	ChainedEntry pos = table[hkey];							//Assign variable to this position
    	if(pos == null){						
    		table[hkey] = new SCEntryImpl(word, definition);//if nothing in this position, insert new word
    		entries++;
    		probeInsert++;	
    	}else{
    		while(pos.getNext()!=null){
    			probeInsert++;
    			if(pos.getWord().equals(word)){					//If word we're looking for exists at this position, add a definition. 
    				pos.addDefinition(definition);
    				return;
    			}else{
    				pos = pos.getNext();						//Move to next node
    			}
    		}
    		if(pos.getWord().equals(word)){						//Catch check of last word that has a .getNext() of null
    			probeInsert++;	
				pos.addDefinition(definition);
				return;
    		}
    		((SCEntryImpl) pos).setNext(new SCEntryImpl(word, definition)); //set the next node of last node equal to the new node
    	}
    }
    
    /**
     * Check table using hash value of word to find if word entry exists
     * @param word
     * @return true (if table contains an entry for word) false (otherwise)
     */
    public boolean containsWord(String word) {
    	int hkey = hashFunction(word);//probeCount initially 0, increments in the case of a clash
    	ChainedEntry pos = table[hkey];
    	if(pos == null){				 //if position where word would be is null, then word cannot exist
    		return false;
    	}else{
    		while(pos!=null){
    			if(pos.getWord().equals(word)){
    				return true;
    			}
    		}
    		return false;
    	}
    }

    /**
     * Find word, and return its list of definitions
     * @param word
     * @return arrayList of definitions if exists, otherwise null
     */
    public List<Definition> getDefinitions(String word) {
    	int hkey = hashFunction(word);
    	ChainedEntry pos = table[hkey];
    	if(pos == null){					//if position where word would be is null, then word cannot exist
    		return null;
    	}else{
			while(pos!=null){
				if(pos.getWord().equals(word)){
					return pos.getDefinitions();
				}else{
					pos = pos.getNext();
				}
			}
			return null;
    	}
    }

        
    public boolean isEmpty() { return entries == 0; }
    
    public void empty() { this.table = new SCEntryImpl[this.table.length]; this.entries=0; }
    
    public int size() { return this.entries; }
    
    public static int getTotalInsertProbe(){
    	return probeInsert;
    }

    /* Hash Table Functions */
    
    /**
     * Obtain the current load factor (entries / table size).
     */
    public double loadFactor() { return entries/(double)table.length; }
        
    
    /* DEBUGGING CODE */
    /**
     * Print the contents of the given hashtable.
     */
    public static void debug_print(SCHashtable hashtable) {
        ChainedEntry[] table = hashtable.table;
        for(int i=0; i<table.length; i++) {
            System.out.printf("\n%4d : %s", i, table[i]);
        }
    }
            
}
