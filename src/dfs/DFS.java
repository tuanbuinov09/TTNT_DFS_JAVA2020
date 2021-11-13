package dfs;

/**
 *
 * @author tuanbuiquoc
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

class Point {

    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

class Node {

    ArrayList<Integer> listAdjacent;
    int label;
    Point p;
    int father;

    Node() {
        this.listAdjacent = new ArrayList<Integer>();
        this.label = -1;
        this.father = 99;
    }

    Node(int label) {
        this.listAdjacent = new ArrayList<Integer>();
        this.label = label;
        this.father = 99;
    }

    void addAdjacents(ArrayList<Integer> listAdjacents) {
        for (int label : listAdjacent) {
            this.listAdjacent.add(label);
        }
    }

    void addAdjacent(int label) {
        if (this.hasAdjacentNodeWithLabel(label)) {
            System.out.println("adjacent existed");
        } else {
            this.listAdjacent.add(label);
        }
    }

    void removeAdjacent(int label) {
        if (!this.hasAdjacentNodeWithLabel(label)) {
            System.out.println("adjacent not existed");
        } else {
            Integer inte = new Integer(label);
            this.listAdjacent.remove(inte);
        }
    }

    Node(Node node) {
        this.listAdjacent = node.listAdjacent;
        this.label = node.label;
        this.father = node.father;
    }

    public void drawNode(Graphics2D graphics2d, String mode) {
        if (mode.equalsIgnoreCase("")) {
            graphics2d.setColor(new Color(0, 153, 204));
//            graphics2d.setColor(new Color(255, 51, 51));
        }
        if (mode.equalsIgnoreCase("start")) {
//            graphics2d.setColor(new Color(0, 153, 204));
            graphics2d.setColor(new Color(255, 51, 51));
        }
        if (mode.equalsIgnoreCase("visited")) {
//            graphics2d.setColor(new Color(0, 153, 204));
            graphics2d.setColor(new Color(0, 204, 102));

        }
        if (mode.equalsIgnoreCase("end")) {
            graphics2d.setColor(new Color(255, 204, 0));
        }

        graphics2d.fillOval(p.getX() - 15, p.getY() - 15, 30, 30);
        if (mode.equalsIgnoreCase("") || mode.equalsIgnoreCase("start")) {
            graphics2d.setColor(Color.white);
        }
        if (mode.equalsIgnoreCase("visited")) {
//            graphics2d.setColor(new Color(0, 153, 204));
            graphics2d.setColor(new Color(0, 204, 102));
        }
        if (mode.equalsIgnoreCase("end")) {
//            graphics2d.setColor(new Color(0, 153, 204));
            graphics2d.setColor(Color.white);
        }
        graphics2d.setColor(Color.white);
        graphics2d.drawOval(p.getX() - 15, p.getY() - 15, 30, 30);
        graphics2d.drawString(String.valueOf(this.label), p.getX() - 5, p.getY() + 7);
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Node{" + this.label + ", " + "adjacentNodes=" + this.listAdjacent + "}";
    }

    public ArrayList<Integer> getListAdjacent() {
        return listAdjacent;
    }

    public void setAdjacentNodes(ArrayList<Integer> adjacentNodes) {
        this.listAdjacent = adjacentNodes;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public boolean hasAdjacentNodeWithLabel(int label) {
        for (int labelof : this.getListAdjacent()) {
            if (labelof == label) {
                return true;
            }
        }
        return false;
    }
}

class Graph {

    ArrayList<Node> listNodes;
    ArrayList<Point> listPoints;
    Graphics2D graphics2d;
    boolean directed = true;
    int endNode;

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public boolean isDirected() {
        return directed;
    }

    public int getEndNode() {
        return endNode;
    }

    Graph(int nodes, Graphics2D graphics2d) {
        initListPoints();
        listNodes = new ArrayList<Node>();
        this.graphics2d = graphics2d;
        this.graphics2d.setStroke(new BasicStroke(3));
        this.graphics2d.setFont(new Font("Roboto", Font.BOLD, 18));
        for (int i = 0; i < nodes; i++) {
            Node node = new Node(i);
            node.setP(listPoints.get(i));
            listNodes.add(node);
        }
        this.endNode = 99;
        for (Node node : listNodes) {
            node.drawNode(this.graphics2d, "");
        }
    }

    Graph(Graphics2D graphics2d) {
        this.graphics2d = graphics2d;
        this.graphics2d.setStroke(new BasicStroke(3));
        this.graphics2d.setFont(new Font("Roboto", Font.BOLD, 18));
        this.initListPoints();
        listNodes = new ArrayList<Node>();
    }

    Graph(int dimension, String path, Graphics2D graphics2d) {
        this.graphics2d = graphics2d;
        this.graphics2d.setStroke(new BasicStroke(3));
        this.graphics2d.setFont(new Font("Roboto", Font.BOLD, 18));
        initListPoints();
        listNodes = new ArrayList<Node>();
    }

    public void setListNodes(ArrayList<Node> listNodes) {
        this.listNodes = listNodes;
    }

    void addNode() {
        Node newNode = new Node(this.listNodes.size());
        newNode.setP(this.listPoints.get(this.listNodes.size()));
        this.listNodes.add(newNode);
        this.listNodes.get(this.listNodes.size() - 1).drawNode(this.graphics2d, "");
    }

    void removeNode(int label) {
        for (Node node : this.listNodes) {
            while (node.getListAdjacent().contains(label)) {
                node.removeAdjacent(label);
            }
        }
        this.listNodes.remove(this.getIndexOfNodeWithLabel(label));
        this.drawAdjacentLines();
    }

    String addEdge(int v, int u) {
        Node nodeV = null;
        Node nodeU = null;
        if ((v == 0 && u == 1) || (v == 1 && u == 0)) {

        } else {
            if (v > this.listNodes.size()) {
                return "order";
            }
            if (u > this.listNodes.size()) {
                return "order";
            }
        }

        if (v == 1 && u == 0 && listNodes.size() == 0) {
            nodeV = new Node(listNodes.size());
            nodeV.setP(this.listPoints.get(listNodes.size()));
            listNodes.add(nodeV);
            nodeU = new Node(listNodes.size());
            nodeU.setP(this.listPoints.get(listNodes.size()));
            listNodes.add(nodeU);
            this.drawAdjacentLines();
            return ""; //just added
        }

        if (this.getIndexOfNodeWithLabel(v) == -1) {
            nodeV = new Node(listNodes.size());
            nodeV.setP(this.listPoints.get(listNodes.size()));
            listNodes.add(nodeV);
        }
        if (this.getIndexOfNodeWithLabel(u) == -1) {
            nodeU = new Node(listNodes.size());
            nodeU.setP(this.listPoints.get(listNodes.size()));
            listNodes.add(nodeU);
        }

        if (listNodes.get(u).getListAdjacent().contains(v) && this.listNodes.get(v).getListAdjacent().contains(u)) {
            return "existed";
        }
        if (this.isDirected()) {
            if (listNodes.get(v).getListAdjacent().contains(u)) {
                return "existed";
            }
        }
        for (int i = 0; i < listNodes.size(); i++) {
            listNodes.get(i).setP(listPoints.get(i));
        }
        listNodes.get(getIndexOfNodeWithLabel(v)).addAdjacent(u);
        if (!this.directed) {
            listNodes.get(getIndexOfNodeWithLabel(u)).addAdjacent(v);
        }
        drawAdjacentLines();
        return "";
    }

    void removeEdge(int v, int u) {
        if (this.getIndexOfNodeWithLabel(v) == -1 || this.getIndexOfNodeWithLabel(u) == -1) {
            return;
        } else {
            listNodes.get(getIndexOfNodeWithLabel(v)).removeAdjacent(u);
//            listNodes.get(getIndexOfNodeWithLabel(u)).removeAdjacent(v);
            if (this.isDirected() == false) {
                listNodes.get(getIndexOfNodeWithLabel(u)).removeAdjacent(v);
            }
        }
        graphics2d.setColor(new Color(48, 57, 82));
        if (this.isDirected()) {
            this.removeArrowLine(graphics2d, listNodes.get(v).getP().getX(), listNodes.get(v).getP().getY(),
                    listNodes.get(u).getP().getX(), listNodes.get(u).getP().getY(), 15, 10);
        } else {
            graphics2d.drawLine(listNodes.get(this.getIndexOfNodeWithLabel(v)).getP().getX(), listNodes.get(this.getIndexOfNodeWithLabel(v)).getP().getY(),
                    listNodes.get(this.getIndexOfNodeWithLabel(u)).getP().getX(), listNodes.get(this.getIndexOfNodeWithLabel(u)).getP().getY());
        }
        this.drawAdjacentLines();
        listNodes.get(this.getIndexOfNodeWithLabel(v)).drawNode(graphics2d, "");
        this.listNodes.get(this.getIndexOfNodeWithLabel(u)).drawNode(graphics2d, "");
    }

    int getIndexOfNodeWithLabel(int label) {
        for (Node node : this.listNodes) {
            if (node.label == label) {
                return this.listNodes.indexOf(node);
            }
        }
        return -1;
    }
    public boolean found = false;
    public String result = "";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
//
//
//

    void path(int node) {
        System.err.println(node);
        Node tmpNode = this.listNodes.get(this.getIndexOfNodeWithLabel(node));
        if (tmpNode.father != 99) {
            if (this.isDirected()) {
                this.drawArrowLine(this.listNodes.get(this.getIndexOfNodeWithLabel(tmpNode.father)).getP().getX(), this.listNodes.get(this.getIndexOfNodeWithLabel(tmpNode.father)).getP().getY(),
                        tmpNode.getP().getX(), tmpNode.getP().getY(), 15, 10, "visited");

                path(tmpNode.father);

            } else {
                this.graphics2d.setColor(new Color(0, 204, 102));
                this.graphics2d.drawLine(tmpNode.getP().getX(), tmpNode.getP().getY(),
                        this.listNodes.get(this.getIndexOfNodeWithLabel(tmpNode.father)).getP().getX(), this.listNodes.get(this.getIndexOfNodeWithLabel(tmpNode.father)).getP().getY());

                path(tmpNode.father);

            }
            this.listNodes.get(this.getIndexOfNodeWithLabel(node)).drawNode(graphics2d, "visited");
            this.listNodes.get(this.getIndexOfNodeWithLabel(tmpNode.father)).drawNode(graphics2d, "visited");
        } else {
            return;
        }
    }
//
//
//

    public boolean DFS(int start, int end, JTextArea jta) {
        ArrayList<Integer> open = new ArrayList<>();
        ArrayList<Integer> close = new ArrayList<>();
        open.add(start);
        jta.setText("");

        if (start == end) {
            System.err.println("Tìm thấy.");
            listNodes.get(this.getIndexOfNodeWithLabel(start)).drawNode(graphics2d, "end");
            return true;
        } else {
            listNodes.get(this.getIndexOfNodeWithLabel(start)).drawNode(graphics2d, "start");
        }
        jta.append("Bước lặp\tOpen\t\t\t\t\tClose\n-----------------------------------------------------------------"
                + "------------------------------------------------------------------------\n");
        jta.append("Khởi tạo\t(" + start + ",null)" + "\t\t\t\t\tRỗng\n");

        int i = 1;
        while (true) {
            if (open.size() == 0) {
                System.err.println("Tìm kiếm thất bại.");
                return false;
            }
            int u = open.get(0);
            open.remove(0);
            close.add(u);

            jta.append(i + "       \t");
            int pos = 0;
            for (int v : listNodes.get(this.getIndexOfNodeWithLabel(u)).getListAdjacent()) {
                if (!open.contains(v) && !close.contains(v)) {
                    listNodes.get(this.getIndexOfNodeWithLabel(v)).father = u;
                    if (v == end) {
                        System.err.println("Tìm thấy.");
                        path(v);
                        listNodes.get(this.getIndexOfNodeWithLabel(v)).drawNode(graphics2d, "end");
                        listNodes.get(this.getIndexOfNodeWithLabel(start)).drawNode(graphics2d, "start");
                        for (int x : open) {
                            jta.append("(" + x + "," + listNodes.get(this.getIndexOfNodeWithLabel(x)).father + "),");
                        }
                        jta.append("\t\t\t\t\t" + close + "\n");
                        return true;
                    }
                    open.add(pos, v);
                    pos = pos + 1;
                }
            }
            System.out.println(open);
            for (int x : open) {
                jta.append("(" + x + "," + listNodes.get(this.getIndexOfNodeWithLabel(x)).father + "),");
            }
            i = i + 1;
            jta.append("\t\t\t\t\t" + close + "\n");
        }
    }
//
//
//

    public void DFSRecur(int node, boolean visited[]) {
        visited[node] = true;
        result = result + node + "->";
        System.out.println(node);
        listNodes.get(node).drawNode(graphics2d, "visited");
        try {
            Thread.sleep(999);
        } catch (InterruptedException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int x : listNodes.get(this.getIndexOfNodeWithLabel(node)).getListAdjacent()) {
            if (x == this.endNode) {
                try {
                    this.graphics2d.setColor(new Color(0, 153, 204));
                    System.out.println(endNode + "found");
                    if (!this.isDirected()) {
                        graphics2d.drawLine(listNodes.get(x).getP().getX(), listNodes.get(x).getP().getY(),
                                listNodes.get(node).getP().getX(), listNodes.get(node).getP().getY());
                    } else {
                        this.drawArrowLine(listNodes.get(node).getP().getX(), listNodes.get(node).getP().getY(),
                                listNodes.get(x).getP().getX(), listNodes.get(x).getP().getY(), 15, 10, "visited");
                    }
                    listNodes.get(node).drawNode(graphics2d, "visited");
                    listNodes.get(x).drawNode(graphics2d, "end");
                } catch (Exception ex) {
                }
                this.found = true;
                graphics2d = null;
                return;
            }
            try {
                if (visited[x] == false) {
                    this.graphics2d.setColor(new Color(0, 153, 204));
                    if (!this.isDirected()) {
                        graphics2d.drawLine(listNodes.get(x).getP().getX(), listNodes.get(x).getP().getY(),
                                listNodes.get(node).getP().getX(), listNodes.get(node).getP().getY());
                    } else {
                        this.drawArrowLine(listNodes.get(node).getP().getX(), listNodes.get(node).getP().getY(),
                                listNodes.get(x).getP().getX(), listNodes.get(x).getP().getY(), 15, 10, "visited");
                    }
                    listNodes.get(node).drawNode(graphics2d, "visited");
                    listNodes.get(x).drawNode(graphics2d, "visited");
                    DFSRecur(x, visited);
                }
            } catch (Exception ex) {
            }
        }
    }

    void DFSRecur_Init(int start) {
        if (start == this.endNode) {
            this.result = "" + start;
            found = true;
            listNodes.get(start).drawNode(graphics2d, "end");
            return;
        }
        boolean visited[] = new boolean[listNodes.size()];
        System.out.println("DFS Result: ");
        this.found = false;
        DFSRecur(start, visited);
    }

    void printGraph() {
        for (Node node : listNodes) {
            System.out.println(node.toString());
        }
        System.out.println("graph-size: " + listNodes.size() + "\n------");
    }

    void drawAdjacentLines() {
        graphics2d.setColor(Color.white);
        for (Node node : listNodes) {
            for (int label : node.getListAdjacent()) {
                if (!this.directed) {
                    graphics2d.drawLine(node.getP().getX(), node.getP().getY(),
                            listNodes.get(this.getIndexOfNodeWithLabel(label)).getP().getX(), listNodes.get(this.getIndexOfNodeWithLabel(label)).getP().getY());
                } else {
                    this.drawArrowLine(node.getP().getX(), node.getP().getY(),
                            listNodes.get(this.getIndexOfNodeWithLabel(label)).getP().getX(), listNodes.get(this.getIndexOfNodeWithLabel(label)).getP().getY(),
                            15, 10, "");
                }
                node.drawNode(graphics2d, "");
                this.listNodes.get(this.getIndexOfNodeWithLabel(label)).drawNode(graphics2d, "");
            }
        }
    }

    void drawArrowLine(int x1, int y1, int x2, int y2, int d, int h, String mode) {
        int dx = x2 - x1, dy = y2 - y1;
        if (dx > 0 && dy > 0) {
            x2 = x2 - 15;
            y2 = y2 - 15;
        }
        if (dx > 0 && dy < 0) {
            x2 = x2 - 15;
            y2 = y2 + 15;
        }
        if (dx < 0 && dy > 0) {
            x2 = x2 + 15;
            y2 = y2 - 15;
        }
        if (dx < 0 && dy < 0) {
            x2 = x2 + 15;
            y2 = y2 + 15;
        }
        if (dy == 0 && dx < 0) {
            x2 = x2 + 15;
        }
        if (dy == 0 && dx > 0) {
            x2 = x2 - 15;
        }
        if (dy > 0 && dx == 0) {
            y2 = y2 - 15;
        }
        if (dy < 0 && dx == 0) {
            y2 = y2 + 15;
        }
        dx = x2 - x1;
        dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        if (mode.equalsIgnoreCase("visited")) {
//            this.graphics2d.setColor(new Color(0, 153, 204));
            this.graphics2d.setColor(new Color(0, 204, 102));
        }
        graphics2d.drawLine(x1, y1, x2, y2);
        graphics2d.fillPolygon(xpoints, ypoints, 3);
        graphics2d.setColor(Color.black);
        graphics2d.setStroke(new BasicStroke(3));
        graphics2d.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
        graphics2d.drawLine(xpoints[0], ypoints[0], xpoints[2], ypoints[2]);
        graphics2d.drawLine(xpoints[2], ypoints[2], xpoints[1], ypoints[1]);
    }

    void removeArrowLine(Graphics2D graphics2d, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        if (dx > 0 && dy > 0) {
            x2 = x2 - 15;
            y2 = y2 - 15;
        }
        if (dx > 0 && dy < 0) {
            x2 = x2 - 15;
            y2 = y2 + 15;
        }
        if (dx < 0 && dy > 0) {
            x2 = x2 + 15;
            y2 = y2 - 15;
        }
        if (dx < 0 && dy < 0) {
            x2 = x2 + 15;
            y2 = y2 + 15;
        }
        if (dy == 0 && dx < 0) {
            x2 = x2 + 15;
        }
        if (dy == 0 && dx > 0) {
            x2 = x2 - 15;
        }
        if (dy > 0 && dx == 0) {
            y2 = y2 - 15;
        }
        if (dy < 0 && dx == 0) {
            y2 = y2 + 15;
        }
        dx = x2 - x1;
        dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        graphics2d.setColor(new Color(48, 57, 82));
        graphics2d.drawLine(x1, y1, x2, y2);
        graphics2d.fillPolygon(xpoints, ypoints, 3);
        graphics2d.setStroke(new BasicStroke(3));
        graphics2d.drawLine(xpoints[0], ypoints[0], xpoints[1], ypoints[1]);
        graphics2d.drawLine(xpoints[0], ypoints[0], xpoints[2], ypoints[2]);
        graphics2d.drawLine(xpoints[2], ypoints[2], xpoints[1], ypoints[1]);
    }

    void initListPoints() {
        this.listPoints = new ArrayList<Point>();
        this.listPoints.add(new Point(300, 40));
        this.listPoints.add(new Point(500, 190));
        this.listPoints.add(new Point(440, 410));
        this.listPoints.add(new Point(160, 410));
        this.listPoints.add(new Point(100, 190));
        this.listPoints.add(new Point(440, 90));
        this.listPoints.add(new Point(500, 300));
        this.listPoints.add(new Point(300, 460));
        this.listPoints.add(new Point(100, 300));
        this.listPoints.add(new Point(160, 90));
    }
}

public class DFS extends javax.swing.JFrame {

    /**
     * Creates new form DFS
     */
    public static String mode = "";
    Graph g;
    Graphics2D graphics2d;

    public DFS() {
        initComponents();
        graphics2d = (Graphics2D) jPanel1.getGraphics();
        g = new Graph(graphics2d);
        jRadioButton_CoHuong.setSelected(true);
        this.setTitle("DeTai10_DFS_Java_N18DCCN197");
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnXoaCanh = new javax.swing.JButton();
        btnThemCanh = new javax.swing.JButton();
        txtDinh1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDinh2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton_KhoiTao = new javax.swing.JButton();
        txtSoDinh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton_ChonFile = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton_LuuFile = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDinhKetThuc = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtDinhBatDau = new javax.swing.JTextField();
        jLabel_BaoLoi = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel_Result = new javax.swing.JLabel();
        jRadioButton_CoHuong = new javax.swing.JRadioButton();
        jRadioButton_VoHuong = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(89, 98, 117));

        jPanel2.setBackground(new java.awt.Color(89, 98, 117));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(48, 57, 82));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(89, 98, 117));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));

        btnXoaCanh.setBackground(new java.awt.Color(255, 51, 51));
        btnXoaCanh.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnXoaCanh.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaCanh.setText("Xóa Cạnh");
        btnXoaCanh.setBorderPainted(false);
        btnXoaCanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCanhActionPerformed(evt);
            }
        });

        btnThemCanh.setBackground(new java.awt.Color(0, 153, 204));
        btnThemCanh.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnThemCanh.setForeground(new java.awt.Color(255, 255, 255));
        btnThemCanh.setText("Thêm Cạnh");
        btnThemCanh.setBorderPainted(false);
        btnThemCanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCanhActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("P1");

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("P2");

        jButton_KhoiTao.setBackground(new java.awt.Color(0, 102, 102));
        jButton_KhoiTao.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton_KhoiTao.setForeground(new java.awt.Color(255, 255, 255));
        jButton_KhoiTao.setText("Khởi Tạo");
        jButton_KhoiTao.setBorderPainted(false);
        jButton_KhoiTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_KhoiTaoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Số Đỉnh Muốn Tạo");

        jButton_ChonFile.setBackground(new java.awt.Color(0, 0, 204));
        jButton_ChonFile.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton_ChonFile.setForeground(new java.awt.Color(255, 255, 255));
        jButton_ChonFile.setText("Chọn File");
        jButton_ChonFile.setBorderPainted(false);
        jButton_ChonFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ChonFileActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(255, 51, 51));
        btnClear.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setText("Clear All");
        btnClear.setBorderPainted(false);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jButton_LuuFile.setBackground(new java.awt.Color(0, 204, 102));
        jButton_LuuFile.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton_LuuFile.setForeground(new java.awt.Color(255, 255, 255));
        jButton_LuuFile.setText("Lưu File");
        jButton_LuuFile.setBorderPainted(false);
        jButton_LuuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_LuuFileActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kết quả");

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Đỉnh kết thúc");

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Tìm kiếm");

        btnTim.setBackground(new java.awt.Color(255, 204, 0));
        btnTim.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnTim.setForeground(new java.awt.Color(255, 255, 255));
        btnTim.setText("Tìm");
        btnTim.setBorderPainted(false);
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Đỉnh bắt đầu");

        jLabel_BaoLoi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_BaoLoi.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_Result.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel_Result.setForeground(new java.awt.Color(255, 51, 51));
        jLabel_Result.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jRadioButton_CoHuong.setBackground(new java.awt.Color(89, 98, 117));
        buttonGroup1.add(jRadioButton_CoHuong);
        jRadioButton_CoHuong.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jRadioButton_CoHuong.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton_CoHuong.setText("Có Hướng");
        jRadioButton_CoHuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_CoHuongMouseClicked(evt);
            }
        });

        jRadioButton_VoHuong.setBackground(new java.awt.Color(89, 98, 117));
        buttonGroup1.add(jRadioButton_VoHuong);
        jRadioButton_VoHuong.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jRadioButton_VoHuong.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton_VoHuong.setText("Vô Hướng");
        jRadioButton_VoHuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton_VoHuongMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator5)
                    .addComponent(jLabel_BaoLoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDinhKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtDinhBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(btnXoaCanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemCanh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_Result, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDinh1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDinh2)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoDinh, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton_KhoiTao, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_LuuFile, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButton_CoHuong))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jRadioButton_VoHuong)
                                    .addComponent(jButton_ChonFile, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(227, 227, 227))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtDinh1, txtDinh2, txtDinhBatDau, txtDinhKetThuc, txtSoDinh});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_CoHuong)
                    .addComponent(jRadioButton_VoHuong))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_LuuFile)
                    .addComponent(jButton_ChonFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSoDinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_KhoiTao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDinh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDinh2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemCanh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaCanh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDinhBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtDinhKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_Result, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_BaoLoi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Roboto", 0, 13)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    void doHoa() {
        new Thread(new Runnable() {
            public void run() {
                g.setResult("");
                try {
                    int x = Integer.parseInt(txtDinhBatDau.getText());
                    int y = 99;
                    if (!txtDinhKetThuc.getText().isEmpty()) {
                        y = Integer.parseInt(txtDinhKetThuc.getText());
                    }
                    if (!(x >= 0 && x <= 9) && !(y >= 0 && y <= 9)) {
                        jLabel_BaoLoi.setText("Nhập đỉnh 0-9");
                        return;
                    }
                } catch (NumberFormatException e) {
                    jLabel_BaoLoi.setText("Nhập số");
                    return;
                }
                jLabel_BaoLoi.setText("");
                g.graphics2d = graphics2d;
                g.drawAdjacentLines();
                if (txtDinhKetThuc.getText().isEmpty()) {
                    g.setEndNode(99);
                } else {
                    g.setEndNode(Integer.parseInt(txtDinhKetThuc.getText()));
                }
                g.DFSRecur_Init(Integer.parseInt(txtDinhBatDau.getText()));
                String fix = "";
                if (g.found && g.result.length() == 1) {
                    fix = g.result;
                } else {
                    if (g.found) {
                        fix = g.result.substring(0, g.result.length() - 2);
                        fix = fix + "->" + g.endNode;
                    } else {
                        fix = g.result.substring(0, g.result.length() - 2);
                    }

                }
                if (g.found) {
                    JOptionPane.showMessageDialog(rootPane, "Tìm thấy");
                    return;
                }
                if (!g.found && !txtDinhKetThuc.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, " Không tìm thấy");
                }
            }
        }).start();
    }
    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:

