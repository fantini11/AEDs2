import java.util.*;
import java.io.*;

class Data {
    private int ano;
    private int mes;
    private int dia;

    public Data(int ano, int mes, int dia) {
        this.ano = ano;
        this.mes = mes;
        this.dia = dia;
    }

    public int getAno() {
        return this.ano;
    }

    public static Data parseData(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter("-");
        int ano = sc.nextInt();
        int mes = sc.nextInt();
        int dia = sc.nextInt();
        sc.close();
        return new Data(ano, mes, dia);
    }

    public String formatar() {
        return String.format("%02d/%02d/%04d", this.dia, this.mes, this.ano);
    }
}

class Hora {
    private int hora;
    private int minuto;

    public Hora(int hora, int minuto) {
        this.hora = hora;
        this.minuto = minuto;
    }

    public static Hora parseHora(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter(":");
        int hora = sc.nextInt();
        int minuto = sc.nextInt();
        sc.close();
        return new Hora(hora, minuto);
    }

    public String formatar() {
        return String.format("%02d:%02d", this.hora, this.minuto);
    }
}

class Restaurante {
    private int id;
    private String nome;
    private String cidade;
    private int capacidade;
    private double avaliacao;
    private String[] cozinhas;
    private int quantidadeCozinhas;
    private int preco;
    private Hora horaAbertura;
    private Hora horaFechamento;
    private Data dataAbertura;
    private boolean aberto;

    public Restaurante(int id, String nome, String cidade, int capacidade, double avaliacao,
                       String[] cozinhas, int quantidadeCozinhas, int preco,
                       Hora horaAbertura, Hora horaFechamento, Data dataAbertura, boolean aberto) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.capacidade = capacidade;
        this.avaliacao = avaliacao;
        this.cozinhas = cozinhas;
        this.quantidadeCozinhas = quantidadeCozinhas;
        this.preco = preco;
        this.horaAbertura = horaAbertura;
        this.horaFechamento = horaFechamento;
        this.dataAbertura = dataAbertura;
        this.aberto = aberto;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getAnoAbertura() {
        return this.dataAbertura.getAno();
    }

    public static Restaurante parseRestaurante(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter(",");

        int id = sc.nextInt();
        String nome = sc.next();
        String cidade = sc.next();
        int capacidade = sc.nextInt();
        double avaliacao = sc.nextDouble();

        String tiposCozinha = sc.next();
        String[] cozinhas = new String[20];
        int quantidadeCozinhas = separarCozinhas(tiposCozinha, cozinhas);

        String faixaPreco = sc.next();
        int preco = converterPreco(faixaPreco);

        String horario = sc.next();
        Hora horaAbertura = parseHoraAbertura(horario);
        Hora horaFechamento = parseHoraFechamento(horario);

        String data = sc.next();
        Data dataAbertura = Data.parseData(data);

        String valorAberto = sc.next();
        boolean aberto = false;

        if (valorAberto.compareTo("true") == 0) {
            aberto = true;
        }

        sc.close();

        return new Restaurante(id, nome, cidade, capacidade, avaliacao, cozinhas,
                               quantidadeCozinhas, preco, horaAbertura, horaFechamento,
                               dataAbertura, aberto);
    }

    public static int separarCozinhas(String s, String[] cozinhas) {
        int qtd = 0;
        String atual = "";

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ';') {
                cozinhas[qtd] = atual;
                qtd++;
                atual = "";
            } else {
                atual = atual + s.charAt(i);
            }
        }

        cozinhas[qtd] = atual;
        qtd++;

        return qtd;
    }

    public static int converterPreco(String s) {
        int preco = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '$') {
                preco++;
            }
        }

        return preco;
    }

    public static String formatarPreco(int preco) {
        String resp = "";

        for (int i = 0; i < preco; i++) {
            resp = resp + "$";
        }

        return resp;
    }

    public static Hora parseHoraAbertura(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter("-");
        String abertura = sc.next();
        sc.close();
        return Hora.parseHora(abertura);
    }

    public static Hora parseHoraFechamento(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter("-");
        sc.next();
        String fechamento = sc.next();
        sc.close();
        return Hora.parseHora(fechamento);
    }

    public String formatarCozinhas() {
        String resp = "[";

        for (int i = 0; i < this.quantidadeCozinhas; i++) {
            resp = resp + this.cozinhas[i];

            if (i < this.quantidadeCozinhas - 1) {
                resp = resp + ",";
            }
        }

        resp = resp + "]";

        return resp;
    }

    public String formatar() {
        return "[" + this.id + " ## " +
               this.nome + " ## " +
               this.cidade + " ## " +
               this.capacidade + " ## " +
               this.avaliacao + " ## " +
               this.formatarCozinhas() + " ## " +
               Restaurante.formatarPreco(this.preco) + " ## " +
               this.horaAbertura.formatar() + "-" + this.horaFechamento.formatar() + " ## " +
               this.dataAbertura.formatar() + " ## " +
               this.aberto + "]";
    }
}

