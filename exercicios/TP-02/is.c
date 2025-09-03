#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

    bool vogais(char string[]){
        bool ehVogal = false;
        int tam = strlen(string);
        int aux = 0;

        for(int i = 0; i < tam; i++){
            if(tolower(string[i]) == "a" || string[i] == "e" || string[i] == "i" || string[i] == "o" || string[i] == "u"){
            aux ++;
            }
        }
        
        if(aux == tam) ehVogal = true;

        return ehVogal;
    }

    bool consoantes(char string[]){
        bool ehConsoante = false;
        int tam = strlen(string);
        int aux = 0;

        for(int i = 0; i < tam; i++){
            if(tolower(string[i]) != "a" || string[i] != "e" || string[i] != "i" || string[i] != "o" || string[i] != "u"){
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

    
