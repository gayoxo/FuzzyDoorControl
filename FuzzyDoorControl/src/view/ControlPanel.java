package view;
import java.applet.Applet;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JSlider;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


import controler.PuertaThread;
import controler.SystemThread;


public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField StreamPeople;
	private JTextField ExpectedTextInput;
	private JTextField OutdoorTextImput;
	private JSlider ExpectedSlide;
	private JSlider OutdoorSlide;
	private JSlider slider;
	private JLabel ProductLabel;
	private JLabel MinimumLabel;
	private JLabel LukasiewiczLabel;
	private JLabel WaitingTime;
	private JLabel Imagen;
	public static JPanel WindowAct;
	
	public ControlPanel() {
		WindowAct=this;
		
		Thread T =new Thread(new SystemThread());
		Thread T2=new Thread(new PuertaThread(this));
		
		
		setLayout(new GridLayout(0, 2, 0, 0));
		setPreferredSize(new Dimension(1000, 500));
		
		setPreferredSize(new Dimension(1000,500));
		setMinimumSize(new Dimension(500, 348));
 
        repaint();
        
        
		JPanel Controles = new JPanel();
		add(Controles);
		Controles.setLayout(new BorderLayout(0, 0));
		
		JPanel ContolesCentrados = new JPanel();
		Controles.add(ContolesCentrados, BorderLayout.CENTER);
		ContolesCentrados.setLayout(new GridLayout(2, 0, 0, 0));
		
		JPanel TemperaturePanel = new JPanel();
		ContolesCentrados.add(TemperaturePanel);
		TemperaturePanel.setLayout(new GridLayout(3, 0, 0, 0));
		
		JLabel DifTemp = new JLabel("Diference Of Temperature");
		DifTemp.setHorizontalAlignment(SwingConstants.CENTER);
		TemperaturePanel.add(DifTemp);
		
		JPanel VariationTextPanel = new JPanel();
		TemperaturePanel.add(VariationTextPanel);
		VariationTextPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel ExpectedPanel = new JPanel();
		VariationTextPanel.add(ExpectedPanel);
		ExpectedPanel.setLayout(new GridLayout(2, 2, 0, 0));
		
		JLabel ExpectedText = new JLabel("Expected/Indoor");
		ExpectedText.setHorizontalAlignment(SwingConstants.CENTER);
		ExpectedPanel.add(ExpectedText);
		
		JPanel TextAreaPanel = new JPanel();
		ExpectedPanel.add(TextAreaPanel);
		TextAreaPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		ExpectedTextInput = new JTextField();
		ExpectedTextInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
				{
					try {
						int ValorNuevo=Integer.parseInt(ExpectedTextInput.getText());
						if ((ValorNuevo>-11) & (ValorNuevo<51))
							{
							ExpectedSlide.setValue(ValorNuevo);
							SystemThread.setDifTemp(getDiferenciaTemperatura());
							}
						else {
							JOptionPane.showMessageDialog(null,
							    "The data in the Expected Temperature is not in data range \n it should be between -10 and 50",
							    "Imput error",
							    JOptionPane.ERROR_MESSAGE);
							ExpectedTextInput.setText(Integer.toString(ExpectedSlide.getValue()));	
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
							    "The data in the Expected Temperature is not looks like a numbrer",
							    "Imput error",
							    JOptionPane.ERROR_MESSAGE);
						ExpectedTextInput.setText(Integer.toString(ExpectedSlide.getValue()));	
					}
					
				}
			}
		});
		
		ExpectedTextInput.setColumns(10);
		TextAreaPanel.add(ExpectedTextInput);
		
		JLabel lblNewLabel = new JLabel("\u00BAC");
		TextAreaPanel.add(lblNewLabel);
		
		JPanel OutdorPanel = new JPanel();
		VariationTextPanel.add(OutdorPanel);
		OutdorPanel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel label_2 = new JLabel("Outdoor");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		OutdorPanel.add(label_2);
		
		JPanel panel = new JPanel();
		OutdorPanel.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		OutdoorTextImput = new JTextField();
		OutdoorTextImput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode()==KeyEvent.VK_ENTER)
				{
					try {
						int ValorNuevo=Integer.parseInt(OutdoorTextImput.getText());
						if ((ValorNuevo>-11) & (ValorNuevo<51))
							{
							OutdoorSlide.setValue(ValorNuevo);
							SystemThread.setDifTemp(getDiferenciaTemperatura());
							}
						else {
							JOptionPane.showMessageDialog(null,
							    "The data in the Outdoor Temperature is not in data range \n it should be between -10 and 50",
							    "Imput error",
							    JOptionPane.ERROR_MESSAGE);
							OutdoorTextImput.setText(Integer.toString(OutdoorSlide.getValue()));	
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
							    "The data in the Outdoor Temperature is not looks like a numbrer",
							    "Imput error",
							    JOptionPane.ERROR_MESSAGE);
						OutdoorTextImput.setText(Integer.toString(OutdoorSlide.getValue()));	
					}
					
				}
			}
		});
		OutdoorTextImput.setColumns(10);
		panel.add(OutdoorTextImput);
		
		JLabel label_3 = new JLabel("\u00BAC");
		panel.add(label_3);
		
		JPanel VariationsPanel = new JPanel();
		TemperaturePanel.add(VariationsPanel);
		VariationsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		ExpectedSlide = new JSlider();
		ExpectedSlide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				ExpectedTextInput.setText(Integer.toString(ExpectedSlide.getValue()));	
				SystemThread.setDifTemp(getDiferenciaTemperatura());
			}
		});
		ExpectedSlide.setMaximum(50);
		ExpectedSlide.setValue(21);
		ExpectedSlide.setMinimum(-10);
		ExpectedSlide.setToolTipText("Move to variate the Expected Temperature");
		VariationsPanel.add(ExpectedSlide);
		
		OutdoorSlide = new JSlider();
		OutdoorSlide.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				OutdoorTextImput.setText(Integer.toString(OutdoorSlide.getValue()));
				SystemThread.setDifTemp(getDiferenciaTemperatura());
				
			}
		});
		OutdoorSlide.setMaximum(50);
		OutdoorSlide.setMinimum(-20);
		OutdoorSlide.setValue(21);
		VariationsPanel.add(OutdoorSlide);
		OutdoorSlide.setToolTipText("Move to variate the Outdoor Temperature");
		
		JPanel StreamPeoplePanel = new JPanel();
		ContolesCentrados.add(StreamPeoplePanel);
		StreamPeoplePanel.setLayout(new GridLayout(4, 0, 0, 0));
		
		Component verticalGlue = Box.createVerticalGlue();
		StreamPeoplePanel.add(verticalGlue);
		
		JLabel label_1 = new JLabel("Stream Of People ( minute )");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		StreamPeoplePanel.add(label_1);
		
		slider = new JSlider();
		
		slider.setToolTipText("Move to variate the stream");
		slider.setValue(0);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMaximum(90);
		StreamPeoplePanel.add(slider);
		
		StreamPeople = new JTextField();
		StreamPeople.setFont(new Font("Tahoma", Font.PLAIN, 19));
		StreamPeople.setHorizontalAlignment(SwingConstants.CENTER);
		StreamPeople.setEditable(false);
		StreamPeople.setColumns(10);
		StreamPeoplePanel.add(StreamPeople);
		StreamPeople.setText(Integer.toString(slider.getValue())+" persons/minute");
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				StreamPeople.setText(Integer.toString(slider.getValue())+" persons/minute");
				SystemThread.setPxM(slider.getValue());
			}
		});
		
		
		JPanel Resultado = new JPanel();
		add(Resultado);
		Resultado.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel ResultadosNumericos = new JPanel();
		Resultado.add(ResultadosNumericos);
		ResultadosNumericos.setLayout(new GridLayout(3, 3, 0, 0));
		
		ProductLabel = new JLabel("Product:");
		ProductLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ResultadosNumericos.add(ProductLabel);
		
		MinimumLabel = new JLabel("Minimum:");
		MinimumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ResultadosNumericos.add(MinimumLabel);
		
		LukasiewiczLabel = new JLabel("Lukasiewicz:");
		ResultadosNumericos.add(LukasiewiczLabel);
		LukasiewiczLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		WaitingTime = new JLabel("0");
		Resultado.add(WaitingTime);
		WaitingTime.setFont(new Font("Tahoma", Font.PLAIN, 87));
		WaitingTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		Imagen=new JLabel();
		Resultado.add(Imagen);
		Imagen.setHorizontalAlignment(SwingConstants.CENTER);
		Imagen.setIcon(new ImageIcon(view.ControlPanel.class.getResource("/icons/Close.gif")));
		T.start();
		T2.start();
	}
	
	protected int getDiferenciaTemperatura() {
		int ValorNuevo=0;
		int ValorNuevo2=0;
		try {
			ValorNuevo=Integer.parseInt(OutdoorTextImput.getText());
			ValorNuevo2=Integer.parseInt(ExpectedTextInput.getText());
		} catch (Exception e) {
		}

		return ValorNuevo-ValorNuevo2;
	}

//	@Override
//	public void init() {
//		super.init();
//		setSize(500, 348);
//	}
	
	public JTextField getExpectedTextInput() {
		return ExpectedTextInput;
	}
	
	public void setProductLabel(String productLabel) {
		ProductLabel.setText("Product: "+ productLabel);
	}
	
	public void setLukasiewiczLabel(String lukasiewiczLabel) {
		LukasiewiczLabel.setText("Lukasiewicz: "+ lukasiewiczLabel);
	}
	
	public void setMinimumLabel(String minimumLabel) {
		MinimumLabel.setText("Minimum: "+ minimumLabel);
	}
	
	public void setWaitingTime(String waitingTime) {
		WaitingTime.setText(waitingTime);
	}
	
	public void setImagen(ImageIcon imagen) {
		Imagen.setIcon(imagen);
		//repaint();
	}
}
