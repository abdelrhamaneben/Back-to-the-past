package controlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import spoon.Launcher;
import spoon.reflect.factory.Factory;

public class app {

	public static void main(String[] args) {
		
		// DEFAULT PARAMS
		//String pomURL = "/Users/abdelrhamanebenhammou/Desktop/Back-to-the-past/Appli-Ex/pom.xml";
		String pomURL = "/home/m2iagl/benhammou/Bureau/Back-to-the-past/Appli-Ex/pom.xml";
		// FOR MACOSX
		//commander.MAVEN_HOME_PATH = "/usr/local/Cellar/maven/3.2.5/libexec";
		// FOR UNIX
		commander.MAVEN_HOME_PATH = "/usr/share/maven";
		
		String ProjectPath = pomURL.replace("pom.xml", "");
		String inputMain = pomURL.replace("pom.xml", "src/main/");
		
		int initialError = launchTest(ProjectPath,true);
		int tmpNbFailure = 0;
		
		// GENERATION DE MUTANT
		mutantGenerator gen = new mutantGenerator();
		
		Launcher spoon = new Launcher(); 
        Factory factory = spoon.getFactory();
       
        spoon.addProcessor(gen);
     
       
        int nbMutant = 0;
        while(true) {
	        nbMutant = gen.trace.size();
	        for(int i  = 1;i<=7;i++) {
	        	gen.MUTED = false;
	        	gen.setValue(i);
	        	spoon.run(new String[]{"-i",inputMain,"-o",commander.tmpFolder + "/src/main/java"});
	        	tmpNbFailure = launchTest(ProjectPath,true);
	        	if(tmpNbFailure < initialError) {
	        		initialError = tmpNbFailure;
	        		try {
						commander.moveTmpToProject(ProjectPath);
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(1);
					}
	        	}
	        }
	        if(nbMutant == gen.trace.size()) break;
	        
        }
        System.out.println("Sortie");
	}
	
	public static int launchTest(String ProjectPath,boolean reset) {
		try {
			if(reset)  commander.resetTmpFolder();
			commander.moveProjectToTmp(ProjectPath);
			return commander.cleanCompileTest();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return 0;
	}
}


