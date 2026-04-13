#include <stdio.h>

int tamanho(char s[]) {
    int i = 0;
    while (s[i] != '\0' && s[i] != '\n') {
        i++;
    }
    return i;
}

int maiorSubstring(char s[]) {
    int max = 0;
    int n = tamanho(s);

    int i = 0;

    while (i < n) {

        int usado[256] = {0};
        int j = i;
        int tamAtual = 0;

        while (j < n && usado[(int)s[j]] == 0) {
            usado[(int)s[j]] = 1;
            tamAtual++;
            j++;
        }

        if (tamAtual > max) {
            max = tamAtual;
        }

        i++;
    }

    return max;
}

int main() {
    char s[1000];

    fgets(s, 1000, stdin);

    while (!(s[0]=='F' && s[1]=='I' && s[2]=='M')) {

        printf("%d\n", maiorSubstring(s));

        fgets(s, 1000, stdin);
    }

    return 0;
}