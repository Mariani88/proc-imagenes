package untref.difusor.service;

public class Anisotropic {

	public float valueDiffusion(int grey, float northDerivative, float southDerivative, float eastDerivative,
			float westDerivative, double sigma) {

		float Cnij = gradient(sigma, northDerivative);
		float Csij = gradient(sigma, southDerivative);
		float Ceij = gradient(sigma, eastDerivative);
		float Coij = gradient(sigma, westDerivative);

		float DnIijCnij = northDerivative * Cnij;
		float DsIijCsij = southDerivative * Csij;
		float DeIijCeij = eastDerivative * Ceij;
		float DoIijCoij = westDerivative * Coij;

		float lambda = 0.25f;
		float value = grey + lambda * (DnIijCnij + DsIijCsij + DeIijCeij + DoIijCoij);
		return value;
	}

	private float gradient(double sigma, float derivada) {
		return (float) (1 / (((float) (Math.pow(Math.abs(derivada), 2) / Math.pow(sigma, 2))) + 1));
	}

}
