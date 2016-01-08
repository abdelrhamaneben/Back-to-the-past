package Analysers;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtInvocation;

public class invocationsGetter extends AbstractProcessor<CtInvocation> {

	
	public boolean isToBeProcessed(CtInvocation element) {
		if(element.getExecutable().getDeclaration() != null)return true;
		return false;
	}
	
	public void process(CtInvocation element) {
		System.out.println(element.getSignature());
	}

}
