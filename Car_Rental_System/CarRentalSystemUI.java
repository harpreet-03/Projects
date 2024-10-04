import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/* Enhanced CAR RENTAL SYSTEM - JAVA Swing */

public class CarRentalSystemUI {
    private JFrame frame;
    private JPanel mainPanel;
    private JTextArea outputArea;
    private List<Car> cars;

    public CarRentalSystemUI() {
        // Initialize UI components
        frame = new JFrame("Car Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        cars = new ArrayList<>(); // Dynamically added cars
        
        mainPanel = new JPanel(new BorderLayout(20, 20)); // Padding around panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        
        JLabel title = new JLabel("Welcome to the Car Rental System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(title, BorderLayout.NORTH);
        
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10)); // Space around text
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Create the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        
        JButton listCarsButton = new JButton("List Cars");
        JButton viewDetailsButton = new JButton("View Car");
        JButton rentCarButton = new JButton("Rent Car");
        JButton returnCarButton = new JButton("Return Car");
        JButton addCarButton = new JButton("Add Car");
        
        buttonPanel.add(listCarsButton);
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(rentCarButton);
        buttonPanel.add(returnCarButton);
        buttonPanel.add(addCarButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);

        // Button functionality
        listCarsButton.addActionListener(e -> listAvailableCars());
        viewDetailsButton.addActionListener(e -> viewCarDetails());
        rentCarButton.addActionListener(e -> rentCar());
        returnCarButton.addActionListener(e -> returnCar());
        addCarButton.addActionListener(e -> addCar());

        // Set visible
        frame.setVisible(true);
    }

    private void listAvailableCars() {
        outputArea.setText("");
        if (cars.isEmpty()) {
            outputArea.append("No cars available.\n");
        } else {
            outputArea.append("Available Cars:\n");
            for (Car car : cars) {
                outputArea.append(car.getMake() + " " + car.getModel() + " (ID: " + car.getID() + ")\n");
            }
        }
    }

    private void viewCarDetails() {
        String carId = JOptionPane.showInputDialog("Enter Car ID:");
        if (carId != null) {
            int id = Integer.parseInt(carId);
            Car selectedCar = findCarById(id);
            if (selectedCar != null) {
                outputArea.setText(printCarInfo(selectedCar));
            } else {
                outputArea.setText("Car with ID " + id + " not found.\n");
            }
        }
    }

    private void rentCar() {
        String carId = JOptionPane.showInputDialog("Enter Car ID to Rent:");
        if (carId != null) {
            int id = Integer.parseInt(carId);
            Car selectedCar = findCarById(id);
            if (selectedCar != null) {
                if (selectedCar.isAvailabilityStatus()) {
                    selectedCar.setAvailabilityStatus(false);
                    outputArea.setText("You have successfully rented the car: " + selectedCar.getMake() + " " + selectedCar.getModel() + "\n");
                } else {
                    outputArea.setText("Sorry, the selected car is not available for rent.\n");
                }
            } else {
                outputArea.setText("Car with ID " + id + " not found.\n");
            }
        }
    }

    private void returnCar() {
        String carId = JOptionPane.showInputDialog("Enter Car ID to Return:");
        if (carId != null) {
            int id = Integer.parseInt(carId);
            Car selectedCar = findCarById(id);
            if (selectedCar != null) {
                if (!selectedCar.isAvailabilityStatus()) {
                    selectedCar.setAvailabilityStatus(true);
                    outputArea.setText("You have successfully returned the car: " + selectedCar.getMake() + " " + selectedCar.getModel() + "\n");
                } else {
                    outputArea.setText("This car is already available.\n");
                }
            } else {
                outputArea.setText("Car with ID " + id + " not found.\n");
            }
        }
    }

    private void addCar() {
        JTextField idField = new JTextField(5);
        JTextField makeField = new JTextField(10);
        JTextField modelField = new JTextField(10);
        JTextField yearField = new JTextField(5);
        JTextField typeField = new JTextField(10);
        JTextField fuelField = new JTextField(10);
        JTextField transmissionField = new JTextField(10);
        JTextField mileageField = new JTextField(5);
        JTextField rateField = new JTextField(5);
        JTextField locationField = new JTextField(10);

        JPanel carInputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        carInputPanel.add(new JLabel("Car ID:"));
        carInputPanel.add(idField);
        carInputPanel.add(new JLabel("Make:"));
        carInputPanel.add(makeField);
        carInputPanel.add(new JLabel("Model:"));
        carInputPanel.add(modelField);
        carInputPanel.add(new JLabel("Year:"));
        carInputPanel.add(yearField);
        carInputPanel.add(new JLabel("Type:"));
        carInputPanel.add(typeField);
        carInputPanel.add(new JLabel("Fuel Type:"));
        carInputPanel.add(fuelField);
        carInputPanel.add(new JLabel("Transmission:"));
        carInputPanel.add(transmissionField);
        carInputPanel.add(new JLabel("Mileage:"));
        carInputPanel.add(mileageField);
        carInputPanel.add(new JLabel("Daily Rate:"));
        carInputPanel.add(rateField);
        carInputPanel.add(new JLabel("Location:"));
        carInputPanel.add(locationField);

        int result = JOptionPane.showConfirmDialog(null, carInputPanel, "Add New Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Car newCar = new Car(
                    Integer.parseInt(idField.getText()),
                    makeField.getText(),
                    modelField.getText(),
                    Integer.parseInt(yearField.getText()),
                    typeField.getText(),
                    fuelField.getText(),
                    transmissionField.getText(),
                    Double.parseDouble(mileageField.getText()),
                    Double.parseDouble(rateField.getText()),
                    true,
                    locationField.getText()
            );
            cars.add(newCar);
            outputArea.setText("New car added: " + newCar.getMake() + " " + newCar.getModel() + "\n");
        }
    }

    private Car findCarById(int id) {
        for (Car car : cars) {
            if (car.getID() == id) {
                return car;
            }
        }
        return null;
    }

    private String printCarInfo(Car car) {
        return String.format("ID: %d\nMake: %s\nModel: %s\nYear: %d\nType: %s\nFuel Type: %s\nTransmission: %s\nMileage: %.2f\nRate: %.2f\nLocation: %s\nAvailable: %s\n",
                car.getID(), car.getMake(), car.getModel(), car.getYear(), car.getType(),
                car.getFuelType(), car.getTransmissionType(), car.getMileage(), car.getDailyRate(),
                car.getLocation(), car.isAvailabilityStatus() ? "Yes" : "No");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarRentalSystemUI::new);
    }
}

class Car {
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

    public int getID() {
        return ID;
    }
    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public double getMileage() {
        return mileage;
    }

    public double getDailyRate() {
        return dailyRate;
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
}

