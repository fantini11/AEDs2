#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MATRICULA "885173"

typedef struct Data {
    int ano;
    int mes;
    int dia;
} Data;

typedef struct Hora {
    int hora;
    int minuto;
} Hora;

typedef struct Restaurante {
    int id;
    char *nome;
    char *cidade;
    int capacidade;
    double avaliacao;
    char **tipos_cozinha;
    int n_tipos_cozinha;
    int faixa_preco;
    Hora horario_abertura;
    Hora horario_fechamento;
    Data data_abertura;
    int aberto;
} Restaurante;

typedef struct Colecao_Restaurantes {
    int tamanho;
    Restaurante **restaurantes;
} Colecao_Restaurantes;

int tamanho_string(char *s) {
    int i = 0;
    while (s[i] != '\0') i++;
    return i;
}

char *copiar_string(char *s) {
    int tam = tamanho_string(s);
    char *resp = (char *) malloc((tam + 1) * sizeof(char));
    int i = 0;

    while (i < tam) {
        resp[i] = s[i];
        i++;
    }

    resp[i] = '\0';
    return resp;
}

void remover_enter(char *s) {
    int i = 0;

    while (s[i] != '\0') {
        if (s[i] == '\n' || s[i] == '\r') {
            s[i] = '\0';
        }
        i++;
    }
}

Data parse_data(char *s) {
    Data d;
    sscanf(s, "%d-%d-%d", &d.ano, &d.mes, &d.dia);
    return d;
}

Hora parse_hora(char *s) {
    Hora h;
    sscanf(s, "%d:%d", &h.hora, &h.minuto);
    return h;
}

void separar_horario(char *s, Hora *abertura, Hora *fechamento) {
    char parte_abertura[20];
    char parte_fechamento[20];
    int i = 0;
    int j = 0;

    while (s[i] != '-' && s[i] != '\0') {
        parte_abertura[j] = s[i];
        i++;
        j++;
    }
    parte_abertura[j] = '\0';

    if (s[i] == '-') i++;

    j = 0;
    while (s[i] != '\0') {
        parte_fechamento[j] = s[i];
        i++;
        j++;
    }
    parte_fechamento[j] = '\0';

    *abertura = parse_hora(parte_abertura);
    *fechamento = parse_hora(parte_fechamento);
}

int converter_faixa_preco(char *s) {
    int resp = 0;
    int i = 0;

    while (s[i] != '\0') {
        if (s[i] == '$') resp++;
        i++;
    }

    return resp;
}

int separar_tipos_cozinha(char *s, char **tipos) {
    int qtd = 0;
    int i = 0;
    int j = 0;
    char atual[200];

    while (s[i] != '\0') {
        if (s[i] == ';') {
            atual[j] = '\0';
            tipos[qtd] = copiar_string(atual);
            qtd++;
            j = 0;
        } else {
            atual[j] = s[i];
            j++;
        }
        i++;
    }

    atual[j] = '\0';
    tipos[qtd] = copiar_string(atual);
    qtd++;

    return qtd;
}

void separar_campos_csv(char *linha, char campos[][300], int *qtd) {
    int i = 0;
    int campo = 0;
    int pos = 0;

    while (linha[i] != '\0') {
        if (linha[i] == ',') {
            campos[campo][pos] = '\0';
            campo++;
            pos = 0;
        } else {
            campos[campo][pos] = linha[i];
            pos++;
        }
        i++;
    }

    campos[campo][pos] = '\0';
    *qtd = campo + 1;
}

Restaurante *parse_restaurante(char *s) {
    Restaurante *r = (Restaurante *) malloc(sizeof(Restaurante));
    char campos[20][300];
    int qtd = 0;

    separar_campos_csv(s, campos, &qtd);

    r->id = 0;
    r->nome = copiar_string("");
    r->cidade = copiar_string("");
    r->capacidade = 0;
    r->avaliacao = 0.0;
    r->tipos_cozinha = (char **) malloc(30 * sizeof(char *));
    r->n_tipos_cozinha = 0;
    r->faixa_preco = 0;
    r->aberto = 0;

    if (qtd >= 10) {
        sscanf(campos[0], "%d", &r->id);
        r->nome = copiar_string(campos[1]);
        r->cidade = copiar_string(campos[2]);
        sscanf(campos[3], "%d", &r->capacidade);
        sscanf(campos[4], "%lf", &r->avaliacao);
        r->n_tipos_cozinha = separar_tipos_cozinha(campos[5], r->tipos_cozinha);
        r->faixa_preco = converter_faixa_preco(campos[6]);
        separar_horario(campos[7], &r->horario_abertura, &r->horario_fechamento);
        r->data_abertura = parse_data(campos[8]);

        if (strcmp(campos[9], "true") == 0) {
            r->aberto = 1;
        }
    }

    return r;
}

