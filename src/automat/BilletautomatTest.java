package automat;

import static org.junit.Assert.*;

import org.junit.Test;

public class BilletautomatTest {
	Billetautomat ba = new Billetautomat();
	
	@Test
	public void testLogin() {
//		Billetautomat ba = new Billetautomat();
		assertFalse(ba.erMontør());
		ba.montørLogin("1234");
		assertTrue(ba.erMontør());
	}
	
	@Test
	public void testLogout() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		assertTrue(ba.erMontør());
		
		ba.montørLogin(null);
		assertFalse(ba.erMontør());
	}
	
	@Test
	public void testGetAntalBilletterSolgt() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		
		int antalBilletterSolgtFør = ba.getAntalBilletterSolgt();
		
		ba.setAntalBilletterSolgt(2);
		assertEquals(antalBilletterSolgtFør + 2, ba.getAntalBilletterSolgt());
	}
	
	@Test
	public void testGetAntalBilletterSolgtAuth() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		ba.setAntalBilletterSolgt(2);
		ba.montørLogin(null);
		
		assertEquals(0, ba.getAntalBilletterSolgt());
	}
	
	@Test
	public void testGetBilletpris() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		
		int billetprisFør = ba.getBilletpris();
		ba.setBilletpris(billetprisFør + 10);
		assertEquals(billetprisFør + 10, ba.getBilletpris());
	}
	
	@Test
	public void testSetBilletprisAuth() {
//		Billetautomat ba = new Billetautomat();
		int billetprisFør = ba.getBilletpris();
		ba.setBilletpris(billetprisFør + 10);
		assertEquals(billetprisFør, ba.getBilletpris());
	}
	
	@Test
	public void testNulstil() {
//		Billetautomat ba = new Billetautomat();
		ba.montørLogin("1234");
		ba.setAntalBilletterSolgt(2);
		ba.nulstil();
		assertEquals(0, ba.getAntalBilletterSolgt());
	}
	
	@Test
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
	public void testIndsætPenge() {
//		Billetautomat ba = new Billetautomat();
		int balanceFør = ba.getBalance();
		ba.indsætPenge(balanceFør + 10);
		assertEquals(balanceFør + 10, ba.getBalance());
	}
	
	@Test
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
	public void testUdskrivBillet() {
		ba.indsætPenge(10);
		ba.udskrivBillet();
		
		ba.montørLogin("1234");
		assertEquals(1, ba.getAntalBilletterSolgt());
		assertEquals(0, ba.getBalance());
	}
	
	@Test
	public void testUdskrivBilletNegativ() {
		ba.udskrivBillet();
		if (ba.getBalance() != 0) {
			fail("Balancen skal være 0.");
		}
		
		assertEquals(0, ba.getAntalBilletterSolgt());
		assertEquals(0, ba.getBalance());
	}
}
