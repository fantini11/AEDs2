
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define TAMANHO_LINHA 4096
#define TAMANHO_CAMPO 1024
#define TAMANHO_ID 15

// Estrutura para representar um jogo do Steam
typedef struct {
    int appid;
    char *nome;
    char *data_lancamento;
    int proprietarios_estimados;
    float valor;
    char **linguas_suportadas;
    int total_linguas;
    int pontuacao_metacritic;
    float avaliacao_usuarios;
    int total_conquistas;
    char **editoras;
    int total_editoras;
    char **desenvolvedoras;
    int total_desenvolvedoras;
    char **tipos_categoria;
    int total_categorias;
    char **tipos_genero;
    int total_generos;
    char **etiquetas;
    int total_etiquetas;
} Game;

// Protótipos das funções
void liberarMemoriaGame(Game *game);
int converterTextoParaInt(const char *texto);
float converterTextoParaFloat(const char *texto);
void formatarDataLancamento(char *resultado, const char *dataOriginal);
void processarListaElementos(char ***lista, int *quantidade, const char *texto);
void separarCamposCSV(char ***campos, int *totalCampos, const char *linha);
void exibirGame(const Game *game);
bool ehEspacoEmBranco(char caractere);

bool ehEspacoEmBranco(char caractere) {
    return (caractere == ' ' || caractere == '\t' || caractere == '\n' || 
            caractere == '\r' || caractere == '\f' || caractere == '\v');
}

void processarListaElementos(char ***lista, int *quantidade, const char *texto) {
    char buffer[TAMANHO_CAMPO];
    strncpy(buffer, texto, TAMANHO_CAMPO - 1);
    buffer[TAMANHO_CAMPO - 1] = '\0';
    char *ponteiro = buffer;

    // Remove aspas duplas externas
    int tamanho = strlen(ponteiro);
    if (tamanho > 1 && ponteiro[0] == '"' && ponteiro[tamanho - 1] == '"') {
        ponteiro[tamanho - 1] = '\0';
        ponteiro++;
    }
    
    // Remove colchetes externos
    tamanho = strlen(ponteiro);
    if (tamanho > 1 && ponteiro[0] == '[' && ponteiro[tamanho - 1] == ']') {
        ponteiro[tamanho - 1] = '\0';
        ponteiro++;
    }

    // Conta elementos separados por vírgula
    *quantidade = 0;
    if (strlen(ponteiro) > 0) {
        *quantidade = 1;
        for (char *c = ponteiro; *c != '\0'; c++) {
            if (*c == ',') (*quantidade)++;
        }
    }

    if (*quantidade == 0) {
        *lista = NULL;
        return;
    }

    *lista = (char **)malloc(*quantidade * sizeof(char *));
    if (!*lista) return;

    int posicao = 0;
    char *comeco = ponteiro;
    for (char *c = ponteiro;; c++) {
        if (*c == ',' || *c == '\0') {
            char backup = *c;
            *c = '\0';
            char *elemento = comeco;
            
            // Remove espaços no início
            while (ehEspacoEmBranco(*elemento)) elemento++;
            if (*elemento == '\'') elemento++;

            // Remove espaços no final
            char *final = elemento + strlen(elemento) - 1;
            while (final >= elemento && (ehEspacoEmBranco(*final) || *final == '\'')) {
                *final = '\0';
                final--;
            }

            (*lista)[posicao++] = strdup(elemento);
            if (backup == '\0') break;
            comeco = c + 1;
        }
    }
}

int converterTextoParaInt(const char *texto) {
    int resultado = 0;
    int i = 0;
    while (texto[i] != '\0') {
        if (texto[i] >= '0' && texto[i] <= '9') {
            resultado = resultado * 10 + (texto[i] - '0');
        }
        i++;
    }
    return resultado;
}

float converterTextoParaFloat(const char *texto) {
    float numero = 0.0f;
    bool encontrouPonto = false;
    float fatorDecimal = 10.0f;
    
    int i = 0;
    while (texto[i] != '\0') {
        char ch = texto[i];
        if (ch >= '0' && ch <= '9') {
            int digito = ch - '0';
            if (!encontrouPonto) {
                numero = numero * 10 + digito;
            } else {
                numero = numero + digito / fatorDecimal;
                fatorDecimal *= 10;
            }
        } else if (ch == '.') {
            encontrouPonto = true;
        }
        i++;
    }
    return numero;
}

