package it.polimi.db2project.web;


import it.polimi.db2project.entities.*;
import it.polimi.db2project.entities.employeeQueries.*;
import it.polimi.db2project.services.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/salesReportPage")
public class SalesReportPageServlet extends HttpServlet {

    @EJB
    private EmployeeService employeeService;


    private ServicePackageToSelectEntity servicePackageSelected;
    private List<ValidityPeriodEntity> validityPeriods = null;
    private TotalPurchasesPerPackageEntity totPurchaseXPackage;
    private TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriod;
    private SalesPerPackageWithoutOptProduct salesPerPackageWithoutOptProduct;
    private SalesPerPackageWithOptProduct salesPerPackageWithOptProduct;
    private AvgNumOfOptProductsSoldPerPackage avgNumOfOptProductsSoldPerPackage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //first query
        String srvPackage = request.getParameter("srvPackage");

        //second query
        String srvPackageWithValPeriod = request.getParameter("srvPackageWithValPeriod");
        String valPeriod = request.getParameter("valPeriod");


        //third query
        String srvPackageWithOptProducts = request.getParameter("srvPackageWithOptProducts");

        //forth query
        String srvPackageAvg = request.getParameter("srvPackageAvg");



        Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
        String destServlet = "salesReportPage";


        //first query
        if(srvPackage!=null) totPurchaseXPackage = employeeService.purchasesPerPackage(Long.parseLong(srvPackage));

        //second query
        if(srvPackageWithValPeriod!=null) {
            servicePackageSelected = employeeService.findServicePackageToSelectByID(Long.parseLong(srvPackageWithValPeriod)).get();
            validityPeriods = employeeService.findValPeriodsOfServicePackage(Long.parseLong(srvPackageWithValPeriod));
        }
        if(servicePackageSelected!=null && valPeriod!=null){
            totalPurchasesPerPackageAndValPeriod = employeeService.purchasesPerPackageAndValPeriod(servicePackageSelected.getServicePackageToSelect_id(), Long.parseLong(valPeriod));
        }

        //third query
        if(srvPackageWithOptProducts!=null){
            salesPerPackageWithOptProduct = employeeService.valueOfSalesWithOptProduct(Long.parseLong(srvPackageWithOptProducts));
            salesPerPackageWithoutOptProduct = employeeService.valueOfSalesWithoutOptProduct(Long.parseLong(srvPackageWithOptProducts));
        }

        //forth query
        if(srvPackageAvg!=null){
            avgNumOfOptProductsSoldPerPackage = employeeService.avgNumOfOptProductsSoldPerPackage(Long.parseLong(srvPackageAvg));

        }


        response.sendRedirect(destServlet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ServicePackageToSelectEntity> servicePackagesToSelect = employeeService.findAllServicePackageToSelect();
        req.setAttribute("servicePackagesToSelect", servicePackagesToSelect);

        //first query
        req.setAttribute("totPurchaseXPackage", totPurchaseXPackage);

        //second query
        req.setAttribute("servicePackageSelected", servicePackageSelected);
        req.setAttribute("validityPeriods", validityPeriods);
        req.setAttribute("totalPurchasesPerPackageAndValPeriod", totalPurchasesPerPackageAndValPeriod);

        //third query
        req.setAttribute("salesPerPackageWithOptProduct", salesPerPackageWithOptProduct);
        req.setAttribute("salesPerPackageWithoutOptProduct", salesPerPackageWithoutOptProduct);

        //forth query
        req.setAttribute("avgNumOfOptProductsSoldPerPackage", avgNumOfOptProductsSoldPerPackage);

        //fifth query
        List<InsolventUsers> insolventUsers = employeeService.findAllInsolventUsers();
        req.setAttribute("insolventUsers", insolventUsers);
        List<SuspendedOrders> suspendedOrders = employeeService.findAllSuspendedOrders();
        req.setAttribute("suspendedOrders", suspendedOrders);
        List<Alerts> alerts = employeeService.findAllAlerts();
        req.setAttribute("alerts", alerts);

        //sixth query
        SalesPerOptProduct salesPerOptProduct = employeeService.findMax();
        req.setAttribute("salesPerOptProduct", salesPerOptProduct);

        RequestDispatcher dispatcher = req.getRequestDispatcher("salesReportPage.jsp");
        dispatcher.forward(req, resp);
    }
}
