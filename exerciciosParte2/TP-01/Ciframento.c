#include <stdio.h>

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

void cifrarRec(char s[], int i) {

    if (s[i] != '\0') {
        char c = s[i] + 3;
        printf("%c", c);

        cifrarRec(s, i + 1);
    }
}

int main() {
    char s[1000];

    fgets(s, 1000, stdin);
    removerEnter(s);

    while (!isFim(s)) {

        cifrarRec(s, 0);
        printf("\n");

        fgets(s, 1000, stdin);
        removerEnter(s);
    }

    return 0;
}