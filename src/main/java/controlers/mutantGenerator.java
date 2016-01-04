package controlers;
import java.util.ArrayList;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtLiteral;

public class mutantGenerator extends AbstractProcessor<CtLiteral<Integer>>{
	
	public static ArrayList<String> trace = new ArrayList<String>();
	public static boolean MUTED = false;
	public int value;

	public mutantGenerator() {
		this.value = 0;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public boolean isToBeProcessed(CtLiteral<Integer> element) {
		if(!element.getType().getSimpleName().equals("int") || MUTED == true) return false;
		if(trace.contains(element.getParent().getSignature()+" : "+element.getSignature() + " Value : " + this.value)) {
			return false;
		}
		trace.add(element.getParent().getSignature()+" : "+element.getSignature()+ " Value : " + this.value);
		return true;
	}
	
	public void process(CtLiteral<Integer> element) {
		element.setValue(value);
		System.out.println("New Signature : " + element.getParent().getSignature());
		MUTED = true;
	}
}
