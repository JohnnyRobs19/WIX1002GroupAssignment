import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Q6 {
    public static void main(String[] args) {
        // Define file paths
        String productFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\LAB7\\src\\main\\java\\product.txt";
        String orderFilePath = "C:\\Users\\HP\\Documents\\WIIX1002GROUPRAGA\\LAB7\\src\\main\\java\\order.txt";

        // Read data from product.txt and store it in a map
        Map<String, Product> productMap = readProductFile(productFilePath);

        // Read data from order.txt and display the merged information
        readOrderFile(orderFilePath, productMap);
    }

    private static Map<String, Product> readProductFile(String filePath) {
        Map<String, Product> productMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String productId = parts[0].trim();
                String productName = parts[1].trim();
                double productPrice = Double.parseDouble(parts[2].trim());

                Product product = new Product(productId, productName, productPrice);
                productMap.put(productId, product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productMap;
    }

    private static void readOrderFile(String filePath, Map<String, Product> productMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String orderId = parts[0].trim();
                String productId = parts[1].trim();
                int orderQuantity = Integer.parseInt(parts[2].trim());

                if (productMap.containsKey(productId)) {
                    Product product = productMap.get(productId);
                    double pricePerUnit = product.getProductPrice();
                    double totalPrice = pricePerUnit * orderQuantity;

                    // Display the merged information
                    System.out.println("OrderID: " + orderId);
                    System.out.println("ProductID: " + productId);
                    System.out.println("ProductName: " + product.getProductName());
                    System.out.println("OrderQuantity: " + orderQuantity);
                    System.out.println("PricePerUnit: " + pricePerUnit);
                    System.out.println("TotalPrice: " + totalPrice);
                    System.out.println();
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Product class to store product information
    private static class Product {
        private String productId;
        private String productName;
        private double productPrice;

        public Product(String productId, String productName, double productPrice) {
            this.productId = productId;
            this.productName = productName;
            this.productPrice = productPrice;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public double getProductPrice() {
            return productPrice;
        }
    }
}
