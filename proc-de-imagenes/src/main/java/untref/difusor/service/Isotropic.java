package untref.difusor.service;

public class Isotropic {
	public float valueDiffusion(int grey,float northDerivative, float southDerivative, float eastDerivative,
			float westDerivative) {

		float lambda = 0.25f;
		float value = grey + lambda * (northDerivative + southDerivative + eastDerivative + westDerivative);
		return value;
	}

}
