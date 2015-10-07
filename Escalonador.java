import java.io.*;
import.java.util.*;

public class Escalonador {

	public static void main(String[] args) {
		// Leitura dos arquivos
		try {
			FileReader processo = new FileReader("processos/01.txt");
			BufferedReader leitura = new BufferedReader(processo);

			String instrucao = leitura.readLine();
			while ( instrucao != null ) {
				System.out.println( instrucao );
				instrucao = leitura.readLine();
			}
			processo.close();
		}
		catch( Exception e ) {
			System.out.println( "ERRO NA LEITURA" );
			e.getMessage();
		}

		LinkedList<Processo> processosProntos = new LinkedList<Processo>();
		LinkedList<Processo> processosBloqueados = new LinkedList<Processo>();
	}	
}