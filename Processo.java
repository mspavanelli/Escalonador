import java.util.LinkedList;

/**
 * Classe Processo
 * 
 * - Inclui todos os dados pertinentes à abstração de um processo
 */

public class Processo implements Comparable<Processo> {
	
	public static final int BLOQUEADO = 0;
	public static final int EXECUTANDO = 1;
	public static final int PRONTO = 2;

	private String nome;
	private int prioridade;
	private int estado;
	private int creditos;
	private LinkedList<String> instrucoes = new LinkedList<>();

	public Processo( String nome, int prioridade, LinkedList<String> instrucoes ) {
		this.nome = nome;
		this.prioridade = prioridade;
		this.creditos = prioridade;
		this.instrucoes = instrucoes;
	}

	public void setEstado( int e ) {
		if ( e == 0 || e == 1 || e == 2 )
			estado = e;
	}

	public int getEstado() {
		return estado;
	}

	public int getCreditos() {
		return creditos;
	}

	public void debitaCredito() {
		if ( creditos > 0 )
			creditos--;
	}

	public int compareTo( Processo p ) {
		return Integer.compare( p.creditos, this.creditos );
	}

	public String toString() {
		return nome + " (" + creditos + ")";
	}
}