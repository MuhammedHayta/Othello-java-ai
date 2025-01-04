import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Player.HumanPlayer;
import Player.Player;

public class SwingOthelloUI extends JFrame {
    private Othello othello;
    private Player player1;
    private Player player2;
    private int currentPlayer = 1; 
    private int[] board;
    private JPanel boardPanel;
    private JLabel statusLabel;

    public SwingOthelloUI() {
        super("Othello - Swing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        showMenu();
    }

    private void showMenu() {
        JPanel menuPanel = new JPanel(new FlowLayout());
        String[] playerOptions = {"Human"}; 
        JComboBox<String> p1Dropdown = new JComboBox<>(playerOptions);
        JComboBox<String> p2Dropdown = new JComboBox<>(playerOptions);
        JButton startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            // For now, always create Human players
            player1 = new HumanPlayer();
            player2 = new HumanPlayer();
            remove(menuPanel);
            initGame();
            revalidate();
            repaint();
        });

        menuPanel.add(new JLabel("Player 1:"));
        menuPanel.add(p1Dropdown);
        menuPanel.add(new JLabel("Player 2:"));
        menuPanel.add(p2Dropdown);
        menuPanel.add(startButton);
        add(menuPanel, BorderLayout.CENTER);
    }

    private void initGame() {
        othello = new Othello(8);
        board = othello.initializeBoard();
        boardPanel = new JPanel(new GridLayout(8, 8));
        statusLabel = new JLabel("Othello Game");
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        statusLabel.setText("Player " + currentPlayer + " (" + (currentPlayer == 1 ? "Black" : "White") + ")'s turn");
        drawBoard();
    }

    private void drawBoard() {
        boardPanel.removeAll();
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                JPanel tile = new JPanel();
                tile.setPreferredSize(new Dimension(50, 50));
                int val = board[x * 8 + y];
                if (val == 0) {
                    tile.setBackground(Color.GREEN);
                } else if (val == 1) {
                    tile.setBackground(Color.BLACK);
                } else {
                    tile.setBackground(Color.WHITE);
                }
                List<int[]> validMoves = othello.getValidMoves(board, currentPlayer);
                if (isValidMoveInList(validMoves, x, y)) {
                    tile.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
                }
                final int fx = x, fy = y;
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleTileClick(fx, fy);
                    }
                });
                boardPanel.add(tile);
            }
        }
        revalidate();
        repaint();
    }

    private boolean isValidMoveInList(List<int[]> validMoves, int x, int y) {
        return validMoves.stream().anyMatch(m -> m[0] == x && m[1] == y);
    }

    private void handleTileClick(int x, int y) {
        Player current = (currentPlayer == 1) ? player1 : player2;
        // Let the player record the click:
        current.handleClick(x, y);
        // Attempt to retrieve the move (null if none)
        int[] move = current.getMove(board, currentPlayer);
        if (move != null && othello.isValidMove(board, move[0], move[1], currentPlayer)) {
            board = othello.getUpdatedBoard(board, move[0], move[1], currentPlayer);
            switchPlayer();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid move!");
        }
        drawBoard();
    }

    private void switchPlayer() {
        currentPlayer = 3 - currentPlayer;
        statusLabel.setText("Player " + currentPlayer + " (" + (currentPlayer == 1 ? "Black" : "White") + ")'s turn");
        if (!canPlay(currentPlayer)) {
            currentPlayer = 3 - currentPlayer;
            if (!canPlay(currentPlayer)) {
                showEndGameOverlay();
            }
        }
    }

    private boolean canPlay(int player) {
        return !othello.getValidMoves(board, player).isEmpty();
    }

    private void showEndGameOverlay() {
        int[] scores = othello.getScores(board);
        String msg = "Game Over!\nBlack: " + scores[0] + "  White: " + scores[1];
        JDialog endDialog = new JDialog(this, "Result", true);
        endDialog.setUndecorated(true);
        endDialog.setSize(300, 150);
        endDialog.setLocationRelativeTo(this);
        endDialog.setBackground(new Color(255, 255, 255, 128));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(msg, SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> {
            endDialog.dispose();
            getContentPane().removeAll();
            showMenu();
            revalidate();
            repaint();
        });
        panel.add(restartButton, BorderLayout.SOUTH);
        endDialog.add(panel);
        endDialog.setVisible(true);
    }
}

