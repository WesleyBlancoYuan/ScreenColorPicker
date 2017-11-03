import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ColorPicker extends JFrame{
    
    private static JTextField fieldRed;
    private static JTextField fieldGreen;
    private static JTextField fieldBlue;
    private Robot robot;
    private static JTable table;
    public JFrame helperScreen;
    public ColorPicker() {
        initialize();
    }

    private void initialize() {
        setAutoRequestFocus(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setAlwaysOnTop(true);
        
        //add exit key combination
        addKeyListener(new KeyListener() {
            
            @Override
            public void keyTyped(KeyEvent e) {
                    
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F4) {
                    System.exit(0);
                }
            }
        });
//        try {
//            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//                | UnsupportedLookAndFeelException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        
        table = new JTable();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {"Keys", "Values", "Color"});
        table.setModel(model);
        table.setDefaultRenderer(Object.class, new ColorRenderer());
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 500, 500);
        
        c.gridheight = 1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        
        add(pane, c);
        
        c = new GridBagConstraints();
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.5;
        fieldRed = new JTextField();
        fieldRed.setPreferredSize(new Dimension(100, 25));
        add(fieldRed, c);
        
        c.gridx = 1;
        fieldGreen = new JTextField();
        fieldGreen.setPreferredSize(new Dimension(100, 25));
        add(fieldGreen, c);
        
        c.gridx = 2;
        fieldBlue = new JTextField();
        fieldBlue.setPreferredSize(new Dimension(100, 25));
        add(fieldBlue, c);
        
        pack();
        setVisible(true);
        
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        helperScreen = new JFrame();
        helperScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        helperScreen.setAlwaysOnTop(false);
        helperScreen.setAutoRequestFocus(false);
        helperScreen.setOpacity(0.05f);
        helperScreen.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        helperScreen.setBounds(0, 0, (int)screen.getWidth(), (int)screen.getHeight());
        helperScreen.addMouseMotionListener(new ScreenMouseListener());
        helperScreen.setVisible(true);
        
    }
    
    public static void main(String[] args) {
     // Determine if the GraphicsDevice supports translucency.
        GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
            System.err.println("Translucency is not supported");
            System.exit(0);
        }
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        ColorPicker picker = new ColorPicker();
    }
    
    private class ColorRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            // TODO Auto-generated method stub
            if (column == 2 && value instanceof Color) {
                Component label = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setBackground((Color)value);
                JLabel label2 = (JLabel)label;
                label2.setText("");
                return label2;
            } else {
                setBackground(Color.WHITE);
                setText(value.toString());
                return this;
            }
        }
        
    }
    
    private class ScreenMouseListener implements MouseInputListener {
        Object[] row = new Object[3];
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                robot = new Robot();
            } catch (AWTException eg) {
                // TODO Auto-generated catch block
                eg.printStackTrace();
            }
            Point point = ((MouseEvent)e).getLocationOnScreen();
            int x = point.x;
            int y = point.y;
            helperScreen.setVisible(false);
            System.out.println("Mouse Pressed at x: " + x + " y: " + y);
            Color c = robot.getPixelColor(x, y);
            
            row[0] = new Integer(x);
            row[1] = new Integer(y);
            row[2] = c;
            
            ((DefaultTableModel)(table.getModel())).addRow(row);
            ((DefaultTableModel)(table.getModel())).fireTableDataChanged();
            helperScreen.setVisible(false);
        }
        @Override
        public void mousePressed(MouseEvent e) {
            try {
                robot = new Robot();
            } catch (AWTException eg) {
                // TODO Auto-generated catch block
                eg.printStackTrace();
            }
            Point point = ((MouseEvent)e).getLocationOnScreen();
            int x = point.x;
            int y = point.y;
            helperScreen.setVisible(false);
            System.out.println("Mouse Pressed at x: " + x + " y: " + y);
            Color c = robot.getPixelColor(x, y);
            
            row[0] = new Integer(x);
            row[1] = new Integer(y);
            row[2] = c;
            
            ((DefaultTableModel)(table.getModel())).addRow(row);
            ((DefaultTableModel)(table.getModel())).fireTableDataChanged();
            helperScreen.setVisible(false);
            
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void mouseMoved(MouseEvent e) {
            try {
                robot = new Robot();
            } catch (AWTException eg) {
                // TODO Auto-generated catch block
                eg.printStackTrace();
            }
            Point point = ((MouseEvent)e).getLocationOnScreen();
            int x = point.x;
            int y = point.y;
            helperScreen.setVisible(false);
            Color c = robot.getPixelColor(x, y);
            
            fieldRed.setText(Integer.toString(c.getRed()));
            fieldGreen.setText(Integer.toString(c.getGreen()));
            fieldBlue.setText(Integer.toString(c.getBlue()));
            helperScreen.setVisible(true);
        }
    }
}
