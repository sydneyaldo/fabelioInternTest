package com.fabelio.ProjectDemo.Controller;

import com.fabelio.ProjectDemo.Models.Dimension;
import com.fabelio.ProjectDemo.Models.Product;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.*;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {
    private MongoCollection mongoCollection;

    private boolean getCollection() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mongoDatabase;
        try {
            mongoDatabase = mongoClient.getDatabase("fabelio");
            mongoCollection = mongoDatabase.getCollection("products");
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList <Product> fetchProducts() {
        ArrayList <Product> products = new ArrayList<>();
        if(getCollection()) {
            ObjectMapper objectMapper = new ObjectMapper();

            FindIterable <Document> fi = mongoCollection.find();
            MongoCursor <Document> cursor = fi.iterator();
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                while(cursor.hasNext()) {
                        String json = cursor.next().toJson();
                        Product product = mapper.readValue(json, Product.class);
                        products.add(product);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                cursor.close();
            }
        }
        return products;
    }

    public boolean seedProducts() {
        getCollection();
        if(mongoCollection.countDocuments() != 0) return false;

        Integer[] id = {1,2,3,4,5,6,7,8,9,10};
        String[] name = {"Sofa 2 dudukan Vienna", "Sofa Tempat Tidur Mochi", "Sofa 2 dudukan Zelado", "Sofa 2 dudukan Toril",
                "Sofa Tempat Tidur Deacon", "Sofa Java", "Sofa 1 dudukan Hughes", "Sofa 1 dudukan Taby", "Sofa 1 dudukan Zoey", "Sofa 1 dudukan Vienna"};
        BigDecimal[] price = {new BigDecimal(3899000), new BigDecimal(3500000), new BigDecimal(4299000),
                new BigDecimal(2899000), new BigDecimal(3299000), new BigDecimal(3869100), new BigDecimal(2500000),
                new BigDecimal(2399000), new BigDecimal(2399000), new BigDecimal(2199000)};
        Dimension[] dimension = {new Dimension(162, 95, 86), new Dimension(160, 95, 90), new Dimension(162,95,86),
                new Dimension(160, 95,90), new Dimension(150, 90, 80), new Dimension(142, 90, 80),
                new Dimension(90, 82, 58), new Dimension(90, 82, 58), new Dimension(90,82,58),
                new Dimension(90, 82, 58)};
        String[][] colors = {{"custard vienna", "graphite vienna", "ruby vienna"}, {"custard vienna", "graphite vienna", "ruby vienna"}, {"graphite vienna, teal vienna"},
                {"blue jay, ruby vienna"}, {"custard vienna, graphite vienna"}, {"custard vienna, graphite vienna"}, {"custard vienna, graphite vienna, ruby vienna"},
                {"brown vienna, ruby vienna"}, {"brown vienna, ruby vienna"}, {"custard vienna, graphite vienna, ruby vienna"}};
        String[] material = {"solid wood", "solid wood", "hollow steel", "solid wood", "hollow steel", "solid wood", "solid wood", "solid wood", "hollow steel", "solid wood"};
        Integer[] stock = {0,2,4,1,2,2,5,6,7,6};
        String[] path = {"https://fabelio.com/media/catalog/product/w/i/wina_2_seater_sofa__custard__1_1.jpg", "https://fabelio.com/media/catalog/product/r/2/r2710.jpg",
                "https://fabelio.com/media/catalog/product/z/e/zelado-2-seater-sofa---custard-01.jpg", "https://fabelio.com/media/catalog/product/t/o/Toril_2_Seater_Sofa_(Paradise)_1.jpg",
                "https://fabelio.com/media/catalog/product/d/e/deacon_white_1_1_1.jpg", "https://fabelio.com/media/catalog/product/t/a/Taby_Java_2_Seater_Living_Set_(Sugar)_1.jpg",
                "https://fabelio.com/media/catalog/product/h/u/Hughes_Armchair_(Wood)_0.jpg", "https://fabelio.com/media/catalog/product/t/a/Taby_Armchair_(Jezebel)_1.jpg",
                "https://fabelio.com/media/catalog/product/k/u/Kursi_Zoey_Armchair_(Brown)_0.jpg", "https://fabelio.com/media/catalog/product/w/i/wina_armchair__graphite__1_1.jpg"};

        List<WriteModel<Document>> writes = new ArrayList<>(); // (1)

        for(int a1 = 0; a1 < id.length; a1++) {
            Product product = new Product(id[a1], name[a1], price[a1], dimension[a1], colors[a1], material[a1], stock[a1], path[a1]);
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(product);
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            finally {
                Document document = Document.parse(jsonString);
                mongoCollection.insertOne(document);
            }
        }

        return true;
    }
}
