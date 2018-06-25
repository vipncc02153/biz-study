import java.math.BigDecimal;
import java.math.BigInteger;

public class RentDTO {
    private Integer id;
    private String address;
    private BigDecimal price;
    private String number_plate;
    private String company;

    public RentDTO(Integer id, String address, BigDecimal price, String number_plate, String company) {
        this.id = id;
        this.address = address;
        this.price = price;
        this.number_plate = number_plate;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "RentDTO{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", number_plate='" + number_plate + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
