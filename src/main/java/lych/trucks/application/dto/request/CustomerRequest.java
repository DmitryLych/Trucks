package lych.trucks.application.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lych.trucks.domain.model.Customer;

import java.io.Serializable;
import java.util.List;

/**
 * Dto for {@link Customer} request.
 */
@Data
@JsonTypeName(value = "customers")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NONE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequest implements Serializable {

    private static final long serialVersionUID = -9050131867256691474L;

    @JsonProperty
    private Integer customerId;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String address;

    @JsonProperty
    private String companyName;

    @JsonProperty
    private String email;

    @JsonProperty
    private String companyTelephoneNumber;

    @JsonProperty
    private String mobileTelephoneNumber;

    @JsonProperty
    private List<OrderRequest> orders;
}
