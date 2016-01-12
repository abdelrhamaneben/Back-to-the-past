package mutantGenerators;
import java.util.ArrayList;

import models.TypeValidator;
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
public class mutantGeneratorLiteralInt extends abstractGenerator<CtLiteral<Integer>>{
	
	/**
	 * Constructeur : définir par default le rang à 1
	 */
	public mutantGeneratorLiteralInt() {
		super(7);
	}

	@Override
	public boolean acceptableElement(CtLiteral<Integer> element) {
		if(!TypeValidator.isNumber(element)) return false;
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
