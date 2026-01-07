package View;

import javax.swing.*;
import java.awt.*;

public class CalendarView extends JPanel {
    //length is the number of days in the month
    //firstDay declares the day of the week (1-7) when the month starts
        //for example: if february starts on a wednesday: length: 28    firstDay: 3
    public CalendarView(int length, int firstDay) {
        int numOfRows = length / 7;
        //if the month doesn't start with a monday, we need one extra row in the end
        if(firstDay != 1)
            numOfRows++;

        //there will be as many rows as needed, but fixed 7 columns
        this.setLayout(new GridLayout(0, 7, 5, 5));

        //filling up the first row
        add(new JLabel("Monday"));
        add(new JLabel("Tuesday"));
        add(new JLabel("Wednesday"));
        add(new JLabel("Thursday"));
        add(new JLabel("Friday"));
        add(new JLabel("Saturday"));
        add(new JLabel("Sunday"));


        //the first row has to be different, based on the firstday
        //the last row has to be different as well
        //i for columns, j for rows
        for(int j = 0; j < numOfRows; j++){
            for(int i = 1; i <= 7; i++){
                //if we are in the first row (j=0) the days before firstday have to be empty)
                if(j == 0 && i < firstDay){
                    JButton b = new JButton("");
                    add(b);
                } else {
                    //the number of the cell equals to the num of week times 7, and the number of days of the current week.
                    //however, the first day also depends on the firstday, thats why it has to be subtracted
                    int num = j * 7 + i - firstDay + 1;
                    //if num is greater than the length of the month, the button is empty again
                    if(num <= length){
                        JButton button = new JButton(Integer.toString(num));
                        add(button);
                    } else {
                        JButton b = new JButton("");
                    }
                }
            }
        }
    }
}
