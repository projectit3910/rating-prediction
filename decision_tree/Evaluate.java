import java.util.List;


public class Evaluate {

	private Node tree;
	private List<Data> testing;
	
	public Evaluate(Node tree, List<Data> testing){
		this.testing = testing;
		this.tree = tree;
	}
	
	public void guessRating(){
		System.out.println("ENTER");
		Node ptr = tree;
		double err = 0;
		double itd = 0;
		for(Data d : testing){
			tree = ptr;
	    	//	System.out.print(tree.attribute.GENRE.values[d.genre.ordinal()]+" ");
	    		tree = tree.getChild(tree.attribute.GENRE.values[d.genre.ordinal()]);
	    //		System.out.print(tree.attribute.GENDER.values[d.gender.ordinal()]+" ");
	    		tree = tree.getChild(tree.attribute.GENDER.values[d.gender.ordinal()]);
	  //  		System.out.print(tree.attribute.AGE.values[d.age.ordinal()]+" ");
	    		tree = tree.getChild(tree.attribute.AGE.values[d.age.ordinal()]);
	//    		System.out.print(tree.attribute.OCCUPATION.values[d.occupation.ordinal()]+" ");
	    		tree = tree.getChild(tree.attribute.OCCUPATION.values[d.occupation.ordinal()]);
	    		
	    		System.out.println(tree.classification.ordinal()+ " "+d.rating.ordinal());
	    		itd = (tree.classification.ordinal()-d.rating.ordinal())*(tree.classification.ordinal()-d.rating.ordinal());
	    		err = err + itd;
		}
		err = err/testing.size();
		System.out.println(err);

	}
	

}