package se.lexicon.shipping_cost.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Box {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false,unique = true, length = 100)
    @NotNull(message = "name should not be null")
    @Size(min = 4, max = 20, message = "Name length should be between 4 and 20")
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "country should not be null")
    private String country; // Enum

    @NotBlank(message = "type should not be null")
    private String type; // Enum

    @Column(nullable = false)
    private double cost;

    @Column(nullable = false)
    @Min(value = 1, message = "weight should not be less than 1")
    @Max(value = 900, message = "weight should not be grater than 900")
    private double weight;

    @Column(nullable = false)
    @NotBlank(message = "name should not be null")
    private String weightType;

    @CreationTimestamp // set default date from database
    @Column(nullable = false, columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean status;


    public Box(String name, String country, String type, double weight) {
        this.name = name;
        this.country = country;
        this.type = type;
        this.weight = weight;
    }

    public double calcShippingCost() {
        double total = 0.0;
        double countryFee;
        double feePerKOrKG;
        if (weightType.equalsIgnoreCase("KG")) {
            feePerKOrKG = 1000; // per kilo gram
        } else {
            feePerKOrKG = 2; // per gram
        }
        if (country.equalsIgnoreCase("sweden")) {
            countryFee = 2.5;
        } else {
            countryFee = 7;
        }
        System.out.println("type = " + type);
        System.out.println("country = " + country);

        total = (weight * feePerKOrKG) * countryFee;

        return total;
    }
}
