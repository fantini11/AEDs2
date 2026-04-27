#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MATRICULA "885173"

typedef struct Restaurante {
    int id;
    char nome[200];
} Restaurante;

void remover_enter(char *s) {
    int i = 0;
    while (s[i] != '\0') {
        if (s[i] == '\n' || s[i] == '\r') {
            s[i] = '\0';
        }
        i++;
    }
}

void pegar_campo(char *linha, int indice, char *campo) {
    int i = 0;
    int j = 0;
    int atual = 0;

    while (linha[i] != '\0') {
        if (linha[i] == ',') {
            if (atual == indice) {
                campo[j] = '\0';
            }
            atual++;
            i++;
            j = 0;
        } else {
            if (atual == indice) {
                campo[j] = linha[i];
                j++;
            }
            i++;
        }
    }

    if (atual == indice) {
        campo[j] = '\0';
    }
}

Restaurante parse_restaurante(char *linha) {
    Restaurante r;
    char campo[200];

    pegar_campo(linha, 0, campo);
    sscanf(campo, "%d", &r.id);

    pegar_campo(linha, 1, r.nome);

    return r;
}

void ler_csv(Restaurante restaurantes[], int *n) {
    FILE *arquivo = fopen("/tmp/restaurantes.csv", "r");
    char linha[1000];

    if (arquivo == NULL) {
        arquivo = fopen("/tmp/RESTAURANTES.CSV", "r");
    }

    *n = 0;

    if (arquivo != NULL) {
        fgets(linha, 1000, arquivo);

        while (fgets(linha, 1000, arquivo) != NULL) {
            remover_enter(linha);
            restaurantes[*n] = parse_restaurante(linha);
            (*n)++;
        }

        fclose(arquivo);
    }
}

Restaurante buscar_por_id(Restaurante todos[], int n, int id) {
    Restaurante resp;
    resp.id = -1;
    resp.nome[0] = '\0';

    for (int i = 0; i < n; i++) {
        if (todos[i].id == id) {
            resp = todos[i];
        }
    }

    return resp;
}

void selecao(Restaurante array[], int n, long *comparacoes) {
    for (int i = 0; i < n - 1; i++) {
        int menor = i;

        for (int j = i + 1; j < n; j++) {
            (*comparacoes)++;
            if (strcmp(array[j].nome, array[menor].nome) < 0) {
                menor = j;
            }
        }

        if (menor != i) {
            Restaurante tmp = array[i];
            array[i] = array[menor];
            array[menor] = tmp;
        }
    }
}

int pesquisa_binaria(Restaurante array[], int n, char *nome, long *comparacoes) {
    int resp = 0;
    int esq = 0;
    int dir = n - 1;

    while (esq <= dir && resp == 0) {
        int meio = (esq + dir) / 2;
        int cmp = strcmp(nome, array[meio].nome);

        (*comparacoes)++;

        if (cmp == 0) {
            resp = 1;
        } else if (cmp > 0) {
            esq = meio + 1;
        } else {
            dir = meio - 1;
        }
    }

    return resp;
}

int confirmar_sequencial(Restaurante array[], int n, char *nome, long *comparacoes) {
    int resp = 0;
    int i = 0;

    while (i < n && resp == 0) {
        (*comparacoes)++;
        if (strcmp(array[i].nome, nome) == 0) {
            resp = 1;
        }
        i++;
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
    Restaurante todos[2000];
    Restaurante selecionados[1000];
    int total = 0;
    int n = 0;
    int id;
    char nome[300];
    long comparacoes = 0;

    ler_csv(todos, &total);

    scanf("%d", &id);

    while (id != -1) {
        Restaurante r = buscar_por_id(todos, total, id);

        if (r.id != -1) {
            selecionados[n] = r;
            n++;
        }

        scanf("%d", &id);
    }

    getchar();

    clock_t inicio = clock();

    selecao(selecionados, n, &comparacoes);

    while (fgets(nome, 300, stdin) != NULL) {
        remover_enter(nome);

        if (strcmp(nome, "FIM") != 0) {
            int achou = pesquisa_binaria(selecionados, n, nome, &comparacoes);

            if (achou == 0) {
                achou = confirmar_sequencial(selecionados, n, nome, &comparacoes);
            }

            if (achou == 1) {
                printf("SIM\n");
            } else {
                printf("NAO\n");
            }
        }
    }

    clock_t fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC;

    escrever_log(comparacoes, tempo);

    return 0;
}
