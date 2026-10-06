package presenter;
 

final class FormatoPesoArista {
 
	private FormatoPesoArista() {
	}
 
	static String aTexto(double peso) {
		if (peso == Math.rint(peso) && Math.abs(peso) < 1e15) {
			return String.valueOf((long) peso);
		}
		return String.valueOf(peso);
	}
}