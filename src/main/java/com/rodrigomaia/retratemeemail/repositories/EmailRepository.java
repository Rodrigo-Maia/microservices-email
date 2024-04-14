package com.rodrigomaia.retratemeemail.repositories;

import com.rodrigomaia.retratemeemail.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {


}
