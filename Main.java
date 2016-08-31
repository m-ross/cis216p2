package lab02;

import javax.swing.JOptionPane;
import java.io.*;
import java.util.Scanner;

/*  Program Name:   Lab 02 Lathe Shipping
 *  Programmer:     Marcus Ross
 *  Date Due:       20 September 2013
 *  Description:	This program uses dialog boxes to take input from the user in the form of a customer's name, address and number of lathes ordered. It determines how many trucks are needed to ship the specified number of lathes then produces a bill based on the cost of the lathes and trucks. All dimensions are in inches.
 */

public class Main {
    public static void main(String[] args) throws IOException {
		//Define vars
		Scanner inputFile;
		String custName, custStreet, custCity, custState, custZip;
		int latheNum = 0, latheRem, truckLNum = 0, truckSNum = 0, latheLngth, latheWdth, latheHght, truckLLngth, truckLWdth, truckLHght, truckSLngth, truckSWdth, truckSHght, truckLLCap, truckLWCap, truckLHCap, truckLCap, truckSLCap, truckSWCap, truckSHCap, truckSCap;
		double latheCost, truckLCost, truckSCost, truckCost, totalCost, lathePrice, truckLPrice, truckSPrice;

		//Get input from user
		custName = JOptionPane.showInputDialog("Customer Name:");
		custStreet = JOptionPane.showInputDialog("Customer Street Address:");
		custCity = JOptionPane.showInputDialog("Customer City:");
		custState = JOptionPane.showInputDialog("Customer State:");
		custZip = JOptionPane.showInputDialog("Customer ZIP:");
		latheNum = Integer.parseInt(JOptionPane.showInputDialog("Number of lathes ordered:"));

		//Get dimensions from file
		File file = new File("lab02\\dimensions.txt");
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null,"dimensions.txt NOT FOUND\n");
			System.exit(0);
		}
		inputFile = new Scanner(file);
		latheLngth = inputFile.nextInt();
		latheWdth = inputFile.nextInt();
		latheHght = inputFile.nextInt();
		truckLLngth = inputFile.nextInt();
		truckLWdth = inputFile.nextInt();
		truckLHght = inputFile.nextInt();
		truckSLngth = inputFile.nextInt();
		truckSWdth = inputFile.nextInt();
		truckSHght = inputFile.nextInt();
		inputFile.close();

		//Get prices from file
		file = new File("lab02\\prices.txt");
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null,"prices.txt NOT FOUND\n");
			System.exit(0);
		}
		inputFile = new Scanner(file);
		lathePrice = inputFile.nextDouble();
		truckLPrice = inputFile.nextDouble();
		truckSPrice = inputFile.nextDouble();
		inputFile.close();
		

		//Calculate truck capacities
		truckLLCap = truckLLngth / latheLngth;
		truckLWCap = truckLWdth / latheWdth;
		truckLHCap = truckLHght / latheHght;
		truckLCap = truckLLCap * truckLWCap * truckLHCap;
		truckSLCap = truckSLngth / latheLngth;
		truckSWCap = truckSWdth / latheWdth;
		truckSHCap = truckSHght / latheHght;
		truckSCap = truckSLCap * truckSWCap * truckSHCap;

		//Calculate trucks needed
		latheRem = latheNum;
		if (latheRem > truckLCap) { //if lathes do not fit into one large truck, find out how many large trucks
			truckLNum = latheNum / truckLCap;
			latheRem -= truckLCap * truckLNum;
		}
		if (latheRem > truckSCap) { //if remainder cannot fit in one small truck, add another large truck
			truckLNum += 1;
		}
		else if (latheRem > 0) { //otherwise, if there is a remainder, add one small truck
				truckSNum += 1;
		}

		//Calculate costs
		latheCost = latheNum * lathePrice;
		truckLCost = truckLNum * truckLPrice;
		truckSCost = truckSNum * truckSPrice;
		truckCost = truckLCost + truckSCost;
		totalCost = latheCost + truckCost;

		//Display trucks needed
		JOptionPane.showMessageDialog(null,"Large truck(s) required: " + truckLNum + "\nSmall truck(s) required: " + truckSNum);

		//Display bill
		System.out.printf("%23s\n","MIRACLE MANUFACTURING");
		System.out.printf("%19s\n\n","HOBB'S END, NH");
		System.out.printf("%20s\n\n","SHIPPING INVOICE");
		System.out.printf("CUSTOMER:\n%s\n%s\n%s, %s  %s\n\n",custName,custStreet,custCity,custState,custZip);
		System.out.printf("%s%13s\n","ITEMS SHIPPED","COST");
		System.out.printf("%3d %s %13.2f\n\n",latheNum,"Lathe(s)",latheCost);
		System.out.printf("%s%9s\n","TRUCKS DISPATCHED","COST");
		System.out.printf("%3d %s %7.2f\n",truckLNum,"Large Truck(s)",truckLCost);
		System.out.printf("%3d %s %7.2f\n",truckSNum,"Small Truck(s)",truckSCost);
		System.out.printf("%s %10.2f\n\n","SHIPPING TOTAL:",truckCost);
		System.out.printf("%s %19.2f\n","TOTAL:",totalCost);
		System.exit(0);
	}
}