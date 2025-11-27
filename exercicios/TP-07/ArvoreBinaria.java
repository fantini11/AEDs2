import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class NoArrayListM {
    public static final int MAX_GAMES = 500;
    public static final int MAX_INNER_ARRAY = 50;
    public static final int MAX_IDS = 100;
}

class GameBinario {
    int id;
    String name;
    String releaseDate;
    int estimatedOwners;
    float price;
    String[] supportedLanguages;
    int supportedLanguagesCount;
    int metacriticScore;
    float userScore;
    int achievements;
    String[] publishers;
    int publishersCount;
    String[] developers;
    int developersCount;
    String[] categories;
    int categoriesCount;
    String[] genres;
    int genresCount;
    String[] tags;
    int tagsCount;

    GameBinario() {
        this.id = 0;
        this.name = "";
        this.releaseDate = "";
        this.estimatedOwners = 0;
        this.price = 0.0f;
        this.supportedLanguages = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.supportedLanguagesCount = 0;
        this.metacriticScore = -1;
        this.userScore = -1.0f;
        this.achievements = 0;
        this.publishers = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.publishersCount = 0;
        this.developers = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.developersCount = 0;
        this.categories = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.categoriesCount = 0;
        this.genres = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.genresCount = 0;
        this.tags = new String[NoArrayListM.MAX_INNER_ARRAY];
        this.tagsCount = 0;
    }

    GameBinario(int id, String name, String releaseDate, int estimatedOwners, float price,
            String[] supportedLanguages, int supportedLanguagesCount, int metacriticScore, float userScore,
            int achievements,
            String[] publishers, int publishersCount, String[] developers, int developersCount,
            String[] categories, int categoriesCount, String[] genres, int genresCount, String[] tags, int tagsCount) {
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;

        // Copiando arrays
        this.supportedLanguages = supportedLanguages;
        this.supportedLanguagesCount = supportedLanguagesCount;
        this.publishers = publishers;
        this.publishersCount = publishersCount;
        this.developers = developers;
        this.developersCount = developersCount;
        this.categories = categories;
        this.categoriesCount = categoriesCount;
        this.genres = genres;
        this.genresCount = genresCount;
        this.tags = tags;
        this.tagsCount = tagsCount;

        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
    }
}

// Árvore
class ArvoreB {
    private No raiz;

    // Inserir
    public void inserir(GameBinario game) throws Exception {
        raiz = inserir(game, raiz);
    }

    public No inserir(GameBinario game, No i) throws Exception {
        if (i == null) {
            i = new No(game);
        } else if (game.name.equals(i.game.name)) {
            throw new Exception("Elemento já existe na árvore");
        } else if (game.name.compareTo(i.game.name) < 0) {
            i.esq = inserir(game, i.esq);
        } else if (game.name.compareTo(i.game.name) > 0) {
            i.dir = inserir(game, i.dir);
        } else {
            throw new Exception("Erro ao inserir na árvore!");
        }
        return i;
    }

    public void pesquisa(String entrada) throws Exception {
        if (raiz != null) {
            ArvoreBinaria.logWriter.print(entrada + ": =>raiz  ");
            System.out.print(entrada + ": =>raiz  ");
        }
        pesquisa(entrada, raiz);
    }

    public void pesquisa(String entrada, No i) throws Exception {
        if (i == null) {
            ArvoreBinaria.logWriter.print(entrada + "NAO");
            System.out.println("NAO");
        } else if (entrada.equals(i.game.name)) {
            ArvoreBinaria.logWriter.print(entrada + "SIM");
            System.out.println("SIM");
        } else if (entrada.compareTo(i.game.name) < 0) {
            ArvoreBinaria.logWriter.print(entrada + "esq ");
            System.out.print("esq ");
            pesquisa(entrada, i.esq);
        } else {
            ArvoreBinaria.logWriter.print(entrada + "esq ");
            System.out.print("dir ");
            pesquisa(entrada, i.dir);
        }
    }
}

// No
class No {
    GameBinario game;
    No esq, dir;

    No(GameBinario game) {
        this.game = game;
        esq = dir = null;
    }
}

