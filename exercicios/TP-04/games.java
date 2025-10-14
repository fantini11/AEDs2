import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class games {
    // Atributos do jogo do Steam
    private int appid;
    private String titulo;
    private String dataLancamento;
    private int proprietariosEstimados;
    private float valorJogo;
    private String idiomasSuportados;
    private int pontuacaoMetacritic;
    private float avaliacaoUsuarios;
    private int totalConquistas;
    private String editoras;
    private String desenvolvedores;
    private String categoriasJogo;
    private String generosJogo;
    private String tagsJogo;

    // Construtor completo
    public games(int appid, String titulo, String dataLancamento, int proprietariosEstimados, 
                 float valorJogo, String idiomasSuportados, int pontuacaoMetacritic, 
                 float avaliacaoUsuarios, int totalConquistas, String editoras, 
                 String desenvolvedores, String categoriasJogo, String generosJogo, String tagsJogo) {
        definirAppid(appid);
        definirTitulo(titulo);
        definirDataLancamento(dataLancamento);
        definirProprietariosEstimados(proprietariosEstimados);
        definirValorJogo(valorJogo);
        definirIdiomasSuportados(idiomasSuportados);
        definirPontuacaoMetacritic(pontuacaoMetacritic);
        definirAvaliacaoUsuarios(avaliacaoUsuarios);
        definirTotalConquistas(totalConquistas);
        definirEditoras(editoras);
        definirDesenvolvedores(desenvolvedores);
        definirCategoriasJogo(categoriasJogo);
        definirGenerosJogo(generosJogo);
        definirTagsJogo(tagsJogo);
    }

    // Construtor padrão
    public games() {
        this.appid = 0;
        this.titulo = "";
        this.dataLancamento = "";
        this.proprietariosEstimados = 0;
        this.valorJogo = 0.0f;
        this.idiomasSuportados = "";
        this.pontuacaoMetacritic = 0;
        this.avaliacaoUsuarios = 0.0f;
        this.totalConquistas = 0;
        this.editoras = "";
        this.desenvolvedores = "";
        this.categoriasJogo = "";
        this.generosJogo = "";  
        this.tagsJogo = "";
    }

    // Métodos de acesso (getters)
    public int obterAppid() {
        return this.appid;
    }

    public String obterTitulo() {
        return this.titulo;
    }

    public String obterDataLancamento() {
        return this.dataLancamento;
    }

    public int obterProprietariosEstimados() {
        return this.proprietariosEstimados;
    }

    public float obterValorJogo() {
        return this.valorJogo;
    }

    public String obterIdiomasSuportados() {
        return this.idiomasSuportados;
    }

    public int obterPontuacaoMetacritic() {
        return this.pontuacaoMetacritic;
    }

    public float obterAvaliacaoUsuarios() {
        return this.avaliacaoUsuarios;
    }

    public int obterTotalConquistas() {
        return this.totalConquistas;
    }

    public String obterEditoras() {
        return this.editoras;
    }

    public String obterDesenvolvedores() {
        return this.desenvolvedores;
    }

    public String obterCategoriasJogo() {
        return this.categoriasJogo;
    }

    public String obterGenerosJogo() {
        return this.generosJogo;
    }

    public String obterTagsJogo() {
        return this.tagsJogo;
    }

    // Métodos de modificação (setters)
    public void definirAppid(int novoAppid) {
        this.appid = novoAppid;
    }

    public void definirTitulo(String novoTitulo) {
        this.titulo = novoTitulo;
    }

    public void definirDataLancamento(String novaDataLancamento) {
        this.dataLancamento = novaDataLancamento;
    }

    public void definirProprietariosEstimados(int novosProprietariosEstimados) {
        this.proprietariosEstimados = novosProprietariosEstimados;
    }

    public void definirValorJogo(float novoValorJogo) {
        this.valorJogo = novoValorJogo;
    }

    public void definirIdiomasSuportados(String novosIdiomasSuportados) {
        this.idiomasSuportados = novosIdiomasSuportados;
    }

    public void definirPontuacaoMetacritic(int novaPontuacaoMetacritic) {
        this.pontuacaoMetacritic = novaPontuacaoMetacritic;
    }

    public void definirAvaliacaoUsuarios(float novaAvaliacaoUsuarios) {
        this.avaliacaoUsuarios = novaAvaliacaoUsuarios;
    }

    public void definirTotalConquistas(int novoTotalConquistas) {
        this.totalConquistas = novoTotalConquistas;
    }

    public void definirEditoras(String novasEditoras) {
        this.editoras = novasEditoras;
    }

    public void definirDesenvolvedores(String novosDesenvolvedores) {
        this.desenvolvedores = novosDesenvolvedores;
    }

    public void definirCategoriasJogo(String novasCategoriasJogo) {
        this.categoriasJogo = novasCategoriasJogo;
    }

    public void definirGenerosJogo(String novosGenerosJogo) {
        this.generosJogo = novosGenerosJogo;
    }

    public void definirTagsJogo(String novasTagsJogo) {
        this.tagsJogo = novasTagsJogo;
    }
    // Método para converter data de formato americano para brasileiro
    public String converterFormatoData(String dataOriginal) {
        // Limpar aspas duplas se existirem
        if (dataOriginal.startsWith("\"") && dataOriginal.endsWith("\"")) {
            dataOriginal = dataOriginal.substring(1, dataOriginal.length() - 1);
        }

        // Separar por espaços
        String[] componentesData = dataOriginal.split(" ");
        if (componentesData.length < 3) return "00/00/0000";

        String nomeDoMes = componentesData[0];
        String diaComVirgula = componentesData[1];
        String anoCompleto = componentesData[2];

        // Remover vírgula do dia usando método manual
        String[] arrayDia = dividirTextoManual(diaComVirgula);
        String diaLimpo = arrayDia[0];

        String codigoMes = "00";

        // Conversão dos nomes dos meses
        if (nomeDoMes.equals("Jan")) codigoMes = "01";
        else if (nomeDoMes.equals("Feb")) codigoMes = "02";
        else if (nomeDoMes.equals("Mar")) codigoMes = "03";
        else if (nomeDoMes.equals("Apr")) codigoMes = "04";
        else if (nomeDoMes.equals("May")) codigoMes = "05";
        else if (nomeDoMes.equals("Jun")) codigoMes = "06";
        else if (nomeDoMes.equals("Jul")) codigoMes = "07";
        else if (nomeDoMes.equals("Aug")) codigoMes = "08";
        else if (nomeDoMes.equals("Sep")) codigoMes = "09";
        else if (nomeDoMes.equals("Oct")) codigoMes = "10";
        else if (nomeDoMes.equals("Nov")) codigoMes = "11";
        else if (nomeDoMes.equals("Dec")) codigoMes = "12";

        // Adicionar zero à esquerda se necessário
        if (diaLimpo.length() == 1) {
            diaLimpo = "0" + diaLimpo;
        }

        return diaLimpo + "/" + codigoMes + "/" + anoCompleto;
    }



    @Override
    public String toString() {
        String dataConvertida = converterFormatoData(this.dataLancamento);

        String editoras = processarColchetes(this.editoras);
        String desenvolvedoras = processarColchetes(this.desenvolvedores);
        String categorias = processarColchetes(this.categoriasJogo);
        String generos = processarColchetes(this.generosJogo);
        String tags = processarColchetes(this.tagsJogo);
        String idiomas = processarColchetes(this.idiomasSuportados);

        return "=> " + this.appid + " ## " 
                + this.titulo + " ## " 
                + dataConvertida + " ## " 
                + this.proprietariosEstimados + " ## " 
                + this.valorJogo + " ## " 
                + idiomas + " ## " 
                + this.pontuacaoMetacritic + " ## " 
                + this.avaliacaoUsuarios + " ## " 
                + this.totalConquistas + " ## " 
                + editoras + " ## " 
                + desenvolvedoras + " ## " 
                + categorias + " ## " 
                + generos + " ## " 
                + tags + " ## ";
    }


    // Método para processar strings com colchetes e vírgulas  
    public String processarColchetes(String textoOriginal) {
        if (textoOriginal == null) {
            return "[]";
        }

        String textoResultado = "[";
        boolean primeiroElemento = true;

        // Limpar aspas duplas externas se existirem
        String textoLimpo = textoOriginal;
        if (textoLimpo.startsWith("\"") && textoLimpo.endsWith("\"")) {
            textoLimpo = textoLimpo.substring(1, textoLimpo.length() - 1);
        }
        
        // Se já tem colchetes, remover também
        if (textoLimpo.startsWith("[") && textoLimpo.endsWith("]")) {
            textoLimpo = textoLimpo.substring(1, textoLimpo.length() - 1);
        }

        // Dividir por vírgula e processar cada elemento
        String[] elementos = textoLimpo.split(",");
        
        for (int i = 0; i < elementos.length; i++) {
            String elemento = elementos[i].trim();
            
            // Remover aspas simples externas apenas se ambas estiverem presentes
            if (elemento.length() > 1 && elemento.startsWith("'") && elemento.endsWith("'")) {
                elemento = elemento.substring(1, elemento.length() - 1);
            }
            
            if (!primeiroElemento) {
                textoResultado += ", ";
            }
            textoResultado += elemento;
            primeiroElemento = false;
        }

        textoResultado += "]";
        return textoResultado;
    }



    // Método para dividir texto CSV manualmente
    public String[] dividirTextoManual(String textoCompleto) {
        // Contar número de separadores válidos
        int contadorVirgulas = 0;
        boolean estaEntreAspas = false;
        boolean estaEntreListas = false;

        for (int posicao = 0; posicao < textoCompleto.length(); posicao++) {
            char caractere = textoCompleto.charAt(posicao);
            if (caractere == '"') {
                estaEntreAspas = !estaEntreAspas;
            } else if (caractere == '[') {
                estaEntreListas = true;
            } else if (caractere == ']') {
                estaEntreListas = false;
            } else if (caractere == ',' && !estaEntreAspas && !estaEntreListas) {
                contadorVirgulas++;
            }
        }

        // Criar array com tamanho correto
        String[] arrayResultado = new String[contadorVirgulas + 1];

        // Preencher array com os campos
        String campoAtual = "";
        estaEntreAspas = false;
        estaEntreListas = false;
        int indiceCampo = 0;

        for (int posicao = 0; posicao < textoCompleto.length(); posicao++) {
            char caractere = textoCompleto.charAt(posicao);

            if (caractere == '"') {
                estaEntreAspas = !estaEntreAspas;
            } else if (caractere == '[') {
                estaEntreListas = true;
            } else if (caractere == ']') {
                estaEntreListas = false;
            } else if (caractere == ',' && !estaEntreAspas && !estaEntreListas) {
                arrayResultado[indiceCampo] = campoAtual;
                indiceCampo++;
                campoAtual = "";
                continue;
            }

            campoAtual += caractere;
        }

        arrayResultado[indiceCampo] = campoAtual; // Adicionar último campo

        return arrayResultado;
    }

    // Método para converter string em número inteiro
    public int converterTextoParaInteiro(String textoNumerico) {
        int valorNumerico = 0;

        for (int posicao = 0; posicao < textoNumerico.length(); posicao++) {
            // Extrair valor numérico do caractere
            int valorDigito = textoNumerico.charAt(posicao) - '0';
            valorNumerico = valorNumerico * 10 + valorDigito;
        }

        return valorNumerico;
    }

    public static void main(String[] args) throws IOException {
        Scanner leitorEntrada = new Scanner(System.in);
        games jogoSteam = new games();

        String identificadorBusca = leitorEntrada.nextLine();

        while (!identificadorBusca.equals("FIM")) {
            boolean jogoEncontrado = false;

            // Abrir arquivo CSV para cada busca
            BufferedReader leitorArquivo = new BufferedReader(new FileReader("/tmp/games.csv"));
            leitorArquivo.readLine(); // Ignorar cabeçalho
            String linhaAtual;

            while ((linhaAtual = leitorArquivo.readLine()) != null) {
                String[] dadosJogo = jogoSteam.dividirTextoManual(linhaAtual);
                
                if (jogoSteam.converterTextoParaInteiro(dadosJogo[0]) == jogoSteam.converterTextoParaInteiro(identificadorBusca)) {
                    jogoEncontrado = true;
                    
                    jogoSteam.definirAppid(jogoSteam.converterTextoParaInteiro(dadosJogo[0]));
                    jogoSteam.definirTitulo(dadosJogo[1]);
                    jogoSteam.definirDataLancamento(dadosJogo[2]);
                    jogoSteam.definirProprietariosEstimados(jogoSteam.converterTextoParaInteiro(dadosJogo[3]));

                    String valorTexto = dadosJogo[4];
                    float valorConvertido;
                    if (valorTexto.equals("Free to Play")) {
                        valorConvertido = 0.0f;
                    } else {
                        String[] componentesPreco = jogoSteam.dividirTextoManual(valorTexto);
                        valorConvertido = Float.parseFloat(componentesPreco[0]);
                    }
                    
                    jogoSteam.definirValorJogo(valorConvertido);
                    jogoSteam.definirIdiomasSuportados(dadosJogo[5]);
                    jogoSteam.definirPontuacaoMetacritic(jogoSteam.converterTextoParaInteiro(dadosJogo[6]));
                    jogoSteam.definirAvaliacaoUsuarios(Float.parseFloat(dadosJogo[7]));
                    jogoSteam.definirTotalConquistas(jogoSteam.converterTextoParaInteiro(dadosJogo[8]));
                    jogoSteam.definirEditoras(dadosJogo[9]);
                    jogoSteam.definirDesenvolvedores(dadosJogo[10]);
                    jogoSteam.definirCategoriasJogo(dadosJogo[11]);
                    jogoSteam.definirGenerosJogo(dadosJogo[12]);
                    jogoSteam.definirTagsJogo(dadosJogo[13]);

                    System.out.println(jogoSteam.toString());
                    break;
                }
            }

            if (!jogoEncontrado) {
                System.out.println("Jogo com ID " + identificadorBusca + " não encontrado!");
            }

            leitorArquivo.close();
            identificadorBusca = leitorEntrada.nextLine();
        }

        leitorEntrada.close();
    }


}