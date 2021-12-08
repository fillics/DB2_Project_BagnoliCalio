package it.polimi.db2project.web;


import it.polimi.db2project.entities.*;
import it.polimi.db2project.entities.employeeQueries.TotalPurchasesPerPackageAndValPeriodEntity;
import it.polimi.db2project.entities.employeeQueries.TotalPurchasesPerPackageEntity;
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
    private List<OptionalProductEntity> optionalProducts = null;
    private List<OrderEntity> orders = null;
    private int avgNumOptProductsWithServPackage;
    private TotalPurchasesPerPackageEntity totPurchaseXPackage;
    private TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriod;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //first query
        String srvPackage = request.getParameter("srvPackage");

        //second query
        String srvPackageWithValPeriod = request.getParameter("srvPackageWithValPeriod");
        String valPeriod = request.getParameter("valPeriod");



        //third query
        String srvPackageWithOptProducts = request.getParameter("srvPackageWithOptProducts");
        String avgNumOptProductsWithServPackage = request.getParameter("avgNumOptProductsWithServPackage");

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



        if(srvPackageWithOptProducts!=null){
            optionalProducts = employeeService.findOptProdOfServicePackageToSelect(Long.parseLong(srvPackageWithOptProducts));
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


        req.setAttribute("optionalProducts", optionalProducts);
        req.setAttribute("avgNumOptProductsWithServPackage", avgNumOptProductsWithServPackage);
        req.setAttribute("orders", orders);

        RequestDispatcher dispatcher = req.getRequestDispatcher("salesReportPage.jsp");
        dispatcher.forward(req, resp);
    }
}
