package controlers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import models.log;
import mutantGenerators.abstractGenerator;
import mutantGenerators.mutantGeneratorLiteralInt;
import spoon.Launcher;
import spoon.reflect.factory.Factory;

/**
 * Classe principale permettant de faire boucler sur chaque mutation possible en vérifiant les corrections possibles 
 * @author benhammou
 *
 */
public class app {
	

	// DEFAULT PARAMS
	//String pomURL = "/Users/abdelrhamanebenhammou/Desktop/Back-to-the-past/Appli-Ex/pom.xml";
	//String pomURL = "/home/m2iagl/benhammou/Bureau/Back-to-the-past/Appli-Ex/pom.xml";
	
	// FOR MACOSX
	//commander.MAVEN_HOME_PATH = "/usr/local/Cellar/maven/3.2.5/libexec";
	// FOR UNIX
	//commander.MAVEN_HOME_PATH = "/usr/share/maven";
	/**
	 * Lanceur principale
	 * @param args prend comme unique paramètre un lien vers un fichier pom.xml contenu dans le projet à corriger
	 */
	public static void main(String[] args) {
		// Valider arguments
		if(args.length != 2) {
			System.out.println("Invalid Parameters");
			System.out.println("Usage : commande [POM.XML PATH] [MAVEN HOME PATH]");
			log.writeLog("'error';'0';'null'\n");
			System.exit(1);
		}
		
		commander.MAVEN_HOME_PATH = args[1];
		String pomURL = args[0];
	
		String ProjectPath = pomURL.replace("pom.xml", "");
		String inputMain = pomURL.replace("pom.xml", "src/main/java");
		
		// récuperation du nombre initial de failure
		log initialError = launchTest(ProjectPath,true,pomURL);
		log logtest = null;
		int bugDelete = 0;
		int nbDeplacement = 0;
		
		if(initialError.failure == 0) {
			System.out.println("Aucun bug à corriger");
			log.writeLog("'"+0 + "';'" + 0 + "';'" + pomURL+ "'\n");
			System.exit(0);
		}
		
		// Définition du generateur de mutation à adopter (Change Literal Int)
		abstractGenerator gen = new mutantGeneratorLiteralInt();
		Launcher spoon = new Launcher(); 
        Factory factory = spoon.getFactory();  
        spoon.addProcessor(gen);
     
        // Boucle sur toute les mutations possibles
        int nbMutant = 0;
        while(true) {
	        nbMutant = gen.trace.size();
	        for(int i  = 1;i<= gen.round;i++) {
	        	gen.MUTED = false;
	        	gen.setRang(i);
	        	spoon.run(new String[]{"-i",inputMain,"-o",commander.tmpFolder + "/src/main/java"});
	        	logtest = launchTest(ProjectPath,false,pomURL);
	        	System.out.println("Failure détecté : " + logtest.failure + ", Errors : " + logtest.error);
	        	if(logtest.failure < initialError.failure && logtest.error <= initialError.error) {
	        		bugDelete += initialError.failure -logtest.failure ;
	        		initialError.error = logtest.error;
	        		initialError.failure = logtest.failure;
	        		try {
						commander.moveTmpToProject(ProjectPath);
						nbDeplacement++;
					} catch (IOException e) {
						e.printStackTrace();
						log.writeLog("'error';'2';'" + pomURL+ "'\n");
						System.exit(1);
					}
	        	}
	        	 
	        }
	        if(nbMutant == gen.trace.size() || initialError.failure == 0) break;
	       
        }
        log.writeLog("'"+bugDelete + "';'" + nbDeplacement + "';'" + pomURL+ "'\n");
        System.out.println( bugDelete + " Bug(s) réparé(s) soit " + nbDeplacement + " refactor du projet");
	}
	
	/**
	 * Lancer les tests sur le projet temporaire
	 * @param ProjectPath représente le dossier du projet source
	 * @return
	 */
	public static log launchTest(String ProjectPath,boolean reset, String pomURL) {
		try {
			if(reset) {
				//commander.resetTmpFolder();
				commander.moveProjectToTmp(ProjectPath);
			}
			return commander.cleanCompileTest();
		} catch (Exception e) {
			int typeError = (reset)?1:3;
			e.printStackTrace();
			log.writeLog("'error';'"+typeError+"';'" + pomURL+ "'\n");
			System.exit(1);
		}
		return new log();
	}
}


