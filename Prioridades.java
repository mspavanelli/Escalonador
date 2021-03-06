import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * Classe Prioridade
 * 
 * Esta classe Ã© responsÃ¡vel pela implementaÃ§Ã£o do algoritmo de escalonamento propriamente dito.
 * Ela guarda todos os processos e os gerencia conforme as suas instruÃ§Ãµes.
 */

public class Prioridades {
	
	// ALGORITMO DE ESCALONAMENTO POR PRIORIDADES

	private static int quantum;	

	/* Lista de Processos */
	private static LinkedList<BCP> processosProntos = new LinkedList<BCP>();
	private static LinkedList<BCP> processosBloqueados = new LinkedList<BCP>();

	/* Tabela de Processos */
	private static LinkedList<BCP> tabelaProcesso;

<<<<<<< HEAD
	/* Dados para estatÃ­stica */
	double mediaDeTrocas = 0;
	double mediaDeInstrucoes = 0;
=======
	/* Dados para estatística */
	static int nTrocas = 0;
	static int nInstrucoes = 0;
	static int nProcessos = 0;
>>>>>>> 975d14c5c254057c34f40613e5796c04a0c87999

	public static void carrega( LinkedList<Processo> listaProcessos, int q ) throws IOException {
		quantum = q;
		nProcessos = listaProcessos.size();
		tabelaProcesso = carregaBCP(listaProcessos);
		ordena(tabelaProcesso);
		executa();
	}
	
	public static void executa() throws IOException { //???
		String nomeLog;
		if(quantum < 10) nomeLog = new String("log0"+quantum+".txt");
		else nomeLog = new String("log"+quantum+".txt");
		
		FileWriter arq = new FileWriter(nomeLog);
		PrintWriter gravaArq = new PrintWriter(arq);
		
		/*Log completo de cada passo*/
		String nomeLogC = new String("Clog"+quantum+".txt");
		FileWriter arqC = new FileWriter(nomeLogC);
		PrintWriter gravaArqC = new PrintWriter(arqC);
		//--------------
		
		imprimeTabela(tabelaProcesso, gravaArq, gravaArqC);
		
		Iterator<BCP> it = tabelaProcesso.iterator();
		while( it.hasNext() ){
			if(!processosProntos.isEmpty()){
				if(processosProntos.getFirst().getCreditos() > 0){
					nTrocas++;
					executaProcesso(processosProntos.getFirst(), gravaArq, gravaArqC); 
				}
				else if(processosProntos.getFirst().getCreditos() == 0){
					if(processosBloqueados.isEmpty() || processosBloqueados.getFirst().getCreditos() == 0){
						redistribuiCreditos();
					}
					else if(processosBloqueados.getFirst().getCreditos() > 0){
						nTrocas++;
						executaProcesso(processosProntos.getFirst(), gravaArq, gravaArqC);
					}
				}
			}
			else reordenaProntos(gravaArqC);
		}
		gravaArqC.println(estatisticas());
		gravaArq.println(estatisticas());
		arq.close();
		arqC.close();
	}

	public static void executaProcesso(BCP processoAtual, PrintWriter arq, PrintWriter arqC) {
		arqC.print(processoAtual.info());
		processoAtual.debitaCredito();
		int cont = 0;
		arq.println( "Executando " + processoAtual );
<<<<<<< HEAD
		while( cont < quantum ) {
=======
		arqC.println( "Executando " + processoAtual );
		while(cont <quantum){
>>>>>>> 975d14c5c254057c34f40613e5796c04a0c87999
			cont++;
			if(processoAtual.getInstrucao(processoAtual.getContador()).contains("=")){
				if(processoAtual.getInstrucao(processoAtual.getContador()).contains("X"))
					processoAtual.setResgistrador(Integer.parseInt(processoAtual.getInstrucao(processoAtual.getContador()).substring(2)), "X");
				else
					processoAtual.setResgistrador(Integer.parseInt(processoAtual.getInstrucao(processoAtual.getContador()).substring(2)), "Y");
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				nInstrucoes++;
				processoAtual.setContador();
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("E/S")){
				arq.println("E/S iniciado em "+processoAtual);
				arqC.println("E/S iniciado em "+processoAtual);
				nInstrucoes++;
				bloqueiaProcesso(processoAtual);
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				processoAtual.setContador();
				break;
			}
			else if(processoAtual.getInstrucao(processoAtual.getContador()).equalsIgnoreCase("COM")){
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				nInstrucoes++;
				processoAtual.setContador();
			}
			else{
				arqC.println( "I_ " + processoAtual.getInstrucao(processoAtual.getContador()) );
				arq.println(processoAtual+" terminado. X="+processoAtual.getResgistrador("X")+". Y="+processoAtual.getResgistrador("Y"));
				arqC.println(processoAtual+" terminado. X="+processoAtual.getResgistrador("X")+". Y="+processoAtual.getResgistrador("Y"));
				nInstrucoes++;
				processoAtual.setContador();
				finalizaProcesso(processoAtual);
				cont=-1;
				break;
			}
		}
		if ( cont > 0 ) {
			if(cont == 1)arq.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucao" );
			else arq.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucoes" );
			arqC.println( "Interrompendo "+processoAtual+" apos "+cont+" instrucoes" );
		}
		arqC.println(processoAtual.info());
		reordenaProntos(arqC);
	}

	/* funÃ§Ãµes auxiliares da execuÃ§Ã£o */

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
		BCP b;
		while( it.hasNext()){
			b = new BCP();
			b = (BCP) it.next();
			processosProntos.add(b);
		}
	}
	
	/* Recebe a lista de processos prontos, pega a lista de bloqueados.  */
	public static void reordenaProntos(PrintWriter arqC) {
		Iterator<BCP> it = processosBloqueados.iterator();
		while( it.hasNext()){
			BCP b = new BCP();
			b = (BCP) it.next();
			b.atualizaRodada();
			if(b.getRodada() == 0){ 
				b.setEstado(2);
				processosProntos.add(b);
				processosBloqueados.remove(b);
				it = processosBloqueados.iterator();
			}
		}
		Collections.sort( processosProntos );
		arqC.println("\nProntos:");
		imprimeProcessos(processosProntos, arqC);
		arqC.println("\nBloqueados:");
		imprimeProcessos(processosBloqueados, arqC);
		arqC.print("\n");
	}
	
	public static void redistribuiCreditos() {
		Iterator<BCP> it = tabelaProcesso.iterator();
		while( it.hasNext()){
			BCP b = new BCP();
			b = (BCP) it.next();
			b.setCredito();
		}
		Collections.sort( processosProntos );
	}

	public static void imprimeProcessos( LinkedList<BCP> p, PrintWriter arqC ) {
		Iterator<BCP> it = p.iterator();
		BCP b;
		int i =0;
		while( it.hasNext() ){
			i++;
			b = it.next();
			//System.out.println(b.info());
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
			listaBCP.add(new BCP((Processo)it.next()));
		return listaBCP;
	}
	
	public static String estatisticas(){
		NumberFormat dec = new DecimalFormat("0.00");
		double mediaTrocas = (double) nTrocas / nProcessos;
		double mediaInstrucoes = (double) nInstrucoes / nTrocas;
		return "MEDIA DE TROCAS: "+ dec.format(mediaTrocas)+"\nMEDIA INTRUCOES: "+dec.format(mediaInstrucoes)+"\nQUANTUM: "+quantum+"\n";
	}
	
}
