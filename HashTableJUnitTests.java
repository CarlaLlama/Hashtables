import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import org.junit.Assert;
import org.junit.Test;


public class HashTableJUnitTests {
	//LPHashtable tests
	@Test
	public void testLoadLPHashTable() throws FileNotFoundException, IOException {
		Dictionary dictionary = new LPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(3739, dictionary.size());
	}
	
	@Test
	public void testLPContains() throws FileNotFoundException, IOException{
		Dictionary dictionary = new LPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		boolean contains = dictionary.containsWord("bear");
		assertEquals(true, contains);
	}
	
	@Test
	public void testLPGetDefinitions() throws FileNotFoundException, IOException{
		Dictionary dictionary = new LPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		List<Definition> contains = dictionary.getDefinitions("able");
		assertEquals("[(adjective)  capable]", contains.toString());
	}
	@Test
	public void testLoadLPIsEmpty() throws FileNotFoundException, IOException {
		Dictionary dictionary = new LPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(false, dictionary.isEmpty());
	}
	
	//QPHashtable tests
	@Test
	public void testLoadQPHashTable() throws FileNotFoundException, IOException {
		Dictionary dictionary = new QPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(3739, dictionary.size());
	}
	
	@Test
	public void testQPContains() throws FileNotFoundException, IOException{
		Dictionary dictionary = new QPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		boolean contains = dictionary.containsWord("bear");
		assertEquals(true, contains);
	}
	
	@Test
	public void testQPGetDefinitions() throws FileNotFoundException, IOException{
		Dictionary dictionary = new QPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		List<Definition> contains = dictionary.getDefinitions("able");
		assertEquals("[(adjective)  capable]", contains.toString());
	}
	@Test
	public void testLoadQPIsEmpty() throws FileNotFoundException, IOException {
		Dictionary dictionary = new QPHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(false, dictionary.isEmpty());
	}
	
	//SCHashtable tests
	@Test
	public void testLoadSCHashTable() throws FileNotFoundException, IOException {
		Dictionary dictionary = new SCHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(3001, dictionary.size());
	}
	
	@Test
	public void testSCContains() throws FileNotFoundException, IOException{
		Dictionary dictionary = new SCHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		boolean contains = dictionary.containsWord("bear");
		assertEquals(true, contains);
	}
	
	@Test
	public void testSCGetDefinitions() throws FileNotFoundException, IOException{
		Dictionary dictionary = new SCHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		List<Definition> contains = dictionary.getDefinitions("able");
		assertEquals("[(adjective)  capable]", contains.toString());
	}
	@Test
	public void testLoadSCIsEmpty() throws FileNotFoundException, IOException {
		Dictionary dictionary = new SCHashtable(7481);
		FileUtil.load(dictionary, "lexicon.txt");
		assertEquals(false, dictionary.isEmpty());
	}
}
