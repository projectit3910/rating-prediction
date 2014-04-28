import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Personalize {
	Hashtable<Integer, List<Data>> h = new Hashtable<Integer, List<Data>>();
	
	public double rate(Data d, int prev){
		List<Data> l;
		int sum = 0;
		int numRatings = 0;
		
		if(h.containsKey(d.userId)){
			l = h.get(d.userId);
			for(Data data : l){
				if(data.genre == d.genre){
					sum = data.rating.ordinal()+sum;
					numRatings++;
				}
			}
			l.add(d);
			h.remove(d.userId);
			h.put(d.userId, l);
		}
		else{
			l = new ArrayList<Data>();
			l.add(d);
			h.put(d.userId, l);
			return -1;
		}

		return combine(sum, numRatings, prev);
	}
	
	private double combine(double personal, int numRate, double prev){
		double tot = 0;
		for(int i = 0 ; i < numRate; i ++){
			tot = (1-tot)/75 + tot;
		}
		if(numRate != 0){
			personal = personal/numRate;
		}
		else
			tot = 0;
		return tot*personal + (1-tot)* prev;
	}
}