void formatarDataLancamento(char *resultado, const char *dataOriginal) {
    char buffer[100];
    strncpy(buffer, dataOriginal, sizeof(buffer) - 1);
    buffer[sizeof(buffer) - 1] = '\0';

    // Remove aspas se existirem
    if (buffer[0] == '"') {
        memmove(buffer, buffer + 1, strlen(buffer));
        buffer[strlen(buffer) - 1] = '\0';
    }

    char *nomeDoMes = strtok(buffer, " ,");
    char *diaDoMes = strtok(NULL, " ,");
    char *anoCompleto = strtok(NULL, " ,");

    if (!nomeDoMes || !diaDoMes || !anoCompleto) {
        strcpy(resultado, "00/00/0000");
        return;
    }

    char numeroMes[3];
    if (strcmp(nomeDoMes, "Jan") == 0) strcpy(numeroMes, "01");
    else if (strcmp(nomeDoMes, "Feb") == 0) strcpy(numeroMes, "02");
    else if (strcmp(nomeDoMes, "Mar") == 0) strcpy(numeroMes, "03");
    else if (strcmp(nomeDoMes, "Apr") == 0) strcpy(numeroMes, "04");
    else if (strcmp(nomeDoMes, "May") == 0) strcpy(numeroMes, "05");
    else if (strcmp(nomeDoMes, "Jun") == 0) strcpy(numeroMes, "06");
    else if (strcmp(nomeDoMes, "Jul") == 0) strcpy(numeroMes, "07");
    else if (strcmp(nomeDoMes, "Aug") == 0) strcpy(numeroMes, "08");
    else if (strcmp(nomeDoMes, "Sep") == 0) strcpy(numeroMes, "09");
    else if (strcmp(nomeDoMes, "Oct") == 0) strcpy(numeroMes, "10");
    else if (strcmp(nomeDoMes, "Nov") == 0) strcpy(numeroMes, "11");
    else if (strcmp(nomeDoMes, "Dec") == 0) strcpy(numeroMes, "12");
    else strcpy(numeroMes, "00");

    sprintf(resultado, "%02d/%s/%s", atoi(diaDoMes), numeroMes, anoCompleto);
}

void separarCamposCSV(char ***campos, int *totalCampos, const char *linha) {
    *totalCampos = 0;
    bool estaEntreAspas = false;
    bool estaEntreListas = false;

    // Conta o número de campos
    for (int i = 0; linha[i] != '\0'; i++) {
        if (linha[i] == '"') {
            estaEntreAspas = !estaEntreAspas;
        } else if (linha[i] == '[') {
            estaEntreListas = true;
        } else if (linha[i] == ']') {
            estaEntreListas = false;
        } else if (linha[i] == ',' && !estaEntreAspas && !estaEntreListas) {
            (*totalCampos)++;
        }
    }
    (*totalCampos)++;

    *campos = (char **)malloc(*totalCampos * sizeof(char *));
    char bufferCampo[TAMANHO_CAMPO];
    int indiceCampo = 0;
    int indiceChar = 0;
    estaEntreAspas = false;
    estaEntreListas = false;

    for (int i = 0; linha[i] != '\0'; i++) {
        char caractere = linha[i];
        if (caractere == '\n' || caractere == '\r') continue;
        
        if (caractere == '"') {
            estaEntreAspas = !estaEntreAspas;
        } else if (caractere == '[') {
            estaEntreListas = true;
        } else if (caractere == ']') {
            estaEntreListas = false;
        }

        if (caractere == ',' && !estaEntreAspas && !estaEntreListas) {
            bufferCampo[indiceChar] = '\0';
            (*campos)[indiceCampo++] = strdup(bufferCampo);
            indiceChar = 0;
        } else {
            bufferCampo[indiceChar++] = caractere;
        }
    }
    bufferCampo[indiceChar] = '\0';
    (*campos)[indiceCampo] = strdup(bufferCampo);
}

void liberarMemoriaGame(Game *game) {
    if (!game) return;
    
    free(game->nome);
    free(game->data_lancamento);

    for (int i = 0; i < game->total_linguas; i++) {
        free(game->linguas_suportadas[i]);
    }
    free(game->linguas_suportadas);

    for (int i = 0; i < game->total_editoras; i++) {
        free(game->editoras[i]);
    }
    free(game->editoras);

    for (int i = 0; i < game->total_desenvolvedoras; i++) {
        free(game->desenvolvedoras[i]);
    }
    free(game->desenvolvedoras);

    for (int i = 0; i < game->total_categorias; i++) {
        free(game->tipos_categoria[i]);
    }
    free(game->tipos_categoria);

    for (int i = 0; i < game->total_generos; i++) {
        free(game->tipos_genero[i]);
    }
    free(game->tipos_genero);

    for (int i = 0; i < game->total_etiquetas; i++) {
        free(game->etiquetas[i]);
    }
    free(game->etiquetas);
}

