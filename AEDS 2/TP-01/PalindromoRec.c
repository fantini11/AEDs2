    #include <stdio.h>
    #include <stdlib.h>
    #include <string.h>
    #include <stdbool.h>

    
    bool ehPalindromo(char palavra[], int i, int tam){
        bool palindromo;

         if(i >= tam/2) palindromo = true;

            else if(palavra[i] != palavra[tam - 1 - i]) palindromo = false; 
            
                else palindromo = ehPalindromo(palavra, i + 1, tam);

        return palindromo;
    }
    
    int main(){
        char palavra[100];
        scanf("%c",palavra);
        boolean resultado = ehPalindromo(palavra, 0, strlen(palavra));

        if (resultado) printf("SIM\n");
            else printf("NÃO\n");

        return 0;
    }
