package lych.trucks.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NonNull;
import lych.trucks.domain.model.InsurancePolicy;

import java.io.Serializable;

/**
 * Dto for {@link InsurancePolicy} request.
 */
@Data
@JsonTypeName(value = "insurancePolicy")
public class InsurancePolicyRequest implements Serializable {

    private static final long serialVersionUID = 807516511206616097L;

    @JsonProperty
    private Integer id;

    @JsonProperty
    @NonNull
    private Long validate;

    @JsonProperty
    @NonNull
    private String type;

    @JsonProperty
    @NonNull
    private Double cost;

    @JsonProperty
    private DriverRequest driver;
}
