#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>

    bool vogais(char string[], int tam, int aux, int i){
    if(aux == tam){ 
        return true; // chegou ao final sem falhar -> só vogais
    }

    if(tolower(string[i]) == 'a' || tolower(string[i]) == 'e' || 
       tolower(string[i]) == 'i' || tolower(string[i]) == 'o' || 
       tolower(string[i]) == 'u'){
        // continua verificando
        return vogais(string, tam, aux + 1, i + 1);
    } else {
        // achou consoante
        return false;
    }
}

    bool consoantes(char string[]){
        bool ehConsoante = false;
        int tam = strlen(string);
        int aux = 0;

        for(int i = 0; i < tam; i++){
            if(tolower(string[i]) != 'a' && tolower(string[i]) != 'e' && tolower(string[i]) != 'i' && tolower(string[i]) != 'o' && tolower(string[i]) != 'u' && !(string[i] >= 48 && string[i] <= 57)){
            aux ++;
            }
        }

        if(aux == tam) ehConsoante = true;

        return ehConsoante;
    }

    bool inteiro(char string[]){
        bool ehInteiro = false;
        int tam = strlen(string);
        int aux = 0;

        for(int i = 0; i < tam; i++){
            if(string[i] >= 48 && string[i] <= 57){
            aux ++;
            }
        }

        if(aux == tam) ehInteiro = true;

        return ehInteiro;
    }

    bool real(char string[]){
        bool ehReal = false;
        int tam = strlen(string);
        int aux = 0;
        int auxDec = 0;

        for(int i = 0; i < tam; i++){
            if(string[i] >= 48 && string[i] <= 57 || string[i] == 44 || string[i] == 46){
                if(string[i] == 44 || string[i] == 46){
                    auxDec ++;
                }
                aux ++;
            }
        }

        if(aux == tam && auxDec == 1) ehReal = true;

        return ehReal;
    }

    void verifica(char string[], int tam, int aux, int auxDec, int i) {
    bool ehVogal = vogais(string, tam, aux, i);
    bool ehConsoante = consoantes(string);
    bool ehInteiro = inteiro(string);
    bool ehReal = real(string);

    if (ehVogal) printf("SIM ");
    else printf("NAO ");

    if (ehConsoante) printf("SIM ");
    else printf("NAO ");

    if (ehInteiro) printf("SIM ");
    else printf("NAO ");
    
    if (ehReal) printf("SIM\n");
    else printf("NAO\n");
}

    int main() {
    char string[1000];
    int aux = 0;
    int auxDec = 0;
    int tam = strlen(string);
    int i = 0;
    
    for (;;) {
        fgets(string, sizeof(string), stdin);

        string[strcspn(string, "\n")] = '\0';

        if (strcmp(string, "FIM") == 0) {
            break;
        }

        verifica(string, tam, aux, auxDec, i);
    }

    return 0;
}