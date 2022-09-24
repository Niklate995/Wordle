package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogicMainTest {
	String palabra = "SALIR";
	LogicMain juego = new LogicMain(5, 600);

	@Before
	public void setUp() throws Exception {
		this.juego.elegirPalabra(palabra);
	}
	
	@Test
	public void testIntentarAdivinar1() {
		assertEquals("GGGGG", juego.intentarAdivinar("SALIR"));	
		assertEquals("GGGGG", juego.intentarAdivinar("salir"));
	}
	
	@Test
	public void testIntentarAdivinar2() {
		assertEquals("BGGGG", juego.intentarAdivinar("ZALIR"));	
		assertEquals("BYGYG", juego.intentarAdivinar("ZILAR"));
		assertEquals("GGGBY", juego.intentarAdivinar("SALXI"));
	}
	
	@Test
	public void testIntentarAdivinar3() {
		assertEquals("GGGGG", juego.intentarAdivinar("SALIR"));
		assertEquals("GYYYY", juego.intentarAdivinar("SSSSS"));
		assertEquals("YGYGY", juego.intentarAdivinar("AAAII"));
	}
	
	@Test
	public void testSinIntentos() {
		juego.intentarAdivinar("aaaaa");
		juego.intentarAdivinar("aaaaa");
		
		juego.intentarAdivinar("aaaaa");
		assertEquals((Integer) 2, (Integer) juego.getIntentos());
		assertEquals((Integer) 1, (Integer) juego.getEstadoPartida());
		
		juego.intentarAdivinar("aaaaa");
		assertEquals((Integer) 1, (Integer) juego.getIntentos());
		assertEquals((Integer) 1, (Integer) juego.getEstadoPartida());
		
		juego.intentarAdivinar("aaaaa");
		assertEquals((Integer) 0, (Integer) juego.getIntentos());
		assertEquals((Integer) 3, (Integer) juego.getEstadoPartida());
	}
}