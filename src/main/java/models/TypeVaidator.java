package models;

import spoon.reflect.code.CtExpression;
import spoon.reflect.reference.CtTypeReference;

public class TypeVaidator {

	public static boolean isBoolean(CtExpression<?> operand) {
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
	
	public static boolean isNumber(CtExpression<?> operand) {
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
	
}
