package br.com.restautomation.assembly;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import br.com.restautomation.infra.CNPJGenerator;
import br.com.restautomation.infra.CPFGenerator;

public enum Generator {

	CNPJ(new Generable() {

		@Override
		public String generate(String modifier) {
			return new CNPJGenerator().generate();
		}
	}),
	CPF(new Generable() {

		@Override
		public String generate(String modifier) {
			return new CPFGenerator().generate();
		}
	}),
	TIME_MILLIS(new Generable() {

		@Override
		public String generate(String modifier) {
			Date date = new Date();

			return Long.toString(date.getTime());
		}
	}),
	RANDOM_LONG(new Generable() {

		@Override
		public String generate(String modifier) {

			Random generator = new Random();

			return Long.toString(generator.nextLong());
		}
	}),
	RANDOM_UUID(new Generable() {

		@Override
		public String generate(String modifier) {

			return UUID.randomUUID().toString();
		}
	});

	private Generable generator;

	private Generator(Generable generator) {
		this.generator = generator;
	}

	public String generate(String modifier) {
		return generator.generate(modifier);
	}

	public String generate() {
		return generator.generate(null);
	}
}

interface Generable {

	String generate(String modifier);
}
