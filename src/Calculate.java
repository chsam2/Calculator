import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Calculate extends JFrame {
	
	static JLabel label;
	static JLabel info;
	static int flag = 0;
	static int check = 0;
	static double record = 0;
	
	public Calculate() {
		setTitle("계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout(5,5));
		c.setBackground(Color.BLACK);
		
		EastPanel EP = new EastPanel();
		c.add(EP, BorderLayout.EAST);
		
		SouthPanel SP = new SouthPanel();
		c.add(SP, BorderLayout.SOUTH);

		setSize(500, 700);
		setVisible(true);
	}
	

		
	class EastPanel extends JPanel {
		public EastPanel() {
			setLayout(new GridLayout(3,1));	
			setBackground(Color.BLACK);
			info = new JLabel("수식을 입력하세요 ");
			label = new JLabel(""); 
			
			info.setFont(new Font("맑은 고딕", 0, 20));		
			info.setBackground(Color.BLACK);
			info.setForeground(Color.WHITE);
			info.setHorizontalAlignment(SwingConstants.RIGHT);
			
			label.setFont(new Font("맑은 고딕", 0, 40));
			label.setBackground(Color.BLACK);
			label.setForeground(Color.WHITE);		
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			
			add(info); 
			add(label); 			
			//label.addMouseListener(new MyMouse());
		}
	}
	/*
	class MyMouse extends MouseAdapter {//Label더블 클릭시 초기화
		public void mousePressed(MouseEvent e) {	
			if (e.getClickCount() == 2) {
				flag = 0;
				label.setText(""); 
				info.setText("수식을 입력하세요 ");
			}		
		}
	}
	*/

	class SouthPanel extends JPanel {
		public SouthPanel() {
			JButton[] bt = new JButton[20];
			setBackground(Color.BLACK);
			setLayout(new GridLayout (5,4,5,5));
			
			bt[0] = new JButton("Re");
			bt[1] = new JButton("P");//이전 계산값
			bt[2] = new JButton("^");//제곱
			bt[3] = new JButton("÷");
			
			bt[4] = new JButton("7");
			bt[5] = new JButton("8");
			bt[6] = new JButton("9");
			bt[7] = new JButton("×");
			
			bt[8] = new JButton("4");
			bt[9] = new JButton("5");
			bt[10] = new JButton("6");
			bt[11] = new JButton("-");
			
			bt[12] = new JButton("1");
			bt[13] = new JButton("2");
			bt[14] = new JButton("3");
			bt[15] = new JButton("+");
			
			bt[16] = new JButton("C");
			bt[17] = new JButton("0");
			bt[18] = new JButton(".");
			bt[19] = new JButton("=");
			
			for (int i = 0; i < 20; i++) {	
				if (i==4 || i==5 || i==6 || i==8 || i==9 || i==10 || i==12 || i==13 || i==14 || i==17) {
					bt[i].setFont(new Font("맑은 고딕", 0, 30));
					bt[i].setBackground(Color.GRAY);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);
					
					bt[i].addActionListener(new ActionListener () {//숫자
						public void actionPerformed(ActionEvent e) {
							if (flag == 0) {
								JButton b = (JButton)e.getSource();
								String oldtext = label.getText();
								String text = b.getText();
								String newtext = oldtext + text;
								
								int n = newtext.length();
								if (n <= 10) label.setFont(new Font("맑은 고딕", 0, 40));
								else if (n > 10) label.setFont(new Font("맑은 고딕", 0, 30));	
								
								if (n <= 25) {
									label.setText(newtext);
									info.setText("수식을 계산 중입니다 ");
								}
								else if (n > 25) info.setText("입력 가능한 범위를 초과하였습니다 ");
							}					
						}
					});
				}
				
				else if (i == 16) {//C
					bt[i].setFont(new Font("맑은 고딕", 0, 30));
					bt[i].setBackground(Color.LIGHT_GRAY);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);
					bt[i].addActionListener(new Cancel ());				
				}
				else if (i == 19) {//=
					bt[i].setFont(new Font("맑은 고딕", 0, 30));
					bt[i].setBackground(Color.ORANGE);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);			
					bt[i].addActionListener(new CalcListener());
				}
				
				else if (i==2 || i==3 || i==7 || i==11 || i==15){//연산자
					bt[i].setFont(new Font("맑은 고딕", 0, 40));
					bt[i].setBackground(new Color (234,150,72));
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);				
					bt[i].addActionListener(new MyListener());
				}
				else if (i==1){//P
					bt[i].setFont(new Font("맑은 고딕", 0, 40));
					bt[i].setBackground(Color.GRAY);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);				
					bt[i].addActionListener(new Past());
				}
				
				else if (i == 18) {//.
					bt[i].setFont(new Font("맑은 고딕", 0, 30));
					bt[i].setBackground(Color.GRAY);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);
					bt[i].addActionListener(new Dot());
				}
				else if(i == 0) {//Re
					bt[i].setFont(new Font("맑은 고딕", 0, 30));
					bt[i].setBackground(Color.LIGHT_GRAY);
					bt[i].setForeground(Color.WHITE);
					add(bt[i]);			
					bt[i].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {	
							flag = 0;
							label.setText(""); 
							info.setText("수식을 입력하세요 ");
						}
					});
				}
			}				
		}
	}
	
	private class Cancel implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int n = label.getText().length()-1;
			if (n == 0) {
				label.setText("");
				info.setText("수식을 입력하세요 ");
				flag = 0;
			}						
			else if (n > 0 && n <= 10) {
				label.setFont(new Font("맑은 고딕", 0, 40));
				label.setText(label.getText().substring(0, n));
				info.setText("수식을 지우는 중입니다 ");		
			}						
			else {
				label.setFont(new Font("맑은 고딕", 0, 35));
				label.setText(label.getText().substring(0, n));
				info.setText("수식을 지우는 중입니다 ");
			}
		}
	}

	private class MyListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			int n = label.getText().length();
			Character c = label.getText().charAt(n-1);
			if (flag == 0 && c != '+' && c != '-' && c != '×' && c != '÷' && c != '^' && c != '.') {
				String oldtext = label.getText();
				String text = b.getText();
				String newtext = oldtext + text;
				label.setText(newtext);
				info.setText("수식을 계산 중입니다 ");
			}		
		}
	}
	private class Past implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String oldtext = label.getText();
			String text = Double.toString(record);
			int old_len = oldtext.length();
			Character c1 = oldtext.charAt(old_len-1);
			String s1 = Character.toString(c1);
			int text_len = text.length();
			Character c2 = text.charAt(text_len-1);
			String s2 = Character.toString(c2);
			if (flag == 0 && (c1 == '+' || c1 == '-' || c1 == '×' || c1 == '÷' || c1 == '^' || c1==null)) {
				if(record<0) {
					if(s1.equals("+")) {
						oldtext=oldtext.substring(0, old_len-1);
					}
					else if(s1.equals("-")) {
						text=text.substring(1, text_len);
						oldtext=oldtext.substring(0, old_len-1)+"+";
					}
					else if(s1.equals("×") || s1.equals("÷")) {
						text=text.substring(1, text_len);
						StringBuffer sb = new StringBuffer(oldtext);
						String reversedStr = sb.reverse().toString();
						int a = 0;
						for(int i = 0; i<old_len; i++) {
							if(reversedStr.charAt(i) == '+' || reversedStr.charAt(i) == '-' || i==old_len-1) {
								a = i;
								break;
							}
						}
						int b = old_len-a-1;
						if(oldtext.charAt(b) == '+') {
							oldtext = oldtext.substring(0, b) + '-'+ oldtext.substring(b + 1);
							System.out.println(1);
						}
						else if(oldtext.charAt(b) == '-') {
							oldtext = oldtext.substring(0, b) + '+'+ oldtext.substring(b + 1);
							System.out.println(2);
						}
						else if (b==0){
							oldtext = '-'+ oldtext;
							System.out.println(3);
						}
					}
					else if(s1.equals("^")) {
						text=text.substring(1, text_len);
						StringBuffer sb = new StringBuffer(oldtext);
						String reversedStr = sb.reverse().toString();
						int a = 0;
						for(int i = 0; i<old_len; i++) {
							if(reversedStr.charAt(i) == '+' || reversedStr.charAt(i) == '-' || reversedStr.charAt(i) == '×' || reversedStr.charAt(i) == '÷' ||i==old_len-1) {
								a = i;
								break;
							}
						}
						int b = old_len-a-1;
						if(b==0) {
							oldtext =  "1÷"+ oldtext;
						}
						else {
							oldtext = oldtext.substring(0, b+1) + "1÷"+ oldtext.substring(b + 1);
						}
					}
				}
				String newtext = oldtext + text;
				
				int n = newtext.length();
				if (n <= 10) label.setFont(new Font("맑은 고딕", 0, 40));
				else if (n > 10) label.setFont(new Font("맑은 고딕", 0, 30));	
				
				if (n <= 25) {
					label.setText(newtext);
					info.setText("수식을 계산 중입니다 ");
				}
				else if (n > 25) info.setText("입력 가능한 범위를 초과하였습니다 ");
			}		
		}
	}
	private class Dot implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			int n = label.getText().length();
			Character c = label.getText().charAt(n-1);
			if (flag == 0 && c != '+' && c != '-' && c != '×' && c != '÷' && c != '^' && c != '.') {
				String oldtext = label.getText();
				String text = b.getText();
				String newtext = oldtext + text;
				label.setText(newtext);
				info.setText("수식을 계산 중입니다 ");
			}	
		}
	}

	public class CalcListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {		
			String formula = label.getText();		
			double result = Calculator(formula);
			record = result;
			label.setFont(new Font("맑은 고딕", 0, 40));
			
			if (result < -10000000) {
				info.setText("-10,000,000 이상의 범위만 계산할 수 있습니다 ");
				label.setText(Double.toString(result));
				flag = 1;
			}	
			else if (result >= 10000000) {
				info.setText("10,000,000 미만의 범위만 계산할 수 있습니다 ");
				label.setText("0.0");
			}
			else label.setText(Double.toString(result));					
		}
	}

	public double Calculator(String formula) {	
		int i; 
		double ans;
		check = 0;
		ArrayList<Double> v = new ArrayList<Double>();
		ArrayList<String> op = new ArrayList<String>();
		
		op.add(null);//0번째 index는 null
		String str = new String("");
		for (i = 0; i < formula.length(); i++) {//v에 숫자 op에 문자 넣기
			Character c = formula.charAt(i);
			String s = Character.toString(c);
			
			if(Character.isDigit(c)) {
				str += Character.toString(c);
				if(i == formula.length()-1)
					v.add(Double.parseDouble(str));
			}
			else if (s.equals(".")) str += s;
			else if (s.equals("-") && i==0) {
				v.add(Double.parseDouble("0"));
				op.add(Character.toString(c));
			}
			else if (s.equals("+") && i==0) {
				
			}
			else {
				v.add(Double.parseDouble(str));
				op.add(Character.toString(c));
				str = "";
			}
		}
		
		for(i = 0; i < v.size(); i++) {//숫자의 크기점검
			if (v.get(i) >= 10000000) {
				check = 1;		
				info.setText("10,000,000 미만의 수끼리만 계산할 수 있습니다 ");
				break;
			}
		}
		
		if (check == 0) {
			for(i = 1; i < v.size(); i++) {//제곱부터 계산
				String s = op.get(i);
				double tmp;
				
				if (s.equals("^")) {
					tmp = Math.pow(v.get(i-1), v.get(i));
					op.remove(i);
					v.remove(i);
					v.remove(i-1);				
					v.add(i-1, tmp);
					i--;
				}
			}
			
			for(i = 1; i < v.size(); i++) {//제곱부터 계산
				String s = op.get(i);
				double tmp;
				
				if (s.equals("×")) {
					tmp = v.get(i-1) * v.get(i);
					op.remove(i);
					v.remove(i);
					v.remove(i-1);				
					v.add(i-1, tmp);
					i--;
				}	
				else if (s.equals("÷")) {
					tmp = v.get(i-1) / v.get(i);
					op.remove(i);
					v.remove(i);
					v.remove(i-1);		
					v.add(i-1, tmp);
					i--;
				}
			}
			
			ans = v.get(0);
			for(i = 1; i < v.size(); i++) {//합 계산
				String s = op.get(i);
				double n = v.get(i);
				
				if(s.equals("+")) ans = ans + n;
				else if(s.equals("-")) ans = ans - n;
			}
			
			return ans;
		}	
		
		return 0;
	}
	
	public static void main(String[] args) {
		new Calculate();
		//i can do it!

	}
}