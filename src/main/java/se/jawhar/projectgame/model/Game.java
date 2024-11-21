package se.jawhar.projectgame.model;

import java.util.Scanner;

public class Game {

    private Resident resident; // Spelaren (Resident) som deltar i spelet

    private Burglar burglar; // Inbrottstjuven (Burglar) som spelaren ska besegra

    Scanner scanner = new Scanner(System.in); // Scanner för att läsa användarens input

    // konstanter för de olika rummen i spelet
    private static final String VARDAGSRUMMET = "Vardagsrummet";

    private static final String KÖKET = "Köket";

    private static final String HALLEN = "Hallen";

    private static final String SOVRUMMET = "Sovrummet";

    private static final String KONTORET = "Kontoret";

    private static final String START = "start";

    private static String currentLocation = START;

    private boolean fryingPanFound = false; // för att hålla reda på om stekpannan har plockats upp

    private boolean fightDone = false;  // Flagga för att hålla reda på om striden är avslutad

    boolean running = true; // Flagga för att kontrollera om spelet fortfarande pågår

    public Game() { // Konstruktor för att initialisera spelet

        this.resident = new Resident("Resident", 12, 3);

        this.burglar = new Burglar("Burglar", 12, 4);
    }

    // Metod för att visa ett välkomstmeddelande till spelaren
    public void welcomeMessage() {
        System.out.println("-----------------------------------");
        System.out.println(" Välkommen till spelet! ");
        System.out.println("-----------------------------------");
        System.out.println("Spelet går ut på att du ska leta efter en inbrottstjuv och besegra tjuven i en fight,\nDu vinner spelet genom att vinna figten och ringa polisen. \n ");

        System.out.println("Du somnade på soffan i vardagsrummet och vaknar nu upp av ett ovanligt ljud!");

        currentLocation = VARDAGSRUMMET;
    }

    // Metod som styr spelarens rörelser mellan rummen
    public void roomDirection() {
        welcomeMessage();
        while (running && resident.isConscious()) {
            if (currentLocation.equals(VARDAGSRUMMET)) {
                // Om spelaren är i Vardagsrummet, visa rummen man kan gå till
                System.out.println("Vart vill du gå?");
                System.out.println("Vardagsrummet");
                System.out.println("Köket");
                System.out.println("Hallen");
                System.out.println("Sovrummet");
                System.out.println("Kontoret");
                System.out.println("Avsluta");

                String userInput = scanner.nextLine();
                switch (userInput) {
                    case "Vardagsrummet" -> vardagsrummet();
                    case "Köket" -> köket();
                    case "Hallen" -> hallen();
                    case "Sovrummet" -> sovrummet();
                    case "Kontoret" -> kontoret();
                    case "Avsluta" -> running = false;
                    default -> System.out.println("Felaktigt val! Försök igen.");
                }
            } else {
                // Om spelaren är i ett annat rum, kan de bara gå tillbaka till Vardagsrummet
                System.out.println("Du är i " + currentLocation + " Gå tillbaka till vardagsrummet");
                String userInput = scanner.nextLine();

                if (userInput.equalsIgnoreCase("Vardagsrummet")) {
                    currentLocation = VARDAGSRUMMET; // Återgå till vardagsrummet
                    System.out.println("Du går tillbaka till vardagsrummet.");
                } else {
                    System.out.println("Felaktigt val!, Du måste gå via vardagsrummet");
                }
            }
        }

    }

    // Metod som utför attacken mellan spelaren (resident) och tjuven (burglar)
    public void executeAttack(Entity resident, Entity burglar) {


        while (resident.isConscious() && burglar.isConscious()) {

            resident.punch(burglar);

            if (!burglar.isConscious()) { // Om tjuven är medvetslös, spelaren vinner

                System.out.println("Bra jobbat!, Du lyckades besegra tjuven!");

                System.out.println("Nu när rånaren är knockad kan du ringa polisen i kontoret");

                fightDone = true; // här tar striden slut

                break;
            }

            burglar.punch(resident); // här ska tjuven slå tillbaka

            if (!resident.isConscious()) { // Om spelaren är medvetslös, tjuven vinner

                System.out.println("Du har förlorat striden, Spelet är slut!");

                fightDone = true;

                running = false; // Spelet tar slut
                break;
            }
        }


    }

    // Metod som hanterar spelets logik för vardagsrummet
    private void vardagsrummet() {

        if (!currentLocation.equals(VARDAGSRUMMET)) {

            System.out.println("Du befinner dig i vardagsrummet");

            currentLocation = VARDAGSRUMMET; // Uppdatera spelarens nuvarande plats

        } else {

            System.out.println("Du befinner dig redan här!");

        }

    }

    // Metod som hanterar logiken för kontoret (där spelaren kan ringa polisen)
    private void kontoret() {

        // Kontrollera om spelaren är i vardagsrummet innan de går till kontoret
        if (currentLocation.equals(VARDAGSRUMMET)) {

            currentLocation = KONTORET;

            System.out.println("Du går in i kontoret.");

            // Om tjuven är knockad, erbjud spelaren att ringa polisen
            if (!burglar.isConscious()) {
                System.out.println("Grattis, du har knockat tjuven!");

                System.out.println("Vill du ringa polisen? Skriv '112'.");
                System.out.println("Vill du inte ringa polisen skriv 'Nej'");

                String callInput = scanner.nextLine();

                if (callInput.equals("112")) {
                    System.out.println("Du ringer nu polisen och vinner spelet. Grattis!");
                    running = false;
                } else if (!callInput.equals("112")) {
                    System.out.println("Rånaren vaknar upp igen, du ringde inte till polisen.");
                    System.out.println("Game over!");
                    running = false; // Spelet är slut

                }
            } else {
                System.out.println("Du kommer till kontoret men här finns det inget, På vägen ut ser du en telefon");
            }
        } else {
            System.out.println("Du måste gå via vardagsrummet");
        }

    }

