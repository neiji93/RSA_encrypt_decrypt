//package RSA_package ;



/*
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Rectangle;

import javax.swing.JTextField;

import java.awt.Point;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class IHM_RSA extends JFrame {


	private JPanel jContentPane = null;
	private JTextArea jTextArea = null;
	private JLabel jLabel_bloc = null;
	private JTextField jTextField_bloc = null;
	private JLabel jLabelClefPublic = null;
	private JLabel jLabelClefPrivee = null;
	private JTextField jTextField_ClefPrivee = null;
	private JTextField jTextField_ClefPublique = null;
	private JTextArea jTextArea1 = null;
	private JButton jButton = null;
	private JButton jButton_valide = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;
	private ButtonGroup buttonGroup1 = null;
	private static RSA appli;
	private JButton jButton1 = null;
	
	private JButton jButtonDecrypt;
	
	//pour charge le fichier
	private JButton jButtonFileChooser;
	
	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	
	/* va initialiser les champs pour les clefs */
	private JPanel createPanelHaut()
	{
		JPanel pan = new JPanel();
		pan.setLayout( new GridLayout(3,2));//new FlowLayout());
	
		jLabelClefPrivee = new JLabel();
		jLabelClefPrivee.setText("Clef privee : ");
		
		jLabelClefPublic = new JLabel();
		jLabelClefPublic.setText("Clef publique : ");
		
		jLabel_bloc = new JLabel();
		jLabel_bloc.setText("Taille des blocs (bits) : ");

		
		//le 2eme textField pour la clef prive : n
		jTextField_ClefPrivee = new JTextField();
		jTextField_ClefPrivee.setEditable(false);
		jTextField_ClefPrivee.setToolTipText("n");
		
		//le textField pr le nb de bits	
		jTextField_bloc = new JTextField();
		jTextField_bloc.setSize(new Dimension(190, 19));
		jTextField_bloc.setToolTipText("Entrer un multiple de 32");

		//le jTextField pr la clef publique
		jTextField_ClefPublique  = new JTextField();
		jTextField_ClefPublique.setEditable(false);
	
		pan.setBackground(new Color(180, 180, 180));
		pan.add(jLabel_bloc);
		pan.add(jTextField_bloc);
		pan.add(jLabelClefPublic);
		pan.add(jLabelClefPrivee);
		pan.add(jTextField_ClefPrivee);
		pan.add(jTextField_ClefPublique );			
			
		return pan;
	} 
		
	private JPanel createPanelCentre()
	{
		JPanel panCentre = new JPanel();
		panCentre.setLayout( new GridLayout(4,1));
		
		JLabel msg = new JLabel("message a crypter ou a decrypter");
		JLabel res = new JLabel("Resultat du cryptage ou du decryptage");
		msg.setVerticalAlignment(SwingConstants.CENTER);
		msg.setVerticalAlignment(SwingConstants.CENTER);

		msg.setSize(new Dimension(10,60));
		res.setPreferredSize(new Dimension(10,60));
		
		jScrollPane = new JScrollPane();
		jScrollPane.setLocation(new Point(75, 160));
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		//le 1er textArea correspondant au msg clair
		jTextArea = new JTextArea();
		jTextArea.setSize( new Dimension(100,100 ) );
		jTextArea.setBackground(new Color(230, 230, 230));
		jTextArea.setLineWrap(true); // On souhaite un retour a ligne automatique
		jTextArea.setWrapStyleWord(true); // On souhaite que les mots ne soient pas coupes
 


		jScrollPane.setViewportView(jTextArea);
		jScrollPane.setSize(new Dimension(630, 200));
			
		jScrollPane1 = new JScrollPane();
		jScrollPane1.setLocation(new Point(75, 400));
		
		//le 2eme textArea correspondant au msg crypte
		jTextArea1 = new JTextArea();
		//jTextArea1.setEditable(false);
		jTextArea1.setBackground(new Color(200, 200, 200));
		jTextArea1.setText("");
		jTextArea1.setEnabled(true);
		jTextArea1.setLineWrap(true); // On souhaite un retour a ligne automatique

		jScrollPane1.setViewportView(jTextArea1);
		jScrollPane1.setSize(new Dimension(630, 200));		
		
		panCentre.add(msg,null);
		panCentre.add(jScrollPane, null);
		panCentre.add(res,null);
		panCentre.add(jScrollPane1, null);
		
		return panCentre;
	}	
		
		
	private JPanel createPanelBas()
	{
		JPanel panBas = new JPanel();
		panBas.setLayout(new FlowLayout());
		
				jButton_valide = new JButton("Crypter");
			jButton_valide.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int bloc = 0;
					
					if(jTextField_bloc.getText().equals("")){
						JOptionPane.showMessageDialog(jContentPane, "Entrer une valeur pour la taille des blocs", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					try{
						bloc = Integer.parseInt(jTextField_bloc.getText());
					}catch(Exception e1){
						JOptionPane.showMessageDialog(jContentPane, "Entrer une valeur entière pour la taille des blocs", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					if(bloc==0){
						JOptionPane.showMessageDialog(jContentPane, "La taille des blocs ne peut être nul", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}

					if(bloc%32 != 0){
						JOptionPane.showMessageDialog(jContentPane, "La taille des blocs doit-être un multiple de 32", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					// La génération des clés doit etre valide
					if(jTextField_ClefPrivee.getText().equals("")){
						JOptionPane.showMessageDialog(jContentPane, "Vous devez generer les clés avant de crypter (decrypter)", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					else jTextArea1.setText(appli.chiffrer(jTextArea.getText(), bloc));
			
				}
			});
			
			
			jButton = new JButton("Generer clefs");
			jButton.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					
									int bloc = 0;
					
					if(jTextField_bloc.getText().equals("")){
						JOptionPane.showMessageDialog(jContentPane, "Entrer une valeur pour la taille des blocs", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					try{
						bloc = Integer.parseInt(jTextField_bloc.getText());
					}catch(Exception e1){
						JOptionPane.showMessageDialog(jContentPane, "Entrer une valeur entière pour la taille des blocs", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					if(bloc==0){
						JOptionPane.showMessageDialog(jContentPane, "La taille des blocs ne peut être nul", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}

					if(bloc%32 != 0){
						JOptionPane.showMessageDialog(jContentPane, "La taille des blocs doit-être un multiple de 32", "Erreur", JOptionPane.ERROR_MESSAGE);
						return ;
					}
					
					
					//sinon, on peut generer la clef sans problemes
					appli.genererClesRSA();
				    jTextField_ClefPrivee.setText(appli.clePrivee.toString());
					//jTextField_ClefPrivee.setText(appli.n.toString());
					jTextField_ClefPublique .setText(appli.clePublique.toString());
				}
			
			});
	
	   jButtonDecrypt = new JButton("Decrypter message");
	   jButtonDecrypt.addActionListener(new java.awt.event.ActionListener() {   
				public void actionPerformed(java.awt.event.ActionEvent e) { 
					
					String res = appli.dechiffrer(jTextArea1.getText());
					jTextArea.setText(res);
					
				}
			
			});
			
			jButtonFileChooser = new JButton("Charger un fichier");
			jButtonFileChooser.addActionListener( new ActionListener( )
						{
							public void actionPerformed(ActionEvent e){
							
							JFileChooser chooser= new JFileChooser();
							//chooser.showOpenDialog( null );
							
							
							int returnVal = chooser.showOpenDialog(null);
    						if(returnVal == JFileChooser.APPROVE_OPTION) {
								File file = chooser.getSelectedFile();
								try{
								
								LoadFile(file);
								}
								catch(FileNotFoundException fnfe){ System.out.println(fnfe); }
								catch(IOException ioe){ System.out.println(ioe); }
								
    						}
							}
						}
				
				);
			
	   panBas.add(jButton_valide);
	   panBas.add(jButton);
	   panBas.add(jButtonDecrypt);
	   panBas.add(jButtonFileChooser);
	
	    return panBas;
	}	
	 




	public void LoadFile(File f) throws FileNotFoundException, IOException
	{
		try{
		
		//File f = new File(filename);
		FileReader reader = new FileReader(f);
		BufferedReader br= new BufferedReader(reader); 
		
		int c;
		String str;
		String texte="";
		
		while ((str = br.readLine()) != null)
				texte += str;
				
		jTextArea.setText(texte);
		reader.close();
		
		}
		catch(FileNotFoundException fnfe){ System.out.println(fnfe);	}
		catch(IOException ioe){ System.out.println(ioe);	}
	}
	



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IHM_RSA thisClass = new IHM_RSA();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setLocationRelativeTo(null);
				thisClass.setVisible(true);
				
			}
		});
	}

	/**
	 * Constructeur
	 */
	public IHM_RSA() {
		super();
		this.setSize(800, 700);
		this.setResizable(false);
		this.appli = new RSA();
		buttonGroup1 = new ButtonGroup();
		
		jContentPane = new JPanel();
		jContentPane.setLayout( new BorderLayout());

		jContentPane.add( createPanelHaut(),BorderLayout.NORTH );
		jContentPane.add( createPanelCentre(), BorderLayout.CENTER);
		jContentPane.add( createPanelBas(), BorderLayout.SOUTH);

		this.getContentPane().add(jContentPane);
		
		this.setTitle("Cryptage / Decryptage de message avec RSA");
		
	}



}  