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

	/* Lista de Processos */
	private static LinkedList<BCP> processosProntos = new LinkedList<BCP>();
	private static LinkedList<BCP> processosBloqueados = new LinkedList<BCP>();

	/* Tabela de Processos */
	private static LinkedList<BCP> tabelaProcesso;

	/* Dados para estatística */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;

	public static void carrega( LinkedList<Processo> listaProcessos, int q ) throws IOException {
		quantum = q;
		tabelaProcesso = carregaBCP(listaProcessos);
		ordena(tabelaProcesso);
		executa(tabelaProcesso);
	}
	
	public static void executa(LinkedList<BCP> tabelaProcesso) throws IOException { //???
		Iterator<BCP> it = tabelaProcesso.iterator();
		String nomeLog = new String("log0"+quantum+".txt");
		FileWriter arq = new FileWriter(nomeLog);
		PrintWriter gravaArq = new PrintWriter(arq);
		
		imprimeTabela(tabelaProcesso, gravaArq);
		
		while( it.hasNext() ){
			/*System.out.println("XX");
			if(processosProntos.getFirst() != null && processosProntos.getFirst().getCreditos() != 0 ){
				System.out.println("KK");*/
				executaProcesso(processosProntos.getFirst(), gravaArq); 
			/*}
			else if (processosProntos.getFirst() == null){
				System.out.println("LL");
				reordenaProntos(processosProntos);
			}
			else if(processosProntos.getFirst() != null && processosProntos.getFirst().getCreditos() == 0){
				if(processosBloqueados == null){
					System.out.println("MM");
					redistribuiCreditos(processosProntos);
				}
				else if(processosBloqueados != null && processosBloqueados.getFirst().getCreditos() > 0 ){
					System.out.println("NN");
					executaProcesso(processosProntos.getFirst()); 
				}
				else if(processosBloqueados != null && processosBloqueados.getFirst().getCreditos() == 0){
					System.out.println("OO");
					redistribuiCreditos(tabelaProcesso);
				}
			}
			System.out.println("YY");*/
			//Pensar como continuar rodando se proximo tem menos creditos
		}
		//imprimeTabela(tabelaProcesso);
		arq.close();
	}

	public static void executaProcesso(BCP processoAtual, PrintWriter arq) {
		processoAtual.debitaCredito();
		int cont = 0;
		arq.println( "Executando " + processoAtual );
		while(cont <quantum){
			cont++;
			if(processoAtual.getInstrucao(processoAtual.getContador()).contains("=")){
				if(processoAtual.getInstrucao(processoAtual.getContador()).contains("X"))
					processoAtual.setResgistrador(Character.getNumericValue(processoAtual.getInstrucao(processoAtual.getContador()).charAt(processoAtual.getInstrucao(processoAtual.getContador()).length()-1)), "X");
				else
					processoAtual.setResgistrador(Character.getNumericValue(processoAtual.getInstrucao(processoAtual.getContador()).charAt(processoAtual.getInstrucao(processoAtual.getContador()).length()-1)), "Y");
				processoAtual.setContador();
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("E/S")){
				arq.println("E/S iniciado em "+processoAtual);
				bloqueiaProcesso(processoAtual);
				processoAtual.setContador();
				break;
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("COM")){
				processoAtual.setContador();
			}
			else{
				arq.println(processoAtual+" terminado. X="+processoAtual.getResgistrador("X")+". Y="+processoAtual.getResgistrador("Y"));
				processoAtual.setContador();
				finalizaProcesso(processoAtual);
				cont=-1;
				break;
			}
		}
		if ( cont > 0 ) arq.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucoes" );
		reordenaProntos( processosProntos );
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

	/* Chamado quando a tabela de processos e criada, preenche a lista de prcessos prontos */
	public static void ordena( LinkedList<BCP> p) {
		Collections.sort( p );
		Iterator<BCP> it = p.iterator();
		while( it.hasNext())
			processosProntos.add(it.next());
	}
	
	/* Recebe a lista de processos prontos, pega a lista de bloqueados.  */
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
	
	public static void imprimeTabela( LinkedList<BCP> p, PrintWriter arq ) {
		Iterator<BCP> it = p.iterator();
		while( it.hasNext() )
			//System.out.println( it.next() );
			arq.println("Carregando "+it.next());
	}
	
	public static LinkedList<BCP> carregaBCP( LinkedList<Processo> p ) {
		LinkedList<BCP> listaBCP = new LinkedList<BCP>();
		Iterator<Processo> it = p.iterator();
		while( it.hasNext())
			listaBCP.add(new BCP(it.next()));
		return listaBCP;
	}
	
}
