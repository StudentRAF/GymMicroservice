package rs.raf.gym.dto.appointment_status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentStatusCreateDto {

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String name;

}
