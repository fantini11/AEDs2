import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

class NoArrayList {
    public static final int MAX_GAMES = 500;
    public static final int MAX_INNER_ARRAY = 50;
    public static final int MAX_IDS = 100;
}

// Classe 'Game' renomeada para 'games' (minúsculo, como em games.java)
// Atributos renomeados para corresponder a games.java
class games {
    int appid; // era id
    String titulo; // era name
    String dataLancamento; // era releaseDate
    int proprietariosEstimados; // era estimatedOwners
    float valorJogo; // era price
    String[] idiomasSuportados; // era supportedLanguages
    int idiomasSuportadosCount; // era supportedLanguagesCount
    int pontuacaoMetacritic; // era metacriticScore
    float avaliacaoUsuarios; // era userScore
    int totalConquistas; // era achievements
    String[] editoras; // era publishers
    int editorasCount; // era publishersCount
    String[] desenvolvedores; // era developers
    int desenvolvedoresCount; // era developersCount
    String[] categoriasJogo; // era categories
    int categoriasJogoCount; // era categoriesCount
    String[] generosJogo; // era genres
    int generosJogoCount; // era genresCount
    String[] tagsJogo; // era tags
    int tagsJogoCount; // era tagsCount

    // Construtor padrão atualizado
    games() {
        this.appid = 0;
        this.titulo = "";
        this.dataLancamento = "";
        this.proprietariosEstimados = 0;
        this.valorJogo = 0.0f;
        this.idiomasSuportados = new String[NoArrayList.MAX_INNER_ARRAY];
        this.idiomasSuportadosCount = 0;
        this.pontuacaoMetacritic = -1;
        this.avaliacaoUsuarios = -1.0f;
        this.totalConquistas = 0;
        this.editoras = new String[NoArrayList.MAX_INNER_ARRAY];
        this.editorasCount = 0;
        this.desenvolvedores = new String[NoArrayList.MAX_INNER_ARRAY];
        this.desenvolvedoresCount = 0;
        this.categoriasJogo = new String[NoArrayList.MAX_INNER_ARRAY];
        this.categoriasJogoCount = 0;
        this.generosJogo = new String[NoArrayList.MAX_INNER_ARRAY];
        this.generosJogoCount = 0;
        this.tagsJogo = new String[NoArrayList.MAX_INNER_ARRAY];
        this.tagsJogoCount = 0;
    }

    // Construtor completo atualizado (parâmetros e 'this' assignments)
    games(int appid, String titulo, String dataLancamento, int proprietariosEstimados, float valorJogo,
            String[] idiomasSuportados, int idiomasSuportadosCount, int pontuacaoMetacritic, float avaliacaoUsuarios,
            int totalConquistas,
            String[] editoras, int editorasCount, String[] desenvolvedores, int desenvolvedoresCount,
            String[] categoriasJogo, int categoriasJogoCount, String[] generosJogo, int generosJogoCount,
            String[] tagsJogo, int tagsJogoCount) {
        this.appid = appid;
        this.titulo = titulo;
        this.dataLancamento = dataLancamento;
        this.proprietariosEstimados = proprietariosEstimados;
        this.valorJogo = valorJogo;

        // Copiando arrays
        this.idiomasSuportados = idiomasSuportados;
        this.idiomasSuportadosCount = idiomasSuportadosCount;
        this.editoras = editoras;
        this.editorasCount = editorasCount;
        this.desenvolvedores = desenvolvedores;
        this.desenvolvedoresCount = desenvolvedoresCount;
        this.categoriasJogo = categoriasJogo;
        this.categoriasJogoCount = categoriasJogoCount;
        this.generosJogo = generosJogo;
        this.generosJogoCount = generosJogoCount;
        this.tagsJogo = tagsJogo;
        this.tagsJogoCount = tagsJogoCount;

        this.pontuacaoMetacritic = pontuacaoMetacritic;
        this.avaliacaoUsuarios = avaliacaoUsuarios;
        this.totalConquistas = totalConquistas;
    }
}

class HLogMetrics {
    public static long comparacoes = 0;
    public static long movimentacoes = 0;
    public static long tempoExecucaoMs = 0;
    public static final String MATRICULA = "885732";

    public static void gerarLog() {
        try {
            FileWriter arq = new FileWriter(MATRICULA + "_heapsort.txt");
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(MATRICULA + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempoExecucaoMs);
            gravarArq.close();
        } catch (IOException e) {
            System.out.println("Erro ao criar log: " + e.getMessage());
        }
    }
}

public class HeapSort {
    public static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        String entrada = sc.nextLine();

        String[] ids = new String[NoArrayList.MAX_IDS];
        int idsTamanho = 0;

        while (!entrada.equals("FIM") && idsTamanho < ids.length) {
            if (idsTamanho < ids.length) {
                ids[idsTamanho++] = entrada;
            }
            entrada = sc.nextLine();
        }

