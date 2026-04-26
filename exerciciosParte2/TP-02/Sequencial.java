import java.util.*;
import java.io.*;

class Restaurante {
    private int id;
    private String nome;
    private String cidade;

    public Restaurante(int id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    public static Restaurante parse(String linha) {
        Scanner sc = new Scanner(linha);
        sc.useDelimiter(",");

        int id = sc.nextInt();
        String nome = sc.next();
        String cidade = sc.next();

        sc.close();
        return new Restaurante(id, nome, cidade);
    }
}

class Colecao {
    private Restaurante[] array;
    private int n;

    public Colecao() {
        array = new Restaurante[2000];
        n = 0;
    }

    public void lerCsv() throws Exception {
        File f1 = new File("/tmp/restaurantes.csv");
        File f2 = new File("/tmp/RESTAURANTES.CSV");

        Scanner sc = new Scanner(f1.exists() ? f1 : f2);

        if (sc.hasNextLine()) sc.nextLine();

        while (sc.hasNextLine()) {
            array[n] = Restaurante.parse(sc.nextLine());
            n++;
        }

        sc.close();
    }

    public Restaurante buscarPorId(int id) {
        Restaurante resp = null;

        for (int i = 0; i < n; i++) {
            if (array[i].getId() == id) {
                resp = array[i];
            }
        }

        return resp;
    }
}

public class Sequencial {
    public static final String MATRICULA = "885173";

    public static boolean buscaSequencial(Restaurante[] arr, int n, String chave, long[] comp) {
        boolean resp = false;
        int i = 0;

        while (i < n && resp == false) {
            comp[0]++;
            if (arr[i].getNome().compareTo(chave) == 0) {
                resp = true;
            }
            i++;
        }

        return resp;
    }

    public static void escreverLog(long comp, double tempo) throws Exception {
        FileWriter fw = new FileWriter(MATRICULA + "_sequencial.txt");
        fw.write(MATRICULA + "\t" + comp + "\t" + tempo);
        fw.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Colecao c = new Colecao();
        c.lerCsv();

        Restaurante[] base = new Restaurante[1000];
        int n = 0;

        int id = sc.nextInt();

        while (id != -1) {
            Restaurante r = c.buscarPorId(id);
            if (r != null) {
                base[n] = r;
                n++;
            }
            id = sc.nextInt();
        }

        sc.nextLine();

        long[] comp = new long[1];

        long inicio = System.nanoTime();

        String linha = sc.nextLine();

        while (!linha.equals("FIM")) {

            boolean achou = buscaSequencial(base, n, linha, comp);

            if (achou) System.out.println("SIM");
            else System.out.println("NAO");

            linha = sc.nextLine();
        }

        long fim = System.nanoTime();

        double tempo = (fim - inicio) / 1000000000.0;

        escreverLog(comp[0], tempo);

        sc.close();
    }
}
