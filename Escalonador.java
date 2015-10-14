import java.io.*;
import java.util.*;

/**
 * Classe Principal
 * 
 * - Leitura dos arquivos
 * 		- lista dos processos -> envio de dados ao escalonador
 * - Geração do LogFile
 */

public class Escalonador {

	public static void main(String[] args) {

		/* Dados */
		LinkedList<Processo> listaProcessos = new LinkedList<Processo>();
		int [] prioridades = new int[10];
		int quantum;

		/* Ferramenta de leitura */
		BufferedReader leitor; 
		
		/* Leitura dos arquivos */
		try {
			/* Quantum */
			leitor = new BufferedReader( new FileReader( "processos/quantum.txt" ) );
			quantum = Integer.parseInt( leitor.readLine() );

			/* Prioridades dos processos */
			leitor = new BufferedReader( new FileReader( "processos/prioridades.txt" ) );
			for ( int i = 0; i < 10; i++ )
				prioridades[i] = Integer.parseInt( leitor.readLine() );

			/* Processos */
			String nomeArquivo;
			for ( int i = 1; i <= 10; i++ ) {
				if ( i == 10 ) nomeArquivo = "10.txt";
				else nomeArquivo = new String( "0" + i + ".txt" );
				
				// Leitura das informações de um processo
				leitor = new BufferedReader( new FileReader("processos/" + nomeArquivo ) );
				
				// Gravação dos dados
				String nomeProcesso = leitor.readLine();
				LinkedList<String> instrucoes = new LinkedList<>();
				String instrucao;
				while ( ( instrucao = leitor.readLine() ) != null )
					instrucoes.add( instrucao );
				
				// Adição do processo na lista e fechamento do arquivo
				listaProcessos.add( new Processo( nomeProcesso, prioridades[i-1], instrucoes) );
			}

			Prioridades.carrega( listaProcessos, quantum );
		}
		catch( Exception e ) {
			System.out.println( "ERRO NA LEITURA" );
		}
	}	
}
