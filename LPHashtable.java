import java.util.Arrays;
import java.util.List;
/**
 * Simple hash table implementation of Dictionary using linear probing.
 * 
 * @author Stephan Jamieson and Carla Wilby
 * @version 24/4/2015
 */

public class LPHashtable implements Dictionary
{
    private final static int DEFAULT_SIZE = 50;
    
    private Entry[] table;
    private int entries;
    private static int probeInsert;		// for performance testing
 
    public LPHashtable() { this(DEFAULT_SIZE); }
    
    public LPHashtable(int size) { 
        this.table = new EntryImpl[size+1];
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
    
    private int probeCount = 0;     //used to keep track of the number of linear probes before required slot is found
    
    /**
     * Check table using hash value of word to find if word entry exists
     * @param word
     * @return true (if table contains an entry for word) false (otherwise)
     */
    public boolean containsWord(String word) {
    	int hkey = hashFunction(word)+probeCount;//probeCount initially 0, increments in the case of a clash
    	if(hkey > table.length){				 //if hkey reaches end of table, loop to beginning
    		hkey -= table.length;
    	}
    	if(probeCount > table.length){			 //if probeCount has checked through entire table, then word cannot exist
    		return false;
    	}
    	if(table[hkey] == null){				 //if position where word would be is null, then word cannot exist
    		return false;
    	}
    	else if(table[hkey].isEntryFor(word)){	 //if correct position, and word exists in that position, then match
    		probeCount = 0;
    		return true;
    	}
    	else if(!table[hkey].isEntryFor(word)){	 //if correct position, but word isn't there, execute recursive linear probe until found.
    		probeCount++;
    		containsWord(word);
    	}
    	return false;
    }

    /**
     * Find word, and return its list of definitions
     * @param word
     * @return arrayList of definitions if exists, otherwise null
     */
    public List<Definition> getDefinitions(String word) {
    	int hkey = hashFunction(word)+probeCount;   //probeCount initially 0, increments in the case of a clash
    	if(hkey > table.length){					//if hkey reaches end of table, loop to beginning
    		hkey -= table.length;
    	}
    	if(probeCount > table.length){              //if probeCount has checked through entire table, then word cannot exist
    		return null;
    	}
    	if(table[hkey] == null){					//if position where word would be is null, then word cannot exist
    		return null;
    	}
    	else if(table[hkey].isEntryFor(word)){		//if correct position, and word exists in that position, then match, return definitions list
    		probeCount = 0;
    		return table[hkey].getDefinitions();
    	}
    	else{										//if correct position, but word isn't there, execute recursive linear probe until found.
    		probeCount++;
    		return getDefinitions(word);
    	}
    }

    /**
     * Insert a word and its definition into appropriate position
     * @param word, definition
     */
    public void insert(String word, Definition definition) {       	
    	int hkey = hashFunction(word)+probeCount;		//probeCount initially 0, increments in the case of a clash until next available space is found
    	if(hkey > table.length){						//if hkey reaches end of table, loop to beginning
    		hkey -= table.length;
    	}
    	if(table[hkey] == null){						//if position where word would be is null, then word doesn't already exist. Insert here.
    		table[hkey] = new EntryImpl(word, definition);
    		probeCount = 0;
    		probeInsert++;								//counter for performance testing
    		entries++;
    	}
    	else if(table[hkey].isEntryFor(word) ){			//if the current position is the word we're looking for, add the current definition to the word's definition list.
    		table[hkey].addDefinition(definition);
    		probeCount = 0;
    		probeInsert++;
    	}
    	else if(!table[hkey].isEntryFor(word)){			//if correct position, but clash has occurred, continue until next available space is found.
    		probeCount++;
    		probeInsert++;
    		insert(word, definition);
    	}
    }
        
    public boolean isEmpty() { return entries == 0; }
    
    public void empty() { this.table = new EntryImpl[this.table.length]; this.entries=0; }
    
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
    public static void debug_print(LPHashtable hashtable) {
        Entry[] table = hashtable.table;
        for(int i=0; i<table.length; i++) {
            System.out.printf("\n%4d : %s", i, table[i]);
        }
    }
            
}
