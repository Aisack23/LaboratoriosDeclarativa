package org.example.screen;

import org.example.Connection;
import org.example.screen.components.Buttons;
import org.example.screen.components.PanelResult;
import org.example.screen.components.TextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Window extends JFrame {

    private static final Color PURPLE = new Color(0xB48CFF);

    private final TextField txtA = new TextField("Ej: 8");
    private final TextField txtB = new TextField("Ej: 2");
    private final PanelResult panelResultado = new PanelResult();
    private final JLabel lblEstado = new JLabel(" ");

    public Window(Connection motor) {
        super("Calculadora en Prolog");

        JPanel campos = new JPanel(new GridLayout(1, 2, 12, 0));
        campos.setOpaque(false);
        campos.add(txtA);
        campos.add(txtB);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        agregar(contenido, etiqueta("Número 1  y  Número 2"), 6);
        agregar(contenido, campos, 22);
        agregar(contenido, crearBotones(motor), 22);
        agregar(contenido, panelResultado, 10);
        agregar(contenido, crearPie(), 0);

        JPanel raiz = new JPanel(new BorderLayout(0, 20));
        raiz.setBackground(Theme.BG);
        raiz.setBorder(new EmptyBorder(24, 28, 24, 28));
        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(contenido, BorderLayout.CENTER);

        setContentPane(raiz);
        setMinimumSize(new Dimension(660, 480));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel crearEncabezado() {
        JLabel titulo = new JLabel("Calculadora");
        titulo.setFont(Theme.sans(Font.BOLD, 24));
        titulo.setForeground(Theme.TEXT);

        JLabel subtitulo = new JLabel("Suma, resta, multiplicación y división resueltas con reglas en Prolog");
        subtitulo.setFont(Theme.sans(Font.PLAIN, 13));
        subtitulo.setForeground(Theme.MUTED);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(subtitulo);
        return p;
    }

    private JPanel crearBotones(Connection motor) {
        Buttons btnSumar = new Buttons("Sumar", Theme.BLUE, Theme.BG, 22, 11, 14);
        Buttons btnRestar = new Buttons("Restar", Theme.GREEN, Theme.BG, 22, 11, 14);
        Buttons btnMultiplicar = new Buttons("Multiplicar", Theme.AMBER, Theme.BG, 22, 11, 14);
        Buttons btnDividir = new Buttons("Dividir", PURPLE, Theme.BG, 22, 11, 14);

        btnSumar.addActionListener(e -> ejecutar(motor, "sum", "Suma"));
        btnRestar.addActionListener(e -> ejecutar(motor, "rest", "Resta"));
        btnMultiplicar.addActionListener(e -> ejecutar(motor, "multi", "Multiplicación"));
        btnDividir.addActionListener(e -> ejecutar(motor, "divi", "División"));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        p.add(btnSumar);
        p.add(btnRestar);
        p.add(btnMultiplicar);
        p.add(btnDividir);
        return p;
    }

    private JPanel crearPie() {
        lblEstado.setFont(Theme.sans(Font.PLAIN, 12));
        lblEstado.setForeground(Theme.MUTED);

        Buttons btnCopiar = new Buttons("Copiar", Theme.CARD, Theme.TEXT, 16, 7, 12);
        btnCopiar.addActionListener(e -> copiarResultado());

        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(lblEstado, BorderLayout.WEST);
        p.add(btnCopiar, BorderLayout.EAST);
        return p;
    }

    private void ejecutar(Connection motor, String pred, String nombre) {
        String a = txtA.getText().trim();
        String b = txtB.getText().trim();
        panelResultado.setTitulo("Resultado de la " + nombre.toLowerCase());

        if (a.isEmpty() || b.isEmpty()) {
            mostrar("", "Escribí los dos números primero.", Theme.AMBER);
            return;
        }
        try {
            String resultado = motor.consultar(pred, a, b);
            if (resultado.startsWith("Sin solución")) {
                String msg = pred.equals("divi")
                        ? "No se puede dividir entre cero."
                        : resultado;
                mostrar("", msg, Theme.AMBER);
            } else {
                mostrar(resultado, "Listo", Theme.GREEN);
            }
        } catch (Exception ex) {
            mostrar("", "Entrada inválida: " + ex.getMessage(), Theme.RED);
        }
    }

    private void mostrar(String resultado, String estado, Color colorEstado) {
        panelResultado.setTexto(resultado);
        lblEstado.setText(estado);
        lblEstado.setForeground(colorEstado);
    }

    private void copiarResultado() {
        String t = panelResultado.getTexto();
        if (t.isEmpty()) {
            return;
        }
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(t), null);
        lblEstado.setText("Copiado al portapapeles");
        lblEstado.setForeground(Theme.BLUE);
    }

    private static void agregar(JPanel panel, JComponent c, int espacioDespues) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(c);
        if (espacioDespues > 0) {
            panel.add(Box.createVerticalStrut(espacioDespues));
        }
    }

    private static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Theme.sans(Font.BOLD, 12));
        l.setForeground(Theme.MUTED);
        return l;
    }
}