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

class Lista {
    private Restaurante[] array;
    private int n;

    public Lista() {
        this.array = new Restaurante[1000];
        this.n = 0;
    }

    public void inserirInicio(Restaurante restaurante) {
        for (int i = n; i > 0; i--) {
            array[i] = array[i - 1];
        }

        array[0] = restaurante;
        n++;
    }

    public void inserir(Restaurante restaurante, int posicao) {
        for (int i = n; i > posicao; i--) {
            array[i] = array[i - 1];
        }

        array[posicao] = restaurante;
        n++;
    }

    public void inserirFim(Restaurante restaurante) {
        array[n] = restaurante;
        n++;
    }

    public Restaurante removerInicio() {
        Restaurante resp = array[0];

        for (int i = 0; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        n--;

        return resp;
    }

    public Restaurante remover(int posicao) {
        Restaurante resp = array[posicao];

        for (int i = posicao; i < n - 1; i++) {
            array[i] = array[i + 1];
        }

        n--;

        return resp;
    }

    public Restaurante removerFim() {
        Restaurante resp = array[n - 1];
        n--;
        return resp;
    }

    public void mostrar() {
        for (int i = 0; i < n; i++) {
            System.out.println(array[i].formatar());
        }
    }
}

public class ListaSequencial {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        ColecaoRestaurantes colecao = ColecaoRestaurantes.lerCsv();
        Lista lista = new Lista();

        int id = sc.nextInt();

        while (id != -1) {
            Restaurante r = colecao.buscarPorId(id);

            if (r != null) {
                lista.inserirFim(r);
            }

            id = sc.nextInt();
        }

        int quantidadeComandos = sc.nextInt();

        for (int i = 0; i < quantidadeComandos; i++) {
            String comando = sc.next();

            if (comando.compareTo("II") == 0) {
                int idRestaurante = sc.nextInt();
                Restaurante r = colecao.buscarPorId(idRestaurante);
                lista.inserirInicio(r);
            } else if (comando.compareTo("I*") == 0) {
                int posicao = sc.nextInt();
                int idRestaurante = sc.nextInt();
                Restaurante r = colecao.buscarPorId(idRestaurante);
                lista.inserir(r, posicao);
            } else if (comando.compareTo("IF") == 0) {
                int idRestaurante = sc.nextInt();
                Restaurante r = colecao.buscarPorId(idRestaurante);
                lista.inserirFim(r);
            } else if (comando.compareTo("RI") == 0) {
                Restaurante r = lista.removerInicio();
                System.out.println("(R) " + r.getNome());
            } else if (comando.compareTo("R*") == 0) {
                int posicao = sc.nextInt();
                Restaurante r = lista.remover(posicao);
                System.out.println("(R) " + r.getNome());
            } else if (comando.compareTo("RF") == 0) {
                Restaurante r = lista.removerFim();
                System.out.println("(R) " + r.getNome());
            }
        }

        lista.mostrar();

        sc.close();
    }
}
