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
	LinkedList<Processo> processosProntos = new LinkedList<Processo>();
	LinkedList<Processo> processosBloqueados = new LinkedList<Processo>();

	/* Dados para estatística */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;

	public static void executa( LinkedList<Processo> listaProcessos, int quantum ) {
		// implementação do algoritmo
		System.out.println( "Executando..." );

		// Impressão dos Processos (debug)
		Iterator it = listaProcessos.iterator();
		while ( it.hasNext() )
			System.out.println( it.next() );
	}

	/* funções auxiliares da execução */

	public static boolean bloqueiaProcesso() {
		return processosBloqueados.add( processosProntos.remove() );
	}

	public static void reordena( LinkedList<Processo> processos ) {}
}
