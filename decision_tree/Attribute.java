public enum Attribute {
    GENRE(Genre.class),
    GENDER(Gender.class),
    AGE(Age.class),
    OCCUPATION(Occupation.class);
    
    public Attributable[] values;
    
    Attribute(Class<? extends Attributable> type) {
        values = type.getEnumConstants();
    }
}