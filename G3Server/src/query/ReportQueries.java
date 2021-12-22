package query;


import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.SupplierByReport;
/**
 * @author Alexander, Martinov
 * Class description: 
 * This is a class for running relevant queries to prepare reports
 * @version 21/12/2021
 */
public class ReportQueries {
    /**
     * queries orders table and saves incomes of suppliers in time period
     * @param suppliersByBranchAndIncome reports array being worked on
     * @param fromDate date when report starts
     * @param toDate date when report ends
     */
	public static void getIncomeForSuppliers(SupplierByReport[] suppliersByBranchAndIncome,String fromDate, String toDate) {
		ResultSet orders=Query.getColumnFromTableInDB("order where issueDateTime between '"+fromDate+"' AND '"
				+toDate+"' group by supplierId","supplierId,sum(totalprice)"); //queries for totalprice sum, by supplierId
		try {
			while(orders.next()) {
				for(int i=0;i<suppliersByBranchAndIncome.length;i++ ) {//finds each supplier by Id in working reports set
					if(suppliersByBranchAndIncome[i].getSupplierId().equals(orders.getString(1))) {
						suppliersByBranchAndIncome[i].setIncome(orders.getString(2)); //sets pulled income
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * queries item_in_menu table to build menus by category for suppliers
     * @param suppliersForOrderByType reports array being worked on
     * @param fromDate date when report starts
     * @param toDate date when report ends
     */
	public static void buildMenusByType(SupplierByReport[] suppliersForOrderByType,String fromDate, String toDate){
		ResultSet menu=Query.getColumnFromTableInDB("item_in_menu order by supplierId","*"); //queries for items by supplierId from menus table
		try {
			while(menu.next()) {
				for(int i=0;i<suppliersForOrderByType.length;i++) {
					if(suppliersForOrderByType[i].getSupplierId().equals(menu.getString(2))){//if the item belongs to a supplier (by id)
					String type=menu.getString(3);
					switch(type) {//adds it to a list of items from that category
					case "SALAD":
						suppliersForOrderByType[i].getSalads().add(menu.getString(1));
						break;
					case "FIRST":
						suppliersForOrderByType[i].getFirsts().add(menu.getString(1));
						break;
					case "MAIN":
						suppliersForOrderByType[i].getMains().add(menu.getString(1));
						break;
					case "DESSERT":
						suppliersForOrderByType[i].getDesserts().add(menu.getString(1));
						break;
					case "DRINK":
						suppliersForOrderByType[i].getDrinks().add(menu.getString(1));
						break;
						default:
							break;
					}
				break;
			}
		}
	}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * queries orders table and gets items ordered lists(receipts) in time period
     * then checks supplier menu to find out the category of each item
     * then increments category counter accordingly
     * @param suppliersForOrderByType reports array being worked on
     * @param fromDate date when report starts
     * @param toDate date when report ends
     */
	public static void getOrdersByType(SupplierByReport[] suppliersForOrderByType,String fromDate, String toDate) {
		ResultSet orders=Query.getColumnFromTableInDB("order where issueDateTime between '"+fromDate+"' AND '"
				+toDate+"' order by supplierId","supplierId,itemsList");//queries for item lists (reciept) by supplierId 
		try {
			while(orders.next()) {
				for(int i=0;i<suppliersForOrderByType.length;i++) {
					if(suppliersForOrderByType[i].getSupplierId().equals(orders.getString(1))){// if the list belongs to the supplier
						String[] items=orders.getString(2).split(",");//split the items list
						for(String item : items) {
							if(suppliersForOrderByType[i].getSalads().contains(item))//check if current item is in category
							suppliersForOrderByType[i].getTypeSums()[0]++;//increment category counter
							if(suppliersForOrderByType[i].getFirsts().contains(item))
							suppliersForOrderByType[i].getTypeSums()[1]++;
							if(suppliersForOrderByType[i].getMains().contains(item))
							suppliersForOrderByType[i].getTypeSums()[2]++;
							if(suppliersForOrderByType[i].getDesserts().contains(item))
							suppliersForOrderByType[i].getTypeSums()[3]++;
							if(suppliersForOrderByType[i].getDrinks().contains(item))
							suppliersForOrderByType[i].getTypeSums()[4]++;
						}
						break;
					}
					}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * queries orders table to get late orders, ordered by supplier in time period
     * @param suppliersByPerformance reports array being worked on
     * @param fromDate date when report starts
     * @param toDate date when report ends
     */
	public static void getLateSuppliesAmount(SupplierByReport[] suppliersByPerformance,String fromDate, String toDate) {
		ResultSet lates=Query.getBasicQuery("(\r\n"
				+ "SELECT supplierId,timeType, timediff(actualSupplyDateTime, estimatedSupplyDateTime) AS date_difference \r\n"
				+ "FROM semesterialproject.order where issueDateTime between '"+fromDate+"' and '"+toDate+"') AS dates \r\n"
				+ "WHERE (dates.date_difference >'01:00:00' AND dates.timeType='REGULAR') or (dates.date_difference >'00:20:00' AND dates.timeType='PRE')group by supplierId;",
				"supplierId,count(supplierId) AS lates ");
		//queries for late orders by supplierID
		//first makes a table with a calculated time difference between expected and actual delivery time
		//then finds any regular orders which are supplied more than an hour later than requested
		//or pre-order orders which are supplied more than 20 minutes later than requested 
		//both types are categorized as "late"
		try {
			while(lates.next()) {
				for(int i=0;i<suppliersByPerformance.length;i++) {
					if(suppliersByPerformance[i].getSupplierId().equals(lates.getString(1))){//saves amount of late orders by supplierId
						suppliersByPerformance[i].setLateOrders(lates.getInt(2));
						break;
						}
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * queries orders table to get total orders in time period by supplier
     * @param suppliersByPerformance reports array being worked on
     * @param fromDate date when report starts
     * @param toDate date when report ends
     */
	public static void getOrderAmounts(SupplierByReport[] suppliersByPerformance,String fromDate, String toDate) {
		ResultSet orders=Query.getColumnFromTableInDB("order where issueDateTime between '"+fromDate+"' AND '"
				+toDate+"' group by supplierId","supplierId, count(orderNumber) AS orders");//queries for amount of orders by counting order rows in given time period by supplierId
				try {
			while(orders.next()) {
				for(int i=0;i<suppliersByPerformance.length;i++) {
					if(suppliersByPerformance[i].getSupplierId().equals(orders.getString(1))){
						suppliersByPerformance[i].setTotalOrders(orders.getInt(2));//saves total order amount to each supplier in report
						break;
						}
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    /**
     * sets report issue date for current working supplier list
     * @param suppliers reports array being worked on
     * @param issueDate relevant report month
     */
	public static void setIssueDates(SupplierByReport[] suppliers,String issueDate) {
		for(int i=0;i<suppliers.length;i++)
			suppliers[i].setIssueDate(issueDate);
	}
	/**
    * sets report type for current working supplier list
    * @param suppliers reports array being worked on
    * @param type date when report starts
    */
	public static void setReportType(SupplierByReport[] suppliers,String type) {
		for(int i=0;i<suppliers.length;i++)
			suppliers[i].setReportType(type);
	}
	/**
    * Creates SupplierByReport list from suppliers table
    * queries supplier table, makes a reports list, sets basic supplier info in list
    * pulled to edit and generate reports with
    */
	public static SupplierByReport[] getSuppliersForReport() {
		int i=0;
		int suppliersNumber=0;
		SupplierByReport[] supplierForReport = null;
		ResultSet suppliers=Query.getColumnFromTableInDB("supplier","*");
		try {
			if(suppliers.last()) {
				suppliersNumber=suppliers.getRow();
				suppliers.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		supplierForReport= new SupplierByReport[suppliersNumber];
		for(i=0;i<supplierForReport.length;i++)
			supplierForReport[i]=new SupplierByReport();
		i=0;
		try {
			while(suppliers.next()) {
				supplierForReport[i].setSupplierId(suppliers.getString(1));
				supplierForReport[i].setSupplierName(suppliers.getString(2));
				supplierForReport[i].setSupplierBranch(suppliers.getString(3));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return supplierForReport;
	}
	/**
    * gets reports from db by type, branch and date
    * returns reports list from which reports can be generated
    * @param fromDate date for which the reports were generated
    * @param branch branch for which we want to make reports (can be all for quarterly)
    * @param type gets report by type (monthly/quarterly)
    */
	public static SupplierByReport[] getSuppliersFromDb(String fromDate,String branch, String type) {
		int i=0;
		int suppliersNumber=0;
		SupplierByReport[] supplierForReport = null;
		ResultSet suppliers;
		if(branch.equals("NOT_APPLICABLE"))
		suppliers=Query.getColumnFromTableInDB("supplier","*");
		else
			suppliers=Query.getColumnFromTableInDB("supplier where homeBranch='"+branch+"'","*");
		try {//first queries for relevant suppliers for requested report
			if(suppliers.last()) {
				suppliersNumber=suppliers.getRow();
				suppliers.beforeFirst();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		supplierForReport = new SupplierByReport[suppliersNumber];
		ResultSet report;
		if(branch.equals("NOT_APPLICABLE"))
		report= Query.getRowsFromTableInDB("reports", "issueDate='"+fromDate+"' and reportType='"+type+"'");
		else
			report= Query.getRowsFromTableInDB("reports", "issueDate='"+fromDate+"' and reportType='"+type+"' and homeBranch='"+branch+"'");
		try {//then gets report objects between the relevant dates 
			while(report.next()) {
				//convert from byte stream to object
				byte[] st = (byte[]) report.getObject(6);
			      ByteArrayInputStream baip = new ByteArrayInputStream(st);
			      ObjectInputStream ois = new ObjectInputStream(baip);
			      supplierForReport[i] = (SupplierByReport) ois.readObject();
			      i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(i==0)
			return null;
		return supplierForReport;
	}
}