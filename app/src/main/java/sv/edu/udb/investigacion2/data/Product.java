package sv.edu.udb.investigacion2.data;

public class Product {
    private String code;
    private String description;
    private Double price;
    String key;

    public Product(){}

    public Product(String code, String description, Double price){
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }
}
