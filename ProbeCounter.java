import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 
 * Performance testing class
 * @author Carla Wilby
 * @version 06/05/2015
 */

public class ProbeCounter {
	
	public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {
		new ProbeCounter();
		
	}
	
	public ProbeCounter() throws FileNotFoundException, IOException{
		System.out.println("***Hash table load performance***\n");
		System.out.println(testMethod(0.5, 7478));
		System.out.println(testMethod(0.75, 4986));
		System.out.println(testMethod(0.1, 37390));
	}
	
	public String testMethod(double loadFactor, int size) throws FileNotFoundException, IOException{
		String out = "Test with a load factor of: "+loadFactor+"\n\n";
		out+= "Total insertion probes in:\n";
		
		Dictionary dictionary = new LPHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		int lpTotal = LPHashtable.getTotalInsertProbe();
		out+= "Linear probing: "+lpTotal+"\n";
		
		dictionary = new QPHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		int qpTotal = QPHashtable.getTotalInsertProbe();
		out+= "Quadratic probing: "+ qpTotal + "\n";
		
		dictionary = new SCHashtable(size);
		FileUtil.load(dictionary, "lexicon.txt");
		int scTotal = SCHashtable.getTotalInsertProbe();
		out+= "Seperate chaining: "+ scTotal + "\n";
		
		out+= "\nPercentage differences between:\n";
		
		out+= "Quadratic probing and linear probing: "+percDifference(lpTotal, qpTotal);
		out+= "Sequential chaining and linear probing: "+percDifference(scTotal, qpTotal);
		out+= "Sequential chaining and quadratic probing: "+percDifference(lpTotal, qpTotal);
		
		
		return out;
	}
	
	public String percDifference(int a, int b){
		double perc = (a-b);
		perc = Math.abs((perc/a)*100);
		return String.format("%.5g%n", perc);
	}

}
