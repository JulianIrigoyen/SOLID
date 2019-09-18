package com.plottwist.solid;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

enum Color {RED, GREEN, BLUE}

enum Size {SMALL, MEDIUM, LARGE, HUGE}

/**
 * Generic interface with a single method
 * which determines if a particular product satisfies a criteria
 *
 * @param <T>
 */
interface Specification<T> {
    boolean isSatisfied(T item);
}

/**
 * The filter interface can actually filter anything
 * Takes a list of the item T to filter and
 * a specification of T which we want to satisfy
 */
interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

/**
 * Open Closed SOLID Principle and Specification DP Demo
 * Open for extension, closed to modification after it has been tested
 */
public class OpenClosed {
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

/**
 * Allow users to filter products by color, then size, then both...
 * This violates the O-C Principle by requiring us to rewrite
 * a class that has already been made.
 * This can be solved by implementing the Specification Design Pattern
 */
class ProductFilter {
    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    //This is not bad for 1, 2 or 3 criteria.. but what if you want more?
    public Stream<Product> filterByColorAndSize(List<Product> products, Color color, Size size) {
        return products.stream().filter(p -> p.size == size && p.color == color);
    }
}

class ColorSpecification implements Specification<Product> {
    private Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    /**
     * Check if the incoming items color
     * matches the color we are looking for
     */
    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }
}

class SizeSpecification implements Specification<Product> {
    private Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size == size;
    }
}

class BetterFilter implements Filter<Product> {
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        //Just make sure that our Specification is actually satisfied by the products.
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

class AndSpecification<T> implements Specification<T> {
    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    /**
     * We need to make sure that both the first and second
     * specifications are satisfied
     *
     * @param item
     * @return
     */
    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

class DemoII {
    public static void main(String[] args) {
        Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
        Product house = new Product("House", Color.BLUE, Size.LARGE);

        List<Product> productList = new ArrayList<>();
        productList.add(apple);
        productList.add(tree);
        productList.add(house);

        ProductFilter filter = new ProductFilter();
        System.out.println("Green products (old): ");
        filter.filterByColor(productList, Color.GREEN).forEach(p -> System.out.println(" - " + p.name + " is green"));

        BetterFilter betterFilter = new BetterFilter();
        //Single criteria example
        System.out.println("Green products (new): ");
        betterFilter.filter(productList, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(" - " + p.name + " is green"));

        //2+ Specification using a Combinator
        System.out.println("Large blue itmes: ");
        betterFilter.filter(productList,
                new AndSpecification<>(
                        new ColorSpecification(Color.BLUE),
                        new SizeSpecification(Size.LARGE)
                ))
                .forEach(p -> System.out.println(" - " + p.name + " is large and blue"));

    }
}
