package mutantGenerators;

import spoon.reflect.code.CtLiteral;

public class mutantGeneratorLitChar  extends abstractGenerator<CtLiteral<Character>>{

	/**
	 * Constructeur Prend comme nombre de mutation le nombre de character possible(128)
	 */
	public mutantGeneratorLitChar() {
		super(94);
	}

	public void process(CtLiteral<Character> element) {
		System.out.println("Char initial : " + element.getValue());
		element.setValue(getValue());
		System.out.println("Char changed : " + element.getValue());
		this.MUTED = true;
	}

	@Override
	public boolean acceptableElement(CtLiteral<Character> element) {
		if(!element.getType().getSimpleName().equals("char")) return false;
		return true;
	}
	
	public char getValue() {
		return (char) (this.rang + 31);
	}
}
