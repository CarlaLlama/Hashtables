/**
 * Implementation of Entry ADT for sequential chaining using EntryImpl
 * @author Carla Wilby
 * @version 02/05/2015
 */

public class SCEntryImpl extends EntryImpl implements ChainedEntry{
	
	private ChainedEntry nextWord;

	public SCEntryImpl(String word, Definition definition) {
		super(word, definition);
		nextWord = null;
	}

	public ChainedEntry getNext() {
		return nextWord;
	}
	
	public void setNext(ChainedEntry link){
		nextWord = link;
	}

}
