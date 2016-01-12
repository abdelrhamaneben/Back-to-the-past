package mutantGenerators;

import models.TypeVaidator;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.reference.CtTypeReference;

public class mutantGeneratorOperator  extends abstractGenerator<CtBinaryOperator>{

	/**
	 * Constructeur Prend comme nombre de mutation le nombre de character possible(128)
	 */
	public mutantGeneratorOperator() {
		super(5);
	}

	public void process(CtBinaryOperator element) {
		String expression = element.getLeftHandOperand().toString() + getValue() + element.getRightHandOperand();
		CtCodeSnippetExpression<Boolean> NewBinaryStatement = getFactory().Core()
				.createCodeSnippetExpression();
		NewBinaryStatement.setValue('(' + expression + ')');
		System.out.println(expression);
		element.replace((CtExpression) NewBinaryStatement);
		this.MUTED = true;
	}

	@Override
	public boolean acceptableElement(CtBinaryOperator element) {
		
		return TypeVaidator.isNumber(element);
	}
	
	public String getValue() {
		switch(this.rang) {
		case 1: return "-";
		case 2 : return "*";
		case 3 : return "/";
		case 4 : return "%";
		}
		return "+";
	}
}
