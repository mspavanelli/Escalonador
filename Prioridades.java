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
		
		/*Log completo de cada passo*/
		String nomeLogC = new String("Clog0"+quantum+".txt");
		FileWriter arqC = new FileWriter(nomeLogC);
		PrintWriter gravaArqC = new PrintWriter(arqC);
		//--------------
		
		imprimeTabela(tabelaProcesso, gravaArq, gravaArqC);
		
		while( it.hasNext() ){
			/*System.out.println("XX");
			if(processosProntos.getFirst() != null && processosProntos.getFirst().getCreditos() != 0 ){
				System.out.println("KK");*/
				executaProcesso(processosProntos.getFirst(), gravaArq, gravaArqC); 
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
		arqC.close();
	}

	public static void executaProcesso(BCP processoAtual, PrintWriter arq, PrintWriter arqC) {
		arqC.print(processoAtual.info());
		processoAtual.debitaCredito();
		int cont = 0;
		arq.println( "Executando " + processoAtual );
		arqC.println( "Executando " + processoAtual );
		while(cont <quantum){
			cont++;
			if(processoAtual.getInstrucao(processoAtual.getContador()).contains("=")){
				if(processoAtual.getInstrucao(processoAtual.getContador()).contains("X"))
					processoAtual.setResgistrador(Integer.parseInt(processoAtual.getInstrucao(processoAtual.getContador()).substring(2)), "X");
				else
					processoAtual.setResgistrador(Integer.parseInt(processoAtual.getInstrucao(processoAtual.getContador()).substring(2)), "Y");
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				processoAtual.setContador();
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("E/S")){
				arq.println("E/S iniciado em "+processoAtual);
				bloqueiaProcesso(processoAtual);
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				processoAtual.setContador();
				break;
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("COM")){
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				processoAtual.setContador();
			}
			else{
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				arq.println(processoAtual+" terminado. X="+processoAtual.getResgistrador("X")+". Y="+processoAtual.getResgistrador("Y"));
				arqC.println(processoAtual+" terminado. X="+processoAtual.getResgistrador("X")+". Y="+processoAtual.getResgistrador("Y"));
				processoAtual.setContador();
				finalizaProcesso(processoAtual);
				cont=-1;
				break;
			}
		}
		if ( cont > 0 ) {
			arq.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucoes" );
			arqC.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucoes" );
		}
		arqC.println(processoAtual.info());
		reordenaProntos( processosProntos, arqC );
	}

	/* funções auxiliares da execução */

	public static void  bloqueiaProcesso(BCP p) {
		p.setEstado(0);
		p.setRodada();
		processosBloqueados.add(p);
		processosProntos.remove(p);
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
	public static void reordenaProntos( LinkedList<BCP> p, PrintWriter arqC) {
		Iterator<BCP> it = processosBloqueados.iterator();
		BCP b,c;
		if(it.hasNext()) b = it.next();
		else b = null;
		while( b != null){
			//arqC.println(b.info());
			//arqC.println("\nentrou\n");
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
		arqC.println("\nProntos:");
		imprimeProcessos(p, arqC);
		arqC.println("\nBloqueados:");
		imprimeProcessos(processosBloqueados, arqC);
		arqC.print("\n");
	}
	
	public static void redistribuiCreditos(LinkedList<BCP> p) {
		Iterator<BCP> it = p.iterator();
		while( it.hasNext()){
			it.next().setCredito();
		}
		Collections.sort( p );
	}

	public static void imprimeProcessos( LinkedList<BCP> p, PrintWriter arqC ) {
		Iterator<BCP> it = p.iterator();
		BCP b;
		int i =0;
		while( it.hasNext() ){
			i++;
			b = it.next();
			arqC.print(i+". "+b.info());
		}
	}
	
	public static void imprimeTabela( LinkedList<BCP> p, PrintWriter arq, PrintWriter arqC ) {
		Iterator<BCP> it = p.iterator();
		BCP b;
		while( it.hasNext() ){
			b = it.next();
			arq.println("Carregando "+ b);
			arqC.println("Carregando "+ b);
		}
	}
	
	public static LinkedList<BCP> carregaBCP( LinkedList<Processo> p ) {
		LinkedList<BCP> listaBCP = new LinkedList<BCP>();
		Iterator<Processo> it = p.iterator();
		while( it.hasNext())
			listaBCP.add(new BCP(it.next()));
		return listaBCP;
	}
	
}
