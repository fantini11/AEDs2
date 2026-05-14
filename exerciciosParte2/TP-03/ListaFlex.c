#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 500
#define MATRICULA "885173"

typedef struct Restaurante {
    int id;
    char nome[200];
} Restaurante;

typedef struct Celula {
    Restaurante elemento;
    struct Celula* prox;
} Celula;

typedef struct Lista {
    Celula* primeiro;
    Celula* ultimo;
    int tamanho;
} Lista;

Celula* nova_celula(Restaurante x) {
    Celula* nova = (Celula*) malloc(sizeof(Celula));

    nova->elemento = x;
    nova->prox = NULL;

    return nova;
}

void iniciar_lista(Lista* lista) {
    lista->primeiro = nova_celula((Restaurante){0, ""});
    lista->ultimo = lista->primeiro;
    lista->tamanho = 0;
}

void inserir_fim(Lista* lista, Restaurante x) {
    lista->ultimo->prox = nova_celula(x);
    lista->ultimo = lista->ultimo->prox;
    lista->tamanho++;
}

void inserir_inicio(Lista* lista, Restaurante x) {
    Celula* tmp = nova_celula(x);

    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;

    if (lista->primeiro == lista->ultimo) {
        lista->ultimo = tmp;
    }

    lista->tamanho++;
}

void inserir(Lista* lista, Restaurante x, int pos) {
    if (pos == 0) {
        inserir_inicio(lista, x);

    } else if (pos == lista->tamanho) {
        inserir_fim(lista, x);

    } else {

        Celula* i = lista->primeiro;

        for (int j = 0; j < pos; j++) {
            i = i->prox;
        }

        Celula* tmp = nova_celula(x);

        tmp->prox = i->prox;
        i->prox = tmp;

        lista->tamanho++;
    }
}

Restaurante remover_inicio(Lista* lista) {
    Restaurante resp;

    Celula* tmp = lista->primeiro->prox;

    resp = tmp->elemento;

    lista->primeiro->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }

    free(tmp);

    lista->tamanho--;

    return resp;
}

Restaurante remover_fim(Lista* lista) {
    Restaurante resp;

    Celula* i = lista->primeiro;

    while (i->prox != lista->ultimo) {
        i = i->prox;
    }

    resp = lista->ultimo->elemento;

    free(lista->ultimo);

    lista->ultimo = i;
    i->prox = NULL;

    lista->tamanho--;

    return resp;
}

Restaurante remover(Lista* lista, int pos) {
    Restaurante resp;

    if (pos == 0) {
        resp = remover_inicio(lista);

    } else if (pos == lista->tamanho - 1) {
        resp = remover_fim(lista);

    } else {

        Celula* i = lista->primeiro;

        for (int j = 0; j < pos; j++) {
            i = i->prox;
        }

        Celula* tmp = i->prox;

        resp = tmp->elemento;

        i->prox = tmp->prox;

        free(tmp);

        lista->tamanho--;
    }

    return resp;
}

void mostrar(Lista* lista) {
    Celula* i = lista->primeiro->prox;

    int pos = 0;

    while (i != NULL) {
        printf("[%d] %d %s\n",
               pos,
               i->elemento.id,
               i->elemento.nome);

        i = i->prox;
        pos++;
    }
}

int main() {

    Lista lista;

    iniciar_lista(&lista);

    Restaurante restaurantes[MAX];

    int qtd = 0;

    int id;

    scanf("%d", &id);

    while (id != -1) {

        restaurantes[qtd].id = id;

        scanf(" %[^\n]", restaurantes[qtd].nome);

        inserir_fim(&lista, restaurantes[qtd]);

        qtd++;

        scanf("%d", &id);
    }

    int n;

    scanf("%d", &n);

    char comando[10];

    for (int i = 0; i < n; i++) {

        scanf("%s", comando);

        if (strcmp(comando, "II") == 0) {

            Restaurante x;

            scanf("%d", &x.id);
            scanf(" %[^\n]", x.nome);

            inserir_inicio(&lista, x);

        } else if (strcmp(comando, "IF") == 0) {

            Restaurante x;

            scanf("%d", &x.id);
            scanf(" %[^\n]", x.nome);

            inserir_fim(&lista, x);

        } else if (strcmp(comando, "I*") == 0) {

            int pos;

            Restaurante x;

            scanf("%d", &pos);
            scanf("%d", &x.id);
            scanf(" %[^\n]", x.nome);

            inserir(&lista, x, pos);

        } else if (strcmp(comando, "RI") == 0) {

            Restaurante r = remover_inicio(&lista);

            printf("(R) %s\n", r.nome);

        } else if (strcmp(comando, "RF") == 0) {

            Restaurante r = remover_fim(&lista);

            printf("(R) %s\n", r.nome);

        } else if (strcmp(comando, "R*") == 0) {

            int pos;

            scanf("%d", &pos);

            Restaurante r = remover(&lista, pos);

            printf("(R) %s\n", r.nome);
        }
    }

    mostrar(&lista);

    return 0;
}