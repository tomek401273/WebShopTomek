package com.tgrajkowski.model.product.reminder;

import com.tgrajkowski.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductEmailReminder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "REMINDER_ID", unique = true)
    private Long id;

    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "JOINING_PRODUCT_REMINDER",
            joinColumns = {@JoinColumn (name = "PRODUCTEMAILREMINDER_ID")} ,
            inverseJoinColumns = {@JoinColumn (name = "PRODUCT_ID")}
    )
    private List<Product> products = new ArrayList<>();

    public ProductEmailReminder(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEmailReminder)) return false;

        ProductEmailReminder reminder = (ProductEmailReminder) o;

        if (id != null ? !id.equals(reminder.id) : reminder.id != null) return false;
        if (email != null ? !email.equals(reminder.email) : reminder.email != null) return false;
        return products != null ? products.equals(reminder.products) : reminder.products == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (products != null ? products.hashCode() : 0);
        return result;
    }
}
