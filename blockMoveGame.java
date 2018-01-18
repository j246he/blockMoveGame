package blockMoveGame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class blockMoveGame extends JFrame {
	int row=3;
	int column=3;
	int boxNum=row*column;//the number of the blocks in the board
	int []boxNums=new int[boxNum];
	
	JButton start=new JButton("start game");//press button to start the game
	JButton []box=new JButton[boxNum];
	
	public blockMoveGame() {
		setTitle("Block Move Game");
		setSize(400,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);//the program exist when the window closes
		
		JPanel panelBox = new JPanel();
		JPanel panelButton = new JPanel();
		panelBox.setLayout(new GridLayout(row,column));
		panelButton.setLayout(new GridLayout(1,1));
		
		setLayout(new BorderLayout());//the wholt layout
		add(panelBox,BorderLayout.CENTER);
		add(panelButton,BorderLayout.SOUTH);

		panelButton.add(start);
		for(int i=0;i<boxNum;i++) {
			box[i]=new JButton(""+(i+1));
			panelBox.add(box[i]);
			box[i].setVisible(true);
		}
		box[boxNum-1].setVisible(false);
		
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameStart();
			}
		});
		
		for(int i=0;i<boxNum;i++) {
			box[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					for( int j=0; j<boxNum; j++ )
						if( (JButton)e.getSource() == box[j] )
							buttonClick(j);			
				}
			});	
		}	
	}
	
	protected void buttonClick(int i) {
		int blank = 0;
		for(int j=0;j<boxNum;j++) {
			if(Integer.parseInt(box[j].getText())==boxNum) {
				blank=j;
				break;
			}
		}
		if (blankIsNeighbour(blank,i)==true) {
			changeBlock(blank,i);
			box[i].setVisible(false);
			box[blank].setVisible(true);
		}
		if(gameFinish()==true) {
			String options[]={"new game","exit"};
			int value=JOptionPane.showOptionDialog(null, "Do you want to continue the game?",
					"congratulations!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, "new game");
			if (value!=JOptionPane.CLOSED_OPTION) {
				switch (value) {
				case 0:gameStart();break;
				case 1:System.exit(0);break;
				}
			}	
		}
	}

	private void changeBlock(int i, int j) {
		String a=box[i].getText();
		String b=box[j].getText();
		box[i].setText(b);
		box[j].setText(a);
	}

	private boolean gameFinish() {
		for(int i=0;i<boxNum;i++) {
			if(Integer.parseInt(box[i].getText())!=(i+1)) {
				return false;
			}
		}
		return true;
	}

	private boolean blankIsNeighbour(int blank,int i) {
		if(blank-i==1)return true;
		else if(i-blank==1)return true;
		else if(blank-i==column)return true;
		else if(i-blank==column)return true;
		else{
			JOptionPane.showMessageDialog(this,"you can't move this block!");
			return false;
		}
	}

	protected void gameStart() {
		for(int i=0;i<boxNum;i++) {
			int a=(int) (Math.random()* boxNum);
			int b=(int) (Math.random()* boxNum);
			changeBlock(a,b);
		}//random move the boxes
		for(int i=0;i<boxNum;i++) {
			//System.out.println(box[i].getText());
			if(Integer.parseInt(box[i].getText())==boxNum) {
				box[i].setVisible(false);
			}
			else {
				box[i].setVisible(true);
			}
		}//show the box except the box with the largest number
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new blockMoveGame().setVisible(true);
		});
	}
}
