class inversaRec{
    public static void inverte(String palavra, int tam, int i){
        if(tam >= i){
        MyIO.println((char)palavra.charAt(tam));
        inverte(palavra, tam - 1, i);
    }
    }

    public static void main(String args[]){
        String palavra = MyIO.readLine();
        int i = 0;

        while(!palavra.equals("FIM")){
        int tam = palavra.length() - 1;

        inverte(palavra, tam, i);

        palavra = MyIO.readLine();
        }
    }
}