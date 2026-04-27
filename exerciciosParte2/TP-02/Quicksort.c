#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MATRICULA "885173"

typedef struct Restaurante {
    int id;
    char *nome;
    char *cidade;
    int capacidade;
    double avaliacao;
} Restaurante;

int tamanho_string(char *s){
    int i=0;
    while(s[i]!='\0') i++;
    return i;
}

char* copiar_string(char *s){
    int tam = tamanho_string(s);
    char *resp = (char*) malloc((tam+1)*sizeof(char));
    int i=0;
    while(i<tam){
        resp[i]=s[i];
        i++;
    }
    resp[i]='\0';
    return resp;
}

void remover_enter(char *s){
    int i=0;
    while(s[i]!='\0'){
        if(s[i]=='\n' || s[i]=='\r') s[i]='\0';
        i++;
    }
}

void separar_campos(char *linha, char campos[][200]){
    int i=0,j=0,c=0;
    while(linha[i]!='\0'){
        if(linha[i]==','){
            campos[c][j]='\0';
            c++; j=0;
        }else{
            campos[c][j++]=linha[i];
        }
        i++;
    }
    campos[c][j]='\0';
}

Restaurante* parse(char *linha){
    char campos[10][200];
    separar_campos(linha, campos);

    Restaurante *r = (Restaurante*) malloc(sizeof(Restaurante));
    r->id = atoi(campos[0]);
    r->nome = copiar_string(campos[1]);
    r->cidade = copiar_string(campos[2]);
    r->capacidade = atoi(campos[3]);
    r->avaliacao = atof(campos[4]);

    return r;
}

int comparar(Restaurante *a, Restaurante *b, long *comp){
    int resp=0;
    (*comp)++;
    if(a->avaliacao < b->avaliacao) resp = -1;
    else if(a->avaliacao > b->avaliacao) resp = 1;
    else{
        (*comp)++;
        resp = strcmp(a->nome,b->nome);
    }
    return resp;
}

void swap(Restaurante **a, Restaurante **b, long *mov){
    Restaurante *tmp = *a;
    *a = *b;
    *b = tmp;
    *mov += 3;
}

void quicksort(Restaurante **arr, int esq, int dir, long *comp, long *mov){
    int i = esq, j = dir;
    Restaurante *pivo = arr[(esq+dir)/2];

    while(i <= j){
        while(comparar(arr[i], pivo, comp) < 0) i++;
        while(comparar(arr[j], pivo, comp) > 0) j--;

        if(i <= j){
            swap(&arr[i], &arr[j], mov);
            i++; j--;
        }
    }

    if(esq < j) quicksort(arr, esq, j, comp, mov);
    if(i < dir) quicksort(arr, i, dir, comp, mov);
}

void escrever_log(long comp, long mov, double tempo){
    FILE *f = fopen(MATRICULA "_quicksort.txt","w");
    if(f){
        fprintf(f,"%s\t%ld\t%ld\t%lf",MATRICULA,comp,mov,tempo);
        fclose(f);
    }
}

int main(){
    FILE *arq = fopen("/tmp/restaurantes.csv","r");
    if(arq == NULL) arq = fopen("/tmp/RESTAURANTES.CSV","r");

    Restaurante *todos[2000];
    int nTotal=0;
    char linha[1000];

    fgets(linha,1000,arq);

    while(fgets(linha,1000,arq)){
        remover_enter(linha);
        todos[nTotal++] = parse(linha);
    }
    fclose(arq);

    Restaurante *sel[1000];
    int n=0, id;

    scanf("%d",&id);
    while(id!=-1){
        for(int i=0;i<nTotal;i++){
            if(todos[i]->id == id){
                sel[n++] = todos[i];
            }
        }
        scanf("%d",&id);
    }

    long comp=0, mov=0;
    clock_t ini = clock();

    quicksort(sel,0,n-1,&comp,&mov);

    clock_t fim = clock();

    for(int i=0;i<n;i++){
        printf("%d %s %.1f\n", sel[i]->id, sel[i]->nome, sel[i]->avaliacao);
    }

    double tempo = (double)(fim-ini)/CLOCKS_PER_SEC;
    escrever_log(comp,mov,tempo);

    return 0;
}
