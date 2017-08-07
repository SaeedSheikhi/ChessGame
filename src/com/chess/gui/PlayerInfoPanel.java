package com.chess.gui;

import com.chess.engine.player.Member;

import com.sun.deploy.panel.JavaPanel;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Asus on 2017-08-02.
 */
public class PlayerInfoPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;
    private final Member loggedMember;
    private BufferedImage hostImage;
    private BufferedImage guestImage;

    public PlayerInfoPanel(final Member hostMember){
        super();
        this.loggedMember = hostMember;

        this.northPanel = new JPanel();
        this.northPanel.setLayout(new BorderLayout());
        this.southPanel = new JPanel();
        this.southPanel.setLayout(new BorderLayout());
//        this.southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        try {
            hostImage = ImageIO.read(new File(hostMember.getImagePath()));
            guestImage = ImageIO.read(new File(hostMember.getImagePath()));
        } catch (IOException e) {
            throw new RuntimeException("can't Load Pic");

        }
        setLayout(new BorderLayout());
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);

        northPanel.add(new JLabel(new ImageIcon(new ImageIcon(hostImage).getImage().getScaledInstance(hostImage.getWidth() - 300, hostImage.getHeight() - 200, Image.SCALE_SMOOTH))), BorderLayout.CENTER);
        southPanel.add(new JLabel(new ImageIcon(new ImageIcon(hostImage).getImage().getScaledInstance(hostImage.getWidth() - 300, hostImage.getHeight() - 200, Image.SCALE_SMOOTH))), BorderLayout.CENTER);


    }





}
