import java.util.Scanner;

public class Yatzy {

    private static Täring[] täringud = {new Täring(), new Täring(), new Täring(), new Täring(), new Täring()};
    private static Integer[] tulemus = new Integer[6];
    private static int viskeid = 0;

    private static String[] tabel = {
            "YATZY",
            "1. Ühed        ",
            "2. Kahed       ",
            "3. Kolmed      ",
            "4. Neljad      ",
            "5. Viied       ",
            "6. Kuued       "
    };


    private static void prindiTabel() {
        for (String rida : tabel) {
            if (rida.contains(".")) { // Peab ainult läbi vaatama need, millel number ees.
                Integer punktid = tulemus[Integer.parseInt(rida.substring(0, rida.indexOf("."))) - 1];
                if (punktid == null) // Kui selle rea punktid on juba sisestatud.
                    System.out.println(rida);
                else
                    System.out.println(rida + punktid);
            } else {
                System.out.println(rida);
            }
        }
    }

    private static boolean täringuViskamine(String sisend) {
        /* Kui täringuid ei pea enam veeretama */
        if (sisend.equals("11111")) {
            viskeid = 0;
            return false;
        }

        /* Veeretab nõutud täringud uuesti */
        int temp = 0;
        for (Character number : sisend.toCharArray()) {
            if (number == '0')
                täringud[temp].veereta();
            temp++;
        }
        viskeid++;

        /* Prindib kõik täringud välja */
        System.out.print("Sinu visked: ");
        for (Täring täring : täringud)
            System.out.print(täring.getArv() + " ");
        System.out.println();

        if (viskeid == 3) {
            viskeid = 0;
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        prindiTabel();
        boolean viskeTulemus = täringuViskamine("00000");
        Scanner scanner = new Scanner(System.in);
        while (viskeTulemus) {
            System.out.print("Millised täringud säilitada? ");
            viskeTulemus = täringuViskamine(scanner.nextLine());
        }
        System.out.println("Lõpp");
    }

}
