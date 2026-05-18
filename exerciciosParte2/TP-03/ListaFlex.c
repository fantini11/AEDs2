#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
    char nome[200];
    char cidade[200];
    int capacidade;
    double avaliacao;
    char cozinhas[20][100];
    int quantidade_cozinhas;
    int preco;
    Hora hora_abertura;
    Hora hora_fechamento;
    Data data_abertura;
    int aberto;
} Restaurante;

typedef struct ColecaoRestaurantes {
    int tamanho;
    Restaurante restaurantes[2000];
} ColecaoRestaurantes;

typedef struct Celula {
    Restaurante elemento;
    struct Celula *prox;
} Celula;

typedef struct Lista {
    Celula *primeiro;
    Celula *ultimo;
} Lista;

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

int converter_preco(char *s) {
    int resp = 0;

    for (int i = 0; s[i] != '\0'; i++) {
        if (s[i] == '$') {
            resp++;
        }
    }

    return resp;
}

void formatar_preco(int preco, char *buffer) {
    for (int i = 0; i < preco; i++) {
        buffer[i] = '$';
    }

    buffer[preco] = '\0';
}

void separar_horario(char *s, Hora *abertura, Hora *fechamento) {
    char a[20];
    char f[20];

    sscanf(s, "%[^-]-%s", a, f);

    *abertura = parse_hora(a);
    *fechamento = parse_hora(f);
}

void separar_cozinhas(char *s, Restaurante *r) {
    char temp[300];
    strcpy(temp, s);

    char *token = strtok(temp, ";");
    r->quantidade_cozinhas = 0;

    while (token != NULL) {
        strcpy(r->cozinhas[r->quantidade_cozinhas], token);
        r->quantidade_cozinhas++;
        token = strtok(NULL, ";");
    }
}

Restaurante parse_restaurante(char *linha) {
    Restaurante r;

    char tipos[300];
    char preco[20];
    char horario[50];
    char data[50];
    char aberto[20];

    sscanf(linha,
           "%d,%[^,],%[^,],%d,%lf,%[^,],%[^,],%[^,],%[^,],%s",
           &r.id,
           r.nome,
           r.cidade,
           &r.capacidade,
           &r.avaliacao,
           tipos,
           preco,
           horario,
           data,
           aberto);

    separar_cozinhas(tipos, &r);

    r.preco = converter_preco(preco);

    separar_horario(horario, &r.hora_abertura, &r.hora_fechamento);

    r.data_abertura = parse_data(data);

    r.aberto = (strcmp(aberto, "true") == 0);

    return r;
}

void formatar_restaurante(Restaurante *r, char *buffer) {
    char preco[10];
    char cozinhas[500] = "[";
    char data[20];

    formatar_preco(r->preco, preco);

    sprintf(data, "%02d/%02d/%04d",
            r->data_abertura.dia,
            r->data_abertura.mes,
            r->data_abertura.ano);

    for (int i = 0; i < r->quantidade_cozinhas; i++) {
        strcat(cozinhas, r->cozinhas[i]);

        if (i < r->quantidade_cozinhas - 1) {
            strcat(cozinhas, ",");
        }
    }

    strcat(cozinhas, "]");

    sprintf(buffer,
            "[%d ## %s ## %s ## %d ## %.1lf ## %s ## %s ## %02d:%02d-%02d:%02d ## %s ## %s]",
            r->id,
            r->nome,
            r->cidade,
            r->capacidade,
            r->avaliacao,
            cozinhas,
            preco,
            r->hora_abertura.hora,
            r->hora_abertura.minuto,
            r->hora_fechamento.hora,
            r->hora_fechamento.minuto,
            data,
            r->aberto ? "true" : "false");
}

void ler_csv(ColecaoRestaurantes *c) {
    FILE *f = fopen("/tmp/restaurantes.csv", "r");

    if (f == NULL) {
        f = fopen("/tmp/RESTAURANTES.CSV", "r");
    }

    char linha[1000];

    fgets(linha, 1000, f);

    c->tamanho = 0;

    while (fgets(linha, 1000, f) != NULL) {
        linha[strcspn(linha, "\r\n")] = '\0';

        c->restaurantes[c->tamanho] = parse_restaurante(linha);
        c->tamanho++;
    }

    fclose(f);
}

