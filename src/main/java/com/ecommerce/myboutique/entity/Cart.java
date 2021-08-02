package com.ecommerce.myboutique.entity;

import com.ecommerce.myboutique.entity.enumeration.CartStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @OneToOne
    @JoinColumn(unique = true)
    private Order order;

    @ManyToOne
    private Customer customer;
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart(Customer customer) {
        this.customer = customer;
        this.status = CartStatus.NEW;
    }

    public Cart(Customer customer, @NotNull CartStatus status) {
        this.customer = customer;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(customer, cart.customer) &&
                status == cart.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, status);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customer=" + customer +
                ", status=" + status +
                '}';
    }
}
