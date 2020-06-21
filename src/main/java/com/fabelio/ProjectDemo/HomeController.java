package com.fabelio.ProjectDemo;

import com.fabelio.ProjectDemo.Controller.DatabaseController;
import com.fabelio.ProjectDemo.Models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Controller
public class HomeController {
    ArrayList<Product> activeProducts;
    ArrayList<Product> inactiveProducts;

    @RequestMapping("/")
    public String index() {
        DatabaseController controller = new DatabaseController();
        System.out.print(controller.seedProducts());
        activeProducts = controller.fetchProducts();
        inactiveProducts = new ArrayList<>(activeProducts);

        return "redirect:home/1";
    }

    @RequestMapping("/home/{id}")
    public String index(@PathVariable("id") Integer id, Model model) {
        if(id == null) id = 1;

        Product product = null;

        product = getProduct(id, activeProducts);
        activeProducts.remove(product);


        Collections.sort(activeProducts, new CustomComparator(product));
        if(activeProducts.isEmpty()) activeProducts = new ArrayList<>(inactiveProducts);
        System.out.println(activeProducts.size());

        model.addAttribute("product", product);
        model.addAttribute("similarProduct", activeProducts.get(0));

        return "Home";
    }

    private Product getProduct(Integer id, ArrayList<Product> product) {
        for (Product p: activeProducts
        ) {
            if(p.getId().equals(id)) return p;
        }
        return null;
    }

    public class CustomComparator implements Comparator<Product> {
        private Product product;
        @Override
        public int compare(Product o1, Product o2) {
            Integer[] dim = product.getDimension().toArray();
            Integer[] dim1 = o1.getDimension().toArray();
            Integer[] dim2 = o2.getDimension().toArray();
            Integer diff1 = 0;
            Integer diff2 = 0;

            for(int a1 = 0; a1 < dim1.length; a1++) {
                diff1 += Math.abs(dim1[a1] - dim[a1]);
                diff2 += Math.abs(dim2[a1] - dim[a1]);
            }
            if(diff1 - diff2 != 0) { //different dimension
                return diff1.compareTo(diff2);
            }
            if(o1.getMaterial().equals(product.getMaterial()) ^ o2.getMaterial().equals(product.getMaterial())) { //different materials and one fits
                if(o1.getMaterial().equals(product.getMaterial())) return -1;
                else return 1;
            }

            BigDecimal priceDiff1 = product.getPrice().subtract(o1.getPrice());
            BigDecimal priceDiff2 = product.getPrice().subtract(o2.getPrice());

            if(!priceDiff1.subtract(priceDiff2).equals(new BigDecimal(0))) { //different price
                return priceDiff1.compareTo(priceDiff2);
            }

            return 0;
        }

        public CustomComparator(Product product) {
            this.product = product;
        }
    }
}
