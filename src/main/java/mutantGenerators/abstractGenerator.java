package mutantGenerators;

import java.util.ArrayList;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.declaration.CtElement;

public abstract class  abstractGenerator<E extends CtElement> extends AbstractProcessor<E> {
	/**
	 * Représente la liste des mutation précedente
	 */
	public static ArrayList<String> trace = new ArrayList<String>();
	/**
	 * Represente le nombre de mutation possible
	 */
	public int round;
	/**
	 * Représente le rang de la mutation à adopter
	 */
	public int rang;
	
	/**
	 * Défini si une mutation à déja eu lieu
	 */
	public static boolean MUTED = false;
	
	public abstractGenerator(int round) {
		this.round = round;
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
	 * Accepter plus le proccess après une mutation
	 */
	@Override
	public boolean isToBeProcessed(E element) {
		if(MUTED == true) return false;
		if(trace.contains(element.getParent().getSignature()+" : "+element.getSignature() + " Value : " + this.rang)) {
			return false;
		}
		trace.add(element.getParent().getSignature()+" : "+element.getSignature()+ " Value : " + this.rang);
		return this.acceptableElement(element);
	}
	
	public String getTraceSignature( E element) {
		return "round : "+ round +","+ element.getParent().getSignature()+" : "+element.getSignature() + " Value : " + this.rang;
	}
	
	public abstract boolean acceptableElement(E element);
}
