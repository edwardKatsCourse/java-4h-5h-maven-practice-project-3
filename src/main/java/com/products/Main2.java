package com.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

public class Main2 {

    private static final String BASE_PATH = "products";

    private static final List<Product> products = new ArrayList<Product>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static void main(String[] args) {
        File basePathDirectory = new File(BASE_PATH);
        if (!basePathDirectory.exists()) {
            basePathDirectory.mkdir();
        }

        Collection<File> files = FileUtils.listFiles(basePathDirectory, new String[]{"json"}, false);
        files.stream()
                .map(file -> readFile(file))
                .map(fileContent -> getProductFromJson(fileContent))
                .forEach(x -> products.add(x));

        products.stream().filter(x -> x.getStatus() == Status.AVAILABLE).forEach(x -> System.out.println(x));


        System.out.println("Enter your product's name:");
        String name = new Scanner(System.in).nextLine();

        System.out.println("Enter you product's price:");
        Double price = new Scanner(System.in).nextDouble();

        Product product_1 = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .price(price)
                .status(Status.AVAILABLE)
                .build();

//        Product product_2 = Product.builder()
//                .id(UUID.randomUUID().toString())
//                .name("product 1")
//                .price(10.0)
//                .status(Status.AVAILABLE)
//                .build();

        //products/UUID.json
        objectMapper.writeValue(new File(basePathDirectory, product_1.getId() + ".json"), product_1);
//        objectMapper.writeValue(new File(basePathDirectory, product_2.getId() + ".json"), product_2);




    }

    @SneakyThrows
    private static Product getProductFromJson(String json) {
        return objectMapper.readValue(json, Product.class);
    }

    @SneakyThrows
    private static String readFile(File file) {
        return FileUtils.readFileToString(file, Charset.defaultCharset());
    }
}
