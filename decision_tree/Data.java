public class Data {
    public final Genre genre;
    public final Gender gender;
    public final Age age;
    public final Occupation occupation;
    public final Rating rating;
    public final int userId;
    
    public Attributable[] attributes;
    
    public Data(
        Genre genre,
        Gender gender,
        Age age,
        Occupation occupation,
        Rating rating,
        int userId) {
    
        this.genre = genre;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.rating = rating;
        this.userId = userId;
        
        attributes = new Attributable[Attribute.values().length];
        attributes[0] = genre;
        attributes[1] = gender;
        attributes[2] = age;
        attributes[3] = occupation;
    }
}