//        new Thread(new Runnable() {
//            public void run() {
        if (g == null || g.listNodes.size() == 0) {
            jLabel_BaoLoi.setText("Không thể tìm kiếm");
            return;
        }
        jLabel_BaoLoi.setText("");
        jLabel_Result.setText("");
        for (int i = 0; i < g.listNodes.size(); i++) {
            g.listNodes.get(i).father = 99;
        }
        g.drawAdjacentLines();
        for (Node node : g.listNodes) {
            node.drawNode(graphics2d, "");
        }
        try {
            int x = Integer.parseInt(txtDinhBatDau.getText());
            int y = Integer.parseInt(txtDinhKetThuc.getText());
            if (!(x >= 0 && x <= 9) && !(y >= 0 && y <= 9)) {
                jLabel_BaoLoi.setText("Nhập đỉnh 0-9");
                return;
            }
        } catch (NumberFormatException e) {
            jLabel_BaoLoi.setText("Nhập số");
            return;
        }
        jLabel_BaoLoi.setText("");
//                g.graphics2d = graphics2d;
//                g.drawAdjacentLines();
        if (txtDinhKetThuc.getText().isEmpty()) {
            g.setEndNode(99);
        } else {
            g.setEndNode(Integer.parseInt(txtDinhKetThuc.getText()));
        }

        if (g.DFS(Integer.parseInt(txtDinhBatDau.getText()), Integer.parseInt(txtDinhKetThuc.getText()), jTextArea1)) {
//            JOptionPane.showMessageDialog(rootPane, "Tìm thấy");
//            if (Integer.parseInt(txtDinhBatDau.getText()) == Integer.parseInt(txtDinhKetThuc.getText())) {
//                g.result = txtDinhKetThuc.getText();
//            }
            jLabel_Result.setForeground(new Color(255, 204, 0));
            jLabel_Result.setText("Tìm Thấy");
        } else {
//            JOptionPane.showMessageDialog(rootPane, "Không tìm thấy");
            jLabel_Result.setForeground(new Color(255, 51, 51));
            jLabel_Result.setText("Không Tìm Thấy");
//            if (Integer.parseInt(txtDinhBatDau.getText()) == Integer.parseInt(txtDinhKetThuc.getText())) {
//                g.result = txtDinhKetThuc.getText();
//                return;
//            }
        }