void exibirGame(const Game *game) {
    char dataFormatada[12];
    formatarDataLancamento(dataFormatada, game->data_lancamento);

    printf("=> %d ## %s ## %s ## %d ## ", game->appid, game->nome, dataFormatada, game->proprietarios_estimados);

    float preco = game->valor;
    if (preco == 0.0f) {
        printf("0.0 ## [");
    } else if (preco == (int)preco) {
        printf("%d ## [", (int)preco);
    } else if (((int)(preco * 10)) == (preco * 10)) {
        printf("%.1f ## [", preco);
    } else {
        printf("%.2f ## [", preco);
    }

    for (int i = 0; i < game->total_linguas; i++) {
        printf("%s", game->linguas_suportadas[i]);
        if (i < game->total_linguas - 1) printf(", ");
    }

    printf("] ## %d ## %.1f ## %d ## [", game->pontuacao_metacritic, game->avaliacao_usuarios, game->total_conquistas);

    for (int i = 0; i < game->total_editoras; i++) {
        printf("%s", game->editoras[i]);
        if (i < game->total_editoras - 1) printf(", ");
    }
    printf("] ## [");

    for (int i = 0; i < game->total_desenvolvedoras; i++) {
        printf("%s", game->desenvolvedoras[i]);
        if (i < game->total_desenvolvedoras - 1) printf(", ");
    }
    printf("] ## [");

    for (int i = 0; i < game->total_categorias; i++) {
        printf("%s", game->tipos_categoria[i]);
        if (i < game->total_categorias - 1) printf(", ");
    }
    printf("] ## [");

    for (int i = 0; i < game->total_generos; i++) {
        printf("%s", game->tipos_genero[i]);
        if (i < game->total_generos - 1) printf(", ");
    }
    printf("] ## [");

    for (int i = 0; i < game->total_etiquetas; i++) {
        printf("%s", game->etiquetas[i]);
        if (i < game->total_etiquetas - 1) printf(", ");
    }
    printf("] ##\n");
}

int main() {
    Game gameAtual = {0};
    char identificadorBusca[TAMANHO_ID];

    if (scanf("%s", identificadorBusca) != 1) return 1;

    while (strcmp(identificadorBusca, "FIM") != 0) {
        FILE *arquivo = fopen("/tmp/games.csv", "r");
        if (!arquivo) {
            printf("Erro ao abrir /tmp/games.csv\n");
            return 1;
        }

        char linhaBuffer[TAMANHO_LINHA];
        fgets(linhaBuffer, sizeof(linhaBuffer), arquivo);

        bool encontrou = false;
        while (fgets(linhaBuffer, sizeof(linhaBuffer), arquivo)) {
            char **listaCampos = NULL;
            int numeroCampos = 0;
            separarCamposCSV(&listaCampos, &numeroCampos, linhaBuffer);

            if (numeroCampos < 14) {
                for (int i = 0; i < numeroCampos; i++) free(listaCampos[i]);
                free(listaCampos);
                continue;
            }

            if (converterTextoParaInt(listaCampos[0]) == converterTextoParaInt(identificadorBusca)) {
                encontrou = true;
                liberarMemoriaGame(&gameAtual);

                gameAtual.appid = converterTextoParaInt(listaCampos[0]);
                gameAtual.nome = strdup(listaCampos[1]);
                gameAtual.data_lancamento = strdup(listaCampos[2]);
                gameAtual.proprietarios_estimados = converterTextoParaInt(listaCampos[3]);
                gameAtual.valor = strcmp(listaCampos[4], "Free to Play") == 0 ? 0.0f : converterTextoParaFloat(listaCampos[4]);

                processarListaElementos(&gameAtual.linguas_suportadas, &gameAtual.total_linguas, listaCampos[5]);
                gameAtual.pontuacao_metacritic = strlen(listaCampos[6]) == 0 ? -1 : converterTextoParaInt(listaCampos[6]);
                gameAtual.avaliacao_usuarios = (strlen(listaCampos[7]) == 0 || strcmp(listaCampos[7], "tbd") == 0) ? -1.0f : converterTextoParaFloat(listaCampos[7]);
                gameAtual.total_conquistas = strlen(listaCampos[8]) == 0 ? 0 : converterTextoParaInt(listaCampos[8]);
                processarListaElementos(&gameAtual.editoras, &gameAtual.total_editoras, listaCampos[9]);
                processarListaElementos(&gameAtual.desenvolvedoras, &gameAtual.total_desenvolvedoras, listaCampos[10]);
                processarListaElementos(&gameAtual.tipos_categoria, &gameAtual.total_categorias, listaCampos[11]);
                processarListaElementos(&gameAtual.tipos_genero, &gameAtual.total_generos, listaCampos[12]);
                processarListaElementos(&gameAtual.etiquetas, &gameAtual.total_etiquetas, listaCampos[13]);

                exibirGame(&gameAtual);

                for (int i = 0; i < numeroCampos; i++) free(listaCampos[i]);
                free(listaCampos);
                break;
            }

            for (int i = 0; i < numeroCampos; i++) free(listaCampos[i]);
            free(listaCampos);
        }

        if (!encontrou) {
            printf("Jogo com ID %s nao encontrado!\n", identificadorBusca);
        }

        fclose(arquivo);

        if (scanf("%s", identificadorBusca) != 1) {
            break;
        }
    }

    liberarMemoriaGame(&gameAtual);
    return 0;
}
