package controlers;

import java.io.IOException;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import spoon.Launcher;
import spoon.reflect.factory.Factory;

public class app {

	public static void main(String[] args) {
		
		// DEFAULT PARAMS
		String pomURL = "/Users/abdelrhamanebenhammou/Desktop/Back-to-the-past/Appli-Ex/pom.xml";
		String ProjectPath = pomURL.replace("pom.xml", "");
		String inputMain = pomURL.replace("pom.xml", "src/main/");
		
		try {
			commander.cleanCompileTest(pomURL);
		} catch (MavenInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			commander.resetTmpFolder();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Class TestClass = checksum_2c155667_003WhiteboxTest.class;
		
		// LANCEMENT INITIAL DES TESTS
		int initialError  = commander.launchtest(TestClass);
		
		// GENERATION DE MUTANT
		mutantGenerator gen = new mutantGenerator();
		
		Launcher spoon = new Launcher(); 
        Factory factory = spoon.getFactory();
       
        spoon.addProcessor(gen);
     
       
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
	        	launchTest(initialError,TestClass);	
	        }
	        if(nbMutant == gen.trace.size()) break;
	        
        }*/
        System.out.println("Sorti");
	}
	
	/*public static  boolean launchTest(int initialError,Class TestClass){
		return (commander.launchtest(TestClass) < initialError);
		// SI IL Y A DES TESTS DE RÉSOLU AVEC CETTE MUTATION ON PREND LE CODE MUTÉ COMME NOUVELLE SOURCE
	}*/
}


