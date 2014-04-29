import java.util.List;

public class Evaluate {

	private Node tree;
	private List<Data> testing;
	Personalize pRating = new Personalize();
	
	public Evaluate(Node tree, List<Data> testing){
		this.testing = testing;
		this.tree = tree;
	}
	
	public void guessRating(){
		Node ptr = tree;
		double err = 0;
		double itd = 0;
		double personalRating = 0;
		int counter = 0;
		int correct = 0;
		
		for(Data d : testing){
			counter++;
			tree = ptr;
			
			while(!tree.isClassification()){
				searchTree(d);
			}
			personalRating = pRating.rate(d, tree.classification.ordinal());
			itd = (Math.round(personalRating)-d.rating.ordinal())*(Math.round(personalRating)-d.rating.ordinal());
			err = err + itd;
			if(Math.round(personalRating) == d.rating.ordinal() ){
				correct++;
			}
            
            System.out.println("Prediction: " +
                Integer.toString((int)Math.round(personalRating) + 1) +
                "; Actual: " + Integer.toString(d.rating.ordinal() + 1));
		}
		err = err/counter;
		System.out.println((double)correct/counter);
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