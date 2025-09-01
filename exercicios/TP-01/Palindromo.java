class Palindromo{
    public static void ehPalindromo(String palavra){
        boolean palindromo = true;
        int tam = palavra.length();
        for(int i = 0; i < tam/2; i++){
            if(palavra.charAt(i) != palavra.charAt(tam - 1 - i))palindromo = false;
        }

        if(palindromo) MyIO.println("SIM");
        else MyIO.println("NAO");

    }

    public static void main(String[] args){
        String palavra = MyIO.readLine();
        while(!palavra.equals("FIM")){
        ehPalindromo(palavra);
        palavra = MyIO.readLine();
        }
    }
}