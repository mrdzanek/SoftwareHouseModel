/**
 * Application created for satisfying project for modelling example business startup
 * in field of IT company
 */
package pl.myro.softwarehousemodel;

import pl.myro.softwarehousemodel.model.DataHelper;
import pl.myro.softwarehousemodel.model.Model;

import java.util.List;

public class Application {

    public static void main(String[] args) {
        System.out.println("Software house model application");
        String runarg = "";

        if (args.length > 0) {
            runarg = args[0];
        }

        Runmode runmode;
        switch (runarg) {
            case "--positive":
            case "--POSITIVE":
                runmode = Runmode.POSITIVE;
                break;
            case "--negative":
            case "--NEGATIVE":
                runmode = Runmode.NEGATIVE;
                break;
            case "--normal":
            default:
                runmode = Runmode.NORMAL;
        }

        Model model = new Model(runmode);
//        Model model = new Model(Runmode.NEGATIVE);

        model.simulateTimeMonths(60);
//        List<Double> snapshot = model.dumpState();
//        System.out.println(model.getCustomerFlow());
//        System.out.println(model.getProgrammerFlow());
//        System.out.println(model.getMaintenanceRatio());
        System.out.println(model.getCashFlow());
        DataHelper.saveSnapshotToFile(model);
    }
}
