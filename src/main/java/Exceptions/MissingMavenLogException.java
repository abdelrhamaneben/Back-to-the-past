package Exceptions;

public class MissingMavenLogException extends Exception{
	public MissingMavenLogException() {
		super("Impossible de lire les fichier du dossier /target/surefire-reports");
	}
}
