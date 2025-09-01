import java.util.Scanner;
import java.util.Random;


    class Aleatorio{
	    public static String alteraString (String word,char letra1,char letra2){

		char[] word2 = new char[word.length()];

		for (int i = 0; i < word.length(); i++)
		{

			if (word.charAt(i) == letra1)
			{
				word2[i] = letra2;
			}
			else
			{
				word2[i] = word.charAt(i);
			}
		}

		return new String(word2);

	}

	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		Random rd = new Random();

		rd.setSeed(4);

		String word;
		
		do
		{
			char letra1 = (char) ('a' + (Math.abs(rd.nextInt())) % 26);
			char letra2 = (char) ('a' + (Math.abs(rd.nextInt())) % 26);
		
			word = sc.nextLine();
			
			if (!word.equals("FIM"))
				System.out.println(alteraString(word,letra1,letra2));
		}
		while(!word.equals("FIM"));

		sc.close();
	}
}