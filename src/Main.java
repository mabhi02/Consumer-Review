public class Main {
	public static void main(String[] args) {
		String chad = Review.textToString("indianfood-edited.txt");
		System.out.println(chad);
		Double theChad = Review.totalSentiment(chad);
		System.out.println(theChad);
		System.out.println(Review.sentimentVal("happily"));
	}
}
