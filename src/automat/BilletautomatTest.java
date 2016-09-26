/*
 * Hold 11_Billetautomat_JUnit-test
 * Hold 11 - 2016-09-20 - Version 1.0
 * Java JDK 1.8.0_102
 * Lavet af:
 *    Anders Wiberg Olsen (s165241)
 *    Iman Chelhi (s165228)
 *    Troels Just Christoffersen (s120052)
 *    Iman Chelhi (s165228)
 *    Sebastian Tibor (s145918)
 */

package automat;

import static org.junit.Assert.*;

import org.junit.Test;

public class BilletautomatTest {
	Billetautomat ba = new Billetautomat();
	
	@Test
	//Tester montør tilstand login funktion
	public void testLogin() {
//		Billetautomat ba = new Billetautomat();
		assertFalse(ba.erMontør());
		ba.montørLogin("1234");
		assertTrue(ba.erMontør());
	}
	
	@Test
	//Tester logout af montør tilstand.
	public void testLogout() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		assertTrue(ba.erMontør());
		
		ba.montørLogin(null);
		assertFalse(ba.erMontør());
	}
	
	@Test
	//Tester status funktion for altal solgte biletter
	public void testGetAntalBilletterSolgt() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		
		int antalBilletterSolgtFør = ba.getAntalBilletterSolgt();
		
		ba.setAntalBilletterSolgt(2);
		assertEquals(antalBilletterSolgtFør + 2, ba.getAntalBilletterSolgt());
	}
	
	@Test
	//Tester om antal billeter solgt kan udskrives uden at logge ind som montør.
	public void testGetAntalBilletterSolgtAuth() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		ba.setAntalBilletterSolgt(2);
		ba.montørLogin(null);
		
		assertEquals(0, ba.getAntalBilletterSolgt());
	}
	

	@Test
	//Tester om billetprisen bliver det den er sat til.
	public void testGetBilletpris() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		
		int billetprisFør = ba.getBilletpris();
		ba.setBilletpris(billetprisFør + 10);
		assertEquals(billetprisFør + 10, ba.getBilletpris());
	}
	
	@Test
	//Tester at billetprisen kan ændres uden at logge ind. Denne test fejler fordi der er fejl i programmet.
	public void testSetBilletprisAuth() {
//		Billetautomat ba = new Billetautomat();
		int billetprisFør = ba.getBilletpris();
		ba.setBilletpris(billetprisFør + 10);
		assertEquals(billetprisFør, ba.getBilletpris());
	}
	
	@Test
	//Tester at nulstil funktion nulstiller korrekt.
	public void testNulstil() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		ba.setAntalBilletterSolgt(2);
		ba.nulstil();
		assertEquals(0, ba.getAntalBilletterSolgt());
	}
	
	@Test
	//Tester at nulstil funktionen kun virker når man er logget ind som montør.
	public void testNulstilAuth() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		ba.setAntalBilletterSolgt(2);
		
		ba.montørLogin(null);
		ba.nulstil();
		ba.montørLogin("1234");
		
		assertEquals(2, ba.getAntalBilletterSolgt());
	}
	
	@Test
	//Tester at indsæt penge opdaterer balancen korrekt.
	public void testIndsætPenge() {
//		Billetautomat ba = new Billetautomat();
		int balanceFør = ba.getBalance();
		ba.indsætPenge(balanceFør + 10);
		assertEquals(balanceFør + 10, ba.getBalance());
	}
	
	@Test
	//Tester om man kan indsætte negativt beløb på konto. Det kan man, og derfor fejler den.
	public void testIndsætNegativPenge() {
//		Billetautomat ba = new Billetautomat();
		int balanceFør = ba.getBalance();
		if (balanceFør != 0)
		{
			fail("Balancen skal være 0");
		}
		ba.indsætPenge(-10);
		assertEquals(0, ba.getBalance());
	}
	
	@Test
	//Tester om udskriv billet korrekt fratrækker billetprisen fra kontoen, samt om udskrivBillet lægger 1 til antal solgte biletter.
	public void testUdskrivBillet() {
		int balanceFør = ba.getBalance();

		ba.indsætPenge(10);
		ba.udskrivBillet();
		
		ba.montørLogin("1234");
		assertEquals(1, ba.getAntalBilletterSolgt());
		assertEquals(balanceFør, ba.getBalance());
	}
	
	@Test
	//Tester om balancen starter på 0, og at man ikke kan købe billeter hvis man ikke har penge. Fejler fordi man godt kan købe.
	public void testUdskrivBilletNegativ() {
		if (ba.getBalance() < ba.getBilletpris()) {
			fail("Balancen skal være mindre end hvad biletten koster.");
		}
		ba.udskrivBillet();
		
		ba.montørLogin("1234");
		
		assertEquals(0, ba.getAntalBilletterSolgt());
		assertEquals(0, ba.getBalance());
	}
	
	@Test
	//Tester om prisen på ny billet er opdateret efter billetprisen er ændret. Tester desuden om korrekt beløb fratrækkes kontoen.
	public void testUdskrivBilletNyPris() {
		ba.montørLogin("1234");
		ba.setBilletpris(ba.getBilletpris() + 20);
		
		int balanceFør = ba.getBalance();
		ba.indsætPenge(ba.getBilletpris() + 20);
		assertEquals(balanceFør + ba.getBilletpris() + 20, ba.getBalance());
		ba.udskrivBillet();
		assertEquals(balanceFør, ba.getBalance());
	}
	
	@Test
	//Tester om returPenge returnerer korrekt beløb, altså beløbet på kontoen.
	public void testReturPenge() {
		ba.indsætPenge(50);
		int balance = ba.getBalance();
		int retur = ba.returpenge();
		
		assertEquals(balance, retur);
		assertEquals(0, ba.getBalance());
	}
}






