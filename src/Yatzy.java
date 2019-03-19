import java.util.Arrays;
import java.util.Scanner;

public class Yatzy {

    private static Täring[] täringud = {new Täring(), new Täring(), new Täring(), new Täring(), new Täring()};
    private static Integer[] tulemus = new Integer[16];
    private static int viskeid = 0;
    private static int lahter;

    private static String[] tabel = {
            "\nYATZY",
            "1.  Ühed         ",
            "2.  Kahed        ",
            "3.  Kolmed       ",
            "4.  Neljad       ",
            "5.  Viied        ",
            "6.  Kuued        ",
            "-----------------",
            "7.  Paar         ",
            "8.  Kaks paari   ",
            "9.  Kolmik       ",
            "10. Nelik        ",
            "11. Väike Rida   ",
            "12. Suur Rida    ",
            "13. Täismaja     ",
            "14. Juhus        ",
            "15. Yatzy!       "
    };


    private static void prindiTabel() {
        for (String rida : tabel) {
            if (rida.contains(".")) { // Peab ainult läbi vaatama need, millel number ees.
                Integer punktid = tulemus[Integer.parseInt(rida.substring(0, rida.indexOf("."))) - 1];
                if (punktid == null) // Kui selle rea punktid on juba sisestatud.
                    System.out.println(rida);
                else
                    System.out.println(rida + punktid);
            } else
                System.out.println(rida);
        }
        System.out.println();
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

    private static int punktid(int lahter) {
        int sum = 0;
        int upper = 0;
        int temp = 0;

        if (lahter >= 1 && lahter <= 6) {
            for (Täring täring : täringud) {
                if (täring.getArv() == lahter)
                    sum += lahter;
            }
            upper += sum;
            if (upper >= 63)
                System.out.println("Saite veel 50 lisapunkti!");
            return sum;

        } else if (lahter == 7) {
            for (int i = 0; i < täringud.length; i++) {
                for (int j = i + 1; j < täringud.length; j++) {
                    if (täringud[i].getArv() == täringud[j].getArv())
                        sum = täringud[i].getArv() + täringud[j].getArv();
                }
            }
            return sum;

        } else if (lahter == 8) {
            boolean leitud = false;
            int paar = 0;

            for (int i = 0; i < täringud.length - 1; i++) {
                if (täringud[i].getArv() == paar)
                    continue;
                for (int j = i + 1; j < täringud.length; j++) {
                    if (täringud[i].getArv() == täringud[j].getArv()) {
                        if (täringud[i].getArv() != paar) {
                            if (leitud)
                                sum = (paar + täringud[i].getArv()) * 2;
                            paar = täringud[i].getArv();
                            leitud = true;
                            break;
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 9) {
            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv())
                            sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv();
                    }
                }
            }
            return sum;

        } else if (lahter == 10) {
            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        for (int l = k + 1; l < täringud.length; l++) {
                            if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv() && täringud[k].getArv() == täringud[l].getArv())
                                sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv() + täringud[l].getArv();
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 11 || lahter == 12) {
            Arrays.sort(täringud);
            for (int i = 1; i < täringud.length; i++) {
                if (täringud[i - 1].getArv() + 1 == täringud[i].getArv())
                    sum += täringud[i - 1].getArv();
            }
            if (sum == 10 || sum == 14)
                return sum + täringud[4].getArv();
            else
                return 0;

        } else if (lahter == 13) {
            for (int i = 0; i < täringud.length - 2; i++) {
                for (int j = i + 1; j < täringud.length - 1; j++) {
                    for (int k = j + 1; k < täringud.length; k++) {
                        if (täringud[i].getArv() == täringud[j].getArv() && täringud[j].getArv() == täringud[k].getArv()) {
                            sum = täringud[i].getArv() + täringud[j].getArv() + täringud[k].getArv();
                            int[] tempL = new int[2];
                            for (int l = 0; l < täringud.length; l++) {
                                if (!(täringud[l].getArv() == täringud[i].getArv())) {
                                    tempL[temp] = täringud[l].getArv();
                                    temp++;
                                }
                            }
                            if (tempL[0] == tempL[1])
                                sum += tempL[0] * 2;
                        }
                    }
                }
            }
            return sum;

        } else if (lahter == 14) {
            for (Täring i : täringud) {
                sum += i.getArv();
            }
            return sum;

        } else if (lahter == 15) {
            int arv = 0;
            temp = täringud[0].getArv();
            for (int i = 1; i < täringud.length; i++) {
                if (temp == täringud[i].getArv())
                    arv++;
            }
            if (arv == 4)
                return 50;
            else
                return 0;

        } else
            return 0;
    }

    public static void main(String[] args) {
        prindiTabel();
        boolean viskeTulemus = täringuViskamine("00000");

        Scanner scanner = new Scanner(System.in);
        while (viskeTulemus) {
            System.out.print("Millised täringud säilitada? ");
            viskeTulemus = täringuViskamine(scanner.nextLine());
        }

        System.out.print("Millisesse lahtrisse soovite punktid sisestada? ");
        lahter = Integer.parseInt(scanner.nextLine());
        if (!(lahter >= 1 && lahter <= 15)) {
            System.out.print("Palun sisestage sobiv lahtrinumber: ");
            lahter = scanner.nextInt();
        }

        System.out.println(punktid(lahter));
    }

}
