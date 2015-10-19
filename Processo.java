import java.util.ArrayList;

/**
 * Classe Processo
 * 
 * - Inclui todos os dados pertinentes à abstração de um processo
 */

public class Processo implements Comparable<Processo> {
	
	private String nome;
	private int prioridade;
	private int creditos;
	private ArrayList<String> instrucoes = new ArrayList<String>();

	public Processo( String nome, int prioridade, ArrayList<String> instrucoes ) {
		this.nome = nome;
		this.prioridade = prioridade;
		this.creditos = prioridade;
		this.instrucoes = instrucoes;
	}

	public String getNome() {
		return nome;
	}
	
	public int getPrioridade() {
		return prioridade;
	}
	
	public ArrayList getInstrucoes() {
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