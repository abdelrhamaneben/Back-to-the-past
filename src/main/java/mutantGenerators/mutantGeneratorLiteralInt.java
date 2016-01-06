package mutantGenerators;
import java.util.ArrayList;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldWrite;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtVariableRead;
import spoon.reflect.code.CtVariableWrite;
/**
 * Cette class crée une unique mutation par code spooné
 * @author benhammou
 *
 */
public class mutantGeneratorLiteralInt extends AbstractProcessor<CtLiteral<Integer>>{
	
	/**
	 * Représente la liste des mutation précedente
	 */
	public static ArrayList<String> trace = new ArrayList<String>();
	/**
	 * Défini si une mutation à déja eu lieu
	 */
	public static boolean MUTED = false;
	/**
	 * Représente le rang de la mutation à adopter
	 */
	public int rang;

	/**
	 * Constructeur : définir par default le rang à 1
	 */
	public mutantGeneratorLiteralInt() {
		this.rang = 1;
	}
	
	/**
	 *  Definir le rang de mutation
	 * @param rang représente le nouvelle mutation à adopter
	 */
	public void setRang(int rang) {
		this.rang = rang;
	}
	
	/**
	 * Accepter les valeurs constante numérique jamais modifié par le generateur de mutant 
	 */
	@Override
	public boolean isToBeProcessed(CtLiteral<Integer> element) {
		if(!element.getType().getSimpleName().equals("int") || MUTED == true) return false;
		if(trace.contains(element.getParent().getSignature()+" : "+element.getSignature() + " Value : " + this.rang)) {
			return false;
		}
		trace.add(element.getParent().getSignature()+" : "+element.getSignature()+ " Value : " + this.rang);
		return true;
	}
	
	/**
	 * Modifier la constante courrante
	 */
	public void process(CtLiteral<Integer> element) {
		System.out.println("Valeur précedente : " + element.getParent());
		int oldVal = element.getValue();
		int newVal = getNewValue(rang,oldVal);
		element.setValue(newVal);
		System.out.println("Nouvelle valeur : " + element.getParent());
		MUTED = true;
	}
	
	/**
	 * Fournir la nouvelle valeur de constante du mutant
	 * @param rang représente le numero de la mutation à adopter
	 * @param value représente la valeur par défaut
	 * @return
	 */
	public  int getNewValue(int rang, int value) {
		switch(rang) {
			case 1 : return 0;
			case 2 : return 5000;
			case 3 : return -5;
			case 4 : return 5;
			case 5 : return value +1;
			case 6 : return value -1;
		}
		return -5000;
	}
}