// Classe principal
public class ArvoreBinaria {
    public static Scanner sc;
    public static PrintWriter logWriter;

    // Main
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        // .log
        try {
            logWriter = new PrintWriter(new FileWriter("885375_arvoreBinaria.txt"));
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo de log: " + e.getMessage());
            return; // Interrompe se não puder criar o log
        }
        // Capturando os ids que serão adicionados
        String entrada = sc.nextLine();
        String ids[] = new String[2000];
        int tam = 0;
        for (; !entrada.equals("FIM"); tam++) {
            ids[tam] = entrada;
            entrada = sc.nextLine();
        }
        // Criando a árvore
        ArvoreB arvore = JogosDigitadosArvoreBinaria.inicializacao(ids, tam);
        // Vendo a posição na árvore e mostrando
        entrada = sc.nextLine();
        while (!entrada.equals("FIM")) {
            try {
                arvore.pesquisa(entrada);
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
            entrada = sc.nextLine();
        }
        sc.close();
        logWriter.close();
    }
}

// Capturando os jogos digitados através do id pelo usuário
class JogosDigitadosArvoreBinaria {
    // Scanner
    public static Scanner sc;
    // Variável que pula caracteres das linhas
    static int contador = 0;
    // Ids de pesquisa e seu tamanho
    static String[] ids;
    static int idsTamanho;

    // Capturando os jogos
    static ArvoreB inicializacao(String[] idArray, int tamanho) {
        ArvoreB arvore = new ArvoreB();

        // Copiando IDs para a variável de classe 'ids'
        ids = idArray;
        idsTamanho = tamanho;

        // Pesquisa por id
        for (int j = 0; j < tamanho; j++) {
            int indiceEncontrado = -1;

            try {
                java.io.File arquivo = new java.io.File("/tmp/games.csv");
                if (!arquivo.exists()) {
                    System.out.println("Arquivo 'games.csv' não encontrado!");
                    return arvore;
                }

                InputStream is = new FileInputStream(arquivo); // abre do zero
                Scanner sc = new Scanner(is);

                // Pula cabeçalho
                if (sc.hasNextLine())
                    sc.nextLine();

                while (sc.hasNextLine() && indiceEncontrado == -1) {
                    String linha = sc.nextLine();
                    contador = 0;

                    int id = capturaId(linha);
                    indiceEncontrado = igualId(id);

                    if (indiceEncontrado != -1) {
                        String name = capturaName(linha);
                        String releaseDate = capturaReleaseDate(linha);
                        int estimatedOwners = capturaEstimatedOwners(linha);
                        float price = capturaPrice(linha);

                        String[] supportedLanguages = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int supportedLanguagesCount = capturaSupportedLanguages(linha, supportedLanguages);
                        int metacriticScore = capturaMetacriticScore(linha);
                        float userScore = capturaUserScore(linha);
                        int achievements = capturaAchievements(linha);

                        String[] publishers = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int publishersCount = capturaUltimosArryays(linha, publishers);
                        String[] developers = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int developersCount = capturaUltimosArryays(linha, developers);
                        String[] categories = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int categoriesCount = capturaUltimosArryays(linha, categories);
                        String[] genres = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int genresCount = capturaUltimosArryays(linha, genres);
                        String[] tags = new String[NoArrayListM.MAX_INNER_ARRAY];
                        int tagsCount = capturaUltimosArryays(linha, tags);

                        GameBinario jogo = new GameBinario(id, name, releaseDate, estimatedOwners, price,
                                supportedLanguages, supportedLanguagesCount, metacriticScore, userScore, achievements,
                                publishers, publishersCount, developers, developersCount, categories, categoriesCount,
                                genres, genresCount, tags, tagsCount);
                        removerId(indiceEncontrado);
                        arvore.inserir(jogo);
                    }
                }

                sc.close();
                is.close();

            } catch (Exception e) {
                System.out.println("Erro ao abrir ou ler o arquivo: " + e.getMessage());
            }
        }

        return arvore;
    }

