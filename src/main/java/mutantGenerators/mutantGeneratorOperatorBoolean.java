package mutantGenerators;

import spoon.reflect.code.BinaryOperatorKind;
import spoon.reflect.code.CtBinaryOperator;
import spoon.reflect.code.CtCodeSnippetExpression;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.code.CtExpression;
import spoon.reflect.reference.CtTypeReference;

public class mutantGeneratorOperatorBoolean  extends abstractGenerator<CtBinaryOperator>{

	/**
	 * Constructeur Prend comme nombre de mutation le nombre de character possible(128)
	 */
	public mutantGeneratorOperatorBoolean() {
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
				
		return operand.getType().getSimpleName().equals("boolean")
		|| Number.class.isAssignableFrom(operand.getType().getActualClass());
	}
	
	public String getValue() {
		switch(this.rang) {
		case 1: return BinaryOperatorKind.AND.toString();
		case 2 : return BinaryOperatorKind.BITAND.toString();
		case 3 : return BinaryOperatorKind.BITOR.toString();
		case 4 : return BinaryOperatorKind.BITXOR.toString();
		case 5 : return BinaryOperatorKind.GE.toString();
		case 6 : return BinaryOperatorKind.GT.toString();
		case 7 : return BinaryOperatorKind.LE.toString();
		case 8 : return BinaryOperatorKind.LT.toString();
		case 9 : return BinaryOperatorKind.OR.toString();
		case 10 : return BinaryOperatorKind.SL.toString();
		case 11 : return BinaryOperatorKind.SR.toString();
		case 12 : return BinaryOperatorKind.USR.toString();
		}
		return BinaryOperatorKind.EQ.toString();
	}
}
