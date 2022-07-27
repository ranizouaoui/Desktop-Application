package Classes;

public class CryptoUtils {
	static int key = 6;

	public static String Encrypt(String password) {
		char[] chars = password.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] += key;

		}
		String string = new String(chars);
		System.out.print(string);

		return string;
	}

	public static String Decrypt(String password) {
		char[] chars = password.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			chars[i] -= key;

		}
		String string = new String(chars);
		System.out.print(string);

		return string;
	}

//	public static void main(String[] args) {
//		String txt = "Bonjour je suis rani";
//
//		System.out.print(txt);
//		System.out.print("\n");
////		Encrypt(txt);
//		System.out.print("\n");
//		String x = Encrypt(txt);
//		Decrypt(x);
//	}

}