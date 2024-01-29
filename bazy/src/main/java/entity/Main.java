package entity;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Wybierz odpowiedni moduł: 1 - moduł lekarza, 2 - moduł technika");
        int module = scan.nextInt();
        System.out.println("Podaj swój nr ID:");
        int ID = scan.nextInt();
        System.out.println("Podaj hasło:");
        String password = scan.nextLine();
        if (module == 1){

        }
        else if (module == 2){

        }
        else {
            System.out.println("Wybierz prawidłową opcję: 1 - moduł lekarza, 2 - moduł technika");
            module = scan.nextInt();
        }

    }




}