        // Atualizado para usar 'games[]'
        games[] gamesList = JogosDigitados.inicializacao(ids, idsTamanho);
        int gamesListTamanho = JogosDigitados.obterTamanhoGamesList();

        long tempoInicio = System.currentTimeMillis();
        gamesList = ordenacaoHeapSort(gamesList, gamesListTamanho);
        long tempoFim = System.currentTimeMillis();
        HLogMetrics.tempoExecucaoMs = tempoFim - tempoInicio;

        printando(gamesList, gamesListTamanho);
        HLogMetrics.gerarLog();

        sc.close();
    }

    // Atualizado para usar 'games[]'
    static games[] ordenacaoHeapSort(games[] gameList, int tam) {
        games[] tmp = new games[tam + 1];
        for (int i = 0; i < tam; i++) {
            tmp[i + 1] = gameList[i];
        }
        gameList = tmp;

        for (int i = tam / 2; i >= 1; i--) {
            reconstruir(i, tam, gameList);
        }

        int tamHeap = tam;
        while (tamHeap > 1) {
            swap(1, tamHeap, gameList);
            tamHeap--;
            reconstruir(1, tamHeap, gameList);
        }

        games[] tmp2 = new games[tam];
        for (int i = 0; i < tam; i++) {
            tmp2[i] = gameList[i + 1];
        }
        gameList = tmp2;

        return gameList;
    }

    // Atualizado para usar 'games[]'
    static void reconstruir(int i, int tamHeap, games[] gameList) {
        int filho;
        while (i <= (tamHeap / 2)) {
            filho = getMaiorFilho(i, tamHeap, gameList);
            HLogMetrics.comparacoes++;
            if (maiores(gameList, filho, i)) {
                swap(i, filho, gameList);
                i = filho;
            } else {
                break;
            }
        }
    }

    // Atualizado para usar 'games[]'
    static int getMaiorFilho(int i, int tamHeap, games[] gameList) {
        int filho;
        int filhoEsquerdo = 2 * i;
        int filhoDireito = 2 * i + 1;
        if (filhoDireito > tamHeap) {
            filho = filhoEsquerdo;
        } else {
            HLogMetrics.comparacoes++;
            if (maiores(gameList, filhoDireito, filhoEsquerdo)) {
                filho = filhoDireito;
            } else {
                filho = filhoEsquerdo;
            }
        }
        return filho;
    }

    // Atualizado para usar 'games[]' e 'games'
    static void swap(int p1, int p2, games[] gameList) {
        games aux = gameList[p2];
        gameList[p2] = gameList[p1];
        gameList[p1] = aux;
        HLogMetrics.movimentacoes += 3;
    }

    // Atualizado para usar 'games[]' e os novos nomes de atributos
    static boolean maiores(games[] gameList, int p1, int p2) {
        // Comparando por 'proprietariosEstimados'
        if (gameList[p1].proprietariosEstimados > gameList[p2].proprietariosEstimados) {
            return true;
        } else if (gameList[p1].proprietariosEstimados == gameList[p2].proprietariosEstimados) {
            // Critério de desempate: 'appid'
            return gameList[p1].appid > gameList[p2].appid;
        }
        return false;
    }

    // Atualizado para usar 'games[]' e os novos nomes de atributos
    static void printando(games[] jogosOrdenados, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            // Usando 'dataLancamento'
            String releaseDate = (jogosOrdenados[i].dataLancamento.charAt(1) == '/'
                    ? "0" + jogosOrdenados[i].dataLancamento
                    : jogosOrdenados[i].dataLancamento);
            System.out.println(
                    // Usando os novos nomes de atributos
                    "=> " + jogosOrdenados[i].appid + " ## " + jogosOrdenados[i].titulo + " ## " + releaseDate
                            + " ## " + jogosOrdenados[i].proprietariosEstimados + " ## " + jogosOrdenados[i].valorJogo
                            + " ## "
                            + printArray(jogosOrdenados[i].idiomasSuportados,
                                    jogosOrdenados[i].idiomasSuportadosCount)
                            + " ## " + jogosOrdenados[i].pontuacaoMetacritic + " ## "
                            + jogosOrdenados[i].avaliacaoUsuarios
                            + " ## " + jogosOrdenados[i].totalConquistas
                            + " ## " + printArray(jogosOrdenados[i].editoras, jogosOrdenados[i].editorasCount)
                            + " ## " + printArray(jogosOrdenados[i].desenvolvedores,
                                    jogosOrdenados[i].desenvolvedoresCount)
                            + " ## " + printArray(jogosOrdenados[i].categoriasJogo,
                                    jogosOrdenados[i].categoriasJogoCount)
                            + " ## " + printArray(jogosOrdenados[i].generosJogo, jogosOrdenados[i].generosJogoCount)
                            + " ## "
                            + (jogosOrdenados[i].tagsJogoCount == 0 ? ""
                                    : printArray(jogosOrdenados[i].tagsJogo, jogosOrdenados[i].tagsJogoCount))
                            + (jogosOrdenados[i].tagsJogoCount == 0 ? "" : " ##"));
        }
    }

    static String printArray(String[] array, int tamanho) {
        String resultado = "";
        if (tamanho == 0)
            return "[]";
        resultado += "[";
        for (int i = 0; i < tamanho; i++) {
            resultado += array[i];
            if (i < tamanho - 1)
                resultado += ", ";
        }
        resultado += "]";
        return resultado;
    }
}

