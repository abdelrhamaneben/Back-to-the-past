package controlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.MavenInvocationException;

import Exceptions.MissingMavenLogException;
import models.log;

public abstract class abstractCommander {
	/**
	 * Représente le dossier contenant le code à tester
	 */
	public String tmpFolder = ".tmpProject/";
	
	/**
	 * représente l'enplacement du dossier du compiler
	 */
	public  String COMPILER_PATH ;
	
	public abstractCommander(String COMPILER_PATH) {
		this.COMPILER_PATH = COMPILER_PATH;
	}
	
	/**
	 * Déplacer le projet à source dans le dossier temporaire de test
	 * @param src
	 * @throws IOException
	 */
	public void moveProjectToTmp(String src) throws IOException {
		FileUtils.copyDirectory(new File(src),new File(tmpFolder));
	}
	
	/**
	 * Déplacer le dossier temporaire dans le répertoire source
	 * (Utiliser pour définir le tmp de test comme dossier source)
	 * @param src
	 * @throws IOException
	 */
	public void moveTmpToProject(String src) throws IOException {
		FileUtils.copyDirectory(new File(tmpFolder),new File(src));
	}
	
	/**
	 * vider le répertoire temporaire
	 * @throws SecurityException
	 * @throws IOException
	 */
	public void resetTmpFolder() throws SecurityException, IOException{
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
	 * @throws MalformedURLException 
	 */
	public abstract log cleanCompileTest() throws Exception;

	
	/**
	 * Return number of failure in log
	 * @param ProjectPath
	 * @return
	 * @throws FileNotFoundException
	 * @throws MissingMavenLogException 
	 */
	public abstract log nbFailure(String ProjectPath) throws FileNotFoundException, MissingMavenLogException ;
}
