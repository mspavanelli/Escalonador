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
		int quantum;
		LinkedList<Processo> listaProcessos = new LinkedList<Processo>();
		
		// Leitura dos arquivos
		try {
			quantum = Integer.parseInt( new BufferedReader( new FileReader( "processos/quantum.txt" ) ).readLine() );
			String nomeArquivo;
			for ( int i = 1; i <= 10; i++ ) {
				if ( i == 10 ) nomeArquivo = "10.txt";
				else nomeArquivo = new String( "0" + i + ".txt" );
				FileReader processo = new FileReader("processos/" + nomeArquivo );
				BufferedReader leitura = new BufferedReader(processo);
				String nomeProcesso = leitura.readLine();
				LinkedList<String> instrucoes = new LinkedList<>();
				String instrucao;
				while ( ( instrucao = leitura.readLine() ) != null ) {
					instrucoes.add( instrucao );
				}
				System.out.println( nomeArquivo );
				listaProcessos.add( new Processo( nomeProcesso, instrucoes) );
				processo.close();
			}

			Prioridades.executa( listaProcessos, quantum );
		}
		catch( Exception e ) {
			System.out.println( "ERRO NA LEITURA" );
		}
	}	
}
