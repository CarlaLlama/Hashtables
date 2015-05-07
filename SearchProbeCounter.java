import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * Search performance testing class
 * @author Carla Wilby
 * @version 07/05/2015
 */

public class SearchProbeCounter {	
	
	public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
		new SearchProbeCounter();
	}
	
	public SearchProbeCounter() throws FileNotFoundException, IOException{
		System.out.println("***Hash table search performance***\n");
		testSearchMethod(0.5, 7478);
		testSearchMethod(0.75, 4986);
		testSearchMethod(1.0, 3739);
	}
	
	/**
	 * Method that calculates the search probe totals for all three hashtables
	 * @param loadFactor
	 * @param size
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void testSearchMethod(double loadFactor, int size) throws FileNotFoundException, IOException{
		
		System.out.println("Search test with a load factor of: "+loadFactor+"\n");
		int i = 0;
		int lpTotal = 0;
		int qpTotal = 0;
		int scTotal = 0;
		
		while(i < 100){  //running 100 tests initially
			Dictionary dictionary = new LPHashtable(size);
			FileUtil.load(dictionary, "lexicon.txt");
			for(int j = 0; j < 100; j++){
				String[] randomWords = randomSample();
				//System.out.println(randomWords[j]);
				dictionary.getDefinitions(randomWords[j]);
				
			}
			lpTotal += ((LPHashtable)dictionary).getTotalSearchProbe();
			
			dictionary = new QPHashtable(size);
			FileUtil.load(dictionary, "lexicon.txt");
			for(int j = 0; j < 100; j++){
				String[] randomWords = randomSample();
				dictionary.getDefinitions(randomWords[i]);
				
			}
			qpTotal += ((QPHashtable)dictionary).getTotalSearchProbe();
			
			dictionary = new SCHashtable(size);
			FileUtil.load(dictionary, "lexicon.txt");
			for(int j = 0; j < 100; j++){
				String[] randomWords = randomSample();
				dictionary.getDefinitions(randomWords[i]);
				
			}
			scTotal += ((SCHashtable)dictionary).getTotalSearchProbe();
			
			i++;
		}
		
		System.out.println("Average number of search probes for a word in:");
		
		System.out.println("Linear probing: "+lpTotal/(100.0*100));
		System.out.println("Quadratic probing: "+qpTotal/(100.0*100));
		System.out.println("Seperate chaining: "+scTotal/(100.0*100) + "\n");

		
		System.out.println("\nPercentage differences between:");
		System.out.println("Quadratic probing and linear probing: "+percDifference(qpTotal, lpTotal));
		System.out.println("Sequential chaining and linear probing: "+percDifference(scTotal, lpTotal));
		System.out.println("Sequential chaining and quadratic probing: "+percDifference(scTotal, qpTotal) + "\n");
	}
	
	/**
	 * Get 80 random words from the lexicon, and 20 random word strings
	 * @return
	 */
	public String[] randomSample(){
		//first get 80 random words that exist in the dictionary
		String[] randomArr = new String[100];
		String[] allWords = FileUtil.getWordArr();
		Random r = new Random();
		for(int i = 0; i < randomArr.length; i++){
			int rndnum = r.nextInt(4997);
			randomArr[i] = allWords[rndnum];
		}
		//then get 20 random words that don't exist in the dictionary
		for(int i = 80; i < 100; i++){
			randomArr[i] = randString(r);
		}
		return randomArr;
	}
	
	//random string generator
	public static String randString(Random r){
	    char[] text = new char[5];
	    for (int i = 0; i < 5; i++){
	        text[i] = (char)r.nextInt();
	    }
	    return new String(text);
	}
	
	
	public String percDifference(int a, int b){
		double perc = (a-b);
		perc = Math.abs((perc/a)*100.0);
		return String.format("%.5g", perc)+"%";
	}

	
}
