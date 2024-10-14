package com.devops.grababargain.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotNull(message = "Campo Nome não pode ser vazio.")
    @Size(message = "O Nome deve ter entre 2 e 50 caracteres", min = 2, max = 50)
    private String name;

    @Email(message = "Formato de e-mail inválido")
    private String email;

    @Past
    @NotNull(message = "Campo Data de Nascimento é obrigatório.")
    private LocalDate birthDate;

    private String phone;
}
