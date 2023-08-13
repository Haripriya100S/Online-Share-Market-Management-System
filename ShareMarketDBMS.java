package dbms_prjct_3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class JavaCrud {

	private JFrame frame;
	private JTextField stockName;
	private JTextField currPrice;
	private JTextField strikeprice;
	private JTable table;
	private JTextField search;
	private JTextField stockPremium;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrud window = new JavaCrud();
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
	public JavaCrud() {
		initialize();
		Connect();
		table_load();
	}

	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	/**
	 * Connect
	 */
	 public void Connect()
    {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sharemarket", "root", "");
        }
        catch (ClassNotFoundException ex) 
        {
          ex.printStackTrace();
        }
        catch (SQLException ex) 
        {
        	   ex.printStackTrace();
        }

    }
	 
	 
	/**
	 * Table Load
	 */
	public void table_load()
	    {
	    	try 
	    	{
		    pst = con.prepareStatement("select * from stock_management");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		} 
	    	catch (SQLException e) 
	    	 {
	    		e.printStackTrace();
		  } 
	    }
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Script MT Bold", Font.PLAIN, 40));
		frame.getContentPane().setBackground(new Color(165, 42, 42));
		frame.setBounds(100, 100, 1247, 743);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Share Market Management System");
		lblNewLabel.setForeground(new Color(240, 248, 255));
		lblNewLabel.setBounds(398, 11, 610, 54);
		lblNewLabel.setFont(new Font("Goudy Old Style", Font.BOLD, 44));
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.ORANGE);
		panel.setBackground(new Color(255, 228, 196));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Stock Details", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(101, 96, 461, 308);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Stock Name");
		lblNewLabel_1.setFont(new Font("Papyrus", Font.BOLD, 23));
		lblNewLabel_1.setBounds(47, 42, 149, 44);
		panel.add(lblNewLabel_1);
		
		JLabel label2 = new JLabel("Current Price");
		label2.setFont(new Font("Papyrus", Font.BOLD, 23));
		label2.setBounds(47, 94, 149, 44);
		panel.add(label2);
		
		JLabel lblNewLabel_3 = new JLabel("Strike Price");
		lblNewLabel_3.setFont(new Font("Papyrus", Font.BOLD, 23));
		lblNewLabel_3.setBounds(47, 149, 131, 36);
		panel.add(lblNewLabel_3);
		
		stockName = new JTextField();
		stockName.setHorizontalAlignment(SwingConstants.CENTER);
		stockName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		stockName.setBounds(239, 48, 131, 35);
		panel.add(stockName);
		stockName.setColumns(10);
		
		currPrice = new JTextField();
		currPrice.setHorizontalAlignment(SwingConstants.CENTER);
		currPrice.setFont(new Font("Tahoma", Font.PLAIN, 18));
		currPrice.setColumns(10);
		currPrice.setBounds(239, 100, 131, 35);
		panel.add(currPrice);
		
		strikeprice = new JTextField();
		strikeprice.setHorizontalAlignment(SwingConstants.CENTER);
		strikeprice.setFont(new Font("Tahoma", Font.PLAIN, 18));
		strikeprice.setColumns(10);
		strikeprice.setBounds(239, 151, 131, 35);
		panel.add(strikeprice);
		
		JLabel lblNewLabel_4 = new JLabel("Premium");
		lblNewLabel_4.setFont(new Font("Papyrus", Font.BOLD, 23));
		lblNewLabel_4.setBounds(47, 200, 107, 36);
		panel.add(lblNewLabel_4);
		
		stockPremium = new JTextField();
		stockPremium.setHorizontalAlignment(SwingConstants.CENTER);
		stockPremium.setFont(new Font("Tahoma", Font.PLAIN, 18));
		stockPremium.setBounds(239, 200, 131, 36);
		panel.add(stockPremium);
		stockPremium.setColumns(10);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stock, currentPrice, strikePrice, premium;
				stock = stockName.getText();
				currentPrice = currPrice.getText();
				strikePrice = strikeprice.getText();
				premium = stockPremium.getText();
				try {
					pst = con.prepareStatement("insert into stock_management(Stock, CurrentPrice, StrikePrice, Premium)values(?,?,?,?)");
					pst.setString(1, stock);
					pst.setString(2, currentPrice);
					pst.setString(3, strikePrice);
					pst.setString(4, premium);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!!");
					table_load();
					          
					stockName.setText("");
					currPrice.setText("");
					strikeprice.setText("");
					stockPremium.setText("");
					stockName.requestFocus();
					 }
					 
				catch (SQLException e1)
				{
//					JOptionPane.showMessageDialog(null, "Record Already Found!!");
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Goudy Old Style", Font.BOLD, 25));
		btnNewButton.setBounds(298, 428, 103, 45);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(637, 109, 508, 390);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		panel_1.setBounds(101, 500, 461, 97);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("StockId");
		lblNewLabel_1_1.setBounds(48, 42, 121, 25);
		lblNewLabel_1_1.setFont(new Font("Papyrus", Font.BOLD, 23));
		panel_1.add(lblNewLabel_1_1);
		
		search = new JTextField();
		search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
					
					 try {
				          
				            String getStock = search.getText();

				                pst = con.prepareStatement("select Stock,CurrentPrice,StrikePrice,Premium from stock_management where Id = ?");
				                pst.setString(1, getStock);
				                ResultSet rs = pst.executeQuery();

				            if(rs.next()==true)
				            {
				              
				                String stock = rs.getString(1);
				                String currentPrice = rs.getString(2);
				                String strikePrice = rs.getString(3);
				                String premium = rs.getString(4);
				                
				                stockName.setText(stock);
				                currPrice.setText(currentPrice);
				                strikeprice.setText(strikePrice);
				                stockPremium.setText(premium);
				                JOptionPane.showMessageDialog(null, "Record Found!!");
				                
				                
				            }   
				            else
				            {
				            	stockName.setText("");
				            	currPrice.setText("");
				            	strikeprice.setText("");
				                stockPremium.setText("");
				            }
				            


				        } 
					
					 catch (SQLException ex) {
						 ex.printStackTrace();
				        }
						
				
			}
		});
		search.setHorizontalAlignment(SwingConstants.CENTER);
		search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		search.setBounds(228, 35, 139, 38);
		panel_1.add(search);
		search.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stock, currentPrice, strikePrice, premium, getStock;
				stock = stockName.getText();
				currentPrice = currPrice.getText();
				strikePrice = strikeprice.getText();
				premium = stockPremium.getText();
				getStock = search.getText();
				try {
					pst = con.prepareStatement("update stock_management set Stock= ?, CurrentPrice= ?, StrikePrice= ?, Premium= ? where Id = ?");
					pst.setString(1, stock);
					pst.setString(2, currentPrice);
					pst.setString(3, strikePrice);
					pst.setString(4, premium);
					pst.setString(5, getStock);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Updated!!");
					table_load();
					          
					stockName.setText("");
					currPrice.setText("");
					strikeprice.setText("");
					stockPremium.setText("");
					search.setText("");
					stockName.requestFocus();
					 }
					 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setFont(new Font("Goudy Old Style", Font.BOLD, 25));
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setBounds(667, 540, 120, 45);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String getStock;
				getStock = search.getText();
				try {
					pst = con.prepareStatement("delete from stock_management where Id = ?");
					pst.setString(1, getStock);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Deleted!!");
					table_load();
					          
					stockName.setText("");
					currPrice.setText("");
					strikeprice.setText("");
					stockPremium.setText("");
					search.setText("");
					stockName.requestFocus();
					 }
					 
				catch (SQLException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Goudy Old Style", Font.BOLD, 25));
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setBounds(872, 540, 120, 45);
		frame.getContentPane().add(btnDelete);
	}

}