class ColecaoRestaurantes {
    private int tamanho;
    private Restaurante[] restaurantes;

    public ColecaoRestaurantes() {
        this.tamanho = 0;
        this.restaurantes = new Restaurante[2000];
    }

    public void lerCsv(String path) throws Exception {
        File arquivo = new File(path);
        Scanner sc = new Scanner(arquivo);

        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();

            if (linha.length() > 0) {
                this.restaurantes[this.tamanho] = Restaurante.parseRestaurante(linha);
                this.tamanho++;
            }
        }

        sc.close();
    }

    public static ColecaoRestaurantes lerCsv() throws Exception {
        ColecaoRestaurantes colecao = new ColecaoRestaurantes();

        File arquivo1 = new File("/tmp/restaurantes.csv");
        File arquivo2 = new File("/tmp/RESTAURANTES.CSV");

        if (arquivo1.exists()) {
            colecao.lerCsv("/tmp/restaurantes.csv");
        } else {
            colecao.lerCsv("/tmp/RESTAURANTES.CSV");
        }

        return colecao;
    }

    public Restaurante buscarPorId(int id) {
        Restaurante resp = null;

        for (int i = 0; i < this.tamanho; i++) {
            if (this.restaurantes[i].getId() == id) {
                resp = this.restaurantes[i];
            }
        }

        return resp;
    }
}

class FilaCircular {
    private Restaurante[] array;
    private int primeiro;
    private int ultimo;

    public FilaCircular() {
        this.array = new Restaurante[6];
        this.primeiro = 0;
        this.ultimo = 0;
    }

    public int tamanho() {
        int resp = this.ultimo - this.primeiro;

        if (resp < 0) {
            resp = resp + this.array.length;
        }

        return resp;
    }

    public boolean cheia() {
        return ((this.ultimo + 1) % this.array.length) == this.primeiro;
    }

    public void inserir(Restaurante restaurante) {
        if (cheia()) {
            Restaurante removido = remover();
            System.out.println("(R)" + removido.getNome());
        }

        this.array[this.ultimo] = restaurante;
        this.ultimo = (this.ultimo + 1) % this.array.length;

        System.out.println("(I)" + mediaAnoArredondada());
    }

    public Restaurante remover() {
        Restaurante resp = this.array[this.primeiro];
        this.primeiro = (this.primeiro + 1) % this.array.length;
        return resp;
    }

    public int mediaAnoArredondada() {
        int soma = 0;
        int tam = tamanho();

        for (int i = 0; i < tam; i++) {
            int pos = (this.primeiro + i) % this.array.length;
            soma = soma + this.array[pos].getAnoAbertura();
        }

        return (int) Math.round((double) soma / tam);
    }

    public void mostrar() {
        int tam = tamanho();

        for (int i = 0; i < tam; i++) {
            int pos = (this.primeiro + i) % this.array.length;
            System.out.println("[" + i + "] " + this.array[pos].formatar());
        }
    }
}

public class FilaCircu {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        FilaCircular fila = new FilaCircular();

        int id = sc.nextInt();

        while (id != -1) {
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                fila.inserir(r);
            }

            id = sc.nextInt();
        }

        int quantidadeComandos = sc.nextInt();

        for (int i = 0; i < quantidadeComandos; i++) {
            String comando = sc.next();

            if (comando.compareTo("I") == 0) {
                int idRestaurante = sc.nextInt();
                Restaurante r = colecao.buscarPorId(idRestaurante);

                if (r != null) {
                    fila.inserir(r);
                }
            } else if (comando.compareTo("R") == 0) {
                Restaurante r = fila.remover();
                System.out.println("(R)" + r.getNome());
            }
        }

        fila.mostrar();

        sc.close();
    }
}
