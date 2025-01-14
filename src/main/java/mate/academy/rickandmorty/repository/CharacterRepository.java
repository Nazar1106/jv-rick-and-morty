package mate.academy.rickandmorty.repository;

import java.util.List;
import mate.academy.rickandmorty.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    List<Character> findAllByName(String name);
}
