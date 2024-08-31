import java.util.*;

/* CAR RENTAL SYSTEM  - JAVA OOPS*/

public class Car_rental_system {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Car> cars = initializeCars(); // Initialize cars

        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome to the Car Rental System");
            System.out.println("1. List available cars");
            System.out.println("2. View car details");
            System.out.println("3. Rent a car");
            System.out.println("4. Return a car");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    listAvailableCars(cars);
                    break;
                case 2:
                    viewCarDetails(cars);
                    break;
                case 3:
                    rentCar(cars);
                    break;
                case 4:
                    returnCar(cars);
                    break;
                case 5:
                    exit = true;
                    System.out.println("Thank you for using the Car Rental System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public static List<Car> initializeCars() {
        List<Car> cars = new ArrayList<>();

        // Initialize cars
        Car rangeRover = new Car(1, "Land Rover", "Range Rover", 2023, "SUV", "Petrol",
                "Automatic", 12.5, 15000.0, false, "BranchA");
        Car audi = new Car(2, "Audi", "R8", 2022, "Sports car", "Petrol",
                "Manual", 13.5, 80520.0, true, "Chandigarh");
        Car toyota = new Car(3, "Toyota", "Corolla", 2024, "Sedan", "Petrol",
                "Automatic", 15.0, 10000.0, true, "BranchB");
        Car bmw = new Car(4, "BMW", "X5", 2022, "SUV", "Diesel",
                "Automatic", 11.5, 20000.0, false, "BranchC");
        Car mercedes = new Car(5, "Mercedes-Benz", "C-Class", 2023, "Sedan", "Petrol",
                "Automatic", 14.0, 18000.0, true, "BranchD");

        cars.add(rangeRover);
        cars.add(audi);
        cars.add(toyota);
        cars.add(bmw);
        cars.add(mercedes);

        return cars;
    }

    public static void listAvailableCars(List<Car> cars) {
        System.out.println("\nAvailable Cars:");
        for (Car car : cars) {
            if (car.isAvailabilityStatus()) {
                System.out.println(car.getMake() + " " + car.getModel());
            }
        }
    }

    public static void viewCarDetails(List<Car> cars) {
        System.out.print("\nEnter the ID of the car you want to view details for: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getID() == id) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            System.out.println("\nCar Details:");
            printCarInfo(selectedCar);
        } else {
            System.out.println("Car with ID " + id + " not found.");
        }
    }

    public static void rentCar(List<Car> cars) {
        System.out.print("\nEnter the ID of the car you want to rent: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getID() == id) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            if (selectedCar.isAvailabilityStatus()) {
                selectedCar.setAvailabilityStatus(false);
                System.out.println("You have successfully rented the car: " + selectedCar.getMake() + " " + selectedCar.getModel());
            } else {
                System.out.println("Sorry, the selected car is not available for rent.");
            }
        } else {
            System.out.println("Car with ID " + id + " not found.");
        }
    }

    public static void returnCar(List<Car> cars) {
        System.out.print("\nEnter the ID of the car you want to return: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getID() == id) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            if (!selectedCar.isAvailabilityStatus()) {
                selectedCar.setAvailabilityStatus(true);
                System.out.println("You have successfully returned the car: " + selectedCar.getMake() + " " + selectedCar.getModel());
            } else {
                System.out.println("This car is already available.");
            }
        } else {
            System.out.println("Car with ID " + id + " not found.");
        }
    }

    public static void printCarInfo(Car car) {
        String[] lines = {
                "+------------------------------------------------+",
                "|               Car Information                  |",
                "+------------------------------------------------+",
                "| ID: " + padLeft(Integer.toString(car.getID()), 42) + " |",
                "| Make: " + padLeft(car.getMake(), 39) + "  |",
                "| Model: " + padLeft(car.getModel(), 38) + "  |",
                "| Year: " + padLeft(Integer.toString(car.getYear()), 39) + "  |",
                "| Type: " + padLeft(car.getType(), 39) + "  |",
                "| Fuel Type: " + padLeft(car.getFuelType(), 34) + "  |",
                "| Transmission Type: " + padLeft(car.getTransmissionType(), 26) + "  |",
                "| Mileage: " + padLeft(Double.toString(car.getMileage()), 36) + "  | ",
                "| Daily Rate: " + padLeft(Double.toString(car.getDailyRate()), 34) + " |",
                "| Availability Status: " + padLeft(Boolean.toString(car.isAvailabilityStatus()), 23) + "   |",
                "| Location: " + padLeft(car.getLocation(), 36) + " |",
                "+------------------------------------------------+"
        };

        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static String padLeft(String str, int length) {
        return String.format("%1$" + length + "s", str);
    }
}


class Car {
    // Private attributes
    private int ID;
    private String make;
    private String model;
    private int year;
    private String type;
    private String fuelType;
    private String transmissionType;
    private double mileage;
    private double dailyRate;
    private boolean availabilityStatus;
    private String location;

    // Constructors
    public Car() {
        // Default constructor
    }

    public Car(int ID, String make, String model, int year, String type, String fuelType, String transmissionType,
            double mileage, double dailyRate, boolean availabilityStatus, String location) {
        this.ID = ID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.mileage = mileage;
        this.dailyRate = dailyRate;
        this.availabilityStatus = availabilityStatus;
        this.location = location;
    }

    // Getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
