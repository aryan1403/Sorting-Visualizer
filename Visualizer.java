import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import java.util.List;
import java.util.Random;

public class Visualizer extends JPanel implements ActionListener {
    JFrame frame = new JFrame();
    JButton sort = new JButton();
    JButton shuffle = new JButton();
    JTextArea label = new JTextArea();

    String arr[] = { "Red", "Blue", "Green", "Magenta" };

    JComboBox<String> color = new JComboBox<String>(arr);

    // Variables
    private final int FRAME_WIDTH = 700;
    private final int FRAME_HEIGHT = 500;

    private JTextField[] heightAdjuster = new JTextField[10];

    private List<JPanel> l = new ArrayList<JPanel>();

    private int X = 10;
    private int Y = 305;

    private int width = 50;

    int counter = 0;

    public void init() {
        sort.setBounds(50, 30, 70, 50);
        sort.setText("Sort");
        sort.setFont(new Font("Aerial", Font.BOLD, 15));
        sort.setFocusable(false);
        sort.setBackground(Color.white);
        sort.addActionListener(this);

        shuffle.setBounds(130, 30, 100, 50);
        shuffle.setText("Shuffle");
        shuffle.setFont(new Font("Aerial", Font.BOLD, 15));
        shuffle.setFocusable(false);
        shuffle.setBackground(Color.white);
        shuffle.addActionListener(this);

        label.setBounds(260, 43, 230, 50);
        label.setFont(new Font("Aerial", Font.BOLD, 15));
        label.setBorder(BorderFactory.createEmptyBorder());
        label.setEditable(false);
        label.setLayout(null);

        color.setBounds(520, 30, 120, 50);
        color.setFont(new Font("Aerial", Font.BOLD, 15));
        color.addActionListener(this);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        frame.setLayout(null);
        // frame.add(new Drawer());
        frame.add(VisualizerPanel());
        frame.add(sort);
        frame.add(shuffle);
        frame.add(label);
        frame.add(color);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
    }

    public JTextField makeArea(int x, int y, int width) {
        JTextField area = new JTextField();
        area.setBounds(x, y, width, width);
        area.setBackground(Color.white);
        area.setLayout(null);
        area.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        area.setFont(new Font("Aerial", Font.ITALIC, 30));
        area.addActionListener(this);
        return area;
    }

    public void __initAdjusters__() {
        int x = 59;
        int y = 405;
        for (int i = 0; i < heightAdjuster.length; i++) {
            heightAdjuster[i] = makeArea(x, y, width);
            frame.add(heightAdjuster[i]);
            x += 8 + width;
        }
    }

    public JPanel VisualizerPanel() {
        this.setBounds(50, 93, 590, 307);
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        this.setLayout(null);

        addBlock(15);
        addBlock(9);
        addBlock(18);
        addBlock(7);
        addBlock(10);
        addBlock(5);
        addBlock(4);
        addBlock(10);
        addBlock(20);
        addBlock(25);

        __initAdjusters__();

        // sort();

        return this;

    }

    public void changeColor() {
        for (JPanel panel : l) {
            if (color.getSelectedItem().equals("Red"))
                panel.setBackground(Color.red);
            if (color.getSelectedItem().equals("Blue"))
                panel.setBackground(Color.blue);
            if (color.getSelectedItem().equals("Magenta"))
                panel.setBackground(Color.magenta);
            if (color.getSelectedItem().equals("Green"))
                panel.setBackground(Color.green);
        }
    }

    public void addBlock(int value) {
        JPanel label = new JPanel();
        label.setBackground(Color.red);
        label.setBounds(X, Y - (10 * value), width, (10 * value));
        label.setToolTipText(value + "");
        label.setLayout(null);

        l.add(counter, label);
        this.add(l.get(counter));
        counter++;

        X += 58;
    }

