package org.example.ics372project2;

public abstract class Vehicle {
    protected String vehicleID;
    protected String manufacturer;
    protected String model;
    protected long acquisitionDate;
    protected double price;
    protected String type;
    protected boolean isLoaned;   //adding new filed to track loaned vehicle

    public Vehicle(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, String type, boolean isLoaned) {
        this.vehicleID = vehicleID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.acquisitionDate = acquisitionDate;
        this.price = price;
        this.type = type;
        this.isLoaned = isLoaned;
    }
    // Getter methods to retrieve the values of instance variables/attributes
    public String getVehicleID() { return vehicleID; }
    public String getManufacturer() { return manufacturer; }
    public String getModel() { return model; }
    public long getAcquisitionDate() { return acquisitionDate; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public boolean getIsLoaned() {return isLoaned;}
    public void setLoaned(boolean loaned) {isLoaned = loaned;}
}

class SUV extends Vehicle{
    //Constructor
    public SUV(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, boolean isLoaned){
        super(vehicleID, manufacturer, model, acquisitionDate, price, "suv", isLoaned );
    }
}

class Sedan extends Vehicle{
    //Constructor
    public Sedan(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, boolean isLoaned){
        super(vehicleID, manufacturer, model, acquisitionDate, price, "sedan", isLoaned);
    }
}

class Pickup extends Vehicle{
    //Constructor
    public Pickup(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, boolean isLoaned){
        super(vehicleID, manufacturer, model, acquisitionDate, price, "pickup", isLoaned);
    }
}

class SportsCar extends Vehicle{
    //Constructor
    public SportsCar(String vehicleID, String manufacturer, String model, long acquisitionDate, double price, boolean isLoaned){
        super(vehicleID, manufacturer, model, acquisitionDate, price, "sports car", isLoaned);
    }
}


