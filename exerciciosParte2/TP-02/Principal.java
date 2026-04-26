import java.util.*;

class Data{
	private int ano;
	private int mes;
	private int dia;
	public Data(int ano, int mes, int dia){
		this.ano = ano;
		this.mes = mes;
		this.dia = dia;
	}
	public int getAno(){
		return this.ano;
	}
	public void setAno(int ano){
		this.ano = ano;
	}
	public int getMes(){
		return this.mes;
	}
	public void setMes(int mes){
		this.mes = mes;
	}
	public int getDia(){
		return this.dia;
	}
	public void setDia(int dia){
		this.dia = dia;
	}
	public static Data parseData(String s){
		Scanner sc = new Scanner(s);
		sc.useDelimiter("-");
		int ano = sc.nextInt();
		int mes = sc.nextInt();
		int dia = sc.nextInt();
		sc.close();
		return new Data(ano, mes, dia);
	}
	public String formatar(){
		return String.format("%02d/%02d/%04d",dia, mes, ano);
	}
}

class Hora{
	private int hora;
	private int minuto;
	public Hora(int hora, int minuto){
		this.hora = hora;
		this.minuto = minuto;
	}
	public int getHora(){
		return this.hora;
	}
	public void setHora(int hora){
		this.hora = hora;
	}
	public int getMinuto(){
		return this.minuto;
	}
	public void setMinuto(int minuto){
		this.minuto = minuto;
	}
	public static Hora parseHora(String s){
		Scanner sc = new Scanner(s);
		sc.useDelimiter(":");
		int hora = sc.nextInt();
		int minuto = sc.nextInt();
		sc.close();
		return new Hora(hora, minuto);
	}
	public String formatar(){
		return String.format("%02d:%02d",hora, minuto);
	}
}

class Restaurante{
	private int id;
	private String nome;
	private	String cidade;
	private int capacidade;
	private double avaliacao;
	private String[] cozinhas;
	private int preco;
	private Hora horaAbertura;
	private Hora horaFechamento;
	private Data dataAbertura;
	private boolean aberto;
	}

public class Principal{
	public static void main(String args[]){
		Data d = Data.parseData("2026-04-07");
		System.out.println(d.formatar());
		Hora h = Hora.parseHora("11:30");
		System.out.println(h.formatar());
	}
}
