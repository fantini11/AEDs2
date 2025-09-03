#include <stdio.h>
#include <stdlib.h>
#include <string.h> 


void somaDigitos(int numero) {
    int soma = 0;
    int digito;
    

    while (numero > 0) {
        digito = numero % 10;
        soma += digito;
        numero /= 10;
    }
    
    printf("%i\n", soma);
}

int main() {
    char entrada[50];

    scanf("%s", entrada);

    while (strcmp(entrada, "FIM") != 0) {
        
        int numero = atoi(entrada);

        somaDigitos(numero);

        scanf("%s", entrada);
    }

    return 0;
}