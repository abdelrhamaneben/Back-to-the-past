package controlers;

import mutantGenerators.abstractGenerator;
import mutantGenerators.mutantGeneratorLitChar;
import mutantGenerators.mutantGeneratorLiteralInt;
import mutantGenerators.mutantGeneratorOperator;
import spoon.Launcher;

public class app2 {

	public static void main(String[] args) {
		abstractGenerator gen = new mutantGeneratorOperator();
		Launcher spoon = new Launcher(); 
        spoon.addProcessor(gen);
        spoon.run(new String[]{"-i","/Users/abdelrhamanebenhammou/Desktop/IDL/MutantFactory/Appli-Ex/src/main/java"});
	}

}
