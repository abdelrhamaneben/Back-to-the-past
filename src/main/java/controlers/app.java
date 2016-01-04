package controlers;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import introclassJava.checksum_2c155667_003WhiteboxTest;
import spoon.Launcher;
import spoon.reflect.factory.Factory;

public class app {

	public static void main(String[] args) {
		
		// LANCEMENT INITIAL DES TESTS
		JUnitCore core = new JUnitCore();
		Result result = core.run(checksum_2c155667_003WhiteboxTest.class);
		int initialError  = result.getFailureCount();
		System.out.println("initial Errors : " + initialError);
		
		// GENERATION DE MUTANT
		mutantGenerator gen = new mutantGenerator();
		
		Launcher spoon = new Launcher(); 
        Factory factory = spoon.getFactory();
       
        spoon.addProcessor(gen);
     
        String inputMain = "/Users/abdelrhamanebenhammou/Desktop/Back-to-the-past/Appli-Ex/src/main/java";
        int nbMutant = 0;
        while(true) {
	        int [] arrayInt = new int[5];
	        arrayInt[0] = -5435;
	        arrayInt[1] = -5;
	        arrayInt[2] = 0;
	        arrayInt[3] = 5;
	        arrayInt[4] = 5435;
	        nbMutant = gen.trace.size();
	        for(int i : arrayInt) {
	        	gen.MUTED = false;
	        	gen.setValue(i);
	        	spoon.run(new String[]{"-i",inputMain});
	        	launchTest(initialError);	
	        }
	        if(nbMutant == gen.trace.size()) break;
	        
        }
        System.out.println("Sorti");
	}
	
	public static  boolean launchTest(int initialError){
		// SI IL Y A DES TESTS DE RÉSOLU AVEC CETTE MUTATION ON PREND LE CODE MUTÉ COMME NOUVELLE SOURCE
		return false;
	}
}


