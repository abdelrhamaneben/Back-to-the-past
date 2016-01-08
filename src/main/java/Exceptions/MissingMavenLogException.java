package Exceptions;

public class MissingMavenLogException extends Exception{
	public MissingMavenLogException() {
		super("Impossible de lire les fichier du dossier /target/surefire-reports. Il doit y avoir une erreur dans le lancement de Maven");
	}
}