    // Metod som hanterar att spelaren plockar upp en stekpanna
    private void pickUpFryingPan() {

        System.out.println("Du plockar upp en stekpanna!");

        resident.addDamage(3);

        fryingPanFound = true; // boolean för att markera att stekpannan är plockad

        System.out.println("Du har nu med dig stekpannan som gör dig starkare och kan hjälpa dig att försvara dig själv!");
    }

    // Metod för att låta spelaren fly till ett annat rum (Hallen)
    private void runAway() {
        System.out.println("Du flyr från tjuven!");

        currentLocation = HALLEN; // Uppdatera spelarens plats till Hallen
    }

    // Metod som hanterar logiken för köket
    private void köket() {

        // Om spelaren är i vardagsrummet, tillåter vi att gå till köket.
        if (currentLocation.equals(VARDAGSRUMMET)) {

            System.out.println("Du går in i köket, här verkar det inte hända så mycket");

            currentLocation = KÖKET;  // Uppdatera spelarens plats till Köket

            if (!fryingPanFound) {

                // Om stekpannan inte är plockad än
                System.out.println("På bordet ser du en stekpanna. Vill du plocka upp den?");

                System.out.println("Plocka/Lämna");

                // Vänta på input om "plocka" eller "lämna"
                String panInput = scanner.nextLine();

                // Hantera spelarens beslut om att plocka upp eller lämna stekpannan
                boolean runLoop = true;
                while (runLoop) {

                    switch (panInput.toLowerCase()) {

                        case "plocka":

                            pickUpFryingPan();// Spelaren plockar upp stekpannan

                            runLoop = false;
                            break;  // Avsluta metoden och gå tillbaka till menyn

                        case "lämna":

                            System.out.println("Du lämnar stekpannan på bordet.");
                            runLoop = false;
                            break;  // Avsluta metoden och gå tillbaka till menyn

                        default:

                            System.out.println("Felaktigt val! Välj 'plocka' eller 'lämna'.");

                            panInput = scanner.nextLine();  // Vänta på ny input
                    }
                }
            } else {

                // Om stekpannan redan är plockad
                System.out.println("Köket är tomt, du har redan plockat upp stekpannan.");

            }

        } else if (currentLocation.equals(KÖKET)) {

            // Om spelaren redan är i köket
            System.out.println("Du befinner dig redan i köket.");

        } else {
            // Om spelaren inte är i vardagsrummet och försöker gå till köket direkt
            System.out.println("Du måste gå via vardagsrummet för att komma till köket.");
        }
    }

    // Metod för att hantera interaktionen i hallen.
    private void hallen() {

        // Om tjuven redan är knockad, skriv ett meddelande och skicka spelaren till hallen
        if (fightDone) {
            System.out.println("Rånaren ligger redan knockad här inne!, Skynda dig till kontoret");
            currentLocation = HALLEN;
            return;
        }
        // Om spelaren befinner sig i vardagsrummet, fortsätt med att gå till hallen.
        if (currentLocation.equals(VARDAGSRUMMET)) {

            System.out.println("Du kommer till hallen och ser en inbrottstjuv!");


            System.out.println("Tjuven verkar beväpnad! Är du redo att slåss mot tjuven eller vill du fly till ett annat rum?");

            System.out.println("Skriv 'Slåss' för att slåss, eller 'Fly' för att fly.");

            String fightInput = scanner.nextLine();

            // Sätt den aktuella platsen till hallen
            currentLocation = HALLEN;

            // En loop som kör tills spelaren gör ett korrekt val
            while (true) {

                // Kollar vilket alternativ spelaren valde
                switch (fightInput.toLowerCase()) {

                    case "slåss":
                        // Utför attack

                        executeAttack(resident, burglar);

                        return;

                    case "fly":

                        runAway(); // Gå tillbaka till rumsvalet

                        return;

                    default:

                        System.out.println("Felaktigt val! Du måste välja mellan 'Slåss' eller 'Fly'.");

                        fightInput = scanner.nextLine();
                }
            }

            // Om spelaren inte är i vardagsrummet, påminn om att de måste gå via vardagsrummet för att komma till hallen.
        } else {
            System.out.println("Du måste gå via vardagsrummet för att komma till hallen.");
        }


    }

    // Metod för att hantera interaktionen i sovrummet.
    private void sovrummet() {
        if (currentLocation.equals(VARDAGSRUMMET)) {

            System.out.println("Du kommer till sovrummet för att ta dig en titt, Men märker inte av något ovanligt");

            // Sätt den aktuella platsen till sovrummet
            currentLocation = SOVRUMMET;

            // Om spelaren inte är i vardagsrummet, påminn om att de måste gå via vardagsrummet för att komma till sovrummet.
        } else
            System.out.println("Du måste gå via Vardagsrummet");


    }


}