//            }
//        }).start();

    }//GEN-LAST:event_btnTimActionPerformed

    private void btnThemCanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCanhActionPerformed
        // TODO add your handling code here:
        if (this.jRadioButton_VoHuong.isSelected()) {
            g.setDirected(false);
        }
        jLabel_BaoLoi.setText("");
        jLabel_Result.setText("");
        if (txtDinh1.getText().equalsIgnoreCase(txtDinh2.getText())) {
            jLabel_BaoLoi.setText("2 đỉnh trùng nhau");
            return;
        }
        try {
            int x = Integer.parseInt(txtDinh1.getText());
            int y = Integer.parseInt(txtDinh2.getText());

            if (!(x >= 0 && x <= 9) || !(y >= 0 && y <= 9)) {
                jLabel_BaoLoi.setText("Tối đa 10 đỉnh 0-9");
                return;
            }
        } catch (NumberFormatException e) {
            jLabel_BaoLoi.setText("Nhập số 0-9");
            return;
        }
        g.graphics2d = this.graphics2d;
        if (g.addEdge(Integer.parseInt(txtDinh1.getText()), Integer.parseInt(txtDinh2.getText())).equalsIgnoreCase("existed")) {
            jLabel_BaoLoi.setText("Cạnh tồn tại.");
            return;
        }
        if (g.addEdge(Integer.parseInt(txtDinh1.getText()), Integer.parseInt(txtDinh2.getText())).equalsIgnoreCase("order")) {
            jLabel_BaoLoi.setText("Vui lòng thêm theo thứ tự.");
            return;
        }

        jLabel_BaoLoi.setText("");
        g.printGraph();
    }//GEN-LAST:event_btnThemCanhActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        // TODO add your handling code here:
