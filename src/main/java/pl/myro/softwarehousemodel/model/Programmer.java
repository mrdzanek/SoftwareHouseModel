package pl.myro.softwarehousemodel.model;

public class Programmer {
    private Position position;
    private Double wage;
    private int experience;

    public Programmer(Position position) {
        this.position = position;
        setStartWage();
        this.experience = 0;
    }

    private void setStartWage() {
        switch (this.position) {
            case JUNIOR -> this.wage = 5000 + Math.random() * 2000;
            case REGULAR -> this.wage = 6000 + Math.random() * 6000;
            case SENIOR -> this.wage = 10000 + Math.random() * 15000;
        }
    }

    public double calculateDevelopmentTime(int workDays) {
        switch (this.position) {
            case JUNIOR:
                return workDays * (Math.random() * 0.8 * (experience > 6 ? 1 : experience / 6.0));
            case REGULAR:
                return workDays * (Math.random() * 2 * (experience > 6 ? 1 : experience / 6.0) + 0.5 * (experience > 6 ? 1 : experience / 6.0));
            case SENIOR:
                return workDays * (Math.random() * 4.1 * (experience > 6 ? 1 : experience / 6.0) + 0.9 * (experience > 6 ? 1 : experience / 6.0));
            default:
                return 0;
        }
    }

    public void promoteIfAble() {
        if (Math.random() < 0.1) {
            if (this.position == Position.JUNIOR) {
                this.position = Position.REGULAR;
                this.wage += 1000;
            } else if (this.position == Position.REGULAR) {
                this.position = Position.SENIOR;
                this.wage += 1000;
            }
        }
    }

    public void giveRaiseIfAble() {
        if (Math.random() < 0.05) {
            this.wage = this.wage * (1.05 + Math.random() * 0.15);
        }
    }

    public void raiseExperience() {
        this.experience += 1;
    }

    public Double getWage() {
        return this.wage;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getExperience() {
        return this.experience;
    }
}
