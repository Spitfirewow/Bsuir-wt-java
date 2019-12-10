package entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "coffeeData")
public class CoffeeData {
    @JacksonXmlElementWrapper(localName = "Extras")
    @JacksonXmlProperty(localName = "extra")
    private List<Extra> extras;

    @JacksonXmlElementWrapper(localName = "Coffees")
    @JacksonXmlProperty(localName = "coffee")
    private List<Coffee> coffees;

    @JacksonXmlElementWrapper(localName = "Toppings")
    @JacksonXmlProperty(localName = "topping")
    private List<Topping> toppings;

    @JacksonXmlElementWrapper(localName = "CoffeeToppings")
    @JacksonXmlProperty(localName = "coffeeTopping")
    private List<CoffeeTopping> coffeeToppings;

    @JacksonXmlElementWrapper(localName = "Orders")
    @JacksonXmlProperty(localName = "order")
    private List<Order> orders;

    @JacksonXmlElementWrapper(localName = "OrderedCoffees")
    @JacksonXmlProperty(localName = "orderedCoffee")
    private List<OrderedCoffee> orderedCoffees;

    @JacksonXmlElementWrapper(localName = "OrderedExtras")
    @JacksonXmlProperty(localName = "orderedExtra")
    private List<OrderedExtra> orderedExtras;

    @JacksonXmlElementWrapper(localName = "Users")
    @JacksonXmlProperty(localName = "user")
    private List<User> users;
}