    static int igualId(int id) {
        for (int i = 0; i < idsTamanho; i++) {
            if (Integer.parseInt(ids[0]) == id)
                return 0;
        }
        return -1;
    }

    // Função para remover o ID da lista de pesquisa
    static void removerId(int indice) {
        if (indice >= 0 && indice < idsTamanho) {
            for (int j = indice; j < idsTamanho - 1; j++) {
                ids[j] = ids[j + 1];
            }
            ids[idsTamanho - 1] = null; // Limpa a última posição
            idsTamanho--; // Decrementa o tamanho
        }
    }

    // Capturando Id
    static int capturaId(String jogo) {
        int id = 0;
        while (contador < jogo.length() && Character.isDigit(jogo.charAt(contador))) {
            id = id * 10 + (jogo.charAt(contador) - '0');
            contador++;
        }
        return id;
    }

    // Capturando nome
    static String capturaName(String jogo) {
        String name = "";
        while (jogo.charAt(contador) != ',' && contador < jogo.length()) {
            contador++;
        }
        contador++;
        while (jogo.charAt(contador) != ',' && contador < jogo.length()) {
            name += jogo.charAt(contador);
            contador++;
        }
        return name;
    }

    // Capturando Release Date
    static String capturaReleaseDate(String jogo) {
        while (contador < jogo.length() && jogo.charAt(contador) != '"') {
            contador++;
        }
        contador++;
        String dia = "", mes = "", ano = "";
        // Pegando mês
        for (int i = 0; contador < jogo.length() && i < 3; i++) {
            mes += jogo.charAt(contador);
            contador++;
        }
        mes = mes.trim();
        switch (mes) {
            case "Jan":
                mes = "01";
                break;
            case "Feb":
                mes = "02";
                break;
            case "Mar":
                mes = "03";
                break;
            case "Apr":
                mes = "04";
                break;
            case "May":
                mes = "05";
                break;
            case "Jun":
                mes = "06";
                break;
            case "Jul":
                mes = "07";
                break;
            case "Aug":
                mes = "08";
                break;
            case "Sep":
                mes = "09";
                break;
            case "Oct":
                mes = "10";
                break;
            case "Nov":
                mes = "11";
                break;
            case "Dec":
                mes = "12";
                break;
            default:
                break;
        }
        // Pulando espaço
        while (contador < jogo.length() && !Character.isDigit(jogo.charAt(contador)) && jogo.charAt(contador) != ',') {
            contador++;
        }
        // Pegando dia
        while (contador < jogo.length() && Character.isDigit(jogo.charAt(contador))) {
            dia += jogo.charAt(contador);
            contador++;
        }
        // Pulando espaço
        while (contador < jogo.length() && !Character.isDigit(jogo.charAt(contador))) {
            contador++;
        }
        // Pegando ano
        while (contador < jogo.length() && jogo.charAt(contador) != '"') {
            ano += jogo.charAt(contador);
            contador++;
        }
        if (dia.isEmpty())
            dia = "01";
        if (mes.isEmpty())
            mes = "01";
        if (ano.isEmpty())
            ano = "0000";
        return dia + "/" + mes + "/" + ano;
    }

    // Capturando Estimated Owners
    static int capturaEstimatedOwners(String jogo) {
        int estimatedOwners = 0;
        while (contador < jogo.length() && jogo.charAt(contador) != ',') {
            contador++;
        }
        contador++;
        while (contador < jogo.length() && jogo.charAt(contador) != ',') {
            estimatedOwners = estimatedOwners * 10 + (jogo.charAt(contador) - '0');
            contador++;
        }
        return estimatedOwners;
    }

