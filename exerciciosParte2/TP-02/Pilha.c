#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct Restaurante {
    int id;
    char *nome;
} Restaurante;

typedef struct Pilha {
    Restaurante *array[1000];
    int topo;
} Pilha;

char* copiar_string(char *s){
    char *r = (char*) malloc(strlen(s)+1);
    strcpy(r,s);
    return r;
}

Restaurante* novo_restaurante(int id, char *nome){
    Restaurante *r = (Restaurante*) malloc(sizeof(Restaurante));
    r->id = id;
    r->nome = copiar_string(nome);
    return r;
}

void inicializar(Pilha *p){
    p->topo = -1;
}

void empilhar(Pilha *p, Restaurante *r){
    p->topo++;
    p->array[p->topo] = r;
}

Restaurante* desempilhar(Pilha *p){
    Restaurante *r = p->array[p->topo];
    p->topo--;
    return r;
}

void mostrar(Pilha *p){
    for(int i = p->topo; i >= 0; i--){
        printf("[%d] %s\n", p->array[i]->id, p->array[i]->nome);
    }
}

int main(){
    Pilha p;
    inicializar(&p);

    int n;
    scanf("%d", &n);

    for(int i=0;i<n;i++){
        char op[3];
        scanf("%s", op);

        if(strcmp(op,"I")==0){
            int id;
            char nome[100];
            scanf("%d %s", &id, nome);
            empilhar(&p, novo_restaurante(id,nome));
        } else if(strcmp(op,"R")==0){
            Restaurante *r = desempilhar(&p);
            printf("(R) %s\n", r->nome);
        }
    }

    mostrar(&p);

    return 0;
}
