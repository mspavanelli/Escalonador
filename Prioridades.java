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
	private static LinkedList<BCP> processosProntos = new LinkedList<BCP>();
	private static LinkedList<BCP> processosBloqueados = new LinkedList<BCP>();

	/* Tabela de Processos */
	private static LinkedList<BCP> tabelaProcesso;

	/* Dados para estatística */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;

	public static void carrega( LinkedList<Processo> listaProcessos, int q ) {
		quantum = q;
		tabelaProcesso = carregaBCP(listaProcessos);
		ordena(tabelaProcesso);
		executa(tabelaProcesso);
	}
	
	public static void executa(LinkedList<BCP> tabelaProcesso) {
		Iterator<BCP> it = tabelaProcesso.iterator();
		while( it.hasNext()){
			if(processosProntos.getFirst() != null && processosProntos.getFirst().getCreditos() != 0 )
				executaProcesso(processosProntos.getFirst()); 
			else if (processosProntos.getFirst() == null)
				reordenaProntos(processosProntos);
			else if(processosProntos.getFirst() != null && processosProntos.getFirst().getCreditos() == 0){
				if(processosBloqueados == null)
					redistribuiCreditos(processosProntos);
				else if(processosBloqueados != null && processosBloqueados.getFirst().getCreditos() > 0 )
					executaProcesso(processosProntos.getFirst()); 
				else if(processosBloqueados != null && processosBloqueados.getFirst().getCreditos() == 0)
					redistribuiCreditos(tabelaProcesso);
			}
				
			//Pensar como continuar rodando se proximo tem menos creditos
		}
		//imprimeTabela(tabelaProcesso);
	}

	public static void executaProcesso(BCP processoAtual) {
		//N�o estamos usando estado EXECUTANDO
		System.out.println(processoAtual);
		//
		processoAtual.debitaCredito();
		int cont = 0;
		while(cont <quantum){
			cont++;
			//
			System.out.println(processoAtual.getInstrucao(processoAtual.getContador()));
			//
			if(processoAtual.getInstrucao(processoAtual.getContador()).contains("=")){
				if(processoAtual.getInstrucao(processoAtual.getContador()).contains("X")){
					processoAtual.setResgistrador(3, "X");
				}
				else
					processoAtual.setResgistrador(3, "Y");
				processoAtual.setContador();
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("E/S")){
				bloqueiaProcesso(processoAtual);
				processoAtual.setContador();
				break;
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("COM")){
				processoAtual.setContador();
			}
			else{
				finalizaProcesso(processoAtual);
				break;
			}
		}
		reordenaProntos(processosProntos);
		processoAtual.setRodada();
	}

	/* funções auxiliares da execução */

	public static void bloqueiaProcesso(BCP p) {
		p.setEstado(0);
		processosProntos.remove(p);
		processosBloqueados.add(p);
	}
	
	public static void finalizaProcesso(BCP p) {
		tabelaProcesso.remove(p);
		processosProntos.remove(p);
	}

	public static void ordena( LinkedList<BCP> p) {
		Collections.sort( p );
		Iterator<BCP> it = p.iterator();
		while( it.hasNext())
			processosProntos.add(it.next());
	}
	
	public static void reordenaProntos( LinkedList<BCP> p) {
		Iterator<BCP> it = processosBloqueados.iterator();
		BCP b,c;
		if(it.hasNext()) b = it.next();
		else b = null;
		while( b != null){
			b.atualizaRodada();
			if(b.getRodada() == 0){ 
				if(it.hasNext()) c = it.next();
				else c = null;
				processosBloqueados.remove(b);
				b.setEstado(2);
				processosProntos.add(b);
				b = c;
			}
			else if (it.hasNext()){
				b = it.next();
			}
		}
		Collections.sort( p );
	}
	
	public static void redistribuiCreditos(LinkedList<BCP> p) {
		Iterator<BCP> it = p.iterator();
		while( it.hasNext()){
			it.next().setCredito();
		}
		Collections.sort( p );
	}

	public static void imprimeProcessos( LinkedList<Processo> p ) {
		Iterator<Processo> it = p.iterator();
		while( it.hasNext() )
			System.out.println( it.next() );
	}
	
	public static void imprimeTabela( LinkedList<BCP> p ) {
		Iterator<BCP> it = p.iterator();
		while( it.hasNext() )
			System.out.println( it.next() );
	}
	
	public static LinkedList<BCP> carregaBCP( LinkedList<Processo> p ) {
		LinkedList<BCP> listaBCP = new LinkedList<BCP>();
		Iterator<Processo> it = p.iterator();
		while( it.hasNext())
			listaBCP.add(new BCP(it.next()));
		return listaBCP;
	}
	
}
