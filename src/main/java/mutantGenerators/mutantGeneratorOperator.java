package mutantGenerators;

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
		
		return isNumber(element);
	}
	private boolean isNumber(CtExpression<?> operand) {
		try {
			operand.getType().getActualClass();
		} catch (Exception e) {
			return false;
		}
		if (operand.getType().toString().equals(CtTypeReference.NULL_TYPE_NAME))
			return false;
		if (operand.toString().contains(".class"))
			return false;
		return operand.getType().getSimpleName().equals("int")
			|| operand.getType().getSimpleName().equals("long")
			|| operand.getType().getSimpleName().equals("byte")
			|| operand.getType().getSimpleName().equals("char")
		|| operand.getType().getSimpleName().equals("float")
		|| operand.getType().getSimpleName().equals("double")
		|| Number.class.isAssignableFrom(operand.getType().getActualClass());
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
