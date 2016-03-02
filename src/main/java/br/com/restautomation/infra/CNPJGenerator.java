package br.com.restautomation.infra;

public class CNPJGenerator {

	public String generate() {
		int n1 = Math.abs((int) Math.floor(Math.random() * 10));
		int n2 = Math.abs((int) Math.floor(Math.random() * 10));
		int n3 = Math.abs((int) Math.floor(Math.random() * 10));
		int n4 = Math.abs((int) Math.floor(Math.random() * 10));
		int n5 = Math.abs((int) Math.floor(Math.random() * 10));
		int n6 = Math.abs((int) Math.floor(Math.random() * 10));
		int n7 = Math.abs((int) Math.floor(Math.random() * 10));
		int n8 = Math.abs((int) Math.floor(Math.random() * 10));
		int n9 = 0;
		int n10 = 0;
		int n11 = 0;
		int n12 = 1;

		int d1 = (n12 * 2) + (n11 * 3) + (n10 * 4) + (n9 * 5) + (n8 * 6) + (n7 * 7) + (n6 * 8) + (n5 * 9) + (n4 * 2) + (n3 * 3) + (n2 * 4)
				+ (n1 * 5);
		d1 = (11 - (d1 % 11));
		if (d1 >= 10) {
			d1 = 0;
		}
		int d2 = (d1 * 2) + (n12 * 3) + (n11 * 4) + (n10 * 5) + (n9 * 6) + (n8 * 7) + (n7 * 8) + (n6 * 9) + (n5 * 2) + (n4 * 3) + (n3 * 4)
				+ (n2 * 5) + (n1 * 6);
		d2 = (11 - (d2 % 11));
		if (d2 >= 10) {
			d2 = 0;
		}

		String cnpj = Integer.toString(n1) + Integer.toString(n2) + Integer.toString(n3) + Integer.toString(n4) + Integer.toString(n5)
				+ Integer.toString(n6) + Integer.toString(n7) + Integer.toString(n8) + Integer.toString(n9) + Integer.toString(n10)
				+ Integer.toString(n11) + Integer.toString(n12) + Integer.toString(d1) + Integer.toString(d2);

		return cnpj;
	}
}
