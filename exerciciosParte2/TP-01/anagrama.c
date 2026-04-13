#include <stdio.h>
#include <stdlib.h>

void removerEnter(char s[]) {
    int i = 0;

    while (s[i] != '\0') {
        if (s[i] == '\n') {
            s[i] = '\0';
        }
        i++;
    }
}

int isFim(char s[]) {
    int resp = 0;

    if (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0') {
        resp = 1;
    }

    return resp;
}

int tamanho(char s[]) {
    int i = 0;

    while (s[i] != '\0') {
        i++;
    }

    return i;
}

int ehAnagrama(char a[], char b[]) {
    int resp = 1;
    int cont[256];
    int i = 0;

    while (i < 256) {
        cont[i] = 0;
        i++;
    }

    if (tamanho(a) != tamanho(b)) {
        resp = 0;
    }

    i = 0;
    while (a[i] != '\0') {
        cont[(unsigned char)a[i]]++;
        i++;
    }

    i = 0;
    while (b[i] != '\0') {
        cont[(unsigned char)b[i]]--;
        i++;
    }

    i = 0;
    while (i < 256) {
        if (cont[i] != 0) {
            resp = 0;
        }
        i++;
    }

    return resp;
}

void imprimirResposta(int x) {
    if (x == 1) {
        printf("SIM\n");
    } else {
        printf("NAO\n");
    }
}

int main() {
    char s1[1000];
    char s2[1000];
    int continuar = 1;

    while (continuar == 1 && fgets(s1, 1000, stdin) != NULL) {
        removerEnter(s1);

        if (isFim(s1) == 1) {
            continuar = 0;
        } else {
            if (fgets(s2, 1000, stdin) != NULL) {
                removerEnter(s2);
                imprimirResposta(ehAnagrama(s1, s2));
            } else {
                continuar = 0;
            }
        }
    }

    return 0;
}