package br.com.restautomation.infra;

public class CPFGenerator {

	public String generate() {

		int n1 = Math.abs((int) Math.floor(Math.random() * 10));
		int n2 = Math.abs((int) Math.floor(Math.random() * 10));
		int n3 = Math.abs((int) Math.floor(Math.random() * 10));
		int n4 = Math.abs((int) Math.floor(Math.random() * 10));
		int n5 = Math.abs((int) Math.floor(Math.random() * 10));
		int n6 = Math.abs((int) Math.floor(Math.random() * 10));
		int n7 = Math.abs((int) Math.floor(Math.random() * 10));
		int n8 = Math.abs((int) Math.floor(Math.random() * 10));
		int n9 = Math.abs((int) Math.floor(Math.random() * 10));

		int d1 = (n1 * 10) + (n2 * 9) + (n3 * 8) + (n4 * 7) + (n5 * 6) + (n6 * 5) + (n7 * 4) + (n8 * 3) + (n9 * 2);
		d1 = (11 - (d1 % 11));
		if (d1 >= 10) {
			d1 = 0;
		}
		int d2 = (n1 * 11) + (n2 * 10) + (n3 * 9) + (n4 * 8) + (n5 * 7) + (n6 * 6) + (n7 * 5) + (n8 * 4) + (n9 * 3) + (d1 * 2);
		d2 = (11 - (d2 % 11));
		if (d2 >= 10) {
			d2 = 0;
		}

		String cpf = Integer.toString(n1) + Integer.toString(n2) + Integer.toString(n3) + Integer.toString(n4) + Integer.toString(n5)
				+ Integer.toString(n6) + Integer.toString(n7) + Integer.toString(n8) + Integer.toString(n9) + Integer.toString(d1)
				+ Integer.toString(d2);
		return cpf;
	}
}
