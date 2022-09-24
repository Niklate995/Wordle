package logic;


public class LogicMain {

	private String[] palabras = {"CASAS", "COSAS", "NUNCA", "MUCHO", "SALIR"};
	private String palabra;
	private Integer intentos;	// 0<intentos<10
	private double tiempo, tiempoInicial = 0;
	private int estadoPartida = 0;	//0 = Sin iniciar. 1 = Jugando. 2 = Victoria. 3 = Derrota.
	
	private static int InGame = 1;
	private static int GameVictory = 2;
	private static int GameDefeat = 3;
	
	
	public LogicMain() {
		//elegirPalabra("SALIR");
		elegirPalabra();
		intentos = 7;
		tiempoInicial = 300;
		tiempo = tiempoInicial;
	}
	
	public LogicMain(Integer intentosMax, double tiempoInic) {
		elegirPalabra();
		intentos = intentosMax;
		tiempoInicial = tiempoInic;
		tiempo = tiempoInicial;
	}
		
	public void elegirPalabra(String pal) {
		palabra = pal;
	}
	public void elegirPalabra() {
		palabra = palabras[(int) Math.floor(Math.random() * palabras.length)];	//Nro random entre 0 y el largo de la entrada
	}
	
	/**
	 * Recibe una palabra y reemplaza sus caracteres por los siguientes, en funcion a su similitud con
	 * la palabra secreta: 
	 * <br> 1. Si el caracter es correcto, reemplaza por G
	 * <br> 2. Si el caracter es incorrecto pero existe en otro lado, reemplaza por Y
	 * <br> 3. Si el caracter es incorrecto y no existe en otro lado, reemplaza por B
	 * @param intento la palabra que quiere comprobarse con la palabra secreta
	 * @return una cadena compuesta de los caracteres mencionados
	 */
	public String intentarAdivinar(String intento) {
		intento = intento.toUpperCase();
		String ret = compararPalabra(intento);
		intentos--;
		actualizarEstadoJuego(intento);
		return ret;
	}

	private String compararPalabra(String intento) {
		StringBuilder ret = new StringBuilder();
		for (int i=0; palabra.length() > i; i++) {
			if (palabra.charAt(i) == intento.charAt(i)) ret.append("G");	//Green
			else {
				if (this.containsChar(intento.charAt(i), palabra)) ret.append("Y");	//Yellow
				else ret.append("B");	// Black
			}
		}
		return ret.toString();
	}
	
	private boolean containsChar(char letra, String palabra) {
		boolean aux = false;
		int i = 0;
		while (!aux && i<palabra.length()) {
			aux = letra == palabra.charAt(i);
			i++;
		}
		return aux;
	}
	
	/**
	 * 0 = Sin iniciar. 1 = Jugando. 2 = Victoria. 3 = Derrota.
	 * @return
	 */
	private void actualizarEstadoJuego(String intento) {
		if (intentos == 0) setEstadoPartida(GameDefeat);
		if (intentos > 0) setEstadoPartida(InGame);
		if (intento.compareToIgnoreCase(palabra) == 0) setEstadoPartida(GameVictory);
	}
	
	
	private void setEstadoPartida(int num) {
		if (num >=0 && num <=3) estadoPartida=num;
	}

	public Integer getIntentos() {
		return intentos;
	}

	public void setIntentos(Integer intentos) {
		if (intentos>0 && intentos <10) this.intentos = intentos;
	}
	/**
	 * 0 = Sin iniciar. 1 = Jugando. 2 = Victoria. 3 = Derrota.
	 * @return
	 */
	public int getEstadoPartida() {
		return estadoPartida;
	}
	
	
}

