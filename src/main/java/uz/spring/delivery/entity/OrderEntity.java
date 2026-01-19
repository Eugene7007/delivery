package uz.spring.delivery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import uz.spring.delivery.constant.enums.Status;


/**
 * Represents an order entity in the system which is backed by the "orders" table in the database.
 * Extends the {@code BaseEntity} to inherit common auditing properties such as `createdAt`,
 * `updatedAt`, and `isActive`.
 *
 * The `OrderEntity` maintains details associated with an order including its status, geolocation
 * information, description, and associated merchant details.
 *
 * Supported features:
 * - Entity relationship mapping with the `MerchantEntity` to associate orders with merchants.
 * - Named entity graph `Order.withMerchant` to fetch order details along with its associated merchant.
 * - Fields are encapsulated with private visibility and manipulated through getter and setter methods.
 *
 * This class uses various annotations for persistence and functionality:
 * - `Entity`: Marks this class as a JPA entity.
 * - `Table`: Defines the table name (`orders`) for this entity in the database.
 * - `NamedEntityGraph`: Configures an entity graph for specific fetch strategies with the attribute `merchant`.
 * - `Getter`, `Setter`: Automatically generates getter and setter methods for all fields.
 * - `SuperBuilder`: Enables building instances of the entity in a more concise way.
 * - Other Lombok and persistence annotations for clean and efficient code.
 */
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraph(
        name = "Order.withMerchant",
        attributeNodes = @NamedAttributeNode("merchant")
)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    Status status;

    double latitudeFrom;
    double longitudeFrom;

    double latitudeTo;
    double longitudeTo;

    String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "merchant_id")
    MerchantEntity merchant;
}
