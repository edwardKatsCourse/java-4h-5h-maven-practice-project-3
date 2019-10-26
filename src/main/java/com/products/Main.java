package com.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        System.out.println("Enter product name: ");
        String name = new Scanner(System.in).nextLine();

        System.out.println("Enter product price: ");
        Double price = new Scanner(System.in).nextDouble();

        Status status = Status.AVAILABLE;

        Product product = Product.builder()
                .name(name)
                .price(price)
                .status(status)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //objectMapper.writeValue(new File("products.json"), product);
        String json = objectMapper.writeValueAsString(product);

        FileUtils.write(new File("products.json"), json, Charset.defaultCharset());

        String jsonFromFile = FileUtils.readFileToString(new File("products.json"), "UTF-8");
        //ctrl + alt + v
        Product productFromJson = objectMapper.readValue(jsonFromFile, Product.class);
        System.out.println(productFromJson);

    }
}
