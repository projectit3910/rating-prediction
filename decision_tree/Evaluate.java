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

			while(!tree.isClassification()){
				searchTree(d);
			}

			System.out.println("Estimated: "+tree.classification.ordinal()+ " Observed: "+d.rating.ordinal());
			itd = (tree.classification.ordinal()-d.rating.ordinal())*(tree.classification.ordinal()-d.rating.ordinal());
			err = err + itd;
		}
		err = err/testing.size();
		System.out.println("Squared Error: "+err);

	}
	
	private void searchTree(Data d){
		if(tree.attribute == tree.attribute.OCCUPATION){
    		tree = tree.getChild(tree.attribute.OCCUPATION.values[d.occupation.ordinal()]);
		}
		else if(tree.attribute == tree.attribute.GENRE){
    		tree = tree.getChild(tree.attribute.GENRE.values[d.genre.ordinal()]);
		}
		else if(tree.attribute == tree.attribute.GENDER){
    		tree = tree.getChild(tree.attribute.GENDER.values[d.gender.ordinal()]);
		}
		else if(tree.attribute == tree.attribute.AGE){
    		tree = tree.getChild(tree.attribute.AGE.values[d.age.ordinal()]);
		}
	}
	
}