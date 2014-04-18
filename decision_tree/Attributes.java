public class Attributes {
    public enum Attribute {
    }

    public enum Genre implements Attribute {
        ACTION,
        ADVENTURE,
        ANIMATION,
        CHILDRENS,
        COMEDY,
        CRIME,
        DOCUMENTARY,
        DRAMA,
        FANTASY,
        FILM_NOIR,
        HORROR,
        MUSICAL,
        MYSTERY,
        ROMANCE,
        SCI_FI,
        THRILLER,
        WAR,
        WESTERN
    }
    
    public enum Gender implements Attribute {
        MALE,
        FEMALE
    }
    
    public enum Age implements Attribute {
        _1_17,
        _18_24,
        _25_34,
        _35_44,
        _45_49,
        _50_55,
        _56_PLUS
    }
    
    public enum Occupation implements Attribute {
        OTHER,
        ACADEMIC,
        ARTIST,
        ADMINISTRATOR,
        COLLEGE_STUDENT,
        CUSTOMER_SERVICE,
        HEALTH_CARE,
        EXECUTIVE,
        FARMER,
        HOMEMAKER,
        K12_STUDENT,
        LAWYER,
        PROGRAMMER,
        RETIRED,
        MARKETING,
        SCIENTIST,
        SELF_EMPLOYED,
        ENGINEER,
        TRADESMAN,
        UNEMPLOYED,
        WRITER
    }
}