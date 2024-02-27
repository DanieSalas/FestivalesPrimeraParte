
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Un objeto de esta clase almacena los datos de un
 * festival.
 * Todo festival tiene un nombre, se celebra en un lugar
 * en una determinada fecha, dura una serie de d?as y
 * se engloba en un conjunto determinado de estilos
 *
 */
import java.util.EnumSet;
import java.util.Set;

public class Festival {
    private final String nombre;
    private final String lugar;
    private final LocalDate fechaInicio;
    private final int duracion;
    private final Set<Estilo> estilos;

    public Festival(String nombre, String lugar, LocalDate fechaInicio,
                    int duracion, Set<FestivalesIO.Style> estilos) {
        this.nombre = nombre;
        this.lugar = lugar;
        this.fechaInicio = fechaInicio;
        this.duracion = duracion;
        this.estilos = EnumSet.copyOf(estilos);
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public int getDuracion() {
        return duracion;
    }

    public Set<Estilo> getEstilos() {
        return EnumSet.copyOf(estilos);
    }

    public void addEstilo(Estilo estilo) {
        this.estilos.add(estilo);
    }

    public Mes getMes() {
        return Mes.valueOf(getFechaInicio().getMonth().name());
    }

    public boolean empiezaAntesQue(Festival otro) {
        return this.fechaInicio.isBefore(otro.fechaInicio);
    }

    public boolean empiezaDespuesQue(Festival otro) {
        return this.fechaInicio.isAfter(otro.fechaInicio);
    }

    public boolean haConcluido() {
        LocalDate fechaActual = LocalDate.now();
        if (duracion == 1) {
            return fechaActual.isAfter(fechaInicio);
        } else {
            return fechaActual.isAfter(fechaInicio.plusDays(duracion - 1));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nombre).append("\n");
        stringBuilder.append(lugar).append("\n");

        if (duracion == 1) {
            stringBuilder.append(fechaInicio.format(DateTimeFormatter.ofPattern("dd MMM. yyyy")))
                    .append(" (quedan ")
                    .append(ChronoUnit.DAYS.between(LocalDate.now(), fechaInicio))
                    .append(" dias)")
                    .append("\n");
        } else {
            stringBuilder.append(fechaInicio.format(DateTimeFormatter.ofPattern("dd MMM. yyyy")))
                    .append(" - ")
                    .append(fechaInicio.plusDays(duracion - 1).format(DateTimeFormatter.ofPattern("dd MMM. yyyy")))
                    .append(" (")
                    .append(haConcluido() ? "concluido" : "ON")
                    .append(")\n");
        }

        stringBuilder.append("[");
        int i = 0;
        for (Estilo estilo : estilos) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(estilo.name());
            i++;
        }
        stringBuilder.append("]\n");

        return stringBuilder.toString();
    }

    /**
     * C?digo para probar la clase Festival
     *
     */
    public static void main(String[] args) {
        System.out.println("Probando clase Festival");
        String datosFestival = "Gazpatxo Rock : " +
                "valencia: 28-02-2022  :1  :rock" +
                ":punk " +
                ": hiphop ";
        Festival f1 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f1);

        datosFestival = "black sound fest:badajoz:05-02-2022:  21" +
                ":rock" + ":  blues";
        Festival f2 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f2);

        datosFestival = "guitar bcn:barcelona: 28-01-2022 :  170" +
                ":indie" + ":pop:fusion";
        Festival f3 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f3);

        datosFestival = "  benidorm fest:benidorm:26-01-2022:3" +
                ":indie" + ": pop  :rock";
        Festival f4 = FestivalesIO.parsearLinea(datosFestival);
        System.out.println(f4);


        System.out.println("\nProbando empiezaAntesQue() empiezaDespuesQue()" +
                "\n");
        if (f1.empiezaAntesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza antes que " + f2.getNombre());
        } else if (f1.empiezaDespuesQue(f2)) {
            System.out.println(f1.getNombre() + " empieza despu?s que " + f2.getNombre());
        } else {
            System.out.println(f1.getNombre() + " empieza el mismo d?a que " + f2.getNombre());
        }

        System.out.println("\nProbando haConcluido()\n");
        System.out.println(f4);
        System.out.println(f4.getNombre() + " ha concluido? " + f4.haConcluido());
        System.out.println(f1);
        System.out.println(f1.getNombre() + " ha concluido? " + f1.haConcluido());



    }
}
