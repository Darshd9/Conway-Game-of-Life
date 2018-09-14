
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;


public class Life {

    public static final int SIZE = 50;
    public static final int CELL_SIZE = 30;
    public static List<List<Boolean>> board;
    public static List<List<Boolean>> newBoard;

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Board");
        frame.pack();
        frame.setSize(frame.getWidth() + SIZE * 50, frame.getHeight() + SIZE * 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setSize(2500, 2500);
        panel.setLayout(null);
        JButton glider = new JButton("Glider");
        glider.setFont(new Font("Arial", Font.PLAIN, 30));
        glider.setBounds(1700,100,300,100);
        JButton smallExploder = new JButton("Small Exploder");
        smallExploder.setFont(new Font("Arial", Font.PLAIN, 30));
        smallExploder.setBounds(1700,250,300,100);
        JButton exploder = new JButton("Exploder");
        exploder.setFont(new Font("Arial", Font.PLAIN, 30));
        exploder.setBounds(1700,400,300,100);
        JButton row10 = new JButton("10 Cell Row");
        row10.setFont(new Font("Arial", Font.PLAIN, 30));
        row10.setBounds(1700,550,300,100);
        JButton clear = new JButton("Clear");
        clear.setFont(new Font("Arial", Font.PLAIN, 30));
        clear.setBounds(1700,700,300,100);
        panel.setVisible(true);
        panel.add(glider);
        panel.add(smallExploder);
        panel.add(exploder);
        panel.add(row10);
        panel.add(clear);
        frame.add(panel);


        frame.setVisible(true);

        glider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.get(21).set(20, true);
                board.get(21).set(21, true);
                board.get(21).set(22, true);
                board.get(20).set(22, true);
                board.get(19).set(21, true);
            }
        });

        smallExploder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.get(20).set(20, true);
                board.get(20).set(21, true);
                board.get(20).set(22, true);
                board.get(21).set(20, true);
                board.get(21).set(22, true);
                board.get(19).set(21, true);
                board.get(22).set(21, true);
            }
        });

        exploder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.get(18).set(19, true);
                board.get(19).set(19, true);
                board.get(20).set(19, true);
                board.get(21).set(19, true);
                board.get(22).set(19, true);
                board.get(18).set(21, true);
                board.get(22).set(21, true);
                board.get(18).set(23, true);
                board.get(19).set(23, true);
                board.get(20).set(23, true);
                board.get(21).set(23, true);
                board.get(22).set(23, true);
            }
        });

        row10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.get(20).set(17, true);
                board.get(20).set(18, true);
                board.get(20).set(19, true);
                board.get(20).set(20, true);
                board.get(20).set(21, true);
                board.get(20).set(22, true);
                board.get(20).set(23, true);
                board.get(20).set(24, true);
                board.get(20).set(25, true);
                board.get(20).set(26, true);
            }
        });

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < SIZE; i++) {
                    List<Boolean> row = new ArrayList<Boolean>();
                    for (int j = 0; j < SIZE; j++) {
                        row.add(false);
                    }
                    board.set(i, row);

                }
            }
        });





        Thread.sleep(500);
        Graphics g = panel.getGraphics();

        board = makeBoard();
        newBoard = makeBoard();




        paintComponent(g, board);
        int num = 0;
        while (num < 1) {
            for (int row = 1; row < board.size() - 1; row++) {
                for (int col = 1; col < board.get(row).size() - 1; col++) {
                    int count = countLiveNeighbors(row, col);
                    updateBoard(count, row, col);
                }

            }

            List<List<Boolean>> temp = board;
            board = newBoard;
            newBoard = temp;

            Thread.sleep(700);
            paintComponent(g, board);
        }
    }

    private static List makeBoard() {
        List<List<Boolean>> board = new ArrayList<List<Boolean>>();
        for (int i = 0; i < SIZE; i++) {
            List<Boolean> row = new ArrayList<Boolean>();
            for (int j = 0; j < SIZE; j++) {
                row.add(false);
            }
            board.add(row);

        }
        return board;
    }

    private static int countLiveNeighbors(int row, int col) {
        final int NEXTROW = row + 1;
        final int NEXTCOL = col + 1;
        final int PREVROW = row - 1;
        final int PREVCOL = col - 1;
        int count = 0;
        if (board.get(PREVROW).get(PREVCOL) == true) {
            count++;
        }
        if (board.get(PREVROW).get(col) == true) {
            count++;
        }
        if (board.get(PREVROW).get(NEXTCOL) == true) {
            count++;
        }
        if (board.get(row).get(PREVCOL) == true) {
            count++;
        }
        if (board.get(row).get(NEXTCOL) == true) {
            count++;
        }
        if (board.get(NEXTROW).get(PREVCOL) == true) {
            count++;
        }
        if (board.get(NEXTROW).get(col) == true) {
            count++;
        }
        if (board.get(NEXTROW).get(NEXTCOL) == true) {
            count++;
        }
        return count;
    }

    private static void updateBoard(int count, int row, int col) {
        if (board.get(row).get(col) && (count < 2 || count > 3)) {
            newBoard.get(row).set(col, false);
        } else if (!board.get(row).get(col) && count == 3) {
            newBoard.get(row).set(col, true);
        } else {
            newBoard.get(row).set(col, board.get(row).get(col));
        }

    }

    private static void printBoard(List<List<Boolean>> board) {
        for (int i = 1; i < SIZE - 1; i++) {
            for (int j = 1; j < SIZE - 1; j++) {
                if (board.get(i).get(j) == false) {
                    System.out.print(".");
                } else {
                    System.out.print("o");
                }
            }
            System.out.println("");
        }
    }



    private static void paintComponent(Graphics g, List<List<Boolean>> board) {
        for (int row = 0; row < board.size(); row++) {
            for (int col = 0; col < board.get(row).size(); col++) {
                if (board.get(row).get(col) == true) {
                    g.setColor(Color.green);
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;
                    g.fillRect(x, y, CELL_SIZE - 1, CELL_SIZE - 1);
                } else {
                    g.setColor(Color.black);
                    int x = col * CELL_SIZE;
                    int y = row * CELL_SIZE;
                    g.fillRect(x, y, CELL_SIZE - 1, CELL_SIZE - 1);
                }
            }
        }
    }
}
