package controlers;

import java.io.IOException;

import org.apache.maven.shared.invoker.MavenInvocationException;

import Exceptions.UnTestableException;
import models.log;
import mutantGenerators.abstractGenerator;
import mutantGenerators.mutantGeneratorLitChar;
import mutantGenerators.mutantGeneratorLiteralInt;
import mutantGenerators.mutantGeneratorOperator;
import mutantGenerators.mutantGeneratorOperatorBoolean;
import spoon.Launcher;

/**
 * Classe principale permettant de faire boucler sur chaque mutation possible en vérifiant les corrections possibles 
 * @author benhammou
 *
 */
public class app {
	

	public static abstractCommander commander = null;
	public static long initial_time;
	public static int nbMutant = 0;
	/**
	 * Lanceur principale
	 * @param args prend comme unique paramètre un lien vers un fichier pom.xml contenu dans le projet à corriger
	 * @throws IOException 
	 * @throws SecurityException 
	 * @throws MissingMavenLogException 
	 * @throws MavenInvocationException 
	 */
	public static void main(String[] args) throws Exception {
		
		// Valider arguments
		if(args.length != 3) {
			System.out.println("Invalid Parameters");
			System.out.println("Usage : commande [POM.XML PATH] [MAVEN HOME PATH] [mutation number 1-3]");
			System.out.println("Mutation 1 : Literal Integer changement");
			System.out.println("Mutation 2 : Literal Character changement");
			System.out.println("Mutation 3 : Binary Operator(Integer) changement");
			System.out.println("Mutation 4 : Binary Operator(Boolean) changement");
			log.writeLog("error;0;0;'null'\n");
			System.exit(1);
		}
		commander = new commanderMaven(args[1]);
		//commander = new commanderJunit(args[1]);
		String pomURL = args[0];
		int MutationNumber = Integer.parseInt(args[2]);
	
		String ProjectPath = pomURL.replace("pom.xml", "");
		String inputMain = pomURL.replace("pom.xml", "src/main/java");
		
		initial_time = log.getCurrentTimeStamp();
		
		// récuperation du nombre initial de failure
		log initialError = launchTest(ProjectPath,true,pomURL);
		log logtest = null;
		int bugDelete = 0;
		int nbDeplacement = 0;
		
		if(initialError.failure == 0) {
			System.out.println("Aucun bug à corriger");
			log.writeLog(""+0 + ";" + 0 + ";0;"+(log.getCurrentTimeStamp() - initial_time)+";'" + pomURL+ "'\n");
			System.exit(0);
		}
		
		// Définition du generateur de mutation à adopter (Change Literal Int)
		abstractGenerator gen = null;
		switch(MutationNumber) {
			case 1 : gen = new mutantGeneratorLiteralInt();break;
			case 2 : gen = new mutantGeneratorLitChar();break;
			case 3 : gen = new mutantGeneratorOperator();break;
			default : gen = new mutantGeneratorOperatorBoolean();break;
		}
	
		Launcher spoon = new Launcher(); 
        spoon.addProcessor(gen);
     
        // Boucle sur toute les mutations possibles
        int nbMutant = 0;
        while(true) {
	        nbMutant = gen.trace.size();
	        for(int i  = 1;i<= gen.round;i++) {
	        	try{
	        		nbMutant++;
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
		        			log.writeLogMutation(bugDelete + ";" + gen.trace.get(gen.trace.size() -1) + ";" +pomURL+ "\n");
							nbDeplacement++;
						} catch (IOException e) {
							e.printStackTrace();
							log.writeLog("error;2;"+nbMutant+";"+ (log.getCurrentTimeStamp() - initial_time) +";'" + pomURL+ "'\n");
							System.exit(1);
						}
		        	}
	        	}catch(Exception e) {
	        		System.out.println("____________________ERROR_____________________");
	        	}
	        }
	        if(nbMutant == gen.trace.size() || initialError.failure == 0) break;
        }
        log.writeLog(bugDelete + ";" + nbDeplacement + ";"+nbMutant+";"+(log.getCurrentTimeStamp() - initial_time)+";'" + pomURL+ "'\n");
        System.out.println( bugDelete + " Bug(s) réparé(s) soit " + nbDeplacement + " refactor du projet");
	}
	
	/**
	 * Lancer les tests sur le projet temporaire
	 * @param ProjectPath représente le dossier du projet source
	 * @return
	 * @throws Exception
	 */
	public static log launchTest(String ProjectPath,boolean reset, String pomURL) throws Exception {
			if(reset) {
				commander.resetTmpFolder();
				commander.moveProjectToTmp(ProjectPath);
			}
			return commander.cleanCompileTest();
		
	}
	
	
}


