#include <stdio.h>
#include <stdlib.h>

void somar(int numero, int soma){
    if(numero == 0){
    printf("%i\n", soma);
    } else {
        int digito = numero % 10;
        soma += digito;
        numero /= 10;
        somar(numero, soma);
    }
    
}

int main(){
    int numero = 0;
    int soma = 0;
    while(scanf("%i", &numero) != EOF){
        somar(numero, soma);
    }  
    return 0;
}