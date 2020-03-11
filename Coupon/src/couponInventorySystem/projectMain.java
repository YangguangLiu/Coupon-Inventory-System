package couponInventorySystem;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class projectMain {

	private JFrame frame;
	private JTextField textCouponProvider;
	private JTextField textOriginalPrice;
	private JTextField textExpirationPeriod;
	private JTextField textProductName;
	private JTextField textDiscountRate;
	private JTextField textSearchCoupon;
	private JTextField textSearchResult;
	private JTextField textSearchCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					projectMain window = new projectMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public projectMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(20, 50, 1340, 660);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5),
				"Purchase Coupon", TitledBorder.CENTER, TitledBorder.TOP, null, null)));
		panel.setBounds(10, 127, 499, 210);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblInputDataUsing = new JLabel("Input data using a file");
		lblInputDataUsing.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInputDataUsing.setBounds(20, 20, 171, 30);
		panel.add(lblInputDataUsing);
		
		JLabel lblInputDataManually = new JLabel("Input data manually");
		lblInputDataManually.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblInputDataManually.setBounds(20, 62, 171, 30);
		panel.add(lblInputDataManually);
		
		JButton btnOpenDataFile = new JButton("Open Data File");
		btnOpenDataFile.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOpenDataFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Open data file ...
				try {					
					String path = null;
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Please choose coupon data file...");
					fc.setApproveButtonText("OK");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					fc.setFileFilter(filter);
					fc.setCurrentDirectory(new File("."));
					
					int returnVal = fc.showOpenDialog(fc);
					
					if (returnVal == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null,
								"Data File is not choose, please select a data file!",
								"Warning Message", JOptionPane.ERROR_MESSAGE);					
					} else if(returnVal == JFileChooser.APPROVE_OPTION) {
						path = fc.getSelectedFile().getAbsolutePath();
						File fUnsorted = new File(path);
						//Reading coupon data file							
						if (!fUnsorted.exists()) {
							JOptionPane.showMessageDialog(null,
									"Data File is not exists, please select a data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else {
							//Check whether the input data format is correct or not.								
							BufferedReader bufReaderCheck = new BufferedReader(new FileReader(path));
							String strLinebufCheck = null;
							strLinebufCheck = bufReaderCheck.readLine();
							String[] splitLineCheck = strLinebufCheck.split(" ");
							bufReaderCheck.close();

							if (splitLineCheck.length != 6) {
								JOptionPane.showMessageDialog(null,
										"File format is incorrect, please select a correct file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);			
							} else {							
								String pathName = null;
								JFileChooser fcName = new JFileChooser();
								fcName.setDialogTitle("Please choose a file or input file name with suffix of \".txt\"" );
								fcName.setApproveButtonText("OK");
								FileNameExtensionFilter filterName = new FileNameExtensionFilter("Text Files", "txt");
								fcName.setFileFilter(filterName);
								fcName.setCurrentDirectory(new File("."));
								int returnValue = fcName.showOpenDialog(fcName);
								
								if (returnValue == JFileChooser.CANCEL_OPTION) {
									JOptionPane.showMessageDialog(null,
											"Data File is not choose, please select a data file!",
											"Warning Message", JOptionPane.ERROR_MESSAGE);					
								} else if(returnValue == JFileChooser.APPROVE_OPTION) {
									String fileName = fcName.getSelectedFile().getName();
									
									if (!fileName.endsWith(".txt")) {
										JOptionPane.showMessageDialog(null,
												"File name is invalid, please select/input a valid file!",
												"Warning Message", JOptionPane.ERROR_MESSAGE);					
									} else {
										pathName = fcName.getSelectedFile().getAbsolutePath();
										//Reading NewULCoupon data file if it exists, otherwise create one.
										File fCouponUnstortFile = new File(pathName);
									
										if(!fCouponUnstortFile.exists()) {
											fCouponUnstortFile.createNewFile();
										}
										
										BufferedWriter bufWriter;
										bufWriter = new BufferedWriter(new FileWriter(fCouponUnstortFile,true));
										
										Scanner s = new Scanner(fUnsorted);	
										while (s.hasNextLine()) {
											String line = s.nextLine();
											bufWriter.write(line);
											bufWriter.newLine();
											bufWriter.flush();					
										}	
										s.close();
										bufWriter.close();
										
										JOptionPane.showMessageDialog(null, "Loading Complete!");
									}								
								}
							}
						}	
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnOpenDataFile.setBounds(200, 20, 160, 30);
		panel.add(btnOpenDataFile);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 55, 350, 8);
		panel.add(separator);
		
		JLabel lblCouponProvider = new JLabel("Coupon Provider");
		lblCouponProvider.setHorizontalAlignment(SwingConstants.CENTER);
		lblCouponProvider.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCouponProvider.setBounds(10, 99, 132, 30);
		panel.add(lblCouponProvider);
		
		textCouponProvider = new JTextField();
		textCouponProvider.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				char iNumber = evt.getKeyChar();
				if(!(Character.isLetterOrDigit(iNumber))
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				}
			}
		});
		textCouponProvider.setFont(new Font("Tahoma", Font.BOLD, 15));
		textCouponProvider.setBorder(new LineBorder(new Color(0,0,0),2));
		textCouponProvider.setBounds(142, 99, 97, 30);
		panel.add(textCouponProvider);
		textCouponProvider.setColumns(10);
		
		JLabel lblPrice = new JLabel("Original Price");
		lblPrice.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(10, 134, 132, 30);
		panel.add(lblPrice);
		
		textOriginalPrice = new JTextField();
		textOriginalPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				String text = textOriginalPrice.getText();
				char iNumber = evt.getKeyChar();
				if((!(iNumber >= '0' && iNumber <= '9') && iNumber != '.')						
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				} else if("".equals(text) && iNumber == '0'){//The first number entered cannot be a zero.
					evt.consume();
				} else if("".equals(text) && iNumber == '.'){
					evt.consume();
				} else if(text.contains(".")) {
					if(iNumber == '.') {
						evt.consume();
					}else {
						int idx = text.indexOf('.');
						String tmp = text.substring(idx+1);
						if(tmp.length() >= 2) {
							evt.consume();
						}
						
					}
				}
			}
		});
		textOriginalPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		textOriginalPrice.setBorder(new LineBorder(new Color(0,0,0),2));
		textOriginalPrice.setColumns(10);
		textOriginalPrice.setBounds(142, 134, 97, 30);
		panel.add(textOriginalPrice);
		
		JLabel lblExpirationPeriod = new JLabel("Expiration Period");
		lblExpirationPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		lblExpirationPeriod.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblExpirationPeriod.setBounds(10, 169, 132, 30);
		panel.add(lblExpirationPeriod);
		
		textExpirationPeriod = new JTextField();
		textExpirationPeriod.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				String text = textExpirationPeriod.getText();
				char iNumber = evt.getKeyChar();
				if(!(Character.isDigit(iNumber))
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				} else if("".equals(text) && iNumber == '0'){//The first number entered cannot be a zero.
					evt.consume();
				}
			}
		});
		textExpirationPeriod.setFont(new Font("Tahoma", Font.BOLD, 15));
		textExpirationPeriod.setBorder(new LineBorder(new Color(0,0,0),2));
		textExpirationPeriod.setColumns(10);
		textExpirationPeriod.setBounds(142, 169, 97, 30);
		panel.add(textExpirationPeriod);
		
		JLabel lblPorduct = new JLabel("Porduct Name");
		lblPorduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblPorduct.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPorduct.setBounds(241, 99, 132, 30);
		panel.add(lblPorduct);
		
		textProductName = new JTextField();
		textProductName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				char iNumber = evt.getKeyChar();
				if(!(Character.isLetterOrDigit(iNumber))
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				}
			}
		});
		textProductName.setFont(new Font("Tahoma", Font.BOLD, 15));
		textProductName.setBorder(new LineBorder(new Color(0,0,0),2));
		textProductName.setColumns(10);
		textProductName.setBounds(373, 99, 97, 30);
		panel.add(textProductName);
		
		JLabel lblDiscountRate = new JLabel("Discount Rate");
		lblDiscountRate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiscountRate.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDiscountRate.setBounds(241, 134, 132, 30);
		panel.add(lblDiscountRate);
		
		textDiscountRate = new JTextField();
		textDiscountRate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				String text = textDiscountRate.getText();
				char iNumber = evt.getKeyChar();
				if((!(iNumber >= '0' && iNumber <= '9') && iNumber != '.')						
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				} else if("".equals(text) && iNumber == '.'){//The first number entered cannot be a point.
					evt.consume();
				} else if("".equals(text) && iNumber >= '1' && iNumber <= '9'){
					//The first number entered only 0 allowed.
					evt.consume();
				} else if(text.contains(".")) {
					if(iNumber == '.') {//only one dot is allowed
						evt.consume();
					}else {
						int idx = text.indexOf('.');
						String tmp = text.substring(idx+1);
						//Keep two significant digits after the decimal point
						if(tmp.length() >= 2) {
							evt.consume();
						}
						
					}
				}
			}
		});
		textDiscountRate.setFont(new Font("Tahoma", Font.BOLD, 15));
		textDiscountRate.setBorder(new LineBorder(new Color(0,0,0),2));
		textDiscountRate.setColumns(10);
		textDiscountRate.setBounds(373, 134, 97, 30);
		panel.add(textDiscountRate);
		
		JLabel lblCouponStatus = new JLabel("Coupon Status");
		lblCouponStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblCouponStatus.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCouponStatus.setBounds(241, 169, 132, 30);
		panel.add(lblCouponStatus);
		
		JComboBox<String> comboBoxCouponStatus = new JComboBox<String>();
		comboBoxCouponStatus.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Unused", "Redeemed", "Expired"}));
		comboBoxCouponStatus.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBoxCouponStatus.setBounds(373, 169, 97, 30);
		panel.add(comboBoxCouponStatus);
		
		JButton btnAddData = new JButton("Add Data");
		btnAddData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Adding coupon data manually.
				try {
					String[] couponData = new String[6];
					couponData[0]= textCouponProvider.getText();
					couponData[1]= textProductName.getText();
					couponData[2]= textOriginalPrice.getText();
					couponData[3]= textDiscountRate.getText();
					couponData[4]= textExpirationPeriod.getText();
					couponData[5]= (String) comboBoxCouponStatus.getSelectedItem();
					
					//if any one element is null, waring message and input data				
					if (Arrays.asList(couponData).contains("")) {
						JOptionPane.showMessageDialog(null,
								"Null element is not allowed, please input related information",
								"Warning Message", JOptionPane.ERROR_MESSAGE);
					} else {					
						String pathName = null;
						JFileChooser fcName = new JFileChooser();
						fcName.setDialogTitle("Please choose a file or input file name with suffix of \".txt\"" );
						fcName.setApproveButtonText("OK");
						FileNameExtensionFilter filterName = new FileNameExtensionFilter("Text Files", "txt");
						fcName.setFileFilter(filterName);
						fcName.setCurrentDirectory(new File("."));
						int returnValue = fcName.showOpenDialog(fcName);
						
						if (returnValue == JFileChooser.CANCEL_OPTION) {
							JOptionPane.showMessageDialog(null,
									"Data File is not choose, please select a data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else if(returnValue == JFileChooser.APPROVE_OPTION) {
							String fileName = fcName.getSelectedFile().getName();
							
							if (!fileName.endsWith(".txt")) {
								JOptionPane.showMessageDialog(null,
										"File name is invalid, please select/input a valid file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);					
							} else {							
								pathName = fcName.getSelectedFile().getAbsolutePath();								
								//Reading NewULCoupon data file if it exists, otherwise create one.
								File fCouponUnstortFile = new File(pathName);
										
								if(!fCouponUnstortFile.exists()) {
									fCouponUnstortFile.createNewFile();
									BufferedWriter bufWriter;
									bufWriter = new BufferedWriter(new FileWriter(fCouponUnstortFile,true));
																
									//remove the bracket and comma
									String strr = Arrays.toString(couponData).replaceAll("\\[|\\]", "").replace(",", "");
									bufWriter.write(strr);
									bufWriter.newLine();
									bufWriter.flush();
									
									bufWriter.close();
									JOptionPane.showMessageDialog(null, "Adding Complete!");
									
									//clear the text of the coupon information
									textCouponProvider.setText(null);
									textOriginalPrice.setText(null);
									textExpirationPeriod.setText(null);
									textProductName.setText(null);
									textDiscountRate.setText(null);
									
								} else {								
									//Check whether the input data format is correct or not.								
									BufferedReader bufReaderCheck = new BufferedReader(new FileReader(pathName));
									String strLinebufCheck = null;
									strLinebufCheck = bufReaderCheck.readLine();
																		
									if (strLinebufCheck == null) {
										BufferedWriter bufWriter;
										bufWriter = new BufferedWriter(new FileWriter(fCouponUnstortFile,true));
																	
										//remove the bracket and comma
										String strr = Arrays.toString(couponData).replaceAll("\\[|\\]", "").replace(",", "");
										bufWriter.write(strr);
										bufWriter.newLine();
										bufWriter.flush();
										
										bufWriter.close();
										JOptionPane.showMessageDialog(null, "Adding Complete!");
										
										//clear the text of the coupon information
										textCouponProvider.setText(null);
										textOriginalPrice.setText(null);
										textExpirationPeriod.setText(null);
										textProductName.setText(null);
										textDiscountRate.setText(null);

									} else {
										String[] splitLineCheck = strLinebufCheck.split(" ");
										bufReaderCheck.close();
		
										if (splitLineCheck.length == 6) {
											BufferedWriter bufWriter;
											bufWriter = new BufferedWriter(new FileWriter(fCouponUnstortFile,true));
																		
											//remove the bracket and comma
											String strr = Arrays.toString(couponData).replaceAll("\\[|\\]", "").replace(",", "");
											bufWriter.write(strr);
											bufWriter.newLine();
											bufWriter.flush();
											
											bufWriter.close();
											JOptionPane.showMessageDialog(null, "Adding Complete!");
											
											//clear the text of the coupon information
											textCouponProvider.setText(null);
											textOriginalPrice.setText(null);
											textExpirationPeriod.setText(null);
											textProductName.setText(null);
											textDiscountRate.setText(null);

										} else {
											JOptionPane.showMessageDialog(null,
												"File format is incorrect, please select a correct file!",
												"Warning Message", JOptionPane.ERROR_MESSAGE);	
										}	
									}
								}
							}	
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnAddData.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAddData.setBounds(200, 63, 160, 30);
		panel.add(btnAddData);	
		
		JTextArea textAreaListCoupon = new JTextArea();
		textAreaListCoupon.setFont(new Font("Monospaced", Font.BOLD, 12));
		textAreaListCoupon.setBorder(new LineBorder(new Color(0,0,0),5));
		textAreaListCoupon.setBounds(525, 127, 782, 460);
		frame.getContentPane().add(textAreaListCoupon);
		
		JButton btnDisplace = new JButton("Displace");
		btnDisplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					textAreaListCoupon.setText(null); //clear the content on the text Area
					
					String path = null;
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Please choose coupon data file...");
					fc.setApproveButtonText("OK");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					fc.setFileFilter(filter);
					fc.setCurrentDirectory(new File("."));
					int returnVal = fc.showOpenDialog(fc);
					
					if (returnVal == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null,
								"Data File is not choose, please select data file!",
								"Warning Message", JOptionPane.ERROR_MESSAGE);					
					} else if(returnVal == JFileChooser.APPROVE_OPTION) {
						path = fc.getSelectedFile().getAbsolutePath();
						File fUnsorted = new File(path);
						
						if (!fUnsorted.exists()) {
							JOptionPane.showMessageDialog(null,
									"Data File is not exists, please select a data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else {
							//Check whether the input data format is correct or not.								
							BufferedReader bufReaderCheck = new BufferedReader(new FileReader(path));
							String strLinebufCheck = null;
							strLinebufCheck = bufReaderCheck.readLine();
							String[] splitLineCheck = strLinebufCheck.split(" ");
							bufReaderCheck.close();

							if (splitLineCheck.length != 6) {
								JOptionPane.showMessageDialog(null,
										"File format is incorrect, please select a correct file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);			
							} else {							
								BufferedReader bufReader;								
								FileReader fReader = new FileReader(path);
								bufReader = new BufferedReader(fReader);
	
								textAreaListCoupon.append("Provider"+"\tPorduct Name"+"\tOriginal Price"+"\tDiscount"
								+"\tExpiration"+"\tStatus"+"\n"
								+"= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");	
								String textLine = null;
								while ((textLine = bufReader.readLine()) != null) {
									String[] strLine = textLine.split(" ");
									
									textAreaListCoupon.append(strLine[0]+"\t\t"+strLine[1]+"\t\t"+strLine[2]+"\t\t"
									+strLine[3]+"\t\t"+strLine[4]+"\t\t"+strLine[5]+"\n");				
								}
								
								bufReader.close();
							}
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDisplace.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDisplace.setBounds(370, 30, 100, 50);
		panel.add(btnDisplace);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "Search Coupon",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 342, 499, 144);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblCouponOfProduct = new JLabel("Product Name");
		lblCouponOfProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblCouponOfProduct.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCouponOfProduct.setBounds(185, 22, 130, 30);
		panel_1.add(lblCouponOfProduct);
		
		textSearchCoupon = new JTextField();
		textSearchCoupon.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				
				char iNumber = evt.getKeyChar();
				if(!(Character.isLetterOrDigit(iNumber))
						||(iNumber == KeyEvent.VK_BACK_SPACE)
						||(iNumber == KeyEvent.VK_DELETE)) {
					evt.consume();
				}
			}
		});
		textSearchCoupon.setFont(new Font("Tahoma", Font.BOLD, 15));
		textSearchCoupon.setBorder(new LineBorder(new Color(0,0,0),2));
		textSearchCoupon.setColumns(10);
		textSearchCoupon.setBounds(320, 23, 80, 30);
		panel_1.add(textSearchCoupon);
		
		JLabel lblSearchingResult = new JLabel("Searching Result");
		lblSearchingResult.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchingResult.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSearchingResult.setBounds(10, 62, 132, 30);
		panel_1.add(lblSearchingResult);
		
		textSearchResult = new JTextField();
		textSearchResult.setFont(new Font("Tahoma", Font.BOLD, 12));
		textSearchResult.setBorder(new LineBorder(new Color(0,0,0),2));
		textSearchResult.setColumns(10);
		textSearchResult.setBounds(142, 60, 347, 30);
		panel_1.add(textSearchResult);
		
		JButton btnSearchCoupon = new JButton("Search");
		btnSearchCoupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {										
					String in = textSearchCoupon.getText();
					//To convert any input string from upper case to lower case
					String target = in.toLowerCase();

					if (target.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Null element is not allowed, please input product name!",
								"Warning Message", JOptionPane.ERROR_MESSAGE);
					} else {
						UnsortedLinkedList<Coupon> couponSortedList = new UnsortedLinkedList<Coupon>();
												
						String path = null;
						JFileChooser fc = new JFileChooser();
						fc.setDialogTitle("Please choose coupon data file...");
						fc.setApproveButtonText("OK");
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
						fc.setFileFilter(filter);
						fc.setCurrentDirectory(new File("."));
						int returnVal = fc.showOpenDialog(fc);
						
						if (returnVal == JFileChooser.CANCEL_OPTION) {
							JOptionPane.showMessageDialog(null,
									"Data File is not choose, please select data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else if(returnVal == JFileChooser.APPROVE_OPTION) {
							path = fc.getSelectedFile().getAbsolutePath();						
							File fUnsorted = new File(path);	
							
							if (!fUnsorted.exists()) {
								JOptionPane.showMessageDialog(null,
										"Data File is not exists, please select a data file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);					
							} else {						
								//Check whether the input data format is correct or not.								
								BufferedReader bufReaderCheck = new BufferedReader(new FileReader(path));
								String strLinebufCheck = null;
								strLinebufCheck = bufReaderCheck.readLine();
								String[] splitLineCheck = strLinebufCheck.split(" ");
								bufReaderCheck.close();

								if (splitLineCheck.length != 7) {
									JOptionPane.showMessageDialog(null,
											"File format is incorrect, please select a correct file!",
											"Warning Message", JOptionPane.ERROR_MESSAGE);			
								} else {								
									BufferedReader bufReader = new BufferedReader(new FileReader(path));
									String strLine = null;
									
									while ((strLine = bufReader.readLine()) != null) {
										Coupon coupon = new Coupon();
										String[] splitLine = strLine.split(" ");
										
										coupon.setCouponSite(splitLine[0]);
										//To convert any string product from upper case to lower case
										coupon.setProductName(splitLine[1].toLowerCase()); 
										coupon.setOriginalPrice(splitLine[2]);
										coupon.setDiscountRate(Double.valueOf(splitLine[3]));
										coupon.setExpirationPeriod(Integer.parseInt(splitLine[4]));
										coupon.setCouponStatus(splitLine[5]);
										coupon.setFinalPrice(splitLine[6]);	
										//add coupon information into sorted linked list
										couponSortedList.add(coupon);
									}			
									bufReader.close();
									
									//Searching coupon by BST algorithm
									BinarySearchTree bstRoot = new BinarySearchTree();
									
									for (int i = 0; i < couponSortedList.size(); i++) {
										//To convert any product name from upper case to lower case
										String strName = couponSortedList.get(i).getProductName().toString().toLowerCase();
										bstRoot.add(strName);
									}
			
									if (bstRoot.find(target)) {
										textSearchCount.setText("Found in " + bstRoot.count + " by BST Search ");						
									} else {
										textSearchCount.setText("No coupon is found - " + bstRoot.count + " by BST Search ");
									}
									
									//Searching coupon by linear search algorithm 
									int linearCount = 0;
									int linearIndex = 0;
									boolean contain = false;
									
									for (int j = couponSortedList.size() - 1; j > - 1; j--) {
										contain = couponSortedList.get(j).toString().contains(target);
			
										linearCount = j + 1;
										if (contain) {
											textSearchCount.setText(textSearchCount.getText()+"and " 
													+  linearCount + " by Linear Search. \n");
											textSearchResult.setText(couponSortedList.get(j).toString());
											linearIndex = 1;
										}
									}
									
									if (linearIndex == 0) {				
										textSearchCount.setText(textSearchCount.getText()+"and " 
												+ couponSortedList.size() + " by Linear Search. \n");
										textSearchResult.setText("\nCoupon " + in + " is not included in the file.\n ");
									}
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
		});
		btnSearchCoupon.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSearchCoupon.setBounds(405, 22, 82, 30);
		panel_1.add(btnSearchCoupon);
		
		JLabel lblCountOutput = new JLabel("Searching Count");
		lblCountOutput.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountOutput.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCountOutput.setBounds(10, 99, 132, 30);
		panel_1.add(lblCountOutput);
		
		textSearchCount = new JTextField();
		textSearchCount.setFont(new Font("Tahoma", Font.BOLD, 12));
		textSearchCount.setBorder(new LineBorder(new Color(0,0,0),2));
		textSearchCount.setColumns(10);
		textSearchCount.setBounds(142, 97, 347, 30);
		panel_1.add(textSearchCount);
		
		JButton btnComputeFinalPrice = new JButton("Compute Final Price");
		btnComputeFinalPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					//Read Coupon Data File and calculate the final price
					
					String path = null;
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Please choose coupon data file...");
					fc.setApproveButtonText("OK");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					fc.setFileFilter(filter);
					fc.setCurrentDirectory(new File("."));
					int returnVal = fc.showOpenDialog(fc);
					
					if (returnVal == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null,
								"Data File is not choose, please select data file!",
								"Warning Message", JOptionPane.ERROR_MESSAGE);					
					} else if(returnVal == JFileChooser.APPROVE_OPTION) {
						path = fc.getSelectedFile().getAbsolutePath();
						//Reading coupon data file				
						File fUnsorted = new File(path);
						
						if (!fUnsorted.exists()) {
							JOptionPane.showMessageDialog(null,
									"Data File is not exists, please select a data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else {
							String pathName = null;
							JFileChooser fcName = new JFileChooser();
							fcName.setDialogTitle("Please choose a file or input file name with suffix of \".txt\"" );
							fcName.setApproveButtonText("OK");
							FileNameExtensionFilter filterName = new FileNameExtensionFilter("Text Files", "txt");
							fcName.setFileFilter(filterName);
							fcName.setCurrentDirectory(new File("."));
							int returnValue = fcName.showOpenDialog(fcName);
							
							if (returnValue == JFileChooser.CANCEL_OPTION) {
								JOptionPane.showMessageDialog(null,
										"Data File is not choose, please select a data file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);					
							} else if(returnValue == JFileChooser.APPROVE_OPTION) {
								String fileName = fcName.getSelectedFile().getName();
								
								if (!fileName.endsWith(".txt")) {
									JOptionPane.showMessageDialog(null,
											"File name is invalid, please select/input a valid file!",
											"Warning Message", JOptionPane.ERROR_MESSAGE);					
								} else {
								
									pathName = fcName.getSelectedFile().getAbsolutePath();
									//Reading SearchCoupon data file if it exists, otherwise create one.
									File fCouponUnstortFile = new File(pathName);
							
									if(!fCouponUnstortFile.exists()) {
										fCouponUnstortFile.createNewFile();
									}
									
									BufferedWriter bufWriter;
									bufWriter = new BufferedWriter(new FileWriter(fCouponUnstortFile,true));
									
									//Check whether the input data format is correct or not.
									Scanner sCheck = new Scanner(fUnsorted);	
									String strLineCheck = sCheck.nextLine();
									String[] cStrCheck = strLineCheck.split(" ");
									sCheck.close();
									
									if (cStrCheck.length != 6) {
										JOptionPane.showMessageDialog(null,
												"File format is incorrect, please select a correct file!",
												"Warning Message", JOptionPane.ERROR_MESSAGE);			
									} else {									
										Scanner s = new Scanner(fUnsorted);	
										while (s.hasNextLine()) {						
											String strLine = s.nextLine();
											String[] cStr = strLine.split(" ");
											
											double finalPrice = Double.parseDouble(cStr[2]) *(1-Double.parseDouble(cStr[3]));
											Double finalPriceDouble = BigDecimal.valueOf(finalPrice)
													.setScale(2, RoundingMode.HALF_UP).doubleValue();
											String newStrLine = strLine + " " + finalPriceDouble;
					
											bufWriter.write(newStrLine);
											bufWriter.newLine();
											bufWriter.flush();					
										}
										
										s.close();
										bufWriter.close();
										
										JOptionPane.showMessageDialog(null, "Calculating Complete!");
									}
								}
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnComputeFinalPrice.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnComputeFinalPrice.setBounds(10, 22, 180, 30);
		panel_1.add(btnComputeFinalPrice);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 5), "List Coupons",
				TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_2.setBounds(10, 492, 499, 60);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblKeyField = new JLabel("Key Field");
		lblKeyField.setHorizontalAlignment(SwingConstants.CENTER);
		lblKeyField.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblKeyField.setBounds(10, 18, 86, 30);
		panel_2.add(lblKeyField);
		
		JComboBox<String> comboBoxKeyField = new JComboBox<String>();
		comboBoxKeyField.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Coupon Provider", "Product Name", "Original Price", "Discount Rate", 
				"Expiration Period", "Coupon Status", "Final Price"}));
		comboBoxKeyField.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBoxKeyField.setBounds(100, 18, 217, 30);
		panel_2.add(comboBoxKeyField);
		
		JButton btnListCoupon = new JButton("List Coupon");
		btnListCoupon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String path = null;
					JFileChooser fc = new JFileChooser();
					fc.setDialogTitle("Please choose coupon data file...");
					fc.setApproveButtonText("OK");
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
					fc.setFileFilter(filter);
					fc.setCurrentDirectory(new File("."));
					int returnVal = fc.showOpenDialog(fc);
					
					if (returnVal == JFileChooser.CANCEL_OPTION) {
						JOptionPane.showMessageDialog(null,
								"Data File is not choose, please select data file!",
								"Warning Message", JOptionPane.ERROR_MESSAGE);					
					} else if(returnVal == JFileChooser.APPROVE_OPTION) {
						path = fc.getSelectedFile().getAbsolutePath();	
						File fUnsorted = new File(path);
						
						if (!fUnsorted.exists()) {
							JOptionPane.showMessageDialog(null,
									"Data File is not exists, please select a data file!",
									"Warning Message", JOptionPane.ERROR_MESSAGE);					
						} else {
							//Check whether the input data format is correct or not.								
							BufferedReader bufReaderCheck = new BufferedReader(new FileReader(path));
							String strLinebufCheck = null;
							strLinebufCheck = bufReaderCheck.readLine();
							String[] splitLineCheck = strLinebufCheck.split(" ");
							bufReaderCheck.close();

							if (splitLineCheck.length != 7) {
								JOptionPane.showMessageDialog(null,
										"File format is incorrect, please select a correct file!",
										"Warning Message", JOptionPane.ERROR_MESSAGE);			
							} else {					
								String inputStr = (String) comboBoxKeyField.getSelectedItem();					
			
								SortedLinkedList<Coupon> couponSortedList = new SortedLinkedList<Coupon>();
								
								BufferedReader bufReader = new BufferedReader(new FileReader(path));
		
								String line = null;
								
								while ((line = bufReader.readLine()) != null) {
									Coupon coupon = new Coupon();
									String[] splitLine = line.split(" ");
									
									coupon.setCouponSite(splitLine[0]);
									coupon.setProductName(splitLine[1]); 
									coupon.setOriginalPrice(splitLine[2]);
									coupon.setDiscountRate(Double.valueOf(splitLine[3]));
									coupon.setExpirationPeriod(Integer.parseInt(splitLine[4]));
									coupon.setCouponStatus(splitLine[5]);
									coupon.setFinalPrice(splitLine[6]);	
									//Add coupon information
									couponSortedList.addCoupon(coupon,inputStr);
								}
								bufReader.close();
								
								//clear the content of text area
								textAreaListCoupon.setText(null);
								
								textAreaListCoupon.append("Provider"+"\tPorduct Name"+"\tOriginal Price"+"\tDiscount"
								+"\tExpiration"+"\tFinal Price"+"\tStatus" +"\n"
								+"= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = "
								+ "= = = = = = = = = = = =\n");	
								
								// Display Coupon Information
								for (int i = 0; i < couponSortedList.size(); i++) {
									Coupon strCoupon = (Coupon) couponSortedList.get(i);
			
									textAreaListCoupon.append(strCoupon.getCouponSite().toString() + "\t\t"
											+ strCoupon.getProductName().toString()+ "\t\t"
											+ strCoupon.getOriginalPrice().toString()+ "\t\t"
											+ strCoupon.getDiscountRate()+ "\t\t"
											+ strCoupon.getExpirationPeriod()+ "\t\t"
											+ strCoupon.getFinalPrice().toString()+ "\t\t"
											+ strCoupon.getCouponStatus().toString() + "\n");	
								}
							}
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnListCoupon.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnListCoupon.setBounds(327, 18, 143, 30);
		panel_2.add(btnListCoupon);
		
		JLabel lblNewLabel = new JLabel("Coupon Inventory Systems (CIS)");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 68));
		lblNewLabel.setBounds(107, 11, 1155, 117);
		frame.getContentPane().add(lblNewLabel);		
	
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					textAreaListCoupon.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPrint.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnPrint.setBounds(39, 563, 143, 30);
		frame.getContentPane().add(btnPrint);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame = new JFrame();
				if(JOptionPane.showConfirmDialog(frame, "Confirm if you want to exit",
						"Coupon Inventory Systems (CIS)",
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnExit.setBounds(350, 563, 143, 30);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textCouponProvider.setText(null);
				textOriginalPrice.setText(null);
				textExpirationPeriod.setText(null);
				textProductName.setText(null);
				textDiscountRate.setText(null);
				textSearchCoupon.setText(null);
				textSearchResult.setText(null);
				textSearchCount.setText(null);
				textAreaListCoupon.setText(null);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClear.setBounds(197, 563, 143, 30);
		frame.getContentPane().add(btnClear);
	}
}
