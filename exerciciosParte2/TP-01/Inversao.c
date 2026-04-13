#include <stdio.h>
#include <stdlib.h>

void inverter(char str[], int tam){
    for(int i = 0; i < tam/2; i++){
        char temp = str[i];
        str[i] = str[tam - i - 1];
        str[tam - i - 1] = temp;
    }
    printf("%s\n", str);
}


int main(){
    char str[100];
    scanf("%s", str);  
    while(str[0] != 'F' || str[1] != 'I' || str[2] != 'M'){ 
    int tam = 0;

    for(int i = 0; str[i] != '\0'; i++){
        tam++;
    }

    inverter(str, tam);
    scanf("%s", str);
}
     return 0;
}