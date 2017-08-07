package com.chess;

import com.chess.gui.FxTable;
import com.chess.gui.LoginPage;
import com.chess.gui.Table;
import javafx.application.Application;

import javax.swing.*;

public class RunChess {

    public static void main(final String args[]) throws Exception {

        JOptionPane.showMessageDialog(null, "Saeed Sheikhi \nSaeedSheikhi@outlook.com\nVisit MyGitHub Account:\ngithub.com/SaeedSheikhi","Developed By:",JOptionPane.INFORMATION_MESSAGE );
//        Table.get().show();
//     Application.launch(LoginPage.class, (java.lang.String[])null);
        Application.launch(FxTable.class, (java.lang.String[])null);

    }
}
