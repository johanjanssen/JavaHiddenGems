package com.example;

import com.example.Address.AddressId;
import lombok.Getter;
import lombok.Value;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

@Getter
public class Address implements Entity<Student, AddressId> {

    private Address.AddressId id;
    private final String zipCode;

    public Address(String zipCode) {
        this.id = AddressId.of(UUID.randomUUID());
        this.zipCode = zipCode;
    }

    @Value(staticConstructor = "of")
    public static class AddressId implements Identifier {
        private final UUID id;
    }
}