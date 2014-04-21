public class Data {
    public Genre genre;
    public Gender gender;
    public Age age;
    public Occupation occupation;
    public Rating rating;
    
    public Data(Genre genre, Gender gender, Age age, Occupation occupation,
        Rating rating) {
    
        this.genre = genre;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
        this.rating = rating;
    }
}