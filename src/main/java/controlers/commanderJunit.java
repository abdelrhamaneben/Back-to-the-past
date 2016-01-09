package controlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import Exceptions.MissingMavenLogException;
import models.log;

public class commanderJunit extends abstractCommander{

	public commanderJunit(String COMPILER_PATH) {
		super(COMPILER_PATH);
	}
	
	private  URLClassLoader classLoader;
	
	private  List<String> findTestFolder(String path) {
		File[] files = new File(path).listFiles();
		List<String> pathToSources = new ArrayList<String>();
		for (File f : files) {
			if (f.isDirectory())
				pathToSources.addAll(findTestFolder(f.getAbsolutePath()));
			else if (f.getName().endsWith(".java"))
				pathToSources.add(f.getAbsolutePath());
		}

		return pathToSources;
	}
	
	private  String convertToClassName(String file) {
		String pattern = Pattern.quote(File.separator);
		int ind = file.split(pattern).length;
		return file.split(pattern)[ind-2] + "." + file.split(pattern)[ind -1].replace(".java", "");
	}

	private  List<String> findJavaFiles(String path){
		File[] files = new File(path).listFiles();
		List<String> pathToSources = new ArrayList<String>();
		for(File f : files){
			if(f.isDirectory())
				pathToSources.addAll(findJavaFiles(f.getAbsolutePath()));
			else if(f.getName().endsWith(".java"))
				pathToSources.add(f.getAbsolutePath());
		}
		return pathToSources;
	}
	
	@Override
	public log cleanCompileTest() throws Exception {

		log mylog = new log();
		JUnitCore junit = new JUnitCore();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		
		List<String> listSourceFiles = findJavaFiles(this.tmpFolder + "src/main/java");
		List<String> listTestFiles = findJavaFiles(this.tmpFolder + "src/test/java");

		for(String file : listSourceFiles){
				System.out.println(file);
				compiler.run(null, null, null, file);
		}
		for(String file : listTestFiles) {
			System.out.println(file);
			compiler.run(null, null, null, "-cp",  this.COMPILER_PATH +":" + this.tmpFolder + "src/main/java", file);
		}
			
		
		classLoader = URLClassLoader.newInstance(new URL[] {
				new File(this.tmpFolder + "src/main/java/").toURI().toURL(), new File(this.tmpFolder +"src/test/java/").toURI().toURL()
		});
		
	      try {
	  		for(String file : listSourceFiles)
	    		  Class.forName(convertToClassName(file), true, classLoader);
	  		for(String file : listTestFiles){
	  			
	  			Result results = junit.run(Class.forName(convertToClassName(file), true, classLoader));
	  			mylog.failure += results.getFailures().size();
	  		}
	       }
	       catch(Exception e) {
	    	   e.printStackTrace();
	          System.out.println("Impossible d'instancier la classe");
	       }
	      return mylog;
	}

	@Override
	public log nbFailure(String ProjectPath) throws FileNotFoundException, MissingMavenLogException {
		return null;
	}

}
