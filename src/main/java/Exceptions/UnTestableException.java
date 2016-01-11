package Exceptions;

public class UnTestableException extends Exception{
	public UnTestableException() {
		super("Impossible de lire les fichier du dossier /target/surefire-reports. Il doit y avoir une erreur dans le lancement de Maven");
	}
}
