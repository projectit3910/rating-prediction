import java.io.Reader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

public class Parser {
    private BufferedReader br;
    private Genre[] genres = Genre.values();
    private Gender[] genders = Gender.values();
    private Age[] ages = Age.values();
    private Occupation[] occupations = Occupation.values();
    private Rating[] ratings = Rating.values();

    public Parser(Reader in) {
        br = new BufferedReader(in);
    }
    
    // Parses data by reading CSV lines and finding the proper enum values
    public List<Data> run() throws Exception {
        List<Data> data = new ArrayList<Data>();
        
        String line = br.readLine();
        while (line != null) {
            String[] split = line.split(",");
            int[] indices = new int[split.length];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = Integer.parseInt(split[i]);
            }
            data.add(new Data(
                genres[indices[0]],
                genders[indices[1]],
                ages[indices[2]],
                occupations[indices[3]],
                ratings[indices[4]]
            ));
            line = br.readLine();
        }
        
        return data;
    }
}