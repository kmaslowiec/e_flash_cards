package e_flash_cards;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;


public class QuizCardBuilder extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel questionLabel;
	private JLabel answerLabel;
	private JTextArea questionText;
	private JTextArea answerText;
	private JMenuBar menuBar;
	private JMenu menuBarFile;
	private JMenuItem menuItemNew;
	private JButton nextButton;
	private ArrayList<QuizCard> cards = new ArrayList<>();
	private JMenuItem menuItemClear;
	private JMenuItem menuItemSave;
	private static QuizCardBuilder frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					frame = new QuizCardBuilder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame. Constructor
	 */
	public QuizCardBuilder() {
		gui();
	}

	/**
	 * implements the whole GUI
	 */

	public void gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		menuBar();
		questionArea();
		answerArea();
		buttonNext();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(questionText,
								GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(278).addComponent(questionLabel))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap().addComponent(answerText,
								GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(279).addComponent(answerLabel)))
				.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(271, Short.MAX_VALUE)
						.addComponent(nextButton).addGap(260)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(5).addComponent(questionLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(questionText, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(answerLabel).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(answerText, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(nextButton).addContainerGap(40, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
	}

	/**
	 * Creates the question text field and label
	 */
	public void questionArea() {
		questionLabel = new JLabel("Question:");
		questionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		questionText = new JTextArea();
		questionText.setBorder(BorderFactory.createLoweredBevelBorder());

	}

	/**
	 * Creates the answer text field and label
	 */
	public void answerArea() {
		answerLabel = new JLabel("Answer:");
		answerLabel.setFont(new Font("Tahoma", Font.BOLD, 14));

		answerText = new JTextArea();
		answerText.setBorder(BorderFactory.createLoweredBevelBorder());

	}

	/**
	 * Creates button to "switch" to the next card
	 */

	public void buttonNext() {
		nextButton = new JButton("NEXT CARD");
		nextButton.addActionListener(new ButtonListener());
		nextButton.setFont(new Font("Tahoma", Font.BOLD, 11));
	}

	/**
	 * Creates Menu Bar with the items
	 * 
	 */

	public void menuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		menuBarFile = new JMenu("File");
		menuBar.add(menuBarFile);

		menuItemNew = new JMenuItem("New");
		menuItemNew.addActionListener(new NewListener());
		menuBarFile.add(menuItemNew);

		menuItemClear = new JMenuItem("Clear");
		menuItemClear.addActionListener(new ClearListener());

		menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(new SaveListener());
		menuBarFile.add(menuItemSave);
		menuBarFile.add(menuItemClear);
	}

	// Logic:
	/**
	 * Clears the answer and question JTextFields
	 */
	public void clear() {
		questionText.setText(null);
		answerText.setText(null);
		questionText.requestFocus();
	}

	/**
	 * Temp method
	 */
	public void printText() {

		String text = questionText.getText();
		System.out.println(text);

	}

	/**
	 * Logic: Saving the cards
	 * 
	 * @param file
	 */

	public void saveFile(File file) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file + ".txt"))) {

			// if (questionText.getText() != null && answerText.getText() != null) {

			for (QuizCard card : cards) {
				writer.write(card.getQuestion() + "/");
				writer.write(card.getAnswer() + System.lineSeparator());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addCard() {

		cards.add(new QuizCard(questionText.getText(), answerText.getText()));

	}

	// Action listeners:

	/**
	 * 
	 * Action listener for new in Bar Menu
	 *
	 */

	public class NewListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			clear();
			cards.clear();

		}

	}

	/**
	 * 
	 * Action: save cards ArrayList to the txt file
	 *
	 */

	public class SaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addCard();
			
			JFileChooser fileSave = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files","txt");
			fileSave.addChoosableFileFilter(filter);
			fileSave.setAcceptAllFileFilterUsed(false);
			fileSave.setCurrentDirectory(new File("C:\\Users\\Kondzik\\Desktop\\EFlashCardsSaves"));
			fileSave.showSaveDialog(frame);
			
			
		
			saveFile(fileSave.getSelectedFile());

		}

	}

	/**
	 * 
	 * Action: Clears both JTextFields (questionText and answerText)
	 *
	 */

	public class ClearListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			clear();

		}

	}

	/**
	 * 
	 * Action: Adds button function. Takes text from both JTextFields and adds to
	 * cards ArrayList
	 *
	 */

	public class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			addCard();
			clear();
		}

	}

}