Restaurante *buscar_por_id(ColecaoRestaurantes *c, int id) {
    Restaurante *resp = NULL;

    for (int i = 0; i < c->tamanho; i++) {
        if (c->restaurantes[i].id == id) {
            resp = &c->restaurantes[i];
        }
    }

    return resp;
}

Celula *nova_celula(Restaurante x) {
    Celula *nova = (Celula *) malloc(sizeof(Celula));

    nova->elemento = x;
    nova->prox = NULL;

    return nova;
}

void iniciar_lista(Lista *lista) {
    lista->primeiro = nova_celula((Restaurante){0});
    lista->ultimo = lista->primeiro;
}

void inserir_inicio(Lista *lista, Restaurante x) {
    Celula *tmp = nova_celula(x);

    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;

    if (lista->primeiro == lista->ultimo) {
        lista->ultimo = tmp;
    }
}

void inserir_fim(Lista *lista, Restaurante x) {
    lista->ultimo->prox = nova_celula(x);
    lista->ultimo = lista->ultimo->prox;
}

void inserir(Lista *lista, Restaurante x, int pos) {
    Celula *i = lista->primeiro;

    for (int j = 0; j < pos; j++) {
        i = i->prox;
    }

    Celula *tmp = nova_celula(x);

    tmp->prox = i->prox;
    i->prox = tmp;
}

Restaurante remover_inicio(Lista *lista) {
    Celula *tmp = lista->primeiro->prox;

    Restaurante resp = tmp->elemento;

    lista->primeiro->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = lista->primeiro;
    }

    free(tmp);

    return resp;
}

Restaurante remover_fim(Lista *lista) {
    Celula *i = lista->primeiro;

    while (i->prox != lista->ultimo) {
        i = i->prox;
    }

    Restaurante resp = lista->ultimo->elemento;

    free(lista->ultimo);

    lista->ultimo = i;
    i->prox = NULL;

    return resp;
}

Restaurante remover(Lista *lista, int pos) {
    Celula *i = lista->primeiro;

    for (int j = 0; j < pos; j++) {
        i = i->prox;
    }

    Celula *tmp = i->prox;

    Restaurante resp = tmp->elemento;

    i->prox = tmp->prox;

    if (tmp == lista->ultimo) {
        lista->ultimo = i;
    }

    free(tmp);

    return resp;
}

void mostrar(Lista *lista) {
    Celula *i = lista->primeiro->prox;
    char buffer[1000];

    while (i != NULL) {
        formatar_restaurante(&i->elemento, buffer);
        printf("%s\n", buffer);
        i = i->prox;
    }
}

int main() {
    ColecaoRestaurantes colecao;
    Lista lista;

    ler_csv(&colecao);
    iniciar_lista(&lista);

    char entrada[100];

    scanf("%s", entrada);

    while (strcmp(entrada, "FIM") != 0) {
        int id = atoi(entrada);

        Restaurante *r = buscar_por_id(&colecao, id);

        if (r != NULL) {
            inserir_fim(&lista, *r);
        }

        scanf("%s", entrada);
    }

    int n;
    scanf("%d", &n);

    for (int i = 0; i < n; i++) {
        char comando[10];

        scanf("%s", comando);

        if (strcmp(comando, "II") == 0) {
            int id;
            scanf("%d", &id);

            Restaurante *r = buscar_por_id(&colecao, id);

            inserir_inicio(&lista, *r);

        } else if (strcmp(comando, "IF") == 0) {
            int id;
            scanf("%d", &id);

            Restaurante *r = buscar_por_id(&colecao, id);

            inserir_fim(&lista, *r);

        } else if (strcmp(comando, "I*") == 0) {
            int pos, id;

            scanf("%d %d", &pos, &id);

            Restaurante *r = buscar_por_id(&colecao, id);

            inserir(&lista, *r, pos);

        } else if (strcmp(comando, "RI") == 0) {
            Restaurante r = remover_inicio(&lista);
            printf("(R)%s\n", r.nome);

        } else if (strcmp(comando, "RF") == 0) {
            Restaurante r = remover_fim(&lista);
            printf("(R)%s\n", r.nome);

        } else if (strcmp(comando, "R*") == 0) {
            int pos;

            scanf("%d", &pos);

            Restaurante r = remover(&lista, pos);

            printf("(R)%s\n", r.nome);
        }
    }

    mostrar(&lista);

    return 0;
}
