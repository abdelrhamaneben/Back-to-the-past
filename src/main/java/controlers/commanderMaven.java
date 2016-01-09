package controlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

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
public class commanderMaven extends abstractCommander{

	
	
	public commanderMaven(String COMPILER_PATH) {
		super(COMPILER_PATH);
	}

	/**
	 * Compile , test and return the number of failure
	 * @param pomLocation
	 * @return
	 * @throws MavenInvocationException
	 * @throws FileNotFoundException
	 * @throws MissingMavenLogException 
	 */
	public log cleanCompileTest() throws Exception {
	    InvocationRequest request = new DefaultInvocationRequest();
	    request.setPomFile(new File(tmpFolder + "/pom.xml"));
	    request.setGoals(Arrays.asList("clean","compile","test"));
	
	    Invoker invoker = new DefaultInvoker();
	    invoker.setMavenHome(new File(COMPILER_PATH));
	    
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
	public log nbFailure(String ProjectPath) throws FileNotFoundException, MissingMavenLogException {
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
