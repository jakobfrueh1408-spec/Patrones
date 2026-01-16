package View;

import Model.Calendar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthView extends JPanel {

    private final YearMonth yearMonth;

    public MonthView(int year, int month, Calendar calendar) {
        this.yearMonth = YearMonth.of(year, month);

        setLayout(new GridLayout(0, 7)); // 7 oszlop, sorok automatikusan
        buildMonth();
    }

    private void buildMonth() {
        removeAll();

        // 1️⃣ Fejléc – hét napjai
        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel header = new JLabel(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    SwingConstants.CENTER
            );
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            add(header);
        }

        // 2️⃣ Első nap eltolása
        LocalDate firstDay = yearMonth.atDay(1);
        int firstDayColumn = firstDay.getDayOfWeek().getValue() % 7;
        // (Hétfő=1 ... Vasárnap=7 → Vasárnap=0)

        for (int i = 0; i < firstDayColumn; i++) {
            add(createEmptyCell());
        }

        // 3️⃣ Napok
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            add(createDayCell(day));
        }

        revalidate();
        repaint();
    }

    // ----- segéd panelek -----

    private JPanel createDayCell(int day) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel label = new JLabel(String.valueOf(day));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));

        panel.add(label, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        return panel;
    }
}
