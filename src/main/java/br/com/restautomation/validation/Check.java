package br.com.restautomation.validation;

public enum Check {

	PRESENT(null),
	ANY_INT(null),
	ANY_NUMBER(null),
	ANY_DATE(null),
	ANY_DATE_TIME(null),
	CONTAINS(null),
	START_WITH(null),
	END_WITH(null),
	REGEX(null),
	EQUALS(new Checkable() {

		@Override
		public String getError(String received, String expected) {

			if (received == null) {
				return "Campo não foi encontrado na resposta";
			}

			if (!expected.equals(received)) {
				return "O valor no campo não é igual ao esperado";
			}

			return null;
		}
	}),
	LESS_EQUALS(null),
	LESS(null),
	BIGGER_EQUALS(null),
	BIGGER(null);

	private Checkable checker;

	Check(Checkable checker) {
		this.checker = checker;
	}

	public String getError(String received, String expected) {
		if (checker == null) {
			return "Checker " + name() + " not implemented";
		}
		return checker.getError(received, expected);
	}
}

interface Checkable {

	String getError(String received, String expected);
}
