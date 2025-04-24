import java.awt.*;
import javax.swing.*;

public class UserInterface {

    private JFrame window;
    private GridBagConstraints gbc;
    
    public UserInterface() {
        this.window = new JFrame("Chatbot");
        window.setSize(400, 500);
        window.setLayout(new GridBagLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel welcome_label = new JLabel("Welcome to the Chatbot");
        welcome_label.setFont(new Font("Serif", Font.BOLD, 20)); 
        window.add(welcome_label, gbc);

        gbc.gridy = 1;
        JButton start_button = new JButton("Start Chatting");
        start_button.addActionListener(e -> setChat());
        window.add(start_button, gbc);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void setChat() {
        window.getContentPane().removeAll();

        gbc.gridx = 0;
        gbc.gridy = 0;
        JTextArea text_area = new JTextArea(20, 30);
        text_area.setLineWrap(true);
        text_area.setWrapStyleWord(true);
        text_area.setEditable(false);
        text_area.append("BOT: Hello, welcome to the chat\n");

        JScrollPane scroll_pane = new JScrollPane(text_area);
        scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        window.add(scroll_pane);

        gbc.gridy = 1;
        JPanel user_input_panel = new JPanel();
        JTextArea user_text_area = new JTextArea(3, 20);
        user_text_area.setLineWrap(true);
        user_text_area.setWrapStyleWord(true);
        JButton send_button = new JButton("Send");
        send_button.addActionListener(e -> {sendMessage(text_area, user_text_area);
                                            sendChatBotMessage(text_area);});

        user_input_panel.add(user_text_area);
        user_input_panel.add(send_button);
        window.add(user_input_panel, gbc);
        
        window.revalidate();
        window.repaint();
    }

    public void sendMessage(JTextArea chat, JTextArea user_message) {
        String message = user_message.getText();
        chat.append("USER: " + message + "\n");
        user_message.setText("");
    }

    public void sendChatBotMessage(JTextArea chat) {
        String message = "Bot not implemented yet, this is a filler message.";
        chat.append("BOT: " + message + "\n");
    }
}