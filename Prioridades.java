import java.io.*;
import java.util.*;

/**
 * Classe Prioridade
 * 
 * Esta classe é responsável pela implementação do algoritmo de escalonamento propriamente dito.
 * Ela guarda todos os processos e os gerencia conforme as suas instruções.
 */

public class Prioridades {
	
	// ALGORITMO DE ESCALONAMENTO POR PRIORIDADES

	private static int quantum;	
	/* Registradores de Uso Geral */
	private int registradorX, registradorY;

	/* Processos */
	private static LinkedList<Processo> processosProntos = new LinkedList<Processo>();
	private static LinkedList<Processo> processosBloqueados = new LinkedList<Processo>();

	/* Tabela de Processos */
	private static LinkedList<Processo> tabelaProcesso;

	/* Dados para estatística */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;

	public static void carrega( LinkedList<Processo> listaProcessos, int q ) {
		tabelaProcesso = listaProcessos;
		quantum = q;

		executa();
	}

	public static void executa() {
		// implementação do algoritmo
		System.out.println( "Executando..." );
		System.out.printf( "Quantum(%d)\n", quantum );
	}

	/* funções auxiliares da execução */

	public static boolean bloqueiaProcesso() {
		return processosBloqueados.add( processosProntos.remove() );
	}

	public static void reordena( LinkedList<Processo> p ) {
		Collections.sort( p );
	}

	public static void imprimeProcessos( LinkedList<Processo> p ) {
		Iterator it = p.iterator();
		while( it.hasNext() )
			System.out.println( it.next() );
	}
}