    // Capturando Price
    static float capturaPrice(String jogo) {
        String price = "";
        while (contador < jogo.length() && !Character.isDigit(jogo.charAt(contador)) && jogo.charAt(contador) != 'F') {
            contador++;
        }
        while (contador < jogo.length() && (Character.isDigit(jogo.charAt(contador)) || jogo.charAt(contador) == '.')) {
            price += jogo.charAt(contador);
            contador++;
        }
        price = price.trim();
        if (price.isEmpty() || price.equalsIgnoreCase("Free to play")) {
            return 0.0f;
        }
        price = price.replaceAll("[^0-9.]", "");
        try {
            return Float.parseFloat(price);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    // Capturando idiomas
    static int capturaSupportedLanguages(String jogo, String[] supportedLanguages) {
        int count = 0;
        while (contador < jogo.length() && jogo.charAt(contador) != ']' && count < supportedLanguages.length) {
            String lingua = "";
            while (contador < jogo.length() && !Character.isAlphabetic(jogo.charAt(contador))) {
                contador++;
            }
            while (contador < jogo.length() && jogo.charAt(contador) != ',' && jogo.charAt(contador) != ']') {
                if (Character.isAlphabetic(jogo.charAt(contador)) || jogo.charAt(contador) == ' '
                        || jogo.charAt(contador) == '-') {
                    lingua += jogo.charAt(contador);
                }
                contador++;
            }
            supportedLanguages[count++] = lingua;
        }
        return count; // Retorna o tamanho real
    }

    // Capturando Metacritic Score
    static int capturaMetacriticScore(String jogo) {
        String metacriticScore = "";
        while (contador < jogo.length() && jogo.charAt(contador) != ',') {
            contador++;
        }
        contador++;
        while (contador < jogo.length() && Character.isDigit(jogo.charAt(contador))) {
            metacriticScore += jogo.charAt(contador);
            contador++;
        }
        if (metacriticScore.isEmpty())
            return -1;
        else
            return Integer.parseInt(metacriticScore);
    }

    // Capturando User Score
    static float capturaUserScore(String jogo) {
        String userScore = "";
        while (contador < jogo.length() && jogo.charAt(contador) != ',') {
            contador++;
        }
        contador++;
        while (contador < jogo.length() && (Character.isDigit(jogo.charAt(contador)) || jogo.charAt(contador) == '.')) {
            userScore += jogo.charAt(contador);
            contador++;
        }
        if (userScore.isEmpty())
            return -1.0f;
        else
            return Float.parseFloat(userScore);
    }

    // Capturando Achievements
    static int capturaAchievements(String jogo) {
        String achievements = "";
        while (contador < jogo.length() && jogo.charAt(contador) != ',') {
            contador++;
        }
        contador++;
        while (contador < jogo.length() && (Character.isDigit(jogo.charAt(contador)) || jogo.charAt(contador) == '.')) {
            achievements += jogo.charAt(contador);
            contador++;
        }
        if (achievements.isEmpty())
            return -1;
        else
            return Integer.parseInt(achievements);
    }

    // Capturando Últimos Arrays
    static int capturaUltimosArryays(String jogo, String[] categoria) {
        int count = 0;
        boolean teste = false;
        while (contador < jogo.length() && !Character.isAlphabetic(jogo.charAt(contador))
                && !Character.isDigit(jogo.charAt(contador))) {
            if (jogo.charAt(contador) == '"')
                teste = true;
            contador++;
        }
        if (teste) {
            while (contador < jogo.length() && jogo.charAt(contador) != '"' && count < categoria.length) {
                String parte = "";
                while (contador < jogo.length() && jogo.charAt(contador) != ',' && jogo.charAt(contador) != '"') {
                    parte += jogo.charAt(contador);
                    contador++;
                }
                while (contador < jogo.length() && !Character.isAlphabetic(jogo.charAt(contador))
                        && !Character.isDigit(jogo.charAt(contador))
                        && jogo.charAt(contador) != '"') {
                    contador++;
                }
                categoria[count++] = parte; // Adiciona ao array e incrementa o contador
            }
            contador++;
        } else {
            if (count < categoria.length) {
                String parte = "";
                while (contador < jogo.length() && jogo.charAt(contador) != ',') {
                    parte += jogo.charAt(contador);
                    contador++;
                }
                categoria[count++] = parte; // Adiciona ao array e incrementa o contador
            }
        }
        // Pula a vírgula depois do array (se houver)
        if (contador < jogo.length() && jogo.charAt(contador) == ',') {
            contador++;
        }
        return count; // Retorna o tamanho real
    }
}