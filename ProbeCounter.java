import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Insertion performance testing class
 * @author Carla Wilby
 * @version 06/05/2015
 */

public class ProbeCounter {
	
	public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
		new ProbeCounter();
		
	}
	
	/*
	 * Output controller method, with the load factors and table sizes associated with each load factor
	 */
	public ProbeCounter() throws FileNotFoundException, IOException{
		System.out.println("***Hash table load performance***\n");
		System.out.println(testMethod(0.5, 7478));
		System.out.println(testMethod(0.75, 4986));
		System.out.println(testMethod(1.0, 3739));
	}
	
	/**
	 * Method that calculates the probe totals for all three hashtables
	 * @param loadFactor
	 * @param size
	 * @return string of results to print out
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String testMethod(double loadFactor, int size) throws FileNotFoundException, IOException{
		String out = "Test with a load factor of: "+loadFactor+"\n\n";
		out+= "Total insertion probes in:\n";
		
		Dictionary dictionary = new LPHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		int lpTotal = ((LPHashtable)dictionary).getTotalInsertProbe();
		out+= "Linear probing: "+lpTotal+"\n";
		
		int qpTotal = 1;
		try{								//Catches QP high load factor error
		dictionary = new QPHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		qpTotal = ((QPHashtable)dictionary).getTotalInsertProbe();
		out+= "Quadratic probing: "+ qpTotal + "\n";
		}catch(Exception e){
			System.out.println("Load failed due to extreme load factor");
		}
		
		dictionary = new SCHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		int scTotal = ((SCHashtable)dictionary).getTotalInsertProbe();
		out+= "Seperate chaining: "+ scTotal + "\n";
		
		out+= "\nPercentage differences between:\n";
		
		out+= "Quadratic probing and linear probing: "+percDifference(qpTotal, lpTotal);
		out+= "Sequential chaining and linear probing: "+percDifference(scTotal, lpTotal);
		out+= "Sequential chaining and quadratic probing: "+percDifference(scTotal, qpTotal);
		
		
		return out;
	}
	
	/**
	 * Calculate the percentage difference between inputs a and b
	 * @param a
	 * @param b
	 * @return
	 */
	public String percDifference(int a, int b){
		double perc = (a-b);
		perc = Math.abs((perc/a)*100.0);
		return String.format("%.5g", perc)+"%\n";
	}

}