//        System.out.println(evt.getX());
//        System.out.println(evt.getY());
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jButton_KhoiTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_KhoiTaoActionPerformed
        // TODO add your handling code here:
        jLabel_BaoLoi.setText("");
        jLabel_Result.setText("");
        jTextArea1.setText("");
        try {
            int x = Integer.parseInt(txtSoDinh.getText());
            if (!(x >= 0 && x <= 10)) {
                jLabel_BaoLoi.setText("Nhập số đỉnh tối đa=10");
                return;
            }
        } catch (NumberFormatException e) {
            jLabel_BaoLoi.setText("Nhập số");
            return;
        }
        jLabel_BaoLoi.setText("");
        g.listNodes.removeAll(g.listNodes);
        Color a = graphics2d.getColor();
        graphics2d.setColor(jPanel1.getBackground());
        graphics2d.fillRect(20, 20, 575, 475);
        g.graphics2d = this.graphics2d;
        g = null;
        g = new Graph(Integer.parseInt(txtSoDinh.getText()), graphics2d);
        if (this.jRadioButton_VoHuong.isSelected()) {
            g.setDirected(false);
        }
        System.out.println(g.listNodes.size());
        g.printGraph();
    }//GEN-LAST:event_jButton_KhoiTaoActionPerformed

    private void jButton_ChonFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ChonFileActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser(new File("").getAbsolutePath() + "/src/matrix");
        jfc.setDialogTitle("Chọn file chứa ma trận");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            jTextArea1.setText("");
            if (jfc.getSelectedFile().isFile()) {
                System.out.println(Paths.get(jfc.getSelectedFile().getPath()));
                BufferedReader reader;
                try {
                    reader = Files.newBufferedReader(Paths.get(jfc.getSelectedFile().getPath()));
                    String line = reader.readLine();
                    graphics2d.setColor(jPanel1.getBackground());
                    graphics2d.fillRect(20, 20, 575, 475);
                    g = new Graph(Integer.parseInt(line), this.graphics2d);

                    if (jRadioButton_VoHuong.isSelected()) {
                        g.setDirected(false);
                    }
                    for (int i = 0; i < g.listNodes.size(); i++) {
                        g.listNodes.get(g.getIndexOfNodeWithLabel(i)).setP(g.listPoints.get(i));
                        String str = reader.readLine();
                        for (int j = 0; j < Integer.parseInt(line); j++) {
                            int x = Integer.parseInt(str.substring(j, j + 1));
                            if (x != 0) {
                                if (!g.listNodes.get(i).hasAdjacentNodeWithLabel(j)) {
                                    g.addEdge(i, j);
                                }
                            }
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DFS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        g.printGraph();
    }//GEN-LAST:event_jButton_ChonFileActionPerformed

    private void btnXoaCanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCanhActionPerformed
        // TODO add your handling code here:
        jLabel_BaoLoi.setText("");
        jLabel_Result.setText("");
        g.graphics2d = this.graphics2d;
        try {
            if (g.getIndexOfNodeWithLabel(Integer.parseInt(txtDinh1.getText())) == -1) {
                jLabel_BaoLoi.setText("Đỉnh " + txtDinh1.getText() + " không tồn tại");
                return;
            }
            if (g.getIndexOfNodeWithLabel(Integer.parseInt(txtDinh2.getText())) == -1) {
                jLabel_BaoLoi.setText("Đỉnh " + txtDinh2.getText() + " không tồn tại");
                return;
            }
        } catch (NumberFormatException ex) {
            jLabel_BaoLoi.setText("Nhập số");
            return;
        }

        if (!g.listNodes.get(Integer.parseInt(txtDinh1.getText())).getListAdjacent().contains(Integer.parseInt(txtDinh2.getText()))) {
            jLabel_BaoLoi.setText("Cạnh không tồn tại");
            return;
        }
        jLabel_BaoLoi.setText("");
        g.removeEdge(Integer.parseInt(txtDinh1.getText()), Integer.parseInt(txtDinh2.getText()));
        g.printGraph();
    }//GEN-LAST:event_btnXoaCanhActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        jPanel1.repaint();
        g.listNodes.removeAll(g.listNodes);
        jTextArea1.setText("");
        jLabel_BaoLoi.setText("");
        jLabel_Result.setText("");
    }//GEN-LAST:event_btnClearActionPerformed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
//        System.out.println(jPanel1.getWidth());
//        System.out.println(jPanel1.getHeight());
//        graphics2d.drawString("x: " + evt.getX() + "; y: " + evt.getY(), evt.getX(), evt.getY());
    }//GEN-LAST:event_jPanel1MouseClicked

    private void jButton_LuuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_LuuFileActionPerformed
        // TODO add your handling code here:
        JFileChooser jfc = new JFileChooser(new File("").getAbsolutePath() + "/src/matrix");
        jfc.setDialogTitle("Lưu ma trận kề");
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfc.removeChoosableFileFilter(jfc.getFileFilter());  //remove the default file filter
        FileFilter filter = new FileNameExtensionFilter("Text File", "txt");

        jfc.addChoosableFileFilter(filter); //add txt file filter

        int returnValue = jfc.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            System.out.println(Paths.get(jfc.getSelectedFile().getPath()));
            BufferedWriter writer;
            try {
                String extension = "";
                if (!jfc.getSelectedFile().getPath().contains(".txt")) {
                    extension = ".txt";
                }
                writer = Files.newBufferedWriter(Paths.get(jfc.getSelectedFile().getPath() + extension));
                writer.write(g.listNodes.size() + "");
                writer.newLine();
                for (int i = 0; i < g.listNodes.size(); i++) {
                    for (int j = 0; j < g.listNodes.size(); j++) {
                        if (!g.listNodes.get(i).hasAdjacentNodeWithLabel(j)) {
                            writer.write(0 + "");
                        } else {
                            writer.write(1 + "");
                        }
                    }
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(DFS.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jButton_LuuFileActionPerformed

    private void jRadioButton_VoHuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_VoHuongMouseClicked

        if (g.isDirected() == false) {
            return;
        }
        g.setDirected(false);
        g.listNodes.removeAll(g.listNodes);
        jPanel1.repaint();
    }//GEN-LAST:event_jRadioButton_VoHuongMouseClicked

    private void jRadioButton_CoHuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton_CoHuongMouseClicked

        if (g.isDirected()) {
            return;
        }
        g.setDirected(true);
        g.listNodes.removeAll(g.listNodes);
        jPanel1.repaint();
    }//GEN-LAST:event_jRadioButton_CoHuongMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DFS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DFS().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnThemCanh;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoaCanh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton_ChonFile;
    private javax.swing.JButton jButton_KhoiTao;
    private javax.swing.JButton jButton_LuuFile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_BaoLoi;
    private javax.swing.JLabel jLabel_Result;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton_CoHuong;
    private javax.swing.JRadioButton jRadioButton_VoHuong;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtDinh1;
    private javax.swing.JTextField txtDinh2;
    private javax.swing.JTextField txtDinhBatDau;
    private javax.swing.JTextField txtDinhKetThuc;
    private javax.swing.JTextField txtSoDinh;
    // End of variables declaration//GEN-END:variables
}