void ler_csv_colecao(Colecao_Restaurantes *colecao, char *path) {
    FILE *arquivo = fopen(path, "r");
    char linha[1000];

    colecao->tamanho = 0;
    colecao->restaurantes = (Restaurante **) malloc(2000 * sizeof(Restaurante *));

    if (arquivo != NULL) {
        if (fgets(linha, 1000, arquivo) != NULL) {
            /* pula cabecalho */
        }

        while (fgets(linha, 1000, arquivo) != NULL) {
            remover_enter(linha);

            if (tamanho_string(linha) > 0) {
                colecao->restaurantes[colecao->tamanho] = parse_restaurante(linha);
                colecao->tamanho++;
            }
        }

        fclose(arquivo);
    }
}

Colecao_Restaurantes *ler_csv() {
    Colecao_Restaurantes *colecao = (Colecao_Restaurantes *) malloc(sizeof(Colecao_Restaurantes));
    FILE *teste = fopen("/tmp/restaurantes.csv", "r");

    if (teste != NULL) {
        fclose(teste);
        ler_csv_colecao(colecao, "/tmp/restaurantes.csv");
    } else {
        ler_csv_colecao(colecao, "/tmp/RESTAURANTES.CSV");
    }

    return colecao;
}

Restaurante *buscar_por_id(Colecao_Restaurantes *colecao, int id) {
    Restaurante *resp = NULL;
    int i = 0;

    while (i < colecao->tamanho) {
        if (colecao->restaurantes[i]->id == id) {
            resp = colecao->restaurantes[i];
        }
        i++;
    }

    return resp;
}

void selecao(Restaurante **array, int n, long *comparacoes) {
    int i = 0;

    while (i < n - 1) {
        int menor = i;
        int j = i + 1;

        while (j < n) {
            (*comparacoes)++;

            if (strcmp(array[j]->nome, array[menor]->nome) < 0) {
                menor = j;
            }

            j++;
        }

        if (menor != i) {
            Restaurante *tmp = array[i];
            array[i] = array[menor];
            array[menor] = tmp;
        }

        i++;
    }
}

int pesquisa_binaria(Restaurante **array, int n, char *nome, long *comparacoes) {
    int resp = 0;
    int esq = 0;
    int dir = n - 1;

    while (esq <= dir && resp == 0) {
        int meio = (esq + dir) / 2;
        int cmp = strcmp(nome, array[meio]->nome);

        (*comparacoes)++;

        if (cmp == 0) {
            resp = 1;
        } else {
            (*comparacoes)++;

            if (cmp > 0) {
                esq = meio + 1;
            } else {
                dir = meio - 1;
            }
        }
    }

    return resp;
}

void escrever_log(long comparacoes, double tempo) {
    FILE *arquivo = fopen(MATRICULA "_binaria.txt", "w");

    if (arquivo != NULL) {
        fprintf(arquivo, "%s\t%ld\t%lf", MATRICULA, comparacoes, tempo);
        fclose(arquivo);
    }
}

int main() {
    Colecao_Restaurantes *colecao = ler_csv();
    Restaurante *selecionados[1000];
    int n = 0;
    int id = 0;
    char nome[300];
    long comparacoes = 0;

    scanf("%d", &id);

    while (id != -1) {
        Restaurante *r = buscar_por_id(colecao, id);

        if (r != NULL) {
            selecionados[n] = r;
            n++;
        }

        scanf("%d", &id);
    }

    getchar();

    clock_t inicio = clock();

    selecao(selecionados, n, &comparacoes);

    if (fgets(nome, 300, stdin) != NULL) {
        remover_enter(nome);

        while (strcmp(nome, "FIM") != 0) {
            if (pesquisa_binaria(selecionados, n, nome, &comparacoes) == 1) {
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }

            if (fgets(nome, 300, stdin) != NULL) {
                remover_enter(nome);
            } else {
                strcpy(nome, "FIM");
            }
        }
    }

    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    escrever_log(comparacoes, tempo);

    return 0;
}
