package com.cajuzeiro.payment.ports.dto.input;

import com.cajuzeiro.payment.domain.entity.Account;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class AccountDTO  {
        @Id
        private Long id;

        private String username;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        public Account toAccount() {
            return new Account(this.getId(), this.getUsername(), this.getCreatedAt(), this.getUpdatedAt());
        }

        public Long getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public LocalDateTime getUpdatedAt() {
            return updatedAt;
        }

}
