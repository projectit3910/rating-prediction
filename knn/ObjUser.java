public class ObjUser {
	public final int gender;
	public final int age;
	public final int occupation;
	public final int rating;
	public final int userId;

	public ObjUser(
			int gender,
			int age,
			int occupation,
			int rating,
			int userId) {

		this.gender = gender;
		this.age = age;
		this.occupation = occupation;
		this.rating = rating;
		this.userId = userId;
	}

	public int getGender( ){
		return gender;
	}

	public int getAge( ){
		return age;
	}

	public int getOccupation( ){
		return occupation;
	}

	public int getRating( ){
		return rating;
	}

	public int getUserId( ){
		return userId;
	}
	
//	public String getObj( ){
//		String info= "gender + age + occupation + rating + userId"
//		return info ;
//	}
}
