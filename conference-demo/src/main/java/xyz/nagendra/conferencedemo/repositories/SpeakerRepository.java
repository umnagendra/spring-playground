package xyz.nagendra.conferencedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.nagendra.conferencedemo.models.Speaker;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
