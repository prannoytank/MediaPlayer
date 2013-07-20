import java.io.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javazoom.jl.player.advanced.AdvancedPlayer;
import java.io.FileInputStream;



import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.Clip;
import javax.swing.*;


import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.media.Format;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;


import javax.media.format.AudioFormat;


import com.beaglebuddy.mp3.MP3;




public class player 
{
public static void main(String args[]) throws InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new makeGUI();
			}
		});
		
	}		
		
		
		

}





class makeGUI implements ActionListener
{
	static JFrame f;
	static String[] fullpath=null;
	static String data[][][]=null;
	static String[] col={"Name","Location","Duration","Album","Artist","Genre"};
	static AdvancedPlayer playMp3;
	static FileInputStream fis;
	static int i=0;
	static int j=0;
	static JTable table;
	static JSlider sb;
	static DefaultTableModel model;
	static JFileChooser fc;
	static AudioInputStream audio;
	static Clip clip;
	static File path;
	static JProgressBar br;
	static int position=0;
	static JLabel status;
	static JLabel NowPlaying;
	static JButton playMusic;
	static JPanel p3;
	makeGUI()
	{
		
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		
		
		
		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		} catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
		} 
		
		
		
		
		 f=new JFrame("Dan+e's JukeBox");
		 JPanel p=new JPanel();
		p.setLayout(new BorderLayout());
		
		 playMusic=new JButton("Play");
		 
		playMusic.setForeground(Color.BLUE);
		
		playMusic.setOpaque(true);
		playMusic.setBorderPainted(false);
		playMusic.addActionListener(this);
		playMusic.setActionCommand("play");
		
		JButton remove=new JButton("Remove");
		remove.setForeground(Color.BLUE);
		remove.setActionCommand("remove");
		remove.addActionListener(this);
		
		JButton add=new JButton("Add File");
		add.setActionCommand("add");
		add.addActionListener(this);
		add.setForeground(Color.BLUE);
		
		JButton stop=new JButton("Stop");
		stop.setActionCommand("stop");
		stop.addActionListener(this);
		stop.setForeground(Color.BLUE);
		
		JButton savePlaylist=new JButton("Save Playlist");
		savePlaylist.setActionCommand("playlist");
		savePlaylist.addActionListener(this);
		savePlaylist.setForeground(Color.BLUE);
		
		 model = new DefaultTableModel(data,col);
		 		Font f1=new Font("Arial",Font.BOLD,12);
		   table = new JTable(model);
		
		 
		   
		   table.setFont(f1);
		   
		 table.setShowHorizontalLines(false);
	//	final JList<File> songs=new JList<File>(m);
		  table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane sp=new JScrollPane(table);
		
		
		
		sb=new JSlider(JSlider.HORIZONTAL, 0,20,0);
		sb.setMajorTickSpacing(5);
		sb.setMinorTickSpacing(1);
		sb.setPaintTicks(true);
		sb.setPaintLabels(true);
		
		
		sb.setPreferredSize(new Dimension(700,50));
		//JScrollPane sp1=new JScrollPane(sb);
		NowPlaying=new JLabel("NowPlaying:");
		
		status=new JLabel("Status");
		status.setForeground(Color.BLUE);
		br=new JProgressBar();
		br.setMaximum(100);
		br.setStringPainted(false);
		br.setPreferredSize(new Dimension(600,15));
		 fc=new JFileChooser();
		p.add(sp,BorderLayout.NORTH);
		//p.add(sb,BorderLayout.CENTER);
		JPanel p1=new JPanel();
		//p1.setBackground(Color.YELLOW);
		p.setBackground(Color.WHITE);
		p.add(p1,BorderLayout.CENTER);
		 p3=new JPanel();
		p3.setLayout(new BorderLayout());
		p.add(p3,BorderLayout.SOUTH);
		p3.add(NowPlaying,BorderLayout.NORTH);
		p1.setLayout(new FlowLayout());
		p1.add(br);
		p1.add(add);
		p1.add(playMusic);
		p1.add(stop);
		p1.add(remove);
		p1.add(savePlaylist);
		//p1.add(status);
		status.setVisible(false);
		f.add(p);
		f.setVisible(true);
		f.setSize(650,550);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	
		
		
		
		}
	
	public void actionPerformed(ActionEvent ae)
	{ 
		
		play play1=new play(table,sb,playMp3,fis,br,NowPlaying,playMusic,p3);
		if(ae.getActionCommand().equals("play"))
		{
			if(table.isColumnSelected(0)==false)
			{
				JOptionPane.showMessageDialog(null,"Please Select a song from the table","No song Selected", JOptionPane.WARNING_MESSAGE);
				
			}
			else
			{
			play1.start();
				
				new ProgressBar(br,fis,table,status).start();
				
			}
			
			
			
			/*try {
				fis=new FileInputStream(file_name);
				
				path=new File(file_name);
				
				 audio = AudioSystem.getAudioInputStream(path);
	             clip = AudioSystem.getClip();
	            clip.open(audio);
	            clip.start();*/

		
			
			
			
		}
		else if(ae.getActionCommand().equals("playlist"))
		{
			int save=fc.showSaveDialog(f);
			if(save==JFileChooser.APPROVE_OPTION)
			{
				File save1=fc.getSelectedFile();
				try {
					if(save1.isFile()==true)
					{
						JOptionPane.showMessageDialog(null,"File Name Already Exists","Duplicate File", JOptionPane.WARNING_MESSAGE);
						save=fc.showSaveDialog(f);
					}
					else
					{
					save1.createNewFile();
					 model = (DefaultTableModel) table.getModel();
					 
			        FileWriter excel = new FileWriter(save1);
			        
			        for(int i = 0; i < model.getColumnCount(); i++)
			        {
			            excel.write(model.getColumnName(i) + "\t");
			        }

			        excel.write("\n");

			        for(int i=0; i< model.getRowCount(); i++)
			        {
			            for(int j=0; j < model.getColumnCount(); j++)
			            {
			                excel.write(model.getValueAt(i,j).toString()+"\t");
			            }
			            excel.write("\n");
			        }

			        excel.close();
			        
			        
					}
					
					
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		else if(ae.getActionCommand().equals("stop"))
		{
			//isPlaying=false;
			play1.stop1();
			
		}
		else if(ae.getActionCommand().equals("remove"))
		{
			int pos=table.getSelectedRow();
			model.removeRow(pos);
			//i--;
			j--;
			
			table.repaint();
		}
		else if(ae.getActionCommand().equals("add"))
		{
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			
			int returnVal = fc.showOpenDialog(f);
			
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					
	            File file = fc.getSelectedFile();
	            File name=new File(file.getName());
	            
	            //System.out.println(evt);
	            if(file.getName().trim().endsWith("mp3"))
	            {
	            	MP3 mp3 = new MP3(file);
	            	
	            	 String album=mp3.getAlbum();
	            	//System.out.println(album);
	            	String artist=mp3.getMusicBy();
	            	String genre=mp3.getMusicType();
	            	
	            String act=name.toString();
	            String loc=file.toString();
	            System.out.println(artist+":"+genre);
	            Player p11= (Player) Manager.createRealizedPlayer( file.toURI().toURL() );
		           Time t=p11.getDuration();
		       
		           
		        double length1=t.getSeconds()*1000000;
		        
		        int mili = (int) (length1 / 1000);
		        int sec = (mili / 1000) % 60;
		        int min = (mili / 1000) / 60;
		        String tra=min+":" + sec;
		        
	           
	           
		        if(artist=="" || genre=="")
		        {
		        	artist="Not mentioned";
		        	genre="Not mentioned";
		        	model.insertRow(j, new Object[]{act,loc,tra,album,artist,genre});
		             j++;
		        }
		        else
		        {
		        	model.insertRow(j, new Object[]{act,loc,tra,album,artist,genre});
		             j++;
		        }
	             
	            // i++;
	           
	    }
				
	            else
	            {
	            	JOptionPane.showMessageDialog(null,"Please Select Mp3 file Format","Wrong Format", JOptionPane.WARNING_MESSAGE);
	            }
				}
				catch(Exception e1)
				{
					//System.out.println(e1);
				}
			}
		}
	}
}





	class play extends Thread
	{
		public JTable table;
		public JSlider sb;
		public AdvancedPlayer playMp3;
		public FileInputStream fis;
		public JProgressBar br;
		public JLabel NowPlaying;
		public JButton playMusic;
		private boolean  isPlaying=false;
		public JPanel p3;
		play(JTable t1,JSlider js,AdvancedPlayer p8,  FileInputStream FIS,JProgressBar br1,JLabel now,JButton music,JPanel pp)
		{
			table=t1;
			sb=js;
			playMp3=p8;
			fis=FIS;
			br=br1;
			NowPlaying=now;
			playMusic=music;
			p3=pp;
		}
		
		
		public void run()
		{
			
			
			try
			{
				
					playMusic.setEnabled(false);
					
					Object o = table.getValueAt(table.getSelectedRow(), 1);
		            Object o1=table.getValueAt(table.getSelectedRow(), 0);
					String filename=o1.toString();
					String file_name=o.toString();
					table.setEnabled(false);
		    		
			            	
		    		NowPlaying.setText("NowPlaying:"+filename);
		    		
					
				    fis=new FileInputStream(file_name);
				            
				          
				    playMp3=new AdvancedPlayer(fis);
				   // MP3 mp=new MP3(file_name);
		    		
		    		//BufferedImage image=ImageIO.read((ImageInputStream) mp.getPictures());
		    		//JLabel picLabel = new JLabel(new ImageIcon( image ));
		    		

		    		//p3.add(picLabel,BorderLayout.CENTER);
				             playMp3.play();
				             
				            // playMp3.stop();
				           NowPlaying.setText("NowPlaying:");
				           playMusic.setEnabled(true);
				           table.setEnabled(true);
				
			}
			catch(Exception e4)
			{}
			
		
		}
		public void stop1()
		{
			
			isPlaying=true;
			//playMp3.stop();
			//playMp3.close();
		}
	}
	
	class ProgressBar extends Thread
	{
		private JProgressBar br;
		//private FileInputStream fis;
		private JTable table;
		private JLabel status;
		
		ProgressBar(JProgressBar br2,FileInputStream FIS1,JTable table1,JLabel l1)
		
		{
			br=br2;
			//fis=FIS1;
			table=table1;
			status=l1;
		}
		public void run()
		{
			Object o = table.getValueAt(table.getSelectedRow(), 1);
			String file_name=o.toString();
			
		            File f2=new File(file_name);
		            try
		            {
		            
		            Player p10= (Player) Manager.createRealizedPlayer( f2.toURI().toURL() );
			           Time t=p10.getDuration();
			           
			        double length=t.getSeconds();
			       
			        long wait=(long) (length*10);
			      //System.out.println(wait);
			        	br.setBackground(Color.RED);  
			br.setForeground(Color.RED);
			
			br.setStringPainted(false);
			
	         for(int i=1;i<=100;i++)
             {
	        	 
	        	 br.setValue(i);
	        	 
	        	 
	        	// br.setString(""+i);
	        	 //status.setText("Status:"+i*st1);
	        	 status.repaint();
	        	 br.repaint();
	        	 		try
	        	 		{
	        	 			Thread.sleep(wait);
	        	 		}
	        	 		
	        	 		catch(Exception sl)
	        	 		{
            	 
	        	 		}
             
             			}
		      }
		            catch(Exception e20)
		            {
		            	
		            }
		            br.setValue(0);
		            
		}
		
	}
	
		
		
	

	



