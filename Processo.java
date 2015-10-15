import java.util.LinkedList;

/**
 * Classe Processo
 * 
 * - Inclui todos os dados pertinentes à abstração de um processo
 */

public class Processo implements Comparable<Processo> {
	
	private String nome;
	private int prioridade;
	private int creditos;
	private LinkedList<String> instrucoes = new LinkedList<>();

	public Processo( String nome, int prioridade, LinkedList<String> instrucoes ) {
		this.nome = nome;
		this.prioridade = prioridade;
		this.creditos = prioridade;
		this.instrucoes = instrucoes;
	}

	public String getNome() {
		return nome;
	}

	public LinkedList getInstrucoes() {
		return instrucoes;
	}

	public String getInstrucao( int i ) {
		return instrucoes.get(i);
	}

	public int compareTo( Processo p ) {
		return Integer.compare( p.creditos, this.creditos );
	}

	public String toString() {
		return nome + " (" + creditos + ")";
	}
}