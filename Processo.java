import java.util.LinkedList;

public class Processo {
	
	private String nome;
	private int prioridade;
	private LinkedList<String> instrucoes = new LinkedList<>();

	public Processo( String nome, LinkedList<String> instrucoes ) {
		this.nome = nome;
		this.instrucoes = instrucoes;
	}

	public String toString() {
		return nome + "\n" + instrucoes;
	}
}