#include <stdio.h>

int isFim(char s[]) {
    int resp = 0;

    if (s[0] == 'F' && s[1] == 'I' && s[2] == 'M' && s[3] == '\0') {
        resp = 1;
    }

    return resp;
}

int isVogal(char c) {
    int resp = 0;

    if (c=='A'||c=='E'||c=='I'||c=='O'||c=='U'||
        c=='a'||c=='e'||c=='i'||c=='o'||c=='u') {
        resp = 1;
    }

    return resp;
}

int isLetra(char c) {
    int resp = 0;

    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
        resp = 1;
    }

    return resp;
}

int soVogaisRec(char s[], int i) {
    int resp = 1;

    if (s[i] != '\0') {
        if (!isVogal(s[i])) {
            resp = 0;
        } else {
            resp = soVogaisRec(s, i + 1);
        }
    }

    return resp;
}

int soConsoantesRec(char s[], int i) {
    int resp = 1;

    if (s[i] != '\0') {
        if (!isLetra(s[i]) || isVogal(s[i])) {
            resp = 0;
        } else {
            resp = soConsoantesRec(s, i + 1);
        }
    }

    return resp;
}

int isInteiroRec(char s[], int i) {
    int resp = 1;

    if (s[i] != '\0') {
        if (s[i] < '0' || s[i] > '9') {
            resp = 0;
        } else {
            resp = isInteiroRec(s, i + 1);
        }
    }

    return resp;
}

int isRealRec(char s[], int i, int separadores) {
    int resp = 1;

    if (s[i] != '\0') {
        if (s[i] == '.' || s[i] == ',') {
            separadores++;
            if (separadores > 1) {
                resp = 0;
            } else {
                resp = isRealRec(s, i + 1, separadores);
            }
        } else if (s[i] < '0' || s[i] > '9') {
            resp = 0;
        } else {
            resp = isRealRec(s, i + 1, separadores);
        }
    }

    return resp;
}

void printResp(int x) {
    if (x) {
        printf("SIM");
    } else {
        printf("NAO");
    }
}

int main() {
    char s[1000];

    fgets(s, 1000, stdin);

    while (!isFim(s)) {

        printResp(soVogaisRec(s, 0)); printf(" ");
        printResp(soConsoantesRec(s, 0)); printf(" ");
        printResp(isInteiroRec(s, 0)); printf(" ");
        printResp(isRealRec(s, 0, 0)); printf("\n");

        fgets(s, 1000, stdin);
    }

    return 0;
}