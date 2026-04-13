import java.util.Scanner;

public class Is{

    public static boolean isFim(String s) {
        return (s.length() == 3 &&
                s.charAt(0) == 'F' &&
                s.charAt(1) == 'I' &&
                s.charAt(2) == 'M');
    }

    public static boolean isVogal(char c) {
        return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U' ||
                c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u');
    }

    public static boolean isLetra(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'));
    }

    public static boolean soVogais(String s) {
        boolean resp = true;

        if (s.length() == 0) {
            resp = false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (!isVogal(s.charAt(i))) {
                resp = false;
            }
        }

        return resp;
    }

    public static boolean soConsoantes(String s) {
        boolean resp = true;

        if (s.length() == 0) {
            resp = false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (!isLetra(c) || isVogal(c)) {
                resp = false;
            }
        }

        return resp;
    }

    public static boolean isInteiro(String s) {
        boolean resp = true;

        if (s.length() == 0) {
            resp = false;
        }

        int inicio = 0;

        if (s.length() > 0 && (s.charAt(0) == '-' || s.charAt(0) == '+')) {
            inicio = 1;
            if (s.length() == 1) {
                resp = false;
            }
        }

        for (int i = inicio; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c < '0' || c > '9') {
                resp = false;
            }
        }

        return resp;
    }

    public static boolean isReal(String s) {
        boolean resp = true;

        if (s.length() == 0) {
            resp = false;
        }

        int inicio = 0;
        int separadores = 0;

        if (s.length() > 0 && (s.charAt(0) == '-' || s.charAt(0) == '+')) {
            inicio = 1;
            if (s.length() == 1) {
                resp = false;
            }
        }

        for (int i = inicio; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '.' || c == ',') {
                separadores++;
                if (separadores > 1) {
                    resp = false;
                }
            } else if (c < '0' || c > '9') {
                resp = false;
            }
        }

        return resp;
    }

    public static String resp(boolean x) {
        String r = "NAO";
        if (x) {
            r = "SIM";
        }
        return r;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        while (!isFim(s)) {

            System.out.println(
                resp(soVogais(s)) + " " +
                resp(soConsoantes(s)) + " " +
                resp(isInteiro(s)) + " " +
                resp(isReal(s))
            );

            s = sc.nextLine();
        }

        sc.close();
    }
}