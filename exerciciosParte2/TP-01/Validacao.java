import java.util.Scanner;

public class Validacao {

    public static boolean isFim(String s) {
        boolean resp = false;

        if (s.length() == 3 &&
            s.charAt(0) == 'F' &&
            s.charAt(1) == 'I' &&
            s.charAt(2) == 'M') {
            resp = true;
        }

        return resp;
    }

    public static boolean isMaiuscula(char c) {
        boolean resp = false;

        if (c >= 'A' && c <= 'Z') {
            resp = true;
        }

        return resp;
    }

    public static boolean isMinuscula(char c) {
        boolean resp = false;

        if (c >= 'a' && c <= 'z') {
            resp = true;
        }

        return resp;
    }

    public static boolean isNumero(char c) {
        boolean resp = false;

        if (c >= '0' && c <= '9') {
            resp = true;
        }

        return resp;
    }

    public static boolean isEspecial(char c) {
        boolean resp = true;

        if (isMaiuscula(c) || isMinuscula(c) || isNumero(c)) {
            resp = false;
        }

        return resp;
    }

    public static boolean senhaValida(String s) {
        boolean resp = true;
        boolean temMaiuscula = false;
        boolean temMinuscula = false;
        boolean temNumero = false;
        boolean temEspecial = false;

        if (s.length() < 8) {
            resp = false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (isMaiuscula(c)) {
                temMaiuscula = true;
            } else if (isMinuscula(c)) {
                temMinuscula = true;
            } else if (isNumero(c)) {
                temNumero = true;
            } else {
                temEspecial = true;
            }
        }

        if (!temMaiuscula || !temMinuscula || !temNumero || !temEspecial) {
            resp = false;
        }

        return resp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        while (!isFim(s)) {

            if (senhaValida(s)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }

            s = sc.nextLine();
        }

        sc.close();
    }
}