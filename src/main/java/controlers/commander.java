package controlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import Exceptions.MissingMavenLogException;
import models.log;
/**
 * Utilitaire permettant de commander les différentes actions sur le dossier source et le temporaire
 * @author benhammou
 *
 */
public class commander {

	/**
	 * Représente le dossier contenant le code à tester
	 */
	public static String tmpFolder = ".tmpProject/";
	/**
	 * représente l'enplacement du dossier MAVEN utilisé par l'invokerMaven
	 */
	public static String MAVEN_HOME_PATH ;
	
	/**
	 * Déplacer le projet à source dans le dossier temporaire de test
	 * @param src
	 * @throws IOException
	 */
	
	public static  void moveProjectToTmp(String src) throws IOException {
		FileUtils.copyDirectory(new File(src),new File(tmpFolder));
	}
	
	/**
	 * Déplacer le dossier temporaire dans le répertoire source
	 * (Utiliser pour définir le tmp de test comme dossier source)
	 * @param src
	 * @throws IOException
	 */
	public static  void moveTmpToProject(String src) throws IOException {
		FileUtils.copyDirectory(new File(tmpFolder),new File(src));
	}
	
	/**
	 * vider le répertoire temporaire
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static void resetTmpFolder() throws SecurityException, IOException{
		File theDir = new File(tmpFolder);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			theDir.mkdir();
		}
		else {
			FileUtils.deleteDirectory(theDir);
			resetTmpFolder();
		}
	}
	

	/**
	 * Compile , test and return the number of failure
	 * @param pomLocation
	 * @return
	 * @throws MavenInvocationException
	 * @throws FileNotFoundException
	 * @throws MissingMavenLogException 
	 */
	public static log cleanCompileTest() throws MavenInvocationException, FileNotFoundException, MissingMavenLogException {
	    InvocationRequest request = new DefaultInvocationRequest();
	    request.setPomFile(new File(tmpFolder + "/pom.xml"));
	    request.setGoals(Arrays.asList("clean","compile","test"));
	
	    Invoker invoker = new DefaultInvoker();
	    

	    invoker.setMavenHome(new File(MAVEN_HOME_PATH));
	    
	    invoker.setLocalRepositoryDirectory(new File(tmpFolder));
	    InvocationResult result =  invoker.execute(request);
	    return nbFailure(tmpFolder);
	 }
	
	/**
	 * Return number of failure in log
	 * @param ProjectPath
	 * @return
	 * @throws FileNotFoundException
	 * @throws MissingMavenLogException 
	 */
	public static log nbFailure(String ProjectPath) throws FileNotFoundException, MissingMavenLogException {
		File folder = new File(ProjectPath + "/target/surefire-reports");
		File[] listOfFiles = folder.listFiles();
		int nbFailure = 0;
		int nbError= 0;
		String line = "";
		Scanner scanner = null;
		if(listOfFiles == null) throw new MissingMavenLogException();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile() && listOfFiles[i].getName().contains(".xml")) {
	    	  scanner = new Scanner(listOfFiles[i]);
	    	  while (scanner.hasNextLine()) {
	    	        line = scanner.nextLine();
	    	        if(line.contains("</failure>")) nbFailure++;
	    	        if(line.contains("</error>")) nbError++;
	    	  }
	    	  scanner.close();
	      }
	    }
	    return new log(nbFailure,nbError);
	}
}
