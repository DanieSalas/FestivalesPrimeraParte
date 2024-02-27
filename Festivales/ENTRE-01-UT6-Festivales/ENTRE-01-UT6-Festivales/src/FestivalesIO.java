

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene m�odos est�ticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FestivalesIO {

    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }


    public static Festival parsearLinea(String lineaFestival) {
        if (lineaFestival == null || lineaFestival.isEmpty()) {
            return null;
        }

        String[] festivalData = lineaFestival.trim().split(":");
        String name = capitalizeFirstLetterOfEachWord(festivalData[0]);
        String place = festivalData[1].toUpperCase();
        LocalDate startDate = LocalDate.parse(festivalData[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int duration = Integer.parseInt(festivalData[3]);
        Set<Style> styles = getStyles(Arrays.copyOfRange(festivalData, 4, festivalData.length));

        return new Festival(name, place, startDate, duration, styles);
    }


    private static String capitalizeFirstLetterOfEachWord(String input) {
        String[] words = input.split(" ");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }

        return capitalized.toString().trim();
    }


    private static Set<Style> getStyles(String[] styleStrings) {
        Set<Style> styles = new HashSet<>();
        for (String styleString : styleStrings) {
            Style style = Style.valueOf(styleString.toUpperCase());
            styles.add(style);
        }
        return EnumSet.copyOf(styles);
    }

    enum Style {
        INDIE, POP, ROCK
    }

}