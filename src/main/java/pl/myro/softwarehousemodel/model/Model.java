package pl.myro.softwarehousemodel.model;

import pl.myro.softwarehousemodel.Runmode;

import java.util.*;

public class Model {
    private final Set<Programmer> programmers;
    private final Set<Customer> customers;
    private final List<Double> cashFlow;
    private final List<Set<Customer>> customerFlow;
    private final List<Set<Programmer>> programmerFlow;
    private final List<Double> maintenanceRatio;
    private Double cash;
    private int remainingProductDevelopmentDays;
    private int productDevelopmentDays;
    private int currentMonth;
    private int constPayments;
    private int marketingPayments;
    private List<Position> freeVacates;


    public Model(Runmode runmode) {
        this.programmers = new HashSet<>();
        this.customers = new HashSet<>();
        this.cash = 2000000.0;
        this.productDevelopmentDays = 0;
        this.remainingProductDevelopmentDays = runmode == Runmode.NORMAL ? 2500 : runmode == Runmode.NEGATIVE ? 5000 : 1250;
        this.currentMonth = 0;
        this.constPayments = 5000;
        this.marketingPayments = 5000;
        this.freeVacates = new ArrayList<>();
        this.freeVacates.addAll(Arrays.asList(
                Position.JUNIOR,
                Position.JUNIOR,
                Position.JUNIOR,
                Position.REGULAR,
                Position.REGULAR,
                Position.REGULAR,
                Position.REGULAR,
                Position.REGULAR,
                Position.SENIOR,
                Position.SENIOR)
        );
        this.cashFlow = new ArrayList<>();
        this.customerFlow = new ArrayList<>();
        this.programmerFlow = new ArrayList<>();
        this.maintenanceRatio = new ArrayList<>();
    }

    public void simulateTimeMonths(int monthCount) {
        for (int i = 0; i <= monthCount; i++) {
            simulateMonth();
        }
    }

    private void simulateMonth() {
        currentMonth++;
        cash += generateCashFromCustomers();
        cash -= constPayments;
        cash -= marketingPayments;
        cash -= payWages();
        cash -= payAlternateCosts();

        changePersonnel();
        developProduct();
        developProgrammers();
        if (this.productDevelopmentDays > this.remainingProductDevelopmentDays) {
            looseCustomers();
            catchNewCustomers();
        }
        this.cashFlow.add(this.cash);
        this.programmerFlow.add(new HashSet<>(this.programmers));
        this.customerFlow.add(new HashSet<>(this.customers));
    }

    private void looseCustomers() {
        this.customers.removeIf(customer -> Math.random() < 0.1);
    }

    private void catchNewCustomers() {
        int newCustomerCount = (int) ((Math.random() + 0.1) * (this.marketingPayments / 1000));

        for (int i = 0; i <= newCustomerCount; i++) {
            this.customers.add(new Customer());
        }
    }

    private void developProgrammers() {
        this.programmers.forEach(Programmer::raiseExperience);
        this.programmers.forEach(Programmer::promoteIfAble);
        this.programmers.forEach(Programmer::giveRaiseIfAble);
    }

    private void developProduct() {
        int developRealTime = 0;
        for (Programmer programmer : this.programmers) {
            developRealTime += programmer.calculateDevelopmentTime(20);
        }
        this.maintenanceRatio.add((double) developRealTime);
        this.productDevelopmentDays += developRealTime;
    }

    private void changePersonnel() {
        fireEmployees();
        hireEmployees();
    }

    private void hireEmployees() {
        for (Iterator<Position> it = this.freeVacates.listIterator(); it.hasNext(); ) {
            switch (it.next()) {
                case JUNIOR:
                    if (Math.random() < 0.3) {
                        this.programmers.add(new Programmer(Position.JUNIOR));
                        it.remove();
                    }
                    break;
                case REGULAR:
                    if (Math.random() < 0.2) {
                        this.programmers.add(new Programmer(Position.REGULAR));
                        it.remove();
                    }
                    break;
                case SENIOR:
                    if (Math.random() < 0.1) {
                        this.programmers.add(new Programmer(Position.SENIOR));
                        it.remove();
                    }
                    break;
            }
        }
    }


    private void fireEmployees() {
        for (Iterator<Programmer> it = this.programmers.iterator(); it.hasNext(); ) {
            Programmer programmer = it.next();
            if (Math.random() < 0.1) {
                it.remove();
                this.freeVacates.add(programmer.getPosition());
            }
        }
    }

    private Double payAlternateCosts() {
        return this.programmers.size() * 400.0;
    }

    private Double payWages() {
        Double wages = 0.0;
        for (Programmer programmer : this.programmers) {
            wages += programmer.getWage();
        }
        return wages;
    }

    private Double generateCashFromCustomers() {
        Double income = 0.0;
        for (Customer customer : this.customers) {
            income += customer.getIncome();
        }
        return income;
    }

    public List<Double> dumpState() {
        return this.cashFlow;
    }

    public List<Double> getCashFlow() {
        return this.cashFlow;
    }

    public List<Set<Customer>> getCustomerFlow() {
        return this.customerFlow;
    }

    public List<Set<Programmer>> getProgrammerFlow() {
        return this.programmerFlow;
    }

    public List<Double> getMaintenanceRatio() {
        return this.maintenanceRatio;
    }
}
