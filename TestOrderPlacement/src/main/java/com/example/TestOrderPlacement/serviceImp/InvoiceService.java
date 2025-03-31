package com.example.TestOrderPlacement.serviceImp;

import com.example.TestOrderPlacement.entity.CustomerOrder;
import com.example.TestOrderPlacement.entity.ProductInventory;
import com.example.TestOrderPlacement.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static com.netflix.spectator.api.Statistic.totalAmount;


@Service
public class InvoiceService {

    @Autowired
    private OrderRepo orderRepo;

    public void generateInvoice(CustomerOrder customerOrder) throws IOException {

        System.out.println(customerOrder);
        if (customerOrder == null) {
            System.out.println("Customer order is null!");
            return;
        }

        String fileName = "order-invoice-" + customerOrder.getOrderNumber() + ".txt";
        File invoiceDirectory = new File("C:\\Users\\abhin\\OneDrive\\Desktop\\TestResult\\invoices");
        File invoiceFile = new File(invoiceDirectory, fileName);

        if (!invoiceDirectory.exists()) {
            boolean dirCreated = invoiceDirectory.mkdirs();
            if (dirCreated) {
                System.out.println("Invoice directory created at: " + invoiceDirectory.getAbsolutePath());
            } else {
                System.out.println("Failed to create invoice directory.");
                return;
            }
        }

        System.out.println("Order Number: " + customerOrder.getOrderNumber());
        System.out.println("Order Date: " + customerOrder.getOrderDate());
        System.out.println("Order Status: " + customerOrder.getOrderStatus());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(invoiceFile))) {
            writer.write("Order Invoice\n");
            writer.write("------------------------------------------------------------\n");
            writer.write("Order Number: " + customerOrder.getOrderNumber() + "\n");
            writer.write("Order Date: " + customerOrder.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) + "\n");
            writer.write("Order Status: " + customerOrder.getOrderStatus() + "\n");
            writer.write("Shipping Address: " + customerOrder.getShippingAddress() + "\n");
            writer.write("User ID: " + customerOrder.getUserId() + "\n");
            writer.write("Total Amount: $" + customerOrder.getTotalAmount() + "\n");
            writer.write("------------------------------------------------------------\n");
            writer.write("------------------------------------------------------------\n");
            writer.flush();
            System.out.println("Invoice generated successfully for Order ID: " + customerOrder.getOrderNumber());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

}