// Capturando os jogos digitados através do id pelo usuário
class JogosDigitados {
    // Scanner
    public static Scanner sc;
    // Variável que pula caracteres das linhas
    static int contador = 0;
    // Ids de pesquisa e seu tamanho
    static String[] ids;
    static int idsTamanho;
    // Lista de jogos encontrados e seu tamanho (Atualizado para 'games[]')
    static games[] gamesList;
    static int gamesListTamanho;

    // Função para obter o tamanho final da lista de jogos
    public static int obterTamanhoGamesList() {
        return gamesListTamanho;
    }

    // Capturando os jogos (Atualizado para retornar 'games[]')
    static games[] inicializacao(String[] idArray, int tamanho) {
        // Array de jogos (Atualizado para 'games[]')
        gamesList = new games[NoArrayList.MAX_GAMES];
        gamesListTamanho = 0;

        // Copiando IDs para a variável de classe 'ids'
        ids = idArray;
        idsTamanho = tamanho;

        // Abrindo do arquivo
        InputStream is = null;
        try {
            java.io.File arquivo = new java.io.File("/tmp/games.csv");
            if (!arquivo.exists()) {
                System.out.println("Arquivo 'games.csv' não encontrado!");
                return gamesList;
            }
            is = new FileInputStream(arquivo);
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
            return gamesList;
        }

        sc = new Scanner(is);
        // Pula cabeçalho
        if (sc.hasNextLine())
            sc.nextLine();

        // Pesquisa por id
        while (sc.hasNextLine() && idsTamanho > 0 && gamesListTamanho < gamesList.length) {
            String linha = sc.nextLine();
            // Resetando o contador para a nova linha
            contador = 0;
            // Capturando outras informações
            int id = capturaId(linha); // Variável local 'id' mantida

            int indiceEncontrado = igualId(id);
            if (indiceEncontrado != -1) {
                // Variáveis locais mantidas com nomes antigos (capturaName, etc.)
                // Elas serão passadas para o novo construtor 'games()'
                String name = capturaName(linha);
                String releaseDate = capturaReleaseDate(linha);
                int estimatedOwners = capturaEstimatedOwners(linha);
                float price = capturaPrice(linha);

                String[] supportedLanguages = new String[NoArrayList.MAX_INNER_ARRAY];
                int supportedLanguagesCount = capturaSupportedLanguages(linha, supportedLanguages);
                int metacriticScore = capturaMetacriticScore(linha);
                float userScore = capturaUserScore(linha);
                int achievements = capturaAchievements(linha);

                String[] publishers = new String[NoArrayList.MAX_INNER_ARRAY];
                int publishersCount = capturaUltimosArryays(linha, publishers);
                String[] developers = new String[NoArrayList.MAX_INNER_ARRAY];
                int developersCount = capturaUltimosArryays(linha, developers);
                String[] categories = new String[NoArrayList.MAX_INNER_ARRAY];
                int categoriesCount = capturaUltimosArryays(linha, categories);
                String[] genres = new String[NoArrayList.MAX_INNER_ARRAY];
                int genresCount = capturaUltimosArryays(linha, genres);
                String[] tags = new String[NoArrayList.MAX_INNER_ARRAY];
                int tagsCount = capturaUltimosArryays(linha, tags);

                // Adicionando a classe (Atualizado para 'games')
                // O construtor 'games' aceita (id, name, ...) e os atribui a
                // (appid, titulo, ...) internamente.
                games jogo = new games(id, name, releaseDate, estimatedOwners, price,
                        supportedLanguages, supportedLanguagesCount, metacriticScore, userScore, achievements,
                        publishers, publishersCount, developers, developersCount, categories, categoriesCount, genres,
                        genresCount, tags, tagsCount);
                gamesList[gamesListTamanho++] = jogo;
                removerId(indiceEncontrado);
            }
        }
        sc.close();

        // Redimensiona o array final para o tamanho real (Atualizado para 'games[]')
        games[] resultadoFinal = new games[gamesListTamanho];
        for (int i = 0; i < gamesListTamanho; i++) {
            resultadoFinal[i] = gamesList[i];
        }
        gamesList = resultadoFinal;

        return resultadoFinal;
    }

    // Vendo se id é igual
    static int igualId(int id) {
        for (int i = 0; i < idsTamanho; i++) {
            if (Integer.parseInt(ids[i]) == id) {
                return i;
            }
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