import java.util.*;
import java.io.*;

abstract class MenuItem implements Serializable {
    protected String name;
    protected double price;
    protected List<String> toppings;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.toppings = new ArrayList<>();
    }

    public void addTopping(String topping) {
        toppings.add(topping);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Price: $" + price + ", Toppings: " + String.join(", ", toppings);
    }

    public String getName() {
        return name;
    }

    public abstract String getType();
}

class Salad extends MenuItem {
    public Salad(String name, double price) {
        super(name, price);
    }

    @Override
    public String getType() {
        return "Salad";
    }
}

class Sandwich extends MenuItem {
    private String breadType;

    public Sandwich(String name, double price, String breadType) {
        super(name, price);
        this.breadType = breadType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Bread Type: " + breadType;
    }

    @Override
    public String getType() {
        return "Sandwich";
    }
}

class FrozenYogurt extends MenuItem {
    private String baseFlavor;

    public FrozenYogurt(String name, double price, String baseFlavor, String[] toppings) {
        super(name, price);
        this.baseFlavor = baseFlavor;
        this.toppings.addAll(Arrays.asList(toppings));
    }

    @Override
    public String toString() {
        return super.toString() + ", Base Flavor: " + baseFlavor;
    }

    @Override
    public String getType() {
        return "Frozen Yogurt";
    }
}

public class Menu {
    private Scanner scanner;
    private List<MenuItem> menuItems;

    public Menu() {
        scanner = new Scanner(System.in);
        menuItems = new ArrayList<>();
        loadMenu();
    }

    public void displayMenu() {
        System.out.println("Welcome to the Menu");
        System.out.println();
        System.out.println("Actions:");
        System.out.println("1) Add a salad");
        System.out.println("2) Add a sandwich");
        System.out.println("3) Add a frozen yogurt");
        System.out.println("4) Display an item");
        System.out.println("5) Display all items");
        System.out.println("6) Add a topping to an item");
        System.out.println("7) Save menu");
        System.out.println("8) Load menu");
        System.out.println("9) Quit");
        System.out.println();
    }

    public void addSalad() {
        System.out.println("Please enter the name of the salad:");
        String name = scanner.nextLine();
        System.out.println("Please enter the price for " + name + ":");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        Salad salad = new Salad(name, price);
        menuItems.add(salad);

        System.out.println("Salad added: " + name);
        System.out.println("Price: $" + price);
    }

    public void addSandwich() {
        System.out.println("Please enter the name of the sandwich:");
        String name = scanner.nextLine();
        System.out.println("Please enter the price for " + name + ":");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        System.out.println("Please enter the bread type for " + name + ":");
        String breadType = scanner.nextLine();

        Sandwich sandwich = new Sandwich(name, price, breadType);
        menuItems.add(sandwich);

        System.out.println("Sandwich added: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Bread Type: " + breadType);
    }

    public void addFrozenYogurt() {
        System.out.println("Please enter the name of the frozen yogurt:");
        String name = scanner.nextLine();
        System.out.println("Please enter the price for " + name + ":");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline left-over

        System.out.println("Please enter the base yogurt flavor for " + name + ":");
        String base = scanner.nextLine();

        ArrayList<String> toppings = new ArrayList<>();
        String topping;
        do {
            System.out.println("Please enter a topping for " + name + " (enter 'done' to finish):");
            topping = scanner.nextLine();
            if (!topping.equalsIgnoreCase("done")) {
                toppings.add(topping);
            }
        } while (!topping.equalsIgnoreCase("done"));

        FrozenYogurt yogurt = new FrozenYogurt(name, price, base, toppings.toArray(new String[0]));
        menuItems.add(yogurt);

        System.out.println("Frozen Yogurt added: " + name);
        System.out.println("Price: $" + price);
        System.out.println("Base yogurt flavor: " + base);
        System.out.println("Toppings: " + String.join(", ", toppings));
    }

    public void displayItem() {
        if (menuItems.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }

        System.out.println("DISPLAYING all items:");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println((i + 1) + ") " + menuItems.get(i));
        }
    }

    public void displayAllItems() {
        if (menuItems.isEmpty()) {
            System.out.println("No items to display.");
            return;
        }

        System.out.println("DISPLAYING all items:");
        for (int i = 0; i < menuItems.size(); i++) {
            System.out.println("**** " + (i + 1) + " **** " + menuItems.get(i));
        }
    }

    public void addToppingToItem() {
        displayAllItems();
        System.out.println("Please choose an item to add topping to: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over

        if (index < 1 || index > menuItems.size()) {
            System.out.println("Invalid item index.");
            return;
        }

        MenuItem item = menuItems.get(index - 1);

        if (item instanceof Salad) {
            System.out.print("Please enter the topping to add to the salad: ");
        } else if (item instanceof Sandwich) {
            System.out.print("Please enter the topping to add to the sandwich: ");
        } else if (item instanceof FrozenYogurt) {
            System.out.print("Please enter a topping to add to the frozen yogurt: ");
        } else {
            System.out.println("Toppings can only be added to Salad, Sandwich, or Frozen Yogurt.");
            return;
        }
        String topping = scanner.nextLine();
        item.addTopping(topping);
        System.out.println("Topping added: " + topping);
    }

    public void saveMenu() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("menu.dat"))) {
            out.writeObject(menuItems);
            System.out.println("Menu saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving menu: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadMenu() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("menu.dat"))) {
            menuItems = (List<MenuItem>) in.readObject();
            System.out.println("Menu loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            menuItems = new ArrayList<>();
            System.err.println("Error loading menu: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            menu.displayMenu();
            System.out.print("Choose an action: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1 -> menu.addSalad();
                case 2 -> menu.addSandwich();
                case 3 -> menu.addFrozenYogurt();
                case 4 -> menu.displayItem();
                case 5 -> menu.displayAllItems();
                case 6 -> menu.addToppingToItem();
                case 7 -> menu.saveMenu();
                case 8 -> menu.loadMenu();
                case 9 -> running = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
