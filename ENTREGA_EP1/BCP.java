import java.util.*;

public class BCP implements Comparable<BCP>{

	// INFORMAÇÕES DOS PROCESSOS
	private String nomeProcesso;
	private String referenciaMem;
	private int estadoProcesso;
	private int registradorX, registradorY;
	private int contadorPrograma;
	private int prioridade;
	private int creditosRestantes;
	private int rodadaBloq;
	private ArrayList<String> instrucoes = new ArrayList<>();
	
	/* Carrega as informações do processo */
	public BCP (){
	}
	
	public BCP ( Processo p ) {
		nomeProcesso = p.getNome();
		estadoProcesso = 2;
		registradorX = 0;
		registradorY = 0;
		contadorPrograma = 0;
		prioridade = p.getPrioridade();
		creditosRestantes = prioridade;
		referenciaMem = Integer.toHexString(this.hashCode());	// verificar
		instrucoes = p.getInstrucoes();
		rodadaBloq = 0;
		
	}

	public void setEstado( int e ) {
		if ( e == 0  || e == 2 )
			estadoProcesso = e;
	}

	public void setResgistrador( int v, String c) {
		if(c.equalsIgnoreCase("X"))
			registradorX = v;
		else
			registradorY = v;
	}
	
	public void setContador() {
		contadorPrograma += 1;
	}
	
	public void setRodada() {
		rodadaBloq = 3;
	}
	
	public void atualizaRodada() {
		rodadaBloq -= 1;
	}
	
	public String getNome() {
		return nomeProcesso;
	}
	
	public int getRodada() {
		return rodadaBloq;
	}
	
	public int getEstado() {
		return estadoProcesso;
	}
	
	public void setCredito(){
		creditosRestantes = prioridade;
	}

	public int getCreditos() {
		return creditosRestantes;
	}

	public void debitaCredito() {
		if ( creditosRestantes > 0 )
			creditosRestantes--;
	}
	
	public String toString() {
		return nomeProcesso;
	}
	
	public String getInstrucao( int i ) {
		return instrucoes.get(i);
	}
	
	public int getContador() {
		return contadorPrograma;
	}
	
	public int getResgistrador(String c) {
		if(c.equalsIgnoreCase("X"))
			return registradorX;
		else
			return registradorY;
	}
	
	public int compareTo( BCP p ) {
		return Integer.compare( p.getCreditos(), this.creditosRestantes );
	}

}
