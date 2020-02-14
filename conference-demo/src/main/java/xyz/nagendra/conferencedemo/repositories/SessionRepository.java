package xyz.nagendra.conferencedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nagendra.conferencedemo.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