    public void shuffle() {
        long start = System.currentTimeMillis();
        Random r = new Random();
        JPanel[] arr = new JPanel[l.size()];
        int m = 0;
        for (JPanel jPanel : l) {
            arr[m] = jPanel;
            m++;
        }
        for (int i = 0; i < l.size(); i++) {
            int index = i;
            int index2 = r.nextInt(l.size() - 1);

            JPanel temp = arr[index];
            arr[index] = arr[index2];
            arr[index2] = temp;

            move(l.get(index), l.get(index2), l.get(index).getX(), l.get(index2).getX(), index, index2);

            l.clear();
            for (JPanel panel : arr) {
                l.add(panel);
            }
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        label.setText("Shuffling Takes : " + elapsedTime + " Millisecond");
    }

    public void move(JPanel p, JPanel p1, int x, int y, int index, int index2) {
        x = p1.getX();
        y = p.getX();
        p.setBounds(x, p.getY(), width, p.getHeight());
        p1.setBounds(y, p1.getY(), width, p1.getHeight());
        System.out.println("Alignment Changed");
    }

    public void sort2() {
        long start = System.currentTimeMillis();
        System.out.println("Sorting...");
        JPanel[] arr = new JPanel[l.size()];
        int m = 0;
        for (JPanel jPanel : l) {
            arr[m] = jPanel;
            m++;
        }
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            JPanel temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    
        l.clear();
        for (JPanel jPanel : arr) {
            l.add(jPanel);
        }

        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        label.setText("Sorting Takes : " + elapsedTime + " Millisecond");
        System.out.println(elapsedTime);
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    public void heapify(JPanel[] arr, int n, int i) {
        int largest = i; // Initialize largest as root
        int la = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (la < n && arr[la].getHeight() > arr[largest].getHeight())
            largest = la;

        // If right child is larger than largest so far
        if (r < n && arr[r].getHeight() > arr[largest].getHeight())
            largest = r;

        // If largest is not root
        if (largest != i) {
            JPanel swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            move(arr[i], arr[largest], arr[i].getX(), arr[largest].getX(), i, largest);

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    public void sort() {
        long start = System.currentTimeMillis();
        System.out.println("Sorting...");
        JPanel[] arr = new JPanel[l.size()];
        int m = 0;
        for (JPanel jPanel : l) {
            arr[m] = jPanel;
            m++;
        }

        for (int i = 0; i < l.size(); i++) {
            for (int j = 0; j < l.size(); j++) {
                if (l.get(i).getHeight() < l.get(j).getHeight()) {
                    int index = l.indexOf(l.get(i));
                    int index2 = l.indexOf(l.get(j));
                    JPanel temp = arr[index];
                    arr[index] = arr[index2];
                    arr[index2] = temp;

                    move(l.get(index), l.get(index2), l.get(index).getX(), l.get(index2).getX(), index, index2);

                    System.out.println("Swaping " + (index + 1) + " Panel with " + (index2 + 1) + " Panel");
                    l.clear();
                    for (JPanel panel : arr) {
                        l.add(panel);
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        label.setText("Sorting Takes : " + elapsedTime + " Millisecond");
        System.out.println(elapsedTime);
    }

    void swap(JPanel[] arr, int i, int j) {
        JPanel temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;

        move(arr[i], arr[j], arr[i].getX(), arr[j].getX(), i, j);
    }

    /*
     * This function takes last element as pivot, places the pivot element at its
     * correct position in sorted array, and places all smaller (smaller than pivot)
     * to left of pivot and all greater elements to right of pivot
     */
    int partition(JPanel[] arr, int low, int high) {

        // pivot
        int pivot = arr[high].getHeight();

        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1);

        for (int j = low; j <= high - 1; j++) {

            // If current element is smaller
            // than the pivot
            if (arr[j].getHeight() < pivot) {

                // Increment index of
                // smaller element
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /*
     * The main function that implements QuickSort arr[] --> Array to be sorted, low
     * --> Starting index, high --> Ending index
     */
    public void quickSort(int low, int high) {
        long start = System.currentTimeMillis();
        System.out.println("Sorting...");
        JPanel[] arr = new JPanel[l.size()];
        int m = 0;
        for (JPanel jPanel : l) {
            arr[m] = jPanel;
            m++;
        }
        if (low < high) {

            // pi is partitioning index, arr[p]
            // is now at right place
            int pi = partition(arr, low, high);

            // Separately sort elements before
            // partition and after partition
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
        long end = System.currentTimeMillis();
        long elapsedTime = end - start;
        label.setText("Sorting Takes : " + elapsedTime + " Millisecond");
        System.out.println(elapsedTime);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        changeColor();
        if (e.getSource() == sort) {
            sort();

        }
        if (e.getSource() == shuffle) {
            shuffle();
        }

        for (int i = 0; i < 10; i++) {
            int x = 10;
            JTextField jTextArea = heightAdjuster[i];
            int n = 0;
            try {
                n = Integer.parseInt(jTextArea.getText().toString());
            } catch (NumberFormatException ex) {

            }
            if (n > 30)
                jTextArea.setText("30");
            else {
                if (!jTextArea.getText().equals("")) {
                    String s = jTextArea.getText().toString();
                    System.out.println(s);

                    l.get(i).setBounds(x += (58 * i), 305 - (10 * n), width, (10 * n));
                    l.get(i).setToolTipText(n + "");

                    jTextArea.setText("");
                }
            }
        }

    }
}