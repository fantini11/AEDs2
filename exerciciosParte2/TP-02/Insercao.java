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

    public int getMes() {
        return this.mes;
    }

    public int getDia() {
        return this.dia;
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

    public int getHora() {
        return this.hora;
    }

    public int getMinuto() {
        return this.minuto;
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

    public String getCidade() {
        return this.cidade;
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

    public int getTamanho() {
        return this.tamanho;
    }

    public Restaurante[] getRestaurantes() {
        return this.restaurantes;
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

public class Insercao {
    public static final String MATRICULA = "000000";

    public static void insercao(Restaurante[] array, int n, long[] comparacoes, long[] movimentacoes) {
        for (int i = 1; i < n; i++) {
            Restaurante tmp = array[i];
            movimentacoes[0]++;

            int j = i - 1;
            boolean continuar = true;

            while (j >= 0 && continuar) {
                comparacoes[0]++;

                if (array[j].getCidade().compareTo(tmp.getCidade()) > 0) {
                    array[j + 1] = array[j];
                    movimentacoes[0]++;
                    j--;
                } else {
                    continuar = false;
                }
            }

            array[j + 1] = tmp;
            movimentacoes[0]++;
        }
    }

    public static void escreverLog(long comparacoes, long movimentacoes, double tempo) throws Exception {
        FileWriter fw = new FileWriter(MATRICULA + "_insercao.txt");

        fw.write(MATRICULA + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempo);

        fw.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();

        Restaurante[] selecionados = new Restaurante[1000];
        int n = 0;

        int id = sc.nextInt();

        while (id != -1) {
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                selecionados[n] = r;
                n++;
            }

            id = sc.nextInt();
        }

        long[] comparacoes = new long[1];
        long[] movimentacoes = new long[1];

        long inicio = System.nanoTime();

        insercao(selecionados, n, comparacoes, movimentacoes);

        long fim = System.nanoTime();

        double tempo = (fim - inicio) / 1000000000.0;

        for (int i = 0; i < n; i++) {
            System.out.println(selecionados[i].formatar());
        }

        escreverLog(comparacoes[0], movimentacoes[0], tempo);

        sc.close();
    }
}
