package controlers;
import java.util.ArrayList;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLiteral;
/**
 * 
 * @author benhammou
 *
 */
public class mutantGenerator extends AbstractProcessor<CtLiteral<Integer>>{
	
	public static ArrayList<String> trace = new ArrayList<String>();
	public static boolean MUTED = false;
	public int rang;

	/**
	 * 
	 */
	public mutantGenerator() {
		this.rang = 1;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.rang = value;
	}
	
	/**
	 * 
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
	 * 
	 */
	public void process(CtLiteral<Integer> element) {
		int oldVal = element.getValue();
		int newVal = getNewValue(rang,oldVal);
		element.setValue(newVal);
		System.out.println("modification de : " + element.toString() + "passage de " + oldVal + " à " + newVal);
		MUTED = true;
	}
	/**
	 * 
	 * @param rang
	 * @param value
	 * @return
	 */
	public  int getNewValue(int rang, int value) {
		switch(rang) {
			case 1 : return -5000;
			case 2 : return 5000;
			case 3 : return -5;
			case 4 : return 5;
			case 5 : return value +1;
			case 6 : return value -1;
		}
		return 0;
	}
}
