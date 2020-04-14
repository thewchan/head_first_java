import java.io.*;
import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DayOfTheWeekService implements Service {

    JLabel outputLabel;
    JComboBox<String> month;
    JTextField day;
    JTextField year;

    public class DoItListener implements ActionListener {

        public void actionPerformed(ActionEvent ev) {
            int monthNum = month.getSelectedIndex();
            int dayNum = Integer.parseInt(day.getText());
            int yearNum = Integer.parseInt(year.getText());
            Calendar c = Calendar.getInstance();

            c.set(Calendar.MONTH, monthNum);
            c.set(Calendar.DAY_OF_MONTH, dayNum);
            c.set(Calendar.YEAR, yearNum);
            
            Date date = c.getTime();
            String dayOfWeek = (new SimpleDateFormat("EEEE")).format(date);

            outputLabel.setText(dayOfWeek);
        }
    }

    public JPanel getGuiPanel() {
        JPanel panel = new JPanel();
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JButton button = new JButton ("Do it");
        outputLabel = new JLabel("Date appears here");
        DateFormatSymbols dateStuff = new DateFormatSymbols();
        month = new JComboBox<String>(dateStuff.getMonths());
        day = new JTextField(8);
        year = new JTextField(8);

        button.addActionListener(new DoItListener());
        inputPanel.add(new JLabel("Month"));
        inputPanel.add(month);
        inputPanel.add(new JLabel("Day"));
        inputPanel.add(day);
        inputPanel.add(new JLabel("Year"));
        inputPanel.add(year);
        panel.add(inputPanel);
        panel.add(button);
        panel.add(outputLabel);
        return panel;

    }
}