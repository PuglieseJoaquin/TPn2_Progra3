package presenter;
 
/** Convierte un peso a texto para mostrarlo: 5.0 se ve como "5", 2.5 se ve como "2.5". */
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