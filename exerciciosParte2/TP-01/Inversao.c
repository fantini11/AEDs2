#include <stdio.h>

int isFim(char s[]) {
    int resp = 0;

    if (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0') {
        resp = 1;
    }

    return resp;
}

int tamanho(char s[]) {
    int i = 0;

    while (s[i] != '\0' && s[i] != '\n') {
        i++;
    }

    return i;
}

void inverter(char s[], char resp[]) {
    int tam = tamanho(s);

    for (int i = 0; i < tam; i++) {
        resp[i] = s[tam - 1 - i];
    }

    resp[tam] = '\0';
}

int main() {
    char s[1000];
    char inv[1000];

    fgets(s, 1000, stdin);

    while (!isFim(s)) {

        inverter(s, inv);
        printf("%s\n", inv);

        fgets(s, 1000, stdin);
    }

    return 0;
}