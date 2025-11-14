package views.components;

import javax.swing.*;

public class ConfirmDialog {

    public static boolean show(String title, String message) {
        int option = JOptionPane.showConfirmDialog(
                null,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        return option == JOptionPane.YES_OPTION;
    }
}
