import java.util.Arrays;

class anagrama{
    public static boolean ehAnagrama(String palavra1, String palavra2){
        boolean isAnagrama = false;

        char[] array1 = palavra1.toLowerCase().toCharArray();
        char[] array2 = palavra2.toLowerCase().toCharArray();

        Arrays.sort(array1);
        Arrays.sort(array2);

        if(Arrays.equals(array1, array2)) isAnagrama = true;

        return isAnagrama;
    }

    public static void main(String args[]){
        String palavra1 = MyIO.readString();
        
        while(!palavra1.equals("FIM")){
        String palavra2 = MyIO.readString();

        if(ehAnagrama(palavra1, palavra2)) MyIO.println("SIM");
        else MyIO.println("NÃO");

        palavra1 = MyIO.readString();
        }
    }
}