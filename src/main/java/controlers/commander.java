package controlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

public class commander {

	private static String tmpFolder = ".tmpProject/";
	
	public static  void movePrjectToTmp(String src) throws IOException {
		FileUtils.copyDirectoryToDirectory(new File(src),new File(tmpFolder));
	}
	
	/**
	 * vide le répertoire temporaire
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
	
	private static final String MAVEN_HOME_PATH = "/usr/local/Cellar/maven/3.2.5/libexec";

	public static void cleanCompileTest(String pomLocation) throws MavenInvocationException {
	    InvocationRequest request = new DefaultInvocationRequest();
	    request.setPomFile(new File(pomLocation));
	    request.setGoals(Arrays.asList("compile"));
	
	    Invoker invoker = new DefaultInvoker();
	    invoker.setMavenHome(new File(MAVEN_HOME_PATH));
	    invoker.setLocalRepositoryDirectory(new File("/Users/abdelrhamanebenhammou/Desktop/Back-to-the-past/Appli-Ex"));
	    InvocationResult result =  invoker.execute(request);
	    System.out.println("Result : " + result);
	 }
}
