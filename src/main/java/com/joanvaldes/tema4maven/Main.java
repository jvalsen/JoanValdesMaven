package com.joanvaldes.tema4maven;

import com.github.lalyos.jfiglet.FigletFont;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //nombre con figlet
        String nombre = "Joan Valdés";
        String banner = FigletFont.convertOneLine(nombre);
        //curriculum
        String curriculum =
                "---Curriculum vitae---\n" +
                        "-Nombre: Joan Valdés Sendra\n" +
                        "-Dirección: Xeraco\n" +
                        "-Telefono: +34 600 05 25 88\n" +
                        "-Correo: joavalsen@alu.edu.gva.es\n" +
                        "-Formación académica: Grado de bachillerato en Xeraco\n" +
                        " Actualmente estudiante de DAW en M. Enriquez Gandia\n" +
                        "-Idiomas: Valenciano, Castellano, Inglés\n" +
                        "-Disponibilidad móvil\n" +
                        "-Aptitudes: Trabajador, eficaz, talentoso\n";


        String textoCompleto = banner + "\n" + curriculum;
        List<String> lines = Arrays.asList(textoCompleto.split("\n"));
        //crear ventana para lanterna
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);

        //animación
        TerminalSize size = screen.getTerminalSize();
        int height = size.getRows();


        int yOffset = height;

        //  scroll
        while (yOffset > -lines.size()) {

            //Dibujar el texto con el método drawFrame:
            drawFrame(screen, lines, yOffset);
            screen.doResizeIfNecessary();

            //Esperar 100 milisegundos con el método Thread.sleep(100) para que se pueda ver la animación
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignored) {}

            yOffset--;
        }

        screen.stopScreen();
    }

    //metodo lanterna dibujar texto en la ventana
    private static void drawFrame(Screen screen, List<String> lines, int yOffset)
            throws IOException {

        //devolver el tamaño actual del terminal
        TerminalSize size = screen.getTerminalSize();
        int width = size.getColumns();
        int height = size.getRows();

        //Borrar el buffer interno del frame
        screen.clear();
        //llamar una vez por frame
        TextGraphics tg = screen.newTextGraphics();

        for (int i = 0; i < lines.size(); i++) {
            int y = yOffset + i;
            if (y < 0 || y >= height) continue;

            String line = lines.get(i);
            int x = Math.max(0, (width - line.length()) / 2);

            String visible = (line.length() > width)
                    ? line.substring(0, width)
                    : line;
            //Escribir el String visible en la posición (x, y)
            tg.putString(x, y, visible);
        }
        //hacer que el texto se muestre en la ventana.
        screen.refresh();
    }
}
