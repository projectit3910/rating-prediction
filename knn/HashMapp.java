import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HashMapp {
	public static void main(String[] args) throws FileNotFoundException {

		String currline;
		HashMap<Integer, ArrayList<Integer[]>> Map = new HashMap<Integer, ArrayList<Integer[]>>();
		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		try
		{
			while ((currline = br.readLine()) != null) {

				String[] col = currline.split(" ");

				if (col.length < 1) {
				}

				try {
					int colKey = Integer.parseInt(col[0]);

					Integer[] colValue = new Integer[col.length - 1];

					for (int i = 1; i < col.length; i++) {
						colValue[i - 1] = Integer.parseInt(col[i]);
					}

					if (!Map.containsKey(colKey)) {
						Map.put(colKey, new ArrayList<Integer[]>());
					}
					Map.get(colKey).add(colValue);
				} catch (NumberFormatException e) {
				}
				for (Integer key : Map.keySet()) {
					String row = key + ""; 
					for (Integer[] rows : Map.get(key)) {
						for (Integer columns : rows) {
							row += " " + col;
						}
					}
					System.out.println(row);
				}
			}

		}	
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
