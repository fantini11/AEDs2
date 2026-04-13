#include <stdio.h>

int somaDigitos(int n) {
    int resp;

    if (n == 0) {
        resp = 0;
    } else {
        resp = (n % 10) + somaDigitos(n / 10);
    }

    return resp;
}

int main() {
    int n;

    while (scanf("%d", &n) == 1) {
        printf("%d\n", somaDigitos(n));
    }

    return 0;
}