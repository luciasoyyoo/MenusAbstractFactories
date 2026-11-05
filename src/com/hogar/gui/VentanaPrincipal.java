package com.hogar.gui;

import com.hogar.infantil.FabricaMenuInfantil;
import com.hogar.interfaces.Almuerzo;
import com.hogar.interfaces.Cena;
import com.hogar.interfaces.Desayuno;
import com.hogar.interfaces.FabricaMenuDiario;
import com.hogar.saludables.FabricaMenuSaludable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContenedor;
    
    private Desayuno desayunoActual;
    private Almuerzo almuerzoActual;
    private Cena cenaActual;

    private JLabel lblTituloCarta;
    private JLabel lblDesayunoDetalle;
    private JLabel lblAlmuerzoDetalle;
    private JLabel lblCenaDetalle;
    private JLabel lblCaloriasTotales;
    
    private JLabel lblDetalleTitulo;
    private JLabel lblDetalleContenido;

    // Paleta de colores elegante tipo restaurante clásico
    private final Color FONDO_BEIGE = new Color(242, 235, 221); 
    private final Color COLOR_TEXTO = new Color(45, 38, 32);
    private final Color COLOR_LINEAS = new Color(90, 70, 55); 
    private final Color COLOR_SECUNDARIO = new Color(110, 90, 75);
    
    private final Font FUENTE_TITULO_MENU = new Font("Georgia", Font.BOLD, 46);
    private final Font FUENTE_SECCION = new Font("Garamond", Font.BOLD, 26);
    private final Font FUENTE_DETALLE = new Font("Garamond", Font.PLAIN, 20);

    public VentanaPrincipal() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Planificador de Menús Familiares");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        
        // Marco doble decorativo exterior
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(15, 15, 15, 15),
                BorderFactory.createCompoundBorder(
                        new MatteBorder(3, 3, 3, 3, COLOR_LINEAS),
                        new MatteBorder(15, 15, 15, 15, Color.WHITE)
                )
        ));

        // 1. La primera pestaña (Selección) NO lleva scroll (eliminado de aquí)
        JPanel pantallaSeleccion = crearPantallaSeleccion();
        
        // 2. Las pestañas de menú y detalle sí llevan scroll con barra personalizada
        JScrollPane scrollMenu = crearScrollConEstilo(crearPantallaMenu());
        JScrollPane scrollDetalle = crearScrollConEstilo(crearPantallaDetalle());

        panelContenedor.add(pantallaSeleccion, "SELECCION");
        panelContenedor.add(scrollMenu, "MENU");
        panelContenedor.add(scrollDetalle, "DETALLE");

        add(panelContenedor);
        cardLayout.show(panelContenedor, "SELECCION");
    }
    
    // Método para crear un JScrollPane con barra de desplazamiento personalizada acorde al estilo
    private JScrollPane crearScrollConEstilo(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null); 
        scroll.getVerticalScrollBar().setUnitIncrement(25); 
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBackground(FONDO_BEIGE);
        
        // Personalizar la barra vertical para que tenga el estilo de la página y no la por defecto
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = COLOR_LINEAS;
                this.trackColor = FONDO_BEIGE;
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return crearBotonInvisible();
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                return crearBotonInvisible();
            }
        });
        
        return scroll;
    }

    private JButton crearBotonInvisible() {
        JButton b = new JButton();
        b.setPreferredSize(new Dimension(0, 0));
        b.setMinimumSize(new Dimension(0, 0));
        b.setMaximumSize(new Dimension(0, 0));
        return b;
    }

    private JPanel crearPantallaSeleccion() {
        // Pantalla completa sin scroll ni cortes
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(FONDO_BEIGE);
        
        JPanel panelInterno = new JPanel();
        panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
        panelInterno.setBackground(FONDO_BEIGE);

        JLabel lblHeader = new JLabel("M E N Ú");
        lblHeader.setFont(FUENTE_TITULO_MENU);
        lblHeader.setForeground(COLOR_TEXTO);
        lblHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(FONDO_BEIGE);
        
        JPanel lin1 = crearLineaSeparadoraDinamica();
        JPanel lin2 = crearLineaSeparadoraDinamica();
        
        panelTitulo.add(lin1);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 15)));
        panelTitulo.add(lblHeader);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 15)));
        panelTitulo.add(lin2);
        panelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblPregunta = new JLabel("¿Qué tipo de menú quieres ver?");
        lblPregunta.setFont(new Font("Georgia", Font.ITALIC, 28));
        lblPregunta.setForeground(COLOR_SECUNDARIO);
        lblPregunta.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnInfantil = crearBotonOscuro("MENÚ INFANTIL");
        JButton btnSaludable = crearBotonOscuro("MENÚ SALUDABLE");

        btnInfantil.addActionListener(e -> generarMenu(new FabricaMenuInfantil(), "MENÚ INFANTIL"));
        btnSaludable.addActionListener(e -> generarMenu(new FabricaMenuSaludable(), "MENÚ SALUDABLE"));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        panelBotones.setBackground(FONDO_BEIGE);
        panelBotones.add(btnInfantil);
        panelBotones.add(btnSaludable);

        panelInterno.add(panelTitulo);
        panelInterno.add(Box.createRigidArea(new Dimension(0, 50)));
        panelInterno.add(lblPregunta);
        panelInterno.add(Box.createRigidArea(new Dimension(0, 50)));
        panelInterno.add(panelBotones);

        panel.add(panelInterno);
        return panel;
    }

    private JPanel crearPantallaMenu() {
        JPanel panelCentrado = new JPanel(new GridBagLayout());
        panelCentrado.setBackground(FONDO_BEIGE);

        // Usamos un panel con GridBagLayout interno para que los componentes se centren pero fluyan de punta a punta si es necesario
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(FONDO_BEIGE);
        panelContenido.setBorder(new EmptyBorder(40, 60, 60, 60));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelTop.setBackground(FONDO_BEIGE);
        JButton btnVolver = crearBotonMinimalista("← Volver a selección");
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "SELECCION"));
        panelTop.add(btnVolver);
        panelTop.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblTituloCarta = new JLabel("M E N Ú");
        lblTituloCarta.setFont(FUENTE_TITULO_MENU);
        lblTituloCarta.setForeground(COLOR_TEXTO);
        lblTituloCarta.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDesayunoDetalle = crearLabelPlatoClickeable();
        lblAlmuerzoDetalle = crearLabelPlatoClickeable();
        lblCenaDetalle = crearLabelPlatoClickeable();
        
        lblDesayunoDetalle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { abrirDetalleReceta(desayunoActual.obtenerNombre(), desayunoActual.obtenerCalorias(), desayunoActual.obtenerDescripcion(), desayunoActual.obtenerIngredientes(), desayunoActual.obtenerPasos()); }
        });
        lblAlmuerzoDetalle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { abrirDetalleReceta(almuerzoActual.obtenerNombre(), almuerzoActual.obtenerCalorias(), almuerzoActual.obtenerDescripcion(), almuerzoActual.obtenerIngredientes(), almuerzoActual.obtenerPasos()); }
        });
        lblCenaDetalle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { abrirDetalleReceta(cenaActual.obtenerNombre(), cenaActual.obtenerCalorias(), cenaActual.obtenerDescripcion(), cenaActual.obtenerIngredientes(), cenaActual.obtenerPasos()); }
        });

        lblCaloriasTotales = new JLabel();
        lblCaloriasTotales.setFont(new Font("Garamond", Font.BOLD | Font.ITALIC, 24));
        lblCaloriasTotales.setForeground(COLOR_TEXTO);
        lblCaloriasTotales.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estructura de la carta adaptativa de punta a punta sin cortes
        panelContenido.add(panelTop);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        panelContenido.add(crearLineaSeparadoraDinamica()); 
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        panelContenido.add(lblTituloCarta);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        panelContenido.add(crearLineaSeparadoraDinamica()); 
        panelContenido.add(Box.createRigidArea(new Dimension(0, 45)));
        
        // Sección Desayuno
        panelContenido.add(crearEtiquetaSeccion("D E S A Y U N O"));
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(crearLineaSeparadoraDelgadaDinamica()); 
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblDesayunoDetalle);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Sección Almuerzo
        panelContenido.add(crearEtiquetaSeccion("A L M U E R Z O"));
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(crearLineaSeparadoraDelgadaDinamica()); 
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblAlmuerzoDetalle);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Sección Cena
        panelContenido.add(crearEtiquetaSeccion("C E N A"));
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(crearLineaSeparadoraDelgadaDinamica()); 
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblCenaDetalle);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 50)));
        
        panelContenido.add(crearLineaSeparadoraPunteadaDinamica());
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        panelContenido.add(lblCaloriasTotales);

        panelCentrado.add(panelContenido);
        return panelCentrado;
    }
    
    private JPanel crearPantallaDetalle() {
        JPanel panelCentrado = new JPanel(new GridBagLayout());
        panelCentrado.setBackground(FONDO_BEIGE);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(FONDO_BEIGE);
        panelContenido.setBorder(new EmptyBorder(40, 60, 60, 60));

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelTop.setBackground(FONDO_BEIGE);
        JButton btnVolver = crearBotonMinimalista("← Volver al menú");
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "MENU"));
        panelTop.add(btnVolver);
        panelTop.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDetalleTitulo = new JLabel();
        lblDetalleTitulo.setFont(new Font("Georgia", Font.BOLD, 40));
        lblDetalleTitulo.setForeground(COLOR_TEXTO);
        lblDetalleTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblDetalleContenido = new JLabel();
        lblDetalleContenido.setFont(FUENTE_DETALLE);
        lblDetalleContenido.setForeground(COLOR_TEXTO);
        lblDetalleContenido.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelContenido.add(panelTop);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));
        panelContenido.add(crearLineaSeparadoraDinamica());
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        panelContenido.add(lblDetalleTitulo);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));
        panelContenido.add(crearLineaSeparadoraDinamica());
        panelContenido.add(Box.createRigidArea(new Dimension(0, 40)));
        panelContenido.add(lblDetalleContenido);

        panelCentrado.add(panelContenido);
        return panelCentrado;
    }

    private JLabel crearLabelPlatoClickeable() {
        JLabel label = new JLabel();
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { label.setBorder(BorderFactory.createMatteBorder(0, 6, 0, 0, new Color(130, 95, 70))); }
            public void mouseExited(MouseEvent evt) { label.setBorder(null); }
        });
        return label;
    }

    private JButton crearBotonOscuro(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Garamond", Font.BOLD, 20));
        boton.setForeground(FONDO_BEIGE);
        boton.setBackground(COLOR_TEXTO);
        boton.setFocusPainted(false);
        boton.setBorder(new EmptyBorder(18, 35, 18, 35));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { boton.setBackground(new Color(65, 55, 48)); }
            public void mouseExited(MouseEvent evt) { boton.setBackground(COLOR_TEXTO); }
        });
        return boton;
    }

    private JButton crearBotonMinimalista(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Garamond", Font.ITALIC, 18));
        boton.setForeground(COLOR_TEXTO);
        boton.setBackground(FONDO_BEIGE);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private JLabel crearEtiquetaSeccion(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FUENTE_SECCION);
        label.setForeground(COLOR_TEXTO);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // Líneas separadoras dinámicas que se expanden de punta a punta de la pantalla de manera fluida sin cortes
    private JPanel crearLineaSeparadoraDinamica() {
        JPanel linea = new JPanel();
        linea.setBackground(COLOR_LINEAS);
        linea.setAlignmentX(Component.CENTER_ALIGNMENT);
        linea.setPreferredSize(new Dimension(800, 3));
        linea.setMaximumSize(new Dimension(1200, 3));
        return linea;
    }
    
    private JPanel crearLineaSeparadoraDelgadaDinamica() {
        JPanel linea = new JPanel();
        linea.setBackground(COLOR_SECUNDARIO);
        linea.setAlignmentX(Component.CENTER_ALIGNMENT);
        linea.setPreferredSize(new Dimension(800, 1));
        linea.setMaximumSize(new Dimension(1200, 1));
        return linea;
    }
    
    private JPanel crearLineaSeparadoraPunteadaDinamica() {
        JPanel linea = new JPanel();
        linea.setBackground(FONDO_BEIGE);
        linea.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_LINEAS));
        linea.setAlignmentX(Component.CENTER_ALIGNMENT);
        linea.setPreferredSize(new Dimension(800, 1));
        linea.setMaximumSize(new Dimension(1200, 1));
        return linea;
    }

    private void generarMenu(FabricaMenuDiario fabrica, String titulo) {
        desayunoActual = fabrica.crearDesayuno();
        almuerzoActual = fabrica.crearAlmuerzo();
        cenaActual = fabrica.crearCena();

        int caloriasTotales = desayunoActual.obtenerCalorias() + almuerzoActual.obtenerCalorias() + cenaActual.obtenerCalorias();

        StringBuilder tituloSep = new StringBuilder();
        for (char c : titulo.toCharArray()) { tituloSep.append(c).append(" "); }
        lblTituloCarta.setText(tituloSep.toString().trim());
        
        lblDesayunoDetalle.setText(formatearPlatoCarta(desayunoActual.obtenerNombre(), desayunoActual.obtenerCalorias(), desayunoActual.obtenerDescripcion()));
        lblAlmuerzoDetalle.setText(formatearPlatoCarta(almuerzoActual.obtenerNombre(), almuerzoActual.obtenerCalorias(), almuerzoActual.obtenerDescripcion()));
        lblCenaDetalle.setText(formatearPlatoCarta(cenaActual.obtenerNombre(), cenaActual.obtenerCalorias(), cenaActual.obtenerDescripcion()));
                                 
        lblCaloriasTotales.setText("NUTRICIÓN DIARIA TOTAL . . . . . . . . . . . . . . . . . . . . . . . . " + caloriasTotales + " kcal");

        cardLayout.show(panelContenedor, "MENU");
    }
    
    private String formatearPlatoCarta(String plato, int calorias, String descripcion) {
        return "<html><div style='text-align: center; width: 750px;'>" +
               "<table width='100%' style='color:#2D2620; font-family:Garamond;'>" +
               "<tr><td style='font-size:22px; font-weight:bold; text-align: left;'>" + plato.toUpperCase() + "</td>" +
               "<td align='right' style='font-size:22px; font-weight:bold; text-align: right;'>" + calorias + " kcal</td></tr>" +
               "<tr><td colspan='2' style='font-size:16px; color:#554A40; padding-top:6px; text-align: center;'><i>" + descripcion + "</i></td></tr>" +
               "</table></div></html>";
    }
    
    private void abrirDetalleReceta(String nombre, int calorias, String descripcion, String ingredientes, String preparacion) {
        lblDetalleTitulo.setText("<html><div style='text-align: center; width: 750px;'>" + nombre.toUpperCase() + "</div></html>");
        
        StringBuilder sb = new StringBuilder();
        sb.append("<html><div style='width: 750px; text-align: left; line-height: 1.7;'>");
        
        sb.append("<div style='text-align: center;'><b style='font-size:24px; color:#504137;'>Descripción detallada</b><br>");
        sb.append("<span style='font-size:20px;'><i>").append(descripcion).append("</i></span></div><br><br>");
        
        sb.append("<div style='text-align: center;'><b style='font-size:24px; color:#504137;'>Aporte calórico</b><br>");
        sb.append("<span style='font-size:20px;'>").append(calorias).append(" kcal</span></div><br><br>");
        
        sb.append("<b style='font-size:24px; color:#504137;'>Productos necesarios</b><br>");
        String[] ings = ingredientes.split(",");
        sb.append("<ul style='font-size:20px; margin-left: 20px;'>");
        for (String ing : ings) { sb.append("<li>").append(ing.trim()).append("</li>"); }
        sb.append("</ul><br>");
        
        sb.append("<b style='font-size:24px; color:#504137;'>Preparación paso a paso</b><br>");
        String prepFormat = preparacion.replaceAll("(\\\\d+\\.)", "<br><br><b>$1</b>");        if (prepFormat.startsWith("<br><br>")) {
            prepFormat = prepFormat.substring(8);
        }
        sb.append("<span style='font-size:20px;'>").append(prepFormat).append("</span><br><br>");
        
        sb.append("</div></html>");
        
        lblDetalleContenido.setText(sb.toString());
        cardLayout.show(panelContenedor, "DETALLE");
    }
}
