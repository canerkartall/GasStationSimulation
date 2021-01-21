//Gas Station simulation
package GasStation;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GasStation {

    public static int[] fuel_price_for_sale = {7, 6, 5}; 
    public static int[] fuel_price_for_purchase = {5, 4, 3}; 
    public static int balance = 0; 
    public static int balance_monthly = 0;
    public static int initial_balance = 0;
    public static int[] personnel_expenses = {200, 200, 200};
    public static int station_price = 40000000; 
    public static int fuel_tank_unit_price = 1000000; 
    public static int[] other_expenses = {300, 150, 1000};
    public static ArrayList<Integer> ArrivalDurations = new ArrayList<>();
    public static ArrayList<Integer> ServiceDurations = new ArrayList<>();
    public static ArrayList<Integer> VehicleTypes = new ArrayList<>();
    public static ArrayList<Integer> WarehouseVolumes = new ArrayList<>();
    public static ArrayList<Integer> EmptyWarehouseRate = new ArrayList<>();
    public static ArrayList<Integer> ActualWarehouseRate = new ArrayList<>();
    public static ArrayList<Integer> FuelTypes = new ArrayList<>();
    public static ArrayList<Integer> SaleList = new ArrayList<>();
    public static ArrayList<Integer> PurchaseList = new ArrayList<>();
    public static ArrayList<Integer> ProfitList = new ArrayList<>();
    public static ArrayList<Integer> BenzineQueue = new ArrayList<>();
    public static ArrayList<Integer> DieselQueue = new ArrayList<>();
    public static ArrayList<Integer> GasQueue = new ArrayList<>();
    public static ArrayList<Integer> ServiceBeginDurations = new ArrayList<>(); 
    public static ArrayList<Integer> ServicesIdleTimes = new ArrayList<>();
    public static ArrayList<Integer> ServiceEndDurations = new ArrayList<>();
    public static ArrayList<Integer> CustomersSpentTime = new ArrayList<>();
    public static ArrayList<Integer> CustomersWaitingTime = new ArrayList<>();
    public static ArrayList<String> VehicleNames = new ArrayList<>();
    public static ArrayList<String> FuelTypesString = new ArrayList<>();
    public static ArrayList<String> ArrivalRates = new ArrayList<>();
    public static ArrayList<String> ServiceRates = new ArrayList<>();
    public static int Vehicle_count,warehouse_volume,empty_warehouse_volume,rate,size;
    public static int pre_sum = 0;
    public static int year = 0;
    public static int month = 0;
    public static int rate2 = 70;
    public static int Motorbike_count = 0;
    public static int Car_count = 0;
    public static int credit = 0;
    public static int temp = 0;
    public static int maxnum = 41;
    public static double landa, nu1, nu2, nu3;

    public static void main(String[] args) throws IOException {
        Form form1 = new Form();
        form1.setVisible(true);

    }

    public static void calculate() throws IOException {
        Random rand = new Random();
        for (int tur = 0; tur < 24; tur++) {
            size = FuelTypes.size();
            Vehicle_count = rand.nextInt(maxnum);

            for (int p0 = 0; p0 < Vehicle_count; p0++) {
                int i0 = rand.nextInt(61);
                i0 = i0 + (tur * 60);
                ArrivalDurations.add(i0);
            }
            for (int p1 = 0; p1 < Vehicle_count; p1++) {
                warehouse_volume = rand.nextInt(90) + 10;
                WarehouseVolumes.add(warehouse_volume);
                rate = rand.nextInt(9) + 1;//1-9
                empty_warehouse_volume = (warehouse_volume * rate) / 10;//%10-%90 empty Warehouse
                EmptyWarehouseRate.add(empty_warehouse_volume);
                int i1 = rand.nextInt(2) + 1;//processing time for extra cases 1-2 minute
                int k = 0;
                k = k + (empty_warehouse_volume / 20 + i1);//average filling time 20 liter per min.
                ServiceDurations.add(k);
            }
            int p2 = 0;
            while (p2 < Vehicle_count) {
                VehicleTypes.add(0);
                p2++;
            }
            for (int p3 = 0; p3 < Vehicle_count; p3++) {
                if (WarehouseVolumes.get(p3 + size) >= 20) {//Araba
                    VehicleTypes.set(p3 + size, 1);
                }
            }
            //SORTING
            int hold, min_index;
            for (int i = 0; i < Vehicle_count - 1; i++) {
                min_index = i;
                for (int j = i + 1; j < Vehicle_count; j++) {
                    if (ArrivalDurations.get(j + size) < ArrivalDurations.get(min_index + size)) {
                        min_index = j;
                    }
                }
                hold = ArrivalDurations.get(i + size);
                ArrivalDurations.set(i + size, ArrivalDurations.get(min_index + size));
                ArrivalDurations.set(min_index + size, hold);
                //------
                hold = ServiceDurations.get(i + size);
                ServiceDurations.set(i + size, ServiceDurations.get(min_index + size));
                ServiceDurations.set(min_index + size, hold);
                //------
                hold = VehicleTypes.get(i + size);
                VehicleTypes.set(i + size, VehicleTypes.get(min_index + size));
                VehicleTypes.set(min_index + size, hold);
                //------
                hold = WarehouseVolumes.get(i + size);
                WarehouseVolumes.set(i + size, WarehouseVolumes.get(min_index + size));
                WarehouseVolumes.set(min_index + size, hold);
                //------
                hold = EmptyWarehouseRate.get(i + size);
                EmptyWarehouseRate.set(i + size, EmptyWarehouseRate.get(min_index + size));
                EmptyWarehouseRate.set(min_index + size, hold);
            }

            //-------------
            for (int q0 = 0; q0 < Vehicle_count; q0++) {
                FuelTypes.add(1);
            }
            int q1 = 0;
            while (q1 < Vehicle_count) {
                if (VehicleTypes.get(q1) == 1) {
                    int fuel_type_int = rand.nextInt(3) + 1;
                    FuelTypes.set(q1 + size, fuel_type_int);
                }
                q1++;
            }
            int q2 = 0;
            while (q2 < Vehicle_count) {
                SaleList.add(fuel_price_for_sale[FuelTypes.get(q2 + size) - 1] * EmptyWarehouseRate.get(q2 + size));
                q2++;
            }
            int q3 = 0;
            while (q3 < Vehicle_count) {
                PurchaseList.add(fuel_price_for_purchase[FuelTypes.get(q3 + size) - 1] * EmptyWarehouseRate.get(q3 + size));
                q3++;
            }
            int q4 = 0;
            while (q4 < Vehicle_count) {
                ProfitList.add(SaleList.get(q4 + size) - PurchaseList.get(q4 + size));
                q4++;
            }

            int q5 = 0;
            while (q5 < Vehicle_count) {
                pre_sum = pre_sum + ProfitList.get(q5 + size);
                q5++;
            }
            balance = pre_sum;
        }
        for (int b = 0; b < VehicleTypes.size(); b++) {
            if (VehicleTypes.get(b) == 0) {
                Motorbike_count++;
            }
        }

        Car_count = VehicleTypes.size() - Motorbike_count;

        for (int h1 = 0; h1 < FuelTypes.size(); h1++) {
            if (FuelTypes.get(h1) == 1) {
                BenzineQueue.add(h1);
            }
        }
        for (int h2 = 0; h2 < FuelTypes.size(); h2++) {
            if (FuelTypes.get(h2) == 2) {
                DieselQueue.add(h2);
            }
        }
        for (int h3 = 0; h3 < FuelTypes.size(); h3++) {
            if (FuelTypes.get(h3) == 3) {
                GasQueue.add(h3);
            }
        }
        for (int copy = 0; copy < ArrivalDurations.size(); copy++) {
            ServiceBeginDurations.add(ArrivalDurations.get(copy));
            ServicesIdleTimes.add(0);
            ServiceEndDurations.add(0);
            CustomersSpentTime.add(0);
            CustomersWaitingTime.add(0);
        }

        ServicesIdleTimes.set(BenzineQueue.get(0), ArrivalDurations.get(BenzineQueue.get(0)));
        ServicesIdleTimes.set(DieselQueue.get(0), ArrivalDurations.get(DieselQueue.get(0)));
        ServicesIdleTimes.set(GasQueue.get(0), ArrivalDurations.get(GasQueue.get(0)));
        ServiceEndDurations.set(BenzineQueue.get(0), ServiceBeginDurations.get(BenzineQueue.get(0)) + ServiceDurations.get(BenzineQueue.get(0)));
        ServiceEndDurations.set(DieselQueue.get(0), ServiceBeginDurations.get(DieselQueue.get(0)) + ServiceDurations.get(DieselQueue.get(0)));
        ServiceEndDurations.set(GasQueue.get(0), ServiceBeginDurations.get(GasQueue.get(0)) + ServiceDurations.get(GasQueue.get(0)));
        CustomersSpentTime.set(BenzineQueue.get(0), ServiceEndDurations.get(BenzineQueue.get(0)) - ArrivalDurations.get(BenzineQueue.get(0)));
        CustomersSpentTime.set(DieselQueue.get(0), ServiceEndDurations.get(DieselQueue.get(0)) - ArrivalDurations.get(DieselQueue.get(0)));
        CustomersSpentTime.set(GasQueue.get(0), ServiceEndDurations.get(GasQueue.get(0)) - ArrivalDurations.get(GasQueue.get(0)));
        for (int c = 0; c < BenzineQueue.size() - 1; c++) {
            if (ServiceBeginDurations.get(BenzineQueue.get(c)) + ServiceDurations.get(BenzineQueue.get(c)) > ServiceBeginDurations.get(BenzineQueue.get(c + 1))) {
                ServiceBeginDurations.set(BenzineQueue.get(c + 1), ServiceBeginDurations.get(BenzineQueue.get(c)) + ServiceDurations.get(BenzineQueue.get(c)));
            } else {
                ServiceBeginDurations.set(BenzineQueue.get(c + 1), ArrivalDurations.get(BenzineQueue.get(c + 1)));
            }

            CustomersWaitingTime.set(BenzineQueue.get(c + 1), ServiceBeginDurations.get(BenzineQueue.get(c + 1)) - ArrivalDurations.get(BenzineQueue.get(c + 1)));
            ServiceEndDurations.set(BenzineQueue.get(c + 1), ServiceBeginDurations.get(BenzineQueue.get(c + 1)) + ServiceDurations.get(BenzineQueue.get(c + 1)));
            ServicesIdleTimes.set(BenzineQueue.get(c + 1), ServiceBeginDurations.get(BenzineQueue.get(c + 1)) - (ServiceBeginDurations.get(BenzineQueue.get(c)) + ServiceDurations.get(BenzineQueue.get(c))));
            CustomersSpentTime.set(BenzineQueue.get(c + 1), ServiceEndDurations.get(BenzineQueue.get(c + 1)) - ArrivalDurations.get(BenzineQueue.get(c + 1)));
        }
        for (int c = 0; c < DieselQueue.size() - 1; c++) {
            if (ServiceBeginDurations.get(DieselQueue.get(c)) + ServiceDurations.get(DieselQueue.get(c)) > ServiceBeginDurations.get(DieselQueue.get(c + 1))) {
                ServiceBeginDurations.set(DieselQueue.get(c + 1), ServiceBeginDurations.get(DieselQueue.get(c)) + ServiceDurations.get(DieselQueue.get(c)));
            } else {
                ServiceBeginDurations.set(DieselQueue.get(c + 1), ArrivalDurations.get(DieselQueue.get(c + 1)));
            }

            CustomersWaitingTime.set(DieselQueue.get(c + 1), ServiceBeginDurations.get(DieselQueue.get(c + 1)) - ArrivalDurations.get(DieselQueue.get(c + 1)));
            ServiceEndDurations.set(DieselQueue.get(c + 1), ServiceBeginDurations.get(DieselQueue.get(c + 1)) + ServiceDurations.get(DieselQueue.get(c + 1)));
            ServicesIdleTimes.set(DieselQueue.get(c + 1), ServiceBeginDurations.get(DieselQueue.get(c + 1)) - (ServiceBeginDurations.get(DieselQueue.get(c)) + ServiceDurations.get(DieselQueue.get(c))));
            CustomersSpentTime.set(DieselQueue.get(c + 1), ServiceEndDurations.get(DieselQueue.get(c + 1)) - ArrivalDurations.get(DieselQueue.get(c + 1)));
        }
        for (int c = 0; c < GasQueue.size() - 1; c++) {
            if (ServiceBeginDurations.get(GasQueue.get(c)) + ServiceDurations.get(GasQueue.get(c)) > ServiceBeginDurations.get(GasQueue.get(c + 1))) {
                ServiceBeginDurations.set(GasQueue.get(c + 1), ServiceBeginDurations.get(GasQueue.get(c)) + ServiceDurations.get(GasQueue.get(c)));

            } else {
                ServiceBeginDurations.set(GasQueue.get(c + 1), ArrivalDurations.get(GasQueue.get(c + 1)));
            }

            CustomersWaitingTime.set(GasQueue.get(c + 1), ServiceBeginDurations.get(GasQueue.get(c + 1)) - ArrivalDurations.get(GasQueue.get(c + 1)));
            ServiceEndDurations.set(GasQueue.get(c + 1), ServiceBeginDurations.get(GasQueue.get(c + 1)) + ServiceDurations.get(GasQueue.get(c + 1)));
            ServicesIdleTimes.set(GasQueue.get(c + 1), ServiceBeginDurations.get(GasQueue.get(c + 1)) - (ServiceBeginDurations.get(GasQueue.get(c)) + ServiceDurations.get(GasQueue.get(c))));
            CustomersSpentTime.set(GasQueue.get(c + 1), ServiceEndDurations.get(GasQueue.get(c + 1)) - ArrivalDurations.get(GasQueue.get(c + 1)));
        }

        balance = balance - (personnel_expenses[0] + personnel_expenses[1] + personnel_expenses[2]);
        balance = balance - (other_expenses[0] + other_expenses[1] + other_expenses[2]);
        credit = station_price + (fuel_tank_unit_price * 3) - initial_balance;
        balance_monthly = balance * 30;
        
        temp=credit;
      
        while (temp > 0) {
            if((balance_monthly * rate2 / 100)==0){
                break;
            }
            temp = temp - (balance_monthly * rate2 / 100);
            month++;
        }
        
        while (true) {
            year++;
            month = month - 12;
            if (month < 0) {
                month = month + 12;
                year = year - 1;
                break;
            }
        }      
        landa = (double) ArrivalDurations.size() / ArrivalDurations.get(ArrivalDurations.size() - 1);
        nu1 = (double) BenzineQueue.size() / ServiceEndDurations.get(BenzineQueue.get(BenzineQueue.size() - 1));
        nu2 = (double) DieselQueue.size() / ServiceEndDurations.get(DieselQueue.get(DieselQueue.size() - 1));
        nu3 = (double) GasQueue.size() / ServiceEndDurations.get(GasQueue.get(GasQueue.size() - 1));

        for (int j = 0; j < ArrivalDurations.size(); j++) {
            if (VehicleTypes.get(j) == 0) {
                VehicleNames.add("Motorbike");
            } else {
                VehicleNames.add("Car      ");
            }
            if (null == FuelTypes.get(j)) {
                FuelTypesString.add("Gas    ");
            } else {
                switch (FuelTypes.get(j)) {
                    case 1:
                        FuelTypesString.add("Benzine");
                        break;
                    case 2:
                        FuelTypesString.add("Diesel ");
                        break;
                    default:
                        FuelTypesString.add("Gas    ");
                        break;
                }
            }
            if (ArrivalDurations.get(j) < 10) {
                String s = "   " + ArrivalDurations.get(j).toString();
                ArrivalRates.add(s);
            } else if (ArrivalDurations.get(j) >= 10 && ArrivalDurations.get(j) < 100) {
                String s = "  " + ArrivalDurations.get(j).toString();
                ArrivalRates.add(s);
            } else if (ArrivalDurations.get(j) >= 100 && ArrivalDurations.get(j) < 1000) {
                String s = " " + ArrivalDurations.get(j).toString();
                ArrivalRates.add(s);
            } else {
                ArrivalRates.add(ArrivalDurations.get(j).toString());
            }
            if (ServiceDurations.get(j) < 10) {
                String s = " " + ServiceDurations.get(j).toString();
                ServiceRates.add(s);
            } else {
                ServiceRates.add(ServiceDurations.get(j).toString());
            }
            ActualWarehouseRate.add(WarehouseVolumes.get(j)-EmptyWarehouseRate.get(j));
        }

        File file = new File("REPORT.txt");
        String listString = "";
        for (int s = 0; s < ArrivalDurations.size(); s++) {
            listString = listString + "       " + ArrivalRates.get(s) + " / " + ServiceRates.get(s) + "    \t\t   " + VehicleNames.get(s) + "\t\t" + ActualWarehouseRate.get(s).toString() + " / " + WarehouseVolumes.get(s).toString() + "\t\t    " + FuelTypesString.get(s) + "\t\t" + SaleList.get(s).toString() + " / " + PurchaseList.get(s).toString() + " / " + ProfitList.get(s).toString()+ "\t\t\t        " + ServiceBeginDurations.get(s).toString() + " / " + ServiceEndDurations.get(s).toString()+ "    \t\t\t   " + ServicesIdleTimes.get(s).toString()+ "\t\t      " + CustomersWaitingTime.get(s).toString() + " / " + CustomersSpentTime.get(s).toString() + "\n";
        }
        try (FileWriter dosyaInput = new FileWriter("REPORT.txt"); BufferedWriter dosyaOutput = new BufferedWriter(dosyaInput)) {
            dosyaOutput.write("\t\t\t\t\t\t\t\t\t\t\t\tFINAL REPORT");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            dosyaOutput.write("Initial Balance : " + Integer.toString(initial_balance) + " $      (Default is 0)");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Total Dept : " + Integer.toString(credit) + " $");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Daily Net Profit After All Expenses : " + Integer.toString(balance) + " $");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Monthly Income : " + Integer.toString(balance_monthly) + " $");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Monthly Amount Spent On Debt : " + Integer.toString((balance_monthly * rate2 / 100)) + " $");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Time Left To Close The Debt : " + Integer.toString(year) + " Year "+ Integer.toString(month) + " Month");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Total Vehicle Number : " + Integer.toString(ArrivalDurations.size()) + "\t (Car : " + Integer.toString(Car_count) +"     Motorbike : " +Integer.toString(Motorbike_count) + ")");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Average Arrival Rate Per Minute = " + Double.toString(landa));
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Average Service Rate Per Minute = " + "(For Benzine Queue) - " + Double.toString(nu1) + "   (For Diesel Queue) - " + Double.toString(nu2)+ "   (For Gas Queue) - " + Double.toString(nu3));
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("System Utilization (P)= " + Double.toString(((nu1 + nu2 + nu3)/3) / (landa * 3)));
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Total Idle Time For 3 Queue Services = " + Integer.toString(sum(ServicesIdleTimes)) + " minute");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Total Customers Waiting Time For 3 Queue  = " + Integer.toString(sum(CustomersWaitingTime)) + " minute");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("Total Customers Spent Time For 3 Queue = " + Integer.toString(sum(CustomersSpentTime)) + " minute");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write(" ---------  Details Below  ---------");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write("ARRIVAL / SERVICE TIME(minute)" + "\t" + "VEHİCLE TYPE" + "\t   " + "FUEL TANK RATE (lt)" + "\t   " + "FUEL TYPE" + "\t   " + "SALE / PURCHASE / PROFIT LIST"+ "\t" + "SERVICE BEGIN / END TIME (minute)"+ "\t" + "IDLE TIME"+ "\t" + "WAITING / SPENT TIME");
            dosyaOutput.newLine();
            dosyaOutput.newLine();
            
            dosyaOutput.write(listString);
        }   
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);

    }
    public static int sum(ArrayList<Integer> array) {
        int sum=0;
        for(int i=0; i<array.size(); i++){
            sum += array.get(i);
        }
        return sum;
}

}
