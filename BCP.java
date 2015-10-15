public class BCP {
	
	// Possíveis Estados
	public static final int BLOQUEADO = 0;
	public static final int EXECUTANDO = 1;
	public static final int PRONTO = 2;

	// INFORMAÇÕES DOS PROCESSOS
	String nomeProcesso;	
	int estadoProcesso;
	int registradorX, registradorY;
	int contadorPrograma;
	int prioridade;
	int creditosRestantes;
	
	/* incluir referência ao segmento de texto do programa */
	// Integer.toHexString(segmento.hashCode());

	/* Carrega as informações do processo */
	public BCP ( Processo p ) {
		nomeProcesso = p.getNome();
	}

	public void setEstado( int e ) {
		if ( e == 0 || e == 1 || e == 2 )
			estado = e;
	}

	public int getEstado() {
		return estado;
	}

	public int getCreditos() {
		return creditos;
	}

	public void debitaCredito() {
		if ( creditos > 0 )
			creditos--;
	}
}
