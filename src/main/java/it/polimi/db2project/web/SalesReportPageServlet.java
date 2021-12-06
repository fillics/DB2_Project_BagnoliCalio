package it.polimi.db2project.web;


import it.polimi.db2project.entities.*;
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


    private List<ValidityPeriodEntity> validityPeriods = null;
    private List<OptionalProductEntity> optionalProducts = null;
    private List<OrderEntity> orders = null;
    private int avgNumOptProductsWithServPackage;
    TotalPurchasesPerPackageEntity totPurchaseXPackage;
    TotalPurchasesPerPackageAndValPeriodEntity totalPurchasesPerPackageAndValPeriod;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String srvPackage = request.getParameter("srvPackage");
        String srvPackageWithValPeriod = request.getParameter("srvPackageWithValPeriod");
        String valPeriod = request.getParameter("valPeriod");

        String srvPackageWithOptProducts = request.getParameter("srvPackageWithOptProducts");
        String avgNumOptProductsWithServPackage = request.getParameter("avgNumOptProductsWithServPackage");

        Optional<ServicePackageToSelectEntity> servicePackageToSelect = null;
        String destServlet = "salesReportPage";


        //first query
        if(srvPackage!=null)
        totPurchaseXPackage = employeeService.purchasesPerPackage(Long.parseLong(srvPackage));

        if(valPeriod!=null){
            totalPurchasesPerPackageAndValPeriod = employeeService.purchasesPerPackageAndValPeriod(Long.parseLong(srvPackageWithValPeriod), Long.parseLong(valPeriod));
            System.out.println(totalPurchasesPerPackageAndValPeriod);
        }

        if(srvPackageWithValPeriod!=null){
            validityPeriods = employeeService.findValPeriodsOfServicePackage(Long.parseLong(srvPackageWithValPeriod));
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

        req.setAttribute("totPurchaseXPackage", totPurchaseXPackage);


        req.setAttribute("validityPeriods", validityPeriods);
        req.setAttribute("optionalProducts", optionalProducts);
        req.setAttribute("avgNumOptProductsWithServPackage", avgNumOptProductsWithServPackage);
        req.setAttribute("orders", orders);

        RequestDispatcher dispatcher = req.getRequestDispatcher("salesReportPage.jsp");
        dispatcher.forward(req, resp);
    }
}
