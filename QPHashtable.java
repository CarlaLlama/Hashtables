import java.util.Arrays;
import java.util.List;

/**
 * Simple hash table implementation of Dictionary using quadratic probing.
 * 
 * @author Carla Wilby
 * @version 24/4/2015
 */
public class QPHashtable implements Dictionary{
	private final static int DEFAULT_SIZE = 50;
	    
    private Entry[] table;
    private int entries;
    private int probeInsert = 0;   // for performance testing
    private int searchProbe = 0;   // for search performance testing
    
    public QPHashtable() { this(DEFAULT_SIZE); }
    
    public QPHashtable(int size) { 
        this.table = new EntryImpl[primer(size+1)];
        this.entries = 0; 
        probeInsert = 0;
        searchProbe = 0;
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
    
    private int probeCount = 0;     //used to keep track of the number of probes before required slot is found
    
    /**
     * Check table using hash value of word to find if word entry exists
     * @param word
     * @return true (if table contains an entry for word) false (otherwise)
     */
    public boolean containsWord(String word) {
    	int hkey = hashFunction(word)+(probeCount*probeCount);//probeCount initially 0, increments in the case of a clash
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
    	else if(!table[hkey].isEntryFor(word)){	 //if correct position, but word isn't there, execute recursive quadratic probe until found.
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
    	int hkey = hashFunction(word)+(probeCount*probeCount);   //probeCount initially 0, increments in the case of a clash
    	while(hkey >= table.length){					//if hkey reaches end of table, loop to beginning
    		hkey -= table.length;
    	}
    	if(probeCount > table.length){              //if probeCount has checked through entire table, then word cannot exist
    		return null;
    	}
    	if(table[hkey] == null){					//if position where word would be is null, then word cannot exist
    		searchProbe++;
    		return null;
    	}
    	else if(table[hkey].isEntryFor(word)){		//if correct position, and word exists in that position, then match, return definitions list
    		searchProbe++;
    		probeCount = 0;
    		return table[hkey].getDefinitions();
    	}
    	else{										//if correct position, but word isn't there, execute recursive quadratic probe until found.
    		probeCount++;
    		searchProbe++;
    		return getDefinitions(word);
    	}
    }

    /**
     * Insert a word and its definition into appropriate position
     * @param word, definition
     */
    public void insert(String word, Definition definition) {       	
    	int hkey = hashFunction(word)+(probeCount*probeCount);		//probeCount initially 0, increments quadratically in the case of a clash until next available space is found
    	while(hkey > table.length){						//if hkey reaches end of table, loop to beginning
    		hkey -= table.length;
    	}
    	if(probeCount > table.length){              //if probeCount has checked through entire table, then word cannot exist
    		//throws Exception;
    	}
    	if(loadFactor()>=0.5){							//in case of table saturation, double the size of the table
    		table = Arrays.copyOf(table, primer(table.length*2));
    	}
    	if(table[hkey] == null){						//if position where word would be is null, then word doesn't already exist. Insert here.
    		table[hkey] = new EntryImpl(word, definition);
    		probeCount = 0;
    		entries++;
    		probeInsert++;
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
    
    public int getTotalInsertProbe(){
    	return probeInsert;
    }
    
    public int getTotalSearchProbe(){
    	return searchProbe;
    }
    
    public int primer(int num){     //silly method to ensure that a number is prime, if it isn't, increment until it is
    	int factor = 0;
		for(int i = 2; i < num; i++){
    		if(num%i == 0){
    			factor += i;
    		}
    	}
		if(factor==0){
			return num;
		}else{
			return primer(num+1);
		}
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
    public static void debug_print(QPHashtable hashtable) {
        Entry[] table = hashtable.table;
        for(int i=0; i<table.length; i++) {
            System.out.printf("\n%4d : %s", i, table[i]);
        }
    }
            
}
