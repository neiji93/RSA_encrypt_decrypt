//package RSA_package ;

import java.math.* ;
import java.util.* ;

/**
 * G&egrave;re le syst&egrave;me de chiffrement/d&eacute;chiffrement.
 * @author ALTINISIK Nejdet, MIGAN ELVYSS
 * @version 1.0 06.05.10
 */
public class RSA
{
	// VARIABLES MEMBRES 

	private BigInteger p ;
	private BigInteger q ;
	BigInteger n ;
	private BigInteger phi ;
	private BigInteger d ;
	private BigInteger x ;
	private BigInteger y ;
	BigInteger clePrivee ;
	BigInteger clePublique ;


	// METHODES PUBLIQUES 
	
	/**
	 * Chiffre le message
	**/
	public String chiffrer(String message, int tailleBlocs)
	{
		if(message==null)
			throw new NullPointerException("Le message ne peut etre NULL") ;
		if(message.equals(""))
			throw new IllegalArgumentException("Les messages vides ne peuvent etre traites") ;
		if(tailleBlocs<=0)
			throw new IllegalArgumentException("La taille des blocs doit etre positive") ;
		if((tailleBlocs%32)!=0)
			throw new IllegalArgumentException("La taille des blocs doit etre un multiple de 32") ;
		
		int[] m = new int[message.length()] ;
		for(int i=0; i<m.length; i=i+1)
			m[i] = message.codePointAt(i) ;
				
		BigInteger[] c = new BigInteger[m.length] ;
		for(int i=0; i<c.length; i=i+1)
		{
			BigInteger biM = new BigInteger(m[i]+"") ;
			c[i] = biM.modPow(clePublique,n) ;
		}
		
		String messageCrypte = "" ;
		for(int i=0; i<c.length; i=i+1)
		{
			if(i!=0 && (i%tailleBlocs)==0)
				messageCrypte = messageCrypte + "- " ;
			else
				messageCrypte = messageCrypte + c[i] + " " ;
		}
		
		return messageCrypte ;
	} // fin methode chiffrer
	
	/**
	 * Dechiffre un message
	 **/
	public String dechiffrer(String cryptogramme)
	{
		if(cryptogramme==null)
			throw new NullPointerException("Null cryptogramme not allowed") ;
		if(cryptogramme.equals(""))
			throw new IllegalArgumentException("Empty cryptogramme not allowed") ;
		
		String[] tmp = cryptogramme.split(" - ") ;
		String[][] c = new String[tmp.length][] ;
		for(int i=0; i<tmp.length; i=i+1)
			c[i] = tmp[i].split(" ") ;
		
		BigInteger[][] m = new BigInteger[tmp.length][] ;
		for(int i=0; i<c.length; i=i+1)
			m[i] = new BigInteger[c[i].length] ;
		for(int i=0; i<c.length; i=i+1)
		{
			for(int j=0; j<c[i].length; j=j+1)
			{
				BigInteger biC = new BigInteger(c[i][j]) ;
				m[i][j] = biC.modPow(clePrivee,n) ;
			}
		}
		
		char[][] mc = new char[tmp.length][] ;
		for(int i=0; i<mc.length; i=i+1)
			mc[i] = new char[m[i].length] ;
		for(int i=0; i<m.length; i=i+1)
		{
			for(int j=0; j<m[i].length; j=j+1)
				mc[i][j] = (char) (m[i][j].intValue()) ;
		}
		
		String messageClair = "" ;
		for(int i=0; i<mc.length; i=i+1)
		{
			for(int j=0; j<mc[i].length; j=j+1)
				messageClair = messageClair + mc[i][j] ;
		}
		
		return messageClair ;
	} // fin methode dechiffrer
	
	/**
	 * Algorithme de calcul de clefs pour RSA
	 */
	public void genererClesRSA()
	{
		BigInteger e ;
		boolean tc = false ;

		Random random = new Random() ;
		//do
		//{
			//generation de nombre 1er
			//this.genererNombresPremiers(random.nextInt(150)+50) ;
			

			int tailleBits = random.nextInt(150)+50;
			if(tailleBits<2)
				throw new IllegalArgumentException("La taille en bits doit etre superieure a 2") ;
		
			random = new Random() ;
			do
			{
				p = BigInteger.probablePrime(tailleBits,random) ;
				q = BigInteger.probablePrime(tailleBits,random) ;
			}
			while(p.equals(q)) ;
			
			
			//suite
			
			n = p.multiply(q) ;
			phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)) ;
			
			//generation de e
			//e = genererE(n) ;
			BigInteger deux = new BigInteger("2") ;
				
			if(phi.compareTo(deux)==-1)
				throw new IllegalArgumentException("phi doit etre superieur a 2") ;

			//BigInteger e ;
			random = new Random() ;
			do
			{
				e = BigInteger.probablePrime(phi.bitLength(),random) ;
				e = e.mod(phi) ;
			}
			while(!phi.gcd(e).equals(BigInteger.ONE)) ;
			//fin de la generation de e
			
			
			
			
			euclide(n,e) ;
			try
			{
				clePrivee = e.modInverse(phi) ;
			}
			catch(ArithmeticException ae)
			{
				tc = true ;
			}
			clePublique = e ;
		//}
		//while(tc) ;
		
	} // fin methode genererClesRSA	
	
	
	// METHODES PRIVEES 
	
	/**
	 * Algorithme d Euclide
	 */
	private void euclide(BigInteger a, BigInteger b)
	{
		if(a.compareTo(BigInteger.ZERO)==-1)
			throw new IllegalArgumentException("a doit etre positif") ;
		if(b.compareTo(BigInteger.ZERO)==-1)
			throw new IllegalArgumentException("b doit etre positif") ;
		if(a.compareTo(b)==-1)
			throw new IllegalArgumentException("a doit etre superieur a b") ;
		
		if(b.equals(BigInteger.ZERO))
		{
			d = a ;
			this.x = BigInteger.ONE ;
			this.y = BigInteger.ZERO ;
			return ;
		}
		
		BigInteger x2 = BigInteger.ONE ;
		BigInteger x1 = BigInteger.ZERO ;
		BigInteger y2 = BigInteger.ZERO ;
		BigInteger y1 = BigInteger.ONE ;
		while(b.compareTo(BigInteger.ZERO)==1)
		{
			BigInteger q = a.divide(b) ;
			BigInteger r = a.subtract(q.multiply(b)) ;
			x = x2.subtract(q.multiply(x1)) ;
			y = y2.subtract(q.multiply(y1)) ;
			a = b ;
			b = r ;
			x2 = x1 ;
			x1 = x ;
			y2 = y1 ;
			y1 = y ;
		}
		d = a ;
		this.x = x2 ;
		this.y = y2 ;
	} 
} // fin de la classe
