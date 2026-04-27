#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MATRICULA "885173"

typedef struct Data { int ano, mes, dia; } Data;
typedef struct Hora { int hora, minuto; } Hora;

typedef struct Restaurante {
    int id;
    char *nome;
    char *cidade;
    int capacidade;          // chave do counting sort
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

int tamanho_string(char *s){ int i=0; while(s[i]!='\0') i++; return i; }

char* copiar_string(char *s){
    int tam=tamanho_string(s);
    char *r=(char*)malloc((tam+1)*sizeof(char));
    for(int i=0;i<tam;i++) r[i]=s[i];
    r[tam]='\0';
    return r;
}

void remover_enter(char *s){
    for(int i=0;s[i]!='\0';i++){
        if(s[i]=='\n'||s[i]=='\r') s[i]='\0';
    }
}

Data parse_data(char *s){ Data d; sscanf(s,"%d-%d-%d",&d.ano,&d.mes,&d.dia); return d; }
Hora parse_hora(char *s){ Hora h; sscanf(s,"%d:%d",&h.hora,&h.minuto); return h; }

void separar_horario(char *s, Hora *ab, Hora *fe){
    char a[20], b[20]; int i=0,j=0;
    while(s[i]!='-'&&s[i]!='\0') a[j++]=s[i++];
    a[j]='\0'; if(s[i]=='-') i++; j=0;
    while(s[i]!='\0') b[j++]=s[i++]; b[j]='\0';
    *ab=parse_hora(a); *fe=parse_hora(b);
}

int converter_faixa_preco(char *s){
    int c=0; for(int i=0;s[i]!='\0';i++) if(s[i]=='$') c++; return c;
}

int separar_tipos(char *s, char **tipos){
    int qtd=0,i=0,j=0; char atual[200];
    while(s[i]!='\0'){
        if(s[i]==';'){ atual[j]='\0'; tipos[qtd++]=copiar_string(atual); j=0; }
        else atual[j++]=s[i];
        i++;
    }
    atual[j]='\0'; tipos[qtd++]=copiar_string(atual);
    return qtd;
}

void separar_campos(char *linha, char campos[][300], int *qtd){
    int i=0,c=0,p=0;
    while(linha[i]!='\0'){
        if(linha[i]==','){ campos[c][p]='\0'; c++; p=0; }
        else campos[c][p++]=linha[i];
        i++;
    }
    campos[c][p]='\0'; *qtd=c+1;
}

Restaurante* parse_restaurante(char *s){
    Restaurante *r=(Restaurante*)malloc(sizeof(Restaurante));
    char campos[20][300]; int qtd=0;
    separar_campos(s,campos,&qtd);

    r->id=0; r->nome=copiar_string(""); r->cidade=copiar_string("");
    r->capacidade=0; r->avaliacao=0.0;
    r->tipos_cozinha=(char**)malloc(30*sizeof(char*));
    r->n_tipos_cozinha=0; r->faixa_preco=0; r->aberto=0;

    if(qtd>=10){
        sscanf(campos[0],"%d",&r->id);
        r->nome=copiar_string(campos[1]);
        r->cidade=copiar_string(campos[2]);
        sscanf(campos[3],"%d",&r->capacidade);
        sscanf(campos[4],"%lf",&r->avaliacao);
        r->n_tipos_cozinha=separar_tipos(campos[5],r->tipos_cozinha);
        r->faixa_preco=converter_faixa_preco(campos[6]);
        separar_horario(campos[7],&r->horario_abertura,&r->horario_fechamento);
        r->data_abertura=parse_data(campos[8]);
        if(strcmp(campos[9],"true")==0) r->aberto=1;
    }
    return r;
}

void formatar_data(Data *d, char *buf){ sprintf(buf,"%02d/%02d/%04d",d->dia,d->mes,d->ano); }
void formatar_hora(Hora *h, char *buf){ sprintf(buf,"%02d:%02d",h->hora,h->minuto); }

void formatar_preco(int p, char *buf){ int i=0; while(i<p) buf[i++]='$'; buf[i]='\0'; }

void formatar_restaurante(Restaurante *r, char *buf){
    char data[20], ab[20], fe[20], preco[10], tipos[500], av[30];
    formatar_data(&r->data_abertura,data);
    formatar_hora(&r->horario_abertura,ab);
    formatar_hora(&r->horario_fechamento,fe);
    formatar_preco(r->faixa_preco,preco);
    sprintf(av,"%.1f",r->avaliacao);

    tipos[0]='['; tipos[1]='\0';
    for(int i=0;i<r->n_tipos_cozinha;i++){
        strcat(tipos,r->tipos_cozinha[i]);
        if(i<r->n_tipos_cozinha-1) strcat(tipos,",");
    }
    strcat(tipos,"]");

    sprintf(buf,"[%d ## %s ## %s ## %d ## %s ## %s ## %s ## %s-%s ## %s ## %s]",
            r->id,r->nome,r->cidade,r->capacidade,av,tipos,preco,ab,fe,data,
            r->aberto?"true":"false");
}

void ler_csv_colecao(Colecao_Restaurantes *c, char *path){
    FILE *f=fopen(path,"r"); char linha[1000];
    c->tamanho=0; c->restaurantes=(Restaurante**)malloc(2000*sizeof(Restaurante*));
    if(f){
        if(fgets(linha,1000,f)!=NULL){}
        while(fgets(linha,1000,f)){
            remover_enter(linha);
            if(tamanho_string(linha)>0)
                c->restaurantes[c->tamanho++]=parse_restaurante(linha);
        }
        fclose(f);
    }
}

Colecao_Restaurantes* ler_csv(){
    Colecao_Restaurantes *c=(Colecao_Restaurantes*)malloc(sizeof(Colecao_Restaurantes));
    FILE *t=fopen("/tmp/restaurantes.csv","r");
    if(t){ fclose(t); ler_csv_colecao(c,"/tmp/restaurantes.csv"); }
    else ler_csv_colecao(c,"/tmp/RESTAURANTES.CSV");
    return c;
}

Restaurante* buscar_id(Colecao_Restaurantes *c,int id){
    Restaurante *r=NULL;
    for(int i=0;i<c->tamanho;i++)
        if(c->restaurantes[i]->id==id) r=c->restaurantes[i];
    return r;
}

// -------- COUNTING SORT POR CAPACIDADE --------
void counting_sort(Restaurante **arr, int n, long *mov){
    if(n<=0) return;

    int max = arr[0]->capacidade;
    for(int i=1;i<n;i++){
        if(arr[i]->capacidade > max) max = arr[i]->capacidade;
    }

    int *count = (int*) calloc(max+1, sizeof(int));
    Restaurante **out = (Restaurante**) malloc(n*sizeof(Restaurante*));

    for(int i=0;i<n;i++){
        count[arr[i]->capacidade]++;
    }

    for(int i=1;i<=max;i++){
        count[i] += count[i-1];
    }

    // estável (varre de trás)
    for(int i=n-1;i>=0;i--){
        int k = arr[i]->capacidade;
        out[count[k]-1] = arr[i];
        (*mov)++;
        count[k]--;
    }

    for(int i=0;i<n;i++){
        arr[i] = out[i];
        (*mov)++;
    }

    free(count);
    free(out);
}

void escrever_log(long comp, long mov, double tempo){
    FILE *f=fopen(MATRICULA "_countingsort.txt","w");
    if(f){
        fprintf(f,"%s\t%ld\t%ld\t%lf",MATRICULA,comp,mov,tempo);
        fclose(f);
    }
}

int main(){
    Colecao_Restaurantes *c = ler_csv();
    Restaurante *sel[1000]; int n=0, id;
    char buf[1000];

    long comp=0, mov=0;

    scanf("%d",&id);
    while(id!=-1){
        Restaurante *r = buscar_id(c,id);
        if(r) sel[n++]=r;
        scanf("%d",&id);
    }

    clock_t ini = clock();
    counting_sort(sel,n,&mov);
    clock_t fim = clock();

    double tempo = (double)(fim-ini)/CLOCKS_PER_SEC;

    for(int i=0;i<n;i++){
        formatar_restaurante(sel[i],buf);
        printf("%s\n",buf);
    }

    escrever_log(comp,mov,tempo);
    return 0;
}
