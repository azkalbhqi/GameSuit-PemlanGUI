package PemlanPraktek.tugas7GUI;
//AZKAL BAIHAQI PUTRA SANDITA
//235150407111001

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class Rps extends JFrame implements ActionListener {
    private JButton batuBtn, kertasBtn, guntingBtn;
    private JLabel playerLabel, komLabel, hasiLabel;
    private JLabel pScoreLabel, comScoreLabel;
    private int playerScore, computerScore;

    private ImageIcon batuIcon, kertasIcon, guntingIcon;
    private ImageIcon cbatuIcon, ckertasIcon, cguntingIcon;

    private Clip winClip, loseClip, drawClip;

    public Rps() {
        setTitle("Game Suitt");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Set the background color of the JFrame to white
        getContentPane().setBackground(Color.WHITE);

        // Initialize scores
        playerScore = 0;
        computerScore = 0;

        // Load images using ClassLoader
        batuIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/urock.gif"));
        kertasIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/upaper.gif"));
        guntingIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/uscissors.gif"));
        
        cbatuIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/crock.gif"));
        ckertasIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/cpaper.gif"));
        cguntingIcon = new ImageIcon(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/cscissors.gif"));

        // User and computer choice labels with icons
        playerLabel = new JLabel("Your choice: ");
        playerLabel.setHorizontalTextPosition(JLabel.CENTER);
        playerLabel.setVerticalTextPosition(JLabel.TOP);
        playerLabel.setOpaque(true);
        playerLabel.setBackground(Color.WHITE);

        komLabel = new JLabel("Komputer: ");
        komLabel.setHorizontalTextPosition(JLabel.CENTER);
        komLabel.setVerticalTextPosition(JLabel.TOP);
        komLabel.setOpaque(true);
        komLabel.setBackground(Color.WHITE);

        hasiLabel = new JLabel("", SwingConstants.CENTER);
        hasiLabel.setOpaque(true);
        hasiLabel.setBackground(Color.WHITE);

        JPanel choicesPanel = new JPanel();
        choicesPanel.setLayout(new GridBagLayout());
        choicesPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        choicesPanel.add(hasiLabel, gbc);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        choicesPanel.add(playerLabel, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        choicesPanel.add(komLabel, gbc);

        add(choicesPanel, BorderLayout.CENTER);

        // Buttons
        batuBtn = new JButton("Batu");
        kertasBtn = new JButton("Kertas");
        guntingBtn = new JButton("Gunting");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 50, 0));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.add(batuBtn);
        buttonsPanel.add(guntingBtn);
        buttonsPanel.add(kertasBtn);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Add action listeners
        batuBtn.addActionListener(this);
        kertasBtn.addActionListener(this);
        guntingBtn.addActionListener(this);

        // Score labels
        pScoreLabel = new JLabel("Skor Kamu: 0");
        comScoreLabel = new JLabel("Skor Komputer: 0");

        JPanel scorePanel = new JPanel(new BorderLayout());
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setBorder(new EmptyBorder(100, 100, 0, 100));
        scorePanel.add(pScoreLabel, BorderLayout.WEST);
        scorePanel.add(comScoreLabel, BorderLayout.EAST);

        add(scorePanel, BorderLayout.NORTH);

        // Load sound clips
        try {
            winClip = AudioSystem.getClip();
            loseClip = AudioSystem.getClip();
            drawClip = AudioSystem.getClip();

            winClip.open(AudioSystem.getAudioInputStream(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/win.wav")));
            loseClip.open(AudioSystem.getAudioInputStream(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/lose.wav")));
            drawClip.open(AudioSystem.getAudioInputStream(getClass().getResource("/PemlanPraktek/tugas7GUI/assets/draw.wav")));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userChoice = e.getActionCommand();

        switch (userChoice) {
            case "Batu":
                playerLabel.setIcon(batuIcon);
                break;
            case "Kertas":
                playerLabel.setIcon(kertasIcon);
                break;
            case "Gunting":
                playerLabel.setIcon(guntingIcon);
                break;
        }

        String computerChoice = getComputerChoice();

        switch (computerChoice) {
            case "Batu":
                komLabel.setIcon(cbatuIcon);
                break;
            case "Kertas":
                komLabel.setIcon(ckertasIcon);
                break;
            case "Gunting":
                komLabel.setIcon(cguntingIcon);
                break;
        }

        String result = determineWinner(userChoice, computerChoice);
        hasiLabel.setText("" + result);

        // Update scores and play sound
        if (result.equals("Kamu Menang!!")) {
            playerScore++;
            playSound(winClip);
        } else if (result.equals("Wkwk Kalah")) {
            computerScore++;
            playSound(loseClip);
        } else {
            playSound(drawClip);
        }
        pScoreLabel.setText("Skor Kamu: " + playerScore);
        comScoreLabel.setText("Skor Komputer: " + computerScore);
    }

    private String getComputerChoice() {
        String[] choices = {"Batu", "Kertas", "Gunting"};
        Random random = new Random();
        int index = random.nextInt(choices.length);
        return choices[index];
    }

    private String determineWinner(String userChoice, String computerChoice) {
        if (userChoice.equals(computerChoice)) {
            return "Seri Brow!";
        } else if ((userChoice.equals("Batu") && computerChoice.equals("Gunting")) ||
                   (userChoice.equals("Kertas") && computerChoice.equals("Batu")) ||
                   (userChoice.equals("Gunting") && computerChoice.equals("Kertas"))) {
            return "Kamu Menang!!";
        } else {
            return "Wkwk Kalah";
        }
    }

    private void playSound(Clip clip) {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Rps frame = new Rps();
            frame.setVisible(true);
        });
    }
}
