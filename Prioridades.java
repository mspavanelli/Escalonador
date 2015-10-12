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
	
	/* Processos */
	private static LinkedList<Processo> processosProntos = new LinkedList<Processo>();
	private static LinkedList<Processo> processosBloqueados = new LinkedList<Processo>();

	/* Dados para estatística */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;

	public static void executa( LinkedList<Processo> listaProcessos, int quantum ) {
		// implementação do algoritmo
		System.out.println( "Executando..." );

		// debug
		imprimeProcessos( listaProcessos );
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
