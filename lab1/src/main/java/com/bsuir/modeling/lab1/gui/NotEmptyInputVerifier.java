package com.bsuir.modeling.lab1.gui;

import javax.swing.*;

/**
 * Created by Vlad Kanash on 15.9.16.
 */
class NotEmptyInputVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        if (! (input instanceof JTextField)) {
            return true;
        }
        JTextField textField = (JTextField) input;
        if (textField.getText().length() > 0) {
            textField.setText("1");
            return false;
        }

        return true;
    }
}